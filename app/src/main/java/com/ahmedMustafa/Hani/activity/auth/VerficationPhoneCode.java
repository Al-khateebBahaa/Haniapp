package com.ahmedMustafa.Hani.activity.auth;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;

public class VerficationPhoneCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_phone_code);


        findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCongratulations();

            }
        });

    }


    private void showCongratulations() {

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        mBottomSheetDialog.setContentView(R.layout.congratulations);

/*
        View bottomSheetContainer = mBottomSheetDialog.findViewById(R.id.congratulations_layout);

        View parent = (View) bottomSheetContainer.getParent();
    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) parent.getLayoutParams();
        View inflatedView = View.inflate(this, R.layout.congratulations, null);

*/
        //  ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.congratulations, null);

        try {
            mBottomSheetDialog.findViewById(R.id.enter_to_main).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(VerficationPhoneCode.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    mBottomSheetDialog.dismiss();
                }
            });
        } catch (NullPointerException e) {
            startActivity(new Intent(VerficationPhoneCode.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            e.getMessage();
        }


        mBottomSheetDialog.show();

    }
}
