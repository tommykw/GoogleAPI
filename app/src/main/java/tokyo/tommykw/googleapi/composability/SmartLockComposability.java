package tokyo.tommykw.googleapi.composability;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by tommy on 2016/04/19.
 */
public class SmartLockComposability {
    private final int RC_REQUEST = 10000;
    private final int RC_SAVE = 10001;
    private GoogleApiClient googleApiClient;

    private SmartLockComposability() {
        new AssertionError("you must call onCreate method");
    }

    public void onCreate(Activity activity) {
        initGoogleApiClient();
    }

    public void onCreate(Fragment fragment) {
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
//        googleApiClient = GoogleApiClient
//                .Builder()
//                .
    }
}
