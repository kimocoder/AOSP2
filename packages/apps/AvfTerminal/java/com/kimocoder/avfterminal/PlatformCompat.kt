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

import android.content.Context
import android.os.Build
import android.os.IBinder
import android.view.SurfaceView
import java.io.File

/**
 * Compatibility shims for hidden platform APIs.
 * These use reflection to call hidden methods at runtime on devices that support them,
 * with fallbacks for standalone builds.
 */
internal object PlatformCompat {

    /** android.os.ServiceManager.waitForService(name) */
    fun waitForService(name: String): IBinder? {
        return try {
            val clazz = Class.forName("android.os.ServiceManager")
            val method = clazz.getMethod("waitForService", String::class.java)
            method.invoke(null, name) as? IBinder
        } catch (e: Exception) {
            null
        }
    }

    /** android.os.SystemProperties.get(key) */
    fun getSystemProperty(key: String, default: String = ""): String {
        return try {
            val clazz = Class.forName("android.os.SystemProperties")
            val method = clazz.getMethod("get", String::class.java, String::class.java)
            method.invoke(null, key, default) as? String ?: default
        } catch (e: Exception) {
            default
        }
    }

    /** android.os.Build.isDebuggable() */
    fun isDebuggable(): Boolean {
        return try {
            val clazz = Class.forName("android.os.Build")
            val method = clazz.getMethod("isDebuggable")
            method.invoke(null) as? Boolean ?: (Build.TYPE == "userdebug" || Build.TYPE == "eng")
        } catch (e: Exception) {
            Build.TYPE == "userdebug" || Build.TYPE == "eng"
        }
    }

    /** android.os.Build.VERSION.SDK_INT_FULL - hidden field */
    fun getSdkIntFull(): Int {
        return try {
            val field = Build.VERSION::class.java.getField("SDK_INT_FULL")
            field.getInt(null)
        } catch (e: Exception) {
            Build.VERSION.SDK_INT
        }
    }

    /** Context.userId - hidden property */
    fun getUserId(context: Context): Int {
        return try {
            val method = context.javaClass.getMethod("getUserId")
            method.invoke(context) as? Int ?: 0
        } catch (e: Exception) {
            try {
                val userHandle = android.os.Process.myUserHandle()
                val method = userHandle.javaClass.getMethod("getIdentifier")
                method.invoke(userHandle) as? Int ?: 0
            } catch (e2: Exception) {
                0
            }
        }
    }

    /** android.os.FileUtils.parseSize(sizeString) */
    fun parseSize(sizeString: String): Long {
        return try {
            val clazz = Class.forName("android.os.FileUtils")
            val method = clazz.getMethod("parseSize", String::class.java)
            method.invoke(null, sizeString) as Long
        } catch (e: Exception) {
            val s = sizeString.trim().uppercase()
            val multiplier: Long
            val numStr: String
            when {
                s.endsWith("MB") -> { multiplier = 1024L * 1024L; numStr = s.dropLast(2).trim() }
                s.endsWith("GB") -> { multiplier = 1024L * 1024L * 1024L; numStr = s.dropLast(2).trim() }
                s.endsWith("KB") -> { multiplier = 1024L; numStr = s.dropLast(2).trim() }
                else -> { multiplier = 1L; numStr = s }
            }
            numStr.toLong() * multiplier
        }
    }

    /** android.os.FileUtils.deleteContentsAndDir(dir) */
    fun deleteContentsAndDir(dir: File): Boolean {
        return try {
            val clazz = Class.forName("android.os.FileUtils")
            val method = clazz.getMethod("deleteContentsAndDir", File::class.java)
            method.invoke(null, dir) as? Boolean ?: false
        } catch (e: Exception) {
            dir.deleteRecursively()
        }
    }

    /** SurfaceView.setSurfaceLifecycle - API 35 hidden */
    fun setSurfaceLifecycle(view: SurfaceView, lifecycle: Int) {
        try {
            val method = SurfaceView::class.java.getMethod("setSurfaceLifecycle", Int::class.javaPrimitiveType)
            method.invoke(view, lifecycle)
        } catch (e: Exception) {
            // Not available on this API level, ignore
        }
    }

    /** SurfaceView.SURFACE_LIFECYCLE_FOLLOWS_ATTACHMENT constant */
    fun getSurfaceLifecycleFollowsAttachment(): Int {
        return try {
            val field = SurfaceView::class.java.getField("SURFACE_LIFECYCLE_FOLLOWS_ATTACHMENT")
            field.getInt(null)
        } catch (e: Exception) {
            0
        }
    }

    /** PackageManager.getPackageUidAsUser */
    fun getPackageUidAsUser(context: Context, packageName: String, userId: Int): Int {
        return try {
            val pm = context.packageManager
            val method = pm.javaClass.getMethod("getPackageUidAsUser", String::class.java, Int::class.javaPrimitiveType)
            method.invoke(pm, packageName, userId) as Int
        } catch (e: Exception) {
            context.packageManager.getApplicationInfo(packageName, 0).uid
        }
    }
}
