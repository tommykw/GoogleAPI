package tokyo.tommykw.googleapi.composability;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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
    public void onActivityResultForRequest(int requestCode,
                                           int resultCode,
                                           @Nullable Intent data,
                                           RequesterCallback callback) {
        if (requestCode == RC_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                callback.onSuccess(credential.getId(), credential.getPassword());
            } else {
                callback.onFail();
            }
        }
    }

    @Override
    public void onActivityResultForSave(int requestCode, int resultCode, SaverCallback callback) {
        if (requestCode == RC_SAVE) {
            if (resultCode == Activity.RESULT_OK) {
                callback.onSuccess();
            } else {
                callback.onFail();
            }
        }
    }

    public void sendRequest(final Activity activity, final RequesterCallback callback) {
        CredentialRequest credentialRequest = new CredentialRequest.Builder().build();
        Auth.CredentialsApi.request(googleApiClient, credentialRequest)
                .setResultCallback(new ResultCallback() {
                    @Override
                    public void onResult(@NonNull Result result) {
                        if (result.getStatus().isSuccess()) {
                            // TODO
                        } else if (result.getStatus().hasResolution()) {
                            try {
                                result.getStatus().startResolutionForResult(activity, RC_REQUEST);
                            } catch (IntentSender.SendIntentException e) {
                                callback.onFail();
                            }
                        } else {
                            callback.onFail();
                        }
                    }
                });
    }

    public void saveRequest(Activity activity,
                            String id,
                            String password,
                            SaverCallback callback) {
        Credential credential = new Credential.Builder(id).setPassword(password).build();
        Auth.CredentialsApi.save(googleApiClient, credential)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        CREDENTIAL_RESULT result = credentialResultAction(status);
                    }
                });
    }

    private CREDENTIAL_RESULT credentialResultAction(Status status) {
        if (status.isSuccess()) {
            return CREDENTIAL_RESULT.SUCCESS;
        } else if (status.hasResolution()) {
            return CREDENTIAL_RESULT.RESOLUTION;
        } else if (status.isCanceled()) {
            return CREDENTIAL_RESULT.CANCEL;
        } else if (status.isInterrupted()) {
            return CREDENTIAL_RESULT.INTERRUPT;
        } else {
            return CREDENTIAL_RESULT.FAIL;
        }
    }

    private enum CREDENTIAL_RESULT {
        SUCCESS, RESOLUTION, CANCEL, INTERRUPT, FAIL


    }
}
