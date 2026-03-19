/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kimocoder.avfterminal

import android.crosvm.ICrosvmAndroidDisplayService
import android.graphics.PixelFormat
import android.os.ParcelFileDescriptor
import android.os.RemoteException
import android.system.virtualizationservice_internal.IVirtualizationServiceInternal
import android.util.Log
import android.view.SurfaceControl
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.kimocoder.avfterminal.MainActivity.Companion.TAG
import java.io.IOException
import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.nio.ByteOrder

/** Provides Android-side surface from given SurfaceView to a VM instance as a display for that */
internal class DisplayProvider(
    private val mainView: SurfaceView,
    private val cursorView: SurfaceView,
) {
    private val virtService: IVirtualizationServiceInternal by lazy {
        val b = PlatformCompat.waitForService("android.system.virtualizationservice")
        IVirtualizationServiceInternal.Stub.asInterface(b)
    }
    private var cursorHandler: CursorHandler? = null

    init {
        PlatformCompat.setSurfaceLifecycle(mainView, PlatformCompat.getSurfaceLifecycleFollowsAttachment())
        mainView.holder.addCallback(Callback(SurfaceKind.MAIN))
        PlatformCompat.setSurfaceLifecycle(cursorView, PlatformCompat.getSurfaceLifecycleFollowsAttachment())
        cursorView.holder.addCallback(Callback(SurfaceKind.CURSOR))
        cursorView.holder.setFormat(PixelFormat.RGBA_8888)
        cursorView.setZOrderMediaOverlay(true)
    }

    fun notifyDisplayIsGoingToInvisible() {
        try {
            getDisplayService().saveFrameForSurface(false)
        } catch (e: RemoteException) {
            throw RuntimeException("Failed to save frame for the main surface", e)
        }
    }

    @Synchronized
    private fun getDisplayService(): ICrosvmAndroidDisplayService {
        try {
            val b = virtService.waitDisplayService()
            return ICrosvmAndroidDisplayService.Stub.asInterface(b)
        } catch (e: Exception) {
            throw RuntimeException("Error while getting display service", e)
        }
    }

    enum class SurfaceKind {
        MAIN,
        CURSOR,
    }

    inner class Callback(private val surfaceKind: SurfaceKind) : SurfaceHolder.Callback {
        fun isForCursor(): Boolean {
            return surfaceKind == SurfaceKind.CURSOR
        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                getDisplayService().setSurface(holder.getSurface(), isForCursor())
            } catch (e: Exception) {
                // TODO: don't consume this exception silently. For some unknown reason, setSurface
                // call above throws IllegalArgumentException and that fails the surface
                // configuration.
                Log.e(TAG, "Failed to present surface $surfaceKind to VM", e)
            }
            try {
                when (surfaceKind) {
                    SurfaceKind.MAIN -> getDisplayService().drawSavedFrameForSurface(isForCursor())
                    SurfaceKind.CURSOR -> {
                        val stream = createNewCursorStream()
                        getDisplayService().setCursorStream(stream)
                    }
                }
            } catch (e: Exception) {
                // TODO: don't consume exceptions here too
                Log.e(TAG, "Failed to configure surface $surfaceKind", e)
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // TODO: support resizeable display.
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            try {
                getDisplayService().removeSurface(isForCursor())
            } catch (e: RemoteException) {
                throw RuntimeException("Error while destroying surface for $surfaceKind", e)
            }
        }
    }

    private fun createNewCursorStream(): ParcelFileDescriptor? {
        cursorHandler?.interrupt()
        var pfds: Array<ParcelFileDescriptor> =
            try {
                ParcelFileDescriptor.createSocketPair()
            } catch (e: IOException) {
                throw RuntimeException("Failed to create socketpair for cursor stream", e)
            }
        cursorHandler = CursorHandler(pfds[0]).also { it.start() }
        return pfds[1]
    }

    /**
     * Thread reading cursor coordinate from a stream, and updating the position of the cursor
     * surface accordingly.
     */
    private inner class CursorHandler(private val stream: ParcelFileDescriptor) : Thread() {
        private val cursor: SurfaceControl = this@DisplayProvider.cursorView.surfaceControl
        private val transaction: SurfaceControl.Transaction = SurfaceControl.Transaction()

        init {
            val main = this@DisplayProvider.mainView.surfaceControl
            transaction.reparent(cursor, main).apply()
        }

        override fun run() {
            try {
                val byteBuffer = ByteBuffer.allocate(8 /* (x: u32, y: u32) */)
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
                while (true) {
                    if (interrupted()) {
                        Log.d(TAG, "CursorHandler thread interrupted!")
                        return
                    }
                    byteBuffer.clear()
                    val bytes =
                        libcore.io.IoBridge.read(
                            stream.fileDescriptor,
                            byteBuffer.array(),
                            0,
                            byteBuffer.array().size,
                        )
                    if (bytes == -1) {
                        Log.e(TAG, "cannot read from cursor stream, stop the handler")
                        return
                    }
                    val x = (byteBuffer.getInt() and -0x1).toFloat()
                    val y = (byteBuffer.getInt() and -0x1).toFloat()
                    transaction.setPosition(cursor, x, y).apply()
                }
            } catch (e: IOException) {
                Log.e(TAG, "failed to run CursorHandler", e)
            }
        }
    }
}
