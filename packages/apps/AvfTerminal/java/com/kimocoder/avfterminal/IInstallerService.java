/*
 * This file is auto-generated from IInstallerService.aidl.
 */
package com.kimocoder.avfterminal;

public interface IInstallerService extends android.os.IInterface {
    public static final String DESCRIPTOR = "com.kimocoder.avfterminal.IInstallerService";

    public void requestInstall(boolean isWifiOnly) throws android.os.RemoteException;
    public void setProgressListener(IInstallProgressListener listener) throws android.os.RemoteException;
    public boolean isInstalling() throws android.os.RemoteException;
    public boolean isInstalled() throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements IInstallerService {
        private static final String DESCRIPTOR = "com.kimocoder.avfterminal.IInstallerService";
        static final int TRANSACTION_requestInstall = android.os.IBinder.FIRST_CALL_TRANSACTION + 0;
        static final int TRANSACTION_setProgressListener = android.os.IBinder.FIRST_CALL_TRANSACTION + 1;
        static final int TRANSACTION_isInstalling = android.os.IBinder.FIRST_CALL_TRANSACTION + 2;
        static final int TRANSACTION_isInstalled = android.os.IBinder.FIRST_CALL_TRANSACTION + 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInstallerService asInterface(android.os.IBinder obj) {
            if (obj == null) return null;
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IInstallerService) {
                return (IInstallerService) iin;
            }
            return new Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case TRANSACTION_requestInstall: {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg0 = data.readInt() != 0;
                    this.requestInstall(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_setProgressListener: {
                    data.enforceInterface(DESCRIPTOR);
                    IInstallProgressListener _arg0 = IInstallProgressListener.Stub.asInterface(data.readStrongBinder());
                    this.setProgressListener(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_isInstalling: {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = this.isInstalling();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                }
                case TRANSACTION_isInstalled: {
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = this.isInstalled();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IInstallerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            @Override
            public void requestInstall(boolean isWifiOnly) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(isWifiOnly ? 1 : 0);
                    mRemote.transact(TRANSACTION_requestInstall, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void setProgressListener(IInstallProgressListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    mRemote.transact(TRANSACTION_setProgressListener, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public boolean isInstalling() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_isInstalling, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt() != 0;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public boolean isInstalled() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_isInstalled, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt() != 0;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
    }
}
