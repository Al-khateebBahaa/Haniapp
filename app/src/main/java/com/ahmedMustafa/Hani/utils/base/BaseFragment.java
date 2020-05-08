package com.ahmedMustafa.Hani.utils.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.auth.LoginActivity;
import com.ahmedMustafa.Hani.utils.Api;
import com.ahmedMustafa.Hani.utils.DataFrame;
import com.ahmedMustafa.Hani.utils.NetworkReceiver;
import com.ahmedMustafa.Hani.utils.PrefManager;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class BaseFragment extends Fragment {

    @Inject
    Api api;
    @Inject
    PrefManager prefManager;

    private String emptyField;
    protected DataFrame dataFrame;
    private boolean TypeLoad = false;
    public boolean isDataLoaded = false;
    private boolean isOpenReciver;
    private NetworkReceiver receiver;

    protected final int LAYOUT = 0;
    protected final int NO_ITEM = 1;
    protected final int NO_INTERNET = 2;
    protected final int ERROR = 3;
    protected final int PROGRESS = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        setHasOptionsMenu(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emptyField = getString(R.string.emptyField);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        if (TypeLoad) {
            if (dataFrame == null) {
                throw new NullPointerException("Data Frame it should not be null");
            } else if (dataFrame.getLayoutView() == null) {
                throw new NullPointerException("Layout view it should not be null");
            } else {
                if (TypeLoad) {
                    openReciver();
                }
            }
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isOpenReciver && receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    private void openReciver() {
        if (!isConnected() && !isDataLoaded) {
            dataFrame.setVisible(dataFrame.NO_INTERNET);
            receiver = new NetworkReceiver(new NetworkReceiver.InternetListener() {
                @Override
                public void networkConnected() {
                    isOpenReciver = false;
                    dataFrame.setVisible(dataFrame.PROGRESS);
                    getActivity().unregisterReceiver(receiver);
                    loadData();
                }
            });
            isOpenReciver = true;
            IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            getActivity().registerReceiver(receiver, filter);
        } else if (isConnected() && !isDataLoaded) {
            loadData();
        }
    }

    protected void loadData() {
        isDataLoaded = true;
        // dataFrame.setVisible(dataFrame.PROGRESS);

    }

    protected void setDataFrame(View frame, View view) {
        dataFrame = (DataFrame) frame;
//        dataFrame.setLayout(view);
        setTypeLoad(true);

    }


    private void setTypeLoad(boolean isLoadData) {
        this.TypeLoad = isLoadData;
    }

    protected Api getApi() {
        return api;
    }

    protected PrefManager getPrefManager() {
        return prefManager;
    }

    public void openActivity(Intent intent) {

        startActivity(intent);


    }

    public boolean isConnected() {
        NetworkInfo info = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting() ? true : false;
    }

    public void toast(@StringRes int msgRes) {
        toast(getString(msgRes));
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected boolean isValidEmail(EditText editEmail) {
        String email = editEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(emptyField);
            editEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.inValidEmail));
            editEmail.requestFocus();
            return false;
        }
        return true;
    }

    protected boolean isValidPhone(EditText editPhone) {
        String phone = editPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            editPhone.setError(emptyField);
            editPhone.requestFocus();
            return false;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            editPhone.setError(getString(R.string.invalidPhone));
            editPhone.requestFocus();
            return false;
        }
        return true;
    }

    protected boolean isValidText(EditText editText) {

        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            editText.setError(emptyField);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    protected boolean isValidPassword(EditText editPass) {

        String pass = editPass.getText().toString();
        if (TextUtils.isEmpty(pass)) {

            editPass.setError(emptyField);
            editPass.requestFocus();
            return false;
        }
        return true;
    }

    protected boolean isValidConfirmPassword(EditText editPass, EditText editConfirmPass) {
        String pass = editPass.getText().toString();
        String confiemPass = editConfirmPass.getText().toString();

        if (TextUtils.isEmpty(confiemPass)) {

            editConfirmPass.setError(emptyField);
            editConfirmPass.requestFocus();
            return false;
        }
        if (!TextUtils.equals(pass, confiemPass)) {

            editConfirmPass.setError(getString(R.string.noMatchPassword));
            editConfirmPass.requestFocus();
            return false;

        }

        return true;
    }

    public boolean hasPermission(String permission, int REQUEST_PERMISSION) {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, REQUEST_PERMISSION);
                return true;
            }
        }
        return false;
    }

    public static String convertImageToString(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64
                .encodeToString(baos.toByteArray(), 0)
                .replace("\n", "")
                .replace(" ", "");

    }

    protected String getString(EditText editText) {
        return editText.getText().toString();
    }

    private ProgressDialog progressDialog;

    protected ProgressDialog showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("جاري التحميل ...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
        return progressDialog;
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    protected void noLogin(View view) {
        Snackbar.make(view, "يجب تسجيل الدخول أولا", Snackbar.LENGTH_LONG)
                .setAction("تسجيل الدخول", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }).show();
    }
}
