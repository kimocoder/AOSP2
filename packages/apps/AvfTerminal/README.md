# AvfTerminal — Imported AVF Terminal App

## Overview

This module is a repackaged import of the **AVF Terminal** app from the Android Open Source Project (AOSP),
originally located at:

> `platform/packages/modules/Virtualization/android/TerminalApp/`
> Source: <https://android.googlesource.com/platform/packages/modules/Virtualization/+/refs/heads/main/android/TerminalApp/>

It has been imported into this repository as a self-contained app module under `packages/apps/AvfTerminal/`
with a custom package name to avoid collision with the upstream AOSP build targets.

---

## Package Name

| | Value |
|---|---|
| **Original upstream package** | `com.android.virtualization.terminal` |
| **New repackaged package** | `com.kimocoder.avfterminal` |

---

## Build Target Names

| Original (upstream) | Renamed (this repo) |
|---|---|
| `VmTerminalApp` | `KimoAvfTerminal` |
| `VmTerminalApp.aidl` | `KimoAvfTerminal.aidl` |

---

## What Was Changed

The following were updated consistently during the import:

1. **Kotlin/Java source files** — all `package` declarations and `import` statements
2. **AIDL files** — `package` declarations and cross-file imports
3. **`AndroidManifest.xml`** — `package=`, `android:authorities`, `android:taskAffinity`
4. **`Android.bp`** — module names `VmTerminalApp` → `KimoAvfTerminal`, `VmTerminalApp.aidl` → `KimoAvfTerminal.aidl`; also updated the static lib reference `VmTerminalApp.aidl-java` → `KimoAvfTerminal.aidl-java`
5. **`proguard.flags`** — keep-class rules updated to new package namespace
6. **Source file paths** — Java/Kotlin source resides in `java/com/kimocoder/avfterminal/`
7. **AIDL file paths** — AIDL resides in `aidl/com/kimocoder/avfterminal/`

No functional logic was changed. All activities, services, resources, and assets are preserved as-is from the source.

---

## Directory Structure

```
packages/apps/AvfTerminal/
├── Android.bp                          # Soong build rules (renamed targets)
├── AndroidManifest.xml                 # App manifest (new package name)
├── proguard.flags                      # R8/ProGuard keep rules
├── jarjar-rules.txt                    # JarJar repackaging rules
├── README.md                           # This file
├── aidl/
│   └── com/kimocoder/avfterminal/
│       ├── IInstallerService.aidl
│       └── IInstallProgressListener.aidl
├── java/
│   └── com/kimocoder/avfterminal/
│       ├── Application.kt
│       ├── BaseActivity.kt
│       ├── CertificateUtils.kt
│       ├── ConfigJson.kt
│       ├── DebianServiceImpl.kt
│       ├── DisplayActivity.kt
│       ├── ErrorActivity.kt
│       ├── ImageArchive.kt
│       ├── InstalledImage.kt
│       ├── InstallerActivity.kt
│       ├── InstallerService.kt
│       ├── Logger.kt
│       ├── MainActivity.kt
│       ├── ModifierKeysController.kt
│       ├── PortNotifier.kt
│       ├── Runner.kt
│       ├── SettingsActivity.kt
│       ├── SettingsDiskResizeActivity.kt
│       ├── SettingsItem.kt
│       ├── SettingsItemAdapter.kt
│       ├── SettingsPortForwardingActivity.kt
│       ├── SettingsRecoveryActivity.kt
│       ├── TerminalExceptionHandler.kt
│       ├── TerminalTabAdapter.kt
│       ├── TerminalTabFragment.kt
│       ├── TerminalThreadFactory.kt
│       ├── TerminalView.kt
│       ├── TerminalViewModel.kt
│       ├── UpgradeActivity.kt
│       └── VmLauncherService.kt
├── res/
│   ├── drawable/
│   ├── layout/
│   ├── mipmap-anydpi-v26/
│   ├── values/
│   └── xml/
└── assets/                             # (empty — see limitations below)
```

---

## Platform / AVF Dependencies Required to Build

This app has **hard dependencies on AOSP platform and AVF (Android Virtualization Framework)** internals.
It cannot be built as a standalone SDK APK. An AOSP full-checkout build environment is required.

Required build-time dependencies (from `Android.bp`):

| Library | Purpose |
|---|---|
| `android.system.virtualizationservice_internal-java` | Internal virtualization service binder API |
| `framework-virtualization.impl` | AVF framework implementation (hidden platform API) |
| `avf_aconfig_flags_java` | AVF feature flags (aconfig) |
| `debian-service-grpclib-lite` | gRPC stubs for Debian service protocol |
| `libcrosvm_android_display_service-java` | crosvm display surface service |
| `libforwarder_host_jni` | JNI library for port forwarding |
| `KimoAvfTerminal.aidl-java` | AIDL-generated code from this module's own AIDL |
| `MicrodroidTestHelper` | Device property helper (from Microdroid test lib) |
| `androidx.*`, `com.google.android.material_material`, `gson`, `apache-commons-compress` | Standard Android Jetpack and utility libraries |

Required runtime:
- The device must have `android.software.virtualization_framework` feature (AVF-capable device).
- The app requires privileged system permissions (`MANAGE_VIRTUAL_MACHINE`, `USE_CUSTOM_VIRTUAL_MACHINE`).
- The app must be installed in the `com.android.virt` APEX or as a privileged system app.
- A Linux VM image (Debian-based) must be downloadable/installed for the terminal to function.

---

## Known Limitations

1. **Not a standalone APK.** This app cannot be built with Android Studio or as a non-AOSP SDK APK. It requires the full AOSP build system (`m KimoAvfTerminal`).

2. **Assets directory is empty.** The upstream app uses JavaScript/HTML assets for the terminal WebView frontend. These assets (`assets/js/xterm.js`, etc.) are generated during the AOSP build from upstream sources (e.g., xterm.js) and are not committed to the source tree. In a full AOSP checkout these will be built and packaged automatically.

3. **Privileged app.** The `AndroidManifest.xml` has `android:enabled="false"` and requires privileged permissions and AVF-capable hardware. It must be enabled via a device overlay for the target product.

4. **AVF APEX dependency.** The app is `apex_available: ["com.android.virt"]` — it is normally shipped inside the `com.android.virt` APEX. When building standalone (outside of that APEX), you may need to adjust `apex_available`.

5. **Platform-only APIs.** The app uses `platform_apis: true` and calls non-SDK Android APIs specific to AVF. These APIs are not available in the public Android SDK.

---

## License

Apache License, Version 2.0. See individual source files for copyright notices.

Original copyright: The Android Open Source Project.
