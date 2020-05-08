package com.ahmedMustafa.Hani.activity.auth;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedMustafa.Hani.BuildConfig;
import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.model.UserInfoModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SendPhoneNumberActivity extends BaseActivity {

    GoogleSignInOptions gso;
    CallbackManager callbackManager;
    GPSTracker gpsTracker;


    // private Toolbar toolbar;
    private EditText editPhone, editPassword;
    private View loginBut;
    private String token;
    private final int LOGIN_WITH_GOOGLE = 1;
    private final int LOGIN_WITH_FACEBOOK = 2;
    private Button loginWithGoogle;
    private Button loginWithFB;
    private final static int REQUEST_LOCATION = 5;
    private GoogleSignInClient client;
    private TextView mGoToRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_phone_number);
        token = FirebaseInstanceId.getInstance().getToken();
        FacebookSdk.sdkInitialize(getApplicationContext());

        bind();
        onClick();
        initPermission();
    }


    private void initPermission() {
        if (!hasPermission()) {
            gpsTracker = new GPSTracker(this);
            if (!gpsTracker.isCanGetLocation()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("تحديد الموقع")
                        .setCancelable(false)
                        .setMessage("يجب تحديد الموقع الخاص بك أولا")
                        .setPositiveButton("تحديد الموقع", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        }).create().show();
            }
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                return true;
            }
        }
        return false;
    }

    private void bind() {
        // toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("تسجيل الدخول");
        editPhone = findViewById(R.id.login_editPhone);
        editPassword = findViewById(R.id.login_editPassword);
         loginBut = findViewById(R.id.login_button);


        loginWithGoogle = findViewById(R.id.loginWithGoogleBut);
        loginWithFB = findViewById(R.id.loginWithFbBut);
        // loginWithFB.setReadPermissions(new String[]{"public_profile", "email"});
        mGoToRegistration = findViewById(R.id.go_to_registration);


    }

    private void onClick() {

        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidPhone(editPhone) && isValidPassword(editPassword)) {
                    if (isConnected()) {
                        sendRequest();
                    } else {
                        toast(R.string.noNetwork);
                    }
                }
            }
        });



        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = client.getSignInIntent();
                startActivityForResult(signInIntent, LOGIN_WITH_GOOGLE);
            }
        });

        /*

        loginWithFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            toast("Done");
                            //You can fetch user info like this…
                            String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            object.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("login_withFB_Error", e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.e("candel", "");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("login_withFB_Error", error.getMessage());
            }
        });
*/


        mGoToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendPhoneNumberActivity.this, RegisterActivity.class));
            }
        });

/*
        findViewById(R.id.registerBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendPhoneNumberActivity.this, MainActivity.class));
            }
        });
*/

    }


    private void loginWithSocial(String id, String image, String name, String email, String phone, String lat, String lng) {
        getApi().loginSocial(id, image, name, email, phone, lat, lng).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoModel userInfoModel) {
                        hideProgress();
                        if (userInfoModel.getStatus() == 1) {
                            UserInfoModel.User user = userInfoModel.getUser();
                            getPrefManager().updateData(
                                    user.getId() + "",
                                    user.getName() + "",
                                    user.getEmail() + "",
                                    user.getPhone() + "",
                                    user.getLat() + "",
                                    user.getLng() + "",
                                    user.getImage()

                            );
                            getPrefManager().setLoginMode(Constant.GOOGLE_MODE);
                            openActivity(new Intent(SendPhoneNumberActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            toast(userInfoModel.getError());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast(e.getMessage());
                        hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        client = GoogleSignIn.getClient(this, gso);

    }
*/

    private void sendRequest() {
        showProgress();
        Log.e("phone", getString(editPhone));
        Log.e("password", getString(editPassword));
        Log.e("token", token);
        getApi().loginWithPhone(getString(editPhone), getString(editPassword), token, "1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            UserInfoModel.User user = response.getUser();
                            getPrefManager().updateData(
                                    user.getId() + "",
                                    user.getName() + "",
                                    user.getEmail() + "",
                                    user.getPhone() + "",
                                    user.getLat() + "",
                                    user.getLng() + "",
                                    user.getImage()

                            );
                            getPrefManager().setUserType(user.getType());
                            getPrefManager().setLoginMode(Constant.PHONE_MODE);
                            openActivity(new Intent(SendPhoneNumberActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            Intent i = new Intent(SendPhoneNumberActivity.this, RegisterActivity.class);
                            i.putExtra("phone", getString(editPhone));
                            i.putExtra("password", getString(editPassword));
                            openActivity(i);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast(e.getMessage());
                        hideProgress();
                        Log.e("errorLogin", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_WITH_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginWithSocial(
                        account.getId(),
                        account.getPhotoUrl().toString(),
                        account.getDisplayName() + " " + account.getFamilyName(),
                        account.getEmail(),
                        "0",
                        gpsTracker.getLat() + "",
                        gpsTracker.getLon() + ""

                );
            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("erorr_login_with_google", e.getMessage());
            }


        } else if (requestCode == LOGIN_WITH_FACEBOOK) {

        }

    }

}
