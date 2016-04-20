package tokyo.tommykw.googleapi.composability;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by tommy on 2016/04/19.
 */
public interface SmartLockLifecycle {
    void onCreate(FragmentActivity activity);
    void onActivityResultForRequest(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data,
                                    RequesterCallback callback);

    void onActivityResultForSave(int requestCode,
                                 int resultCode,
                                 SaverCallback callback);

    interface RequesterCallback {
        void onSucceed(String id, String password);
        void onFailed();
    }

    interface SaverCallback {
        void onSucceed();
        void onFailed();
    }
}
