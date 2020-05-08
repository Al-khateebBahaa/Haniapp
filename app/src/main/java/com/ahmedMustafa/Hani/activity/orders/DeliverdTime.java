package com.ahmedMustafa.Hani.activity.orders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ahmedMustafa.Hani.R;

public class DeliverdTime extends AppCompatActivity {

    private Button mSetDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverd_time);

        mSetDateAndTime = findViewById(R.id.set_date_button);
        mSetDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("time", " 5 ساعات"));
                finish();
            }
        });

    }
}
