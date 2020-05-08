package com.ahmedMustafa.Hani.activity.settingPages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.TermsModel;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TermsActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("شروط الإستخدام وسياسة الخصوصية");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setDataFrame(findViewById(R.id.dataLayout), findViewById(R.id.layout));
        textView = findViewById(R.id.text);
    }

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getTerm().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TermsModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TermsModel termsModel) {
                        if (termsModel.getStatus() == 1) {
                            dataFrame.setVisible(LAYOUT);
                            textView.setText(termsModel.getTermsOfUse());

                        } else {
                            dataFrame.setVisible(ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataFrame.setVisible(ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
