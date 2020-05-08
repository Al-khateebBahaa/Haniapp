package com.ahmedMustafa.Hani.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.BankTransferActivity;
import com.ahmedMustafa.Hani.activity.SettingActivity;
import com.ahmedMustafa.Hani.activity.auth.LoginActivity;
import com.ahmedMustafa.Hani.model.ChangeNotificationModel;
import com.ahmedMustafa.Hani.model.UserInfoModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends BaseFragment {

    private Toolbar toolbar;
    private TextView name, profileState, walletText, delivaryText, orderCount;
    private CircleImageView imageView;
    private RatingBar ratingBar;
    private ImageView notifyView, settingVeiw, logOut;
    private View walletView;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        bind(v);
        onClick();

        return v;
    }

    private void bind(View v) {

        logOut = v.findViewById(R.id.logout);
        walletView = v.findViewById(R.id.walletView);
        notifyView = v.findViewById(R.id.notify);
        settingVeiw = v.findViewById(R.id.setting);
        orderCount = v.findViewById(R.id.orderCount);
        delivaryText = v.findViewById(R.id.delivaryText);
        walletText = v.findViewById(R.id.walletText);
        name = v.findViewById(R.id.userName);
        profileState = v.findViewById(R.id.profileState);
        imageView = v.findViewById(R.id.image);
        ratingBar = v.findViewById(R.id.ratingBar);
        toolbar = v.findViewById(R.id.toolbar);
        setDataFrame(v.findViewById(R.id.dataLayout), v.findViewById(R.id.layout));
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }

    private UserInfoModel.User model;


    @Override
    protected void loadData() {
        super.loadData();
        /*
        getApi().getUserInfo(getPrefManager().getUserId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoModel userInfoModel) {
                        if (userInfoModel.getStatus() == 1) {
                            model = userInfoModel.getUser();
                            name.setText(model.getName());
                            ratingBar.setRating(model.getRating());
                            String imagePath;
                            if (getPrefManager().getLoginMode() == Constant.PHONE_MODE) {
                                imagePath = Constant.BASE_IMAGE + model.getImage();
                            } else {
                                imagePath = model.getImage();
                            }
                            Picasso.get().load(imagePath).into(imageView);
                            walletText.setText(model.getUserPercentage() + " ريال ");
                            orderCount.setText(model.getOrdersCountAsUser() + "");
                            delivaryText.setText(model.getTotalDeliveryFee() + " ريال ");
                            profileState.setVisibility(model.getVerified() == 1 ? View.VISIBLE : View.GONE);
                            dataFrame.setVisible(LAYOUT);
                            notifyView.setImageResource(model.getNotifications() == 0 ? R.drawable.ic_off_notify : R.drawable.ic_on_notify);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    private void onClick() {

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("تسجيل الخروج");
                builder.setMessage("هل أنت متأكد من أنك تريد تسجيل الخروج");
                builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPrefManager().setLogout();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        openActivity(i);
                    }
                });
                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        settingVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        notifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected())
                    changeNotifyState();
                else
                    toast(R.string.noNetwork);
            }
        });

        walletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BankTransferActivity.class);
                openActivity(i);
            }
        });
    }

    private void changeNotifyState() {
        showProgress();
        getApi().changeNotificationState(getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ChangeNotificationModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ChangeNotificationModel m) {
                hideProgress();
                if (m.getStatus() == 1) {
                    model.setNotifications(m.getUser().getNotifications());
                    notifyView.setImageResource(m.getUser().getNotifications() == 1 ? R.drawable.ic_on_notify : R.drawable.ic_off_notify);
                } else {
                    toast("حدث خطأ ما برجاء المحاولة في وقت لاحق");
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

}
