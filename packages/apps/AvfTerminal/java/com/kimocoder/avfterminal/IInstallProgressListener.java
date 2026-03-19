/*
 * This file is auto-generated from IInstallProgressListener.aidl.
 */
package com.kimocoder.avfterminal;

public interface IInstallProgressListener extends android.os.IInterface {
    public static final String DESCRIPTOR = "com.kimocoder.avfterminal.IInstallProgressListener";

    public void onCompleted() throws android.os.RemoteException;
    public void onError(String displayText) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements IInstallProgressListener {
        private static final String DESCRIPTOR = "com.kimocoder.avfterminal.IInstallProgressListener";
        static final int TRANSACTION_onCompleted = android.os.IBinder.FIRST_CALL_TRANSACTION + 0;
        static final int TRANSACTION_onError = android.os.IBinder.FIRST_CALL_TRANSACTION + 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInstallProgressListener asInterface(android.os.IBinder obj) {
            if (obj == null) return null;
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof IInstallProgressListener) {
                return (IInstallProgressListener) iin;
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
                case TRANSACTION_onCompleted: {
                    data.enforceInterface(DESCRIPTOR);
                    this.onCompleted();
                    return true;
                }
                case TRANSACTION_onError: {
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    this.onError(_arg0);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IInstallProgressListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            @Override
            public void onCompleted() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_onCompleted, _data, null, android.os.IBinder.FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }

            @Override
            public void onError(String displayText) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(displayText);
                    mRemote.transact(TRANSACTION_onError, _data, null, android.os.IBinder.FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
