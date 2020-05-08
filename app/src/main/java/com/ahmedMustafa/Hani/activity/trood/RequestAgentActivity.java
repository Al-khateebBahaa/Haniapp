package com.ahmedMustafa.Hani.activity.trood;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.model.AllAgetntForTroodModel;
import com.ahmedMustafa.Hani.model.OrderModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.model.TroodAsCompanyModel;
import com.ahmedMustafa.Hani.model.TroodOrderDetailsModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestAgentActivity extends BaseActivity implements OnMapReadyCallback {

    private TextView clientLocation, resLocation, locationUserRes, locationClientRes;
    private Toolbar toolbar;
    private GPSTracker gpsTracker;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private AllAgetntForTroodModel.Agent agent;
    private TroodAsCompanyModel.MyOrder order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_agent);
        gpsTracker = new GPSTracker(this);
        agent = new Gson().fromJson(getIntent().getStringExtra("agent"), new TypeToken<AllAgetntForTroodModel.Agent>() {
        }.getType());
        order = new Gson().fromJson(getIntent().getStringExtra("item"), new TypeToken<TroodAsCompanyModel.MyOrder>() {
        }.getType());
        bind();
        init();
        onClick();
    }

    private void onClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private List<LatLng> latLngs;

    private void init() {

        float agent = getResLocation().distanceTo(getAgentLocation()) / 1000;
        float client = getResLocation().distanceTo(getClientLocation()) / 1000;
        DecimalFormat precision = new DecimalFormat("0.00");
        locationClientRes.setText(precision.format(client) + " KM");
        locationUserRes.setText(precision.format(agent) + " KM");
        latLngs = new ArrayList<>();
        latLngs.add(new LatLng(getResLocation().getLatitude(), getResLocation().getLongitude()));
        latLngs.add(new LatLng(getClientLocation().getLatitude(), getClientLocation().getLongitude()));
        latLngs.add(new LatLng(getAgentLocation().getLatitude(), getAgentLocation().getLongitude()));

        clientLocation.setText(order.getToAddress());
        resLocation.setText(order.getFromAddress());

        mapFragment.getMapAsync(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        LatLng res = new LatLng(getResLocation().getLatitude(), getResLocation().getLongitude());
        LatLng client = new LatLng(getClientLocation().getLatitude(), getClientLocation().getLongitude());
        LatLng agent = new LatLng(getAgentLocation().getLatitude(), getAgentLocation().getLongitude());

        String r = res.latitude + "," + res.longitude;
        String c = client.latitude + "," + client.longitude;
        String a = agent.latitude + "," + agent.longitude;
        dataFrame.setVisible(LAYOUT);
    }

    private void bind() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toolbar = findViewById(R.id.toolbar);
        clientLocation = findViewById(R.id.clientLocation);
        resLocation = findViewById(R.id.resLocation);
        locationUserRes = findViewById(R.id.locationUserRes);
        locationClientRes = findViewById(R.id.locationClientRes);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("مكان التوصيل");
        setDataFrame(findViewById(R.id.dataLayout), findViewById(R.id.layout));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        int res[] = new int[]{R.mipmap.shop, R.mipmap.person, R.mipmap.delivary};
        for (int x = 0; x < latLngs.size(); x++) {

            mMap.addMarker(new MarkerOptions().
                    position(latLngs.get(x))
                    .icon(BitmapDescriptorFactory.fromResource(res[x])));
            builder.include(latLngs.get(x));
        }

//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map).getWidth(), findViewById(R.id.map).getWidth(), 80));
    }

    private Location getAgentLocation() {
        Location location = new Location("AGENT");
        location.setLatitude(Double.parseDouble(agent.getLat()));
        location.setLongitude(Double.parseDouble(agent.getLng()));
        return location;
    }

    private Location getResLocation() {
        Location location = new Location("RES");
        location.setLatitude(Double.parseDouble(order.getCompanyLat()));
        location.setLongitude(Double.parseDouble(order.getCompanyLng()));
        return location;
    }

    private Location getClientLocation() {
        Location location = new Location("CLIENT");
        location.setLatitude(Double.parseDouble(order.getToLat()));
        location.setLongitude(Double.parseDouble(order.getToLng()));
        return location;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delivary_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sendBut) {
            sendRequest();
        }
        return true;
    }

    private void sendRequest() {
        showProgress();
        getApi().companyRequetAgentToDeliverdTrood(agent.getId() + "", agent.getLat(), agent.getLng(), getPrefManager().getUserId(), order.getId() + "",
                order.getTotalPrice() + "")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            toast("تم إرسال الطلب بنجاح");
                            Intent i = new Intent(RequestAgentActivity.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            openActivity(i);
                        } else {
                            toast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, ""));
    }
}
