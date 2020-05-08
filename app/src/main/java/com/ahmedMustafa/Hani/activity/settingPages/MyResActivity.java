package com.ahmedMustafa.Hani.activity.settingPages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.AllAgentFroTroodAdapter;
import com.ahmedMustafa.Hani.adapter.MResAdapter;
import com.ahmedMustafa.Hani.model.AllAgetntForTroodModel;
import com.ahmedMustafa.Hani.model.MResModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyResActivity extends BaseActivity implements MResAdapter.Callback {

    @Inject
    MResAdapter adapter;
    @Inject
    AllAgentFroTroodAdapter adapter2;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private int type;
    private boolean isRequestAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_res);
        type = getPrefManager().getUserType();
        isRequestAgent = getIntent().getIntExtra("type", 0) == 1;
        if (isRequestAgent) {
            adapter2.setDate(getIntent().getStringExtra("item"));
        }
        bind();
    }

    private void bind() {
        recyclerView = findViewById(R.id.recycler);
        setDataFrame(findViewById(R.id.dataLayout), recyclerView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(type == 1 ? "مندوبين لدي الشركه" : "متاجر قمت بالتسجيل بها");
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
        if (type == 1) {
            company();
        } else {
            loadMyStores();
        }

    }

    private void loadMyStores() {
        getApi().getMyRes(getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<MResModel>(new CustomApi.CallApi<MResModel>() {
                    @Override
                    public void onResponse(MResModel response) {
                        if (response.getStatus() != 1 || response.getStores().size() == 0) {
                            dataFrame.setVisible(NO_ITEM);
                            return;
                        }

                        adapter.setList(response.getStores());
                        recyclerView.setAdapter(adapter);
                        dataFrame.setVisible(LAYOUT);
                    }

                    @Override
                    public void onError(String msgError) {
                        dataFrame.setVisible(ERROR);
                    }
                }, ""));
    }

    private void company() {
        getApi().getAllAgentForTroodCompany(getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<AllAgetntForTroodModel>(new CustomApi.CallApi<AllAgetntForTroodModel>() {
                    @Override
                    public void onResponse(AllAgetntForTroodModel response) {
                        if (response.getStatus() == 1) {

                            if (response.getAgents().size() > 0) {
                                dataFrame.setVisible(LAYOUT);
                                adapter2.setList(response.getAgents());
                                recyclerView.setAdapter(adapter2);

                            } else {
                                dataFrame.setVisible(NO_ITEM);
                            }
                        } else {
                            dataFrame.setVisible(NO_ITEM);
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        dataFrame.setVisible(ERROR);
                    }
                }, "fd"));
    }

    @Override
    public void RemeveMeAsAgentListner(final int position, String resId) {
        if (!isConnected()) {
            toast(R.string.noNetwork);
            return;
        }
        showProgress();
        getApi().removeMeAsAgent(getPrefManager().getUserId(), resId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicModel model) {
                        hideProgress();
                        if (model.getStatus() == 1) {
                            toast("تم إلغاء تسجيلك كمندوب بنجاح");
                            adapter.removeItem(position);
                        } else {
                            toast(model.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
