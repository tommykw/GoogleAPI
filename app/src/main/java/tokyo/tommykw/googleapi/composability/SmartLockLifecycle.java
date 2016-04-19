package tokyo.tommykw.googleapi.composability;

import android.support.v4.app.FragmentActivity;

/**
 * Created by tommy on 2016/04/19.
 */
public interface SmartLockLifecycle {
    void onCreate(FragmentActivity activity);
    void onActivityResultForRequest(int requestCode, int resultCode, RequesterCallback callback);
    void onActivityResultForSave(int requestCode, int resultCode, SaverCallback callback);

    interface RequesterCallback {
        void onSucceed();
        void onFailed();
    }

    interface SaverCallback {
        void onSucceed();
        void onFailed();
    }
}
