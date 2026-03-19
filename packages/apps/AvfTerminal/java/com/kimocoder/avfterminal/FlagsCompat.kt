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

import android.util.Log

/**
 * Safe wrapper around [com.android.system.virtualmachine.flags.Flags] that gracefully handles
 * the case where the platform class is not available at runtime.
 *
 * When built via Gradle for sideloading (outside the AOSP build system), the Flags class from
 * `avf_aconfig_flags_java` is only present as a compile-time stub. If the target device's
 * `com.android.virt` APEX does not expose this class, a [NoClassDefFoundError] would crash the
 * app — especially fatal inside [androidx.startup.InitializationProvider] where it prevents the
 * app from launching entirely.
 *
 * This wrapper uses **reflection** to access the Flags class at runtime, which is immune to
 * R8/ProGuard inlining — direct class references can be inlined by R8, stripping the try-catch
 * and causing the very crash this wrapper was designed to prevent.
 */
object FlagsCompat {

    private const val TAG = "FlagsCompat"
    private const val FLAGS_CLASS = "com.android.system.virtualmachine.flags.Flags"

    /** Cached results to avoid repeated reflection overhead. */
    @Volatile private var cachedGuiSupport: Boolean? = null
    @Volatile private var cachedStorageBalloon: Boolean? = null

    @JvmStatic
    fun terminalGuiSupport(): Boolean {
        cachedGuiSupport?.let { return it }
        val result = invokeFlagMethod("terminalGuiSupport")
        cachedGuiSupport = result
        return result
    }

    @JvmStatic
    fun terminalStorageBalloon(): Boolean {
        cachedStorageBalloon?.let { return it }
        val result = invokeFlagMethod("terminalStorageBalloon")
        cachedStorageBalloon = result
        return result
    }

    /**
     * Invoke a boolean no-arg static method on the platform Flags class via reflection.
     * Returns false if the class or method is unavailable.
     */
    private fun invokeFlagMethod(methodName: String): Boolean {
        return try {
            val clazz = Class.forName(FLAGS_CLASS)
            val method = clazz.getMethod(methodName)
            method.invoke(null) as? Boolean ?: false
        } catch (e: ClassNotFoundException) {
            Log.w(TAG, "Flags class not available on this device, disabling $methodName")
            false
        } catch (e: NoClassDefFoundError) {
            Log.w(TAG, "Flags class not loadable on this device, disabling $methodName")
            false
        } catch (e: Exception) {
            Log.w(TAG, "Failed to query flag $methodName, defaulting to disabled", e)
            false
        }
    }
}