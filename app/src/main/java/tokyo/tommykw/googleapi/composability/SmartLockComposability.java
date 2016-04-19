package tokyo.tommykw.googleapi.composability;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by tommy on 2016/04/19.
 */
public class SmartLockComposability implements SmartLockLifecycle {
    private final int RC_REQUEST = 10000;
    private final int RC_SAVE = 10001;
    private GoogleApiClient googleApiClient;

    private SmartLockComposability() {
        new AssertionError("you must call onCreate method");
    }

    public void onCreate(FragmentActivity activity) {
        googleApiClient = new GoogleApiClient
                .Builder(activity)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .enableAutoManage(activity, 0, null)
                .addApi(Auth.CREDENTIALS_API)
                .build();
    }

    @Override
    public void onActivityResultForRequest(int requestCode, int resultCode, RequesterCallback callback) {

    }

    @Override
    public void onActivityResultForSave(int requestCode, int resultCode, SaverCallback callback) {

    }

    public void sendRequest(Activity activity) {
    }
}
