package com.ahmedMustafa.Hani.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.RateAdapter;
import com.ahmedMustafa.Hani.model.RateModel;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentsActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    @Inject
    RateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        bind();
        init();

    }

    private void bind() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler);
        setDataFrame(findViewById(R.id.dataLayout), recyclerView);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تعليقات المستخدمين");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getUserRate(getPrefManager().getUserId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<RateModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RateModel rateModel) {
                if (rateModel.getStatus() == 1) {

                    if (rateModel.getComments().size() > 0) {
                        adapter.setList(rateModel.getComments());
                        recyclerView.setAdapter(adapter);
                        dataFrame.setVisible(LAYOUT);
                    } else {
                        dataFrame.setVisible(NO_ITEM);
                    }
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
