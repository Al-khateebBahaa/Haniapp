package com.ahmedMustafa.Hani.activity.orders;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.activity.auth.VerficationActivity;
import com.ahmedMustafa.Hani.model.AgentAcceptedOrderModel;
import com.ahmedMustafa.Hani.model.OrderModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.model.TroodOrderDetailsModel;
import com.ahmedMustafa.Hani.utils.Api;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.DataParser;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DelivaryRequestActivity extends BaseActivity implements OnMapReadyCallback {

    private TextView clientLocation, resLocation, time, locationUserRes, locationClientRes;
    private EditText editPrice;
    private Toolbar toolbar;
    private GPSTracker gpsTracker;
    private OrderModel.Order order;
    private TroodOrderDetailsModel.Order troodOrder;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private int type;
    private final int STORE = 0;
    private final int TROOD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivary_request);
        gpsTracker = new GPSTracker(this);
        type = getIntent().getIntExtra("type", 0);
        if (type == STORE)
            order = new Gson().fromJson(getIntent().getStringExtra("order"), new TypeToken<OrderModel.Order>() {
            }.getType());
        else
            troodOrder = new Gson().fromJson(getIntent().getStringExtra("order"), new TypeToken<TroodOrderDetailsModel.Order>() {
            }.getType());
        bind();
        init();
        onClick();

    }

    private void bind() {

        findViewById(R.id.fotter).setVisibility(type == TROOD ? View.GONE : View.VISIBLE);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        editPrice = findViewById(R.id.editPrice);
        toolbar = findViewById(R.id.toolbar);
        clientLocation = findViewById(R.id.clientLocation);
        resLocation = findViewById(R.id.resLocation);
        time = findViewById(R.id.time);
        locationUserRes = findViewById(R.id.locationUserRes);
        locationClientRes = findViewById(R.id.locationClientRes);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("مكان التوصيل");
        setDataFrame(findViewById(R.id.dataLayout), findViewById(R.id.layout));

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

        new FetchUrl().execute(getDirectionsUrl(res, client));
        dataFrame.setVisible(LAYOUT);
/*
        Observable<List<List<HashMap<String, String>>>> clientShop = getApi().getRoute(c,r).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observable<List<List<HashMap<String, String>>>> agentShop = getApi().getRoute(a,r).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observable.zip(clientShop, agentShop, new BiFunction<List<List<HashMap<String,String>>>,
                List<List<HashMap<String,String>>>, List<List<List<HashMap<String,String>>>>>(){
            @Override
            public List<List<List<HashMap<String, String>>>> apply(List<List<HashMap<String, String>>> lists, List<List<HashMap<String, String>>> lists2) {
                List<List<List<HashMap<String, String>>>> l = new ArrayList<>();
                l.add(lists);
                l.add(lists2);
                return l;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<List<HashMap<String, String>>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<List<HashMap<String, String>>>> lists) {
                        dataFrame.setVisible(LAYOUT);
                        Log.e("drowMap","done");
                        List<List<HashMap<String, String>>> client = lists.get(0);
                        List<List<HashMap<String, String>>> agent = lists.get(1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataFrame.setVisible(LAYOUT);
                        Log.e("drowMap",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                */
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

        if (type == STORE) {
            clientLocation.setText(order.getAddress());
            resLocation.setText(order.getResAddress());
            double duration = order.getDeliveryDuration() / 60;
            time.setText(precision.format(duration) + " ساعة ");
        } else {
            clientLocation.setText(troodOrder.getToAddress());
            resLocation.setText(troodOrder.getFromAddress());
            // double duration = order.getDeliveryDuration() / 60;
            //time.setText(precision.format(duration) + " ساعة ");
        }

        mapFragment.getMapAsync(this);
    }


    private void validation() {

        if (isValidText(editPrice)) {

            String t = editPrice.getText().toString();
            if (Pattern.compile("\\\\d+").matcher(t).matches()) {

                editPrice.setError("قيمة غير صالحة");
                editPrice.requestFocus();
            } else if (!isConnected()) {
                toast(R.string.noNetwork);
            } else {
                sendRequest();
            }
        }
    }

    private void tardValidation() {
        if (!isConnected()) {
            toast(R.string.noNetwork);
        } else {
            sendTardRequest();
        }
    }

    private void sendRequest() {
        showProgress();
        int total = order.getOrderPrice() + Integer.parseInt(getString(editPrice));
        getApi().agentAcceptedOrder(
                order.getId() + "",
                getPrefManager().getUserId(),
                getString(editPrice),
                order.getOrderPrice() + "", total + "", gpsTracker.getLat() + "", gpsTracker.getLon() + ""
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AgentAcceptedOrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AgentAcceptedOrderModel model) {
                        hideProgress();
                        if (model.getStatus() == 1) {
                            toast("تم إرسال طلبك بنجاح");
                            openActivity(new Intent(DelivaryRequestActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            if (model.getError().equals("يجب ان يكون حسابك موثق من قبل الاداره لتقديم طلب التوصيل")) {
                                Snackbar.make(dataFrame, model.getError(), Snackbar.LENGTH_LONG)
                                        .setAction("طلب التوثيق", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                openActivity(new Intent(DelivaryRequestActivity.this, VerficationActivity.class));
                                            }
                                        }).show();
                            } else {
                                toast(model.getError());
                            }
                            Log.e("error", model.getError());
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

    private void sendTardRequest() {
        showProgress();
        getApi().agentApplyToDeliverTard(getPrefManager().getUserId(), gpsTracker.getLat() + "", gpsTracker.getLon() + "", troodOrder.getTotalPrice() + "", troodOrder.getId() + "")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            toast("تم إرسال طلبك بنجاح");
                            openActivity(new Intent(DelivaryRequestActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            if (response.getMsg().equals("يجب ان يكون حسابك موثق من قبل الاداره لتقديم طلب التوصيل")) {
                                Snackbar.make(dataFrame, response.getMsg(), Snackbar.LENGTH_LONG)
                                        .setAction("طلب التوثيق", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                openActivity(new Intent(DelivaryRequestActivity.this, VerficationActivity.class));
                                            }
                                        }).show();
                            } else {
                                toast(response.getMsg());
                            }
                            Log.e("error", response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, "TROOD_REQUEST"));
    }

    private Location getAgentLocation() {
        Location location = new Location("AGENT");
        location.setLatitude(gpsTracker.getLat());
        location.setLongitude(gpsTracker.getLon());
        return location;
    }

    private Location getResLocation() {
        Location location = new Location("RES");
        location.setLatitude(Double.parseDouble(type == STORE ? order.getResLat() : troodOrder.getFromLat()));
        location.setLongitude(Double.parseDouble(type == STORE ? order.getResLng() : troodOrder.getFromLng()));
        return location;
    }

    private Location getClientLocation() {
        Location location = new Location("CLIENT");
        location.setLatitude(Double.parseDouble(type == STORE ? order.getLat() : troodOrder.getToLat()));
        location.setLongitude(Double.parseDouble(type == STORE ? order.getLng() : troodOrder.getToLng()));
        return location;
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map).getWidth(), findViewById(R.id.map).getWidth(), 80));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delivary_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sendBut) {
            if (type == STORE)
                validation();
            else
                tardValidation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    public class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }

        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();
                Log.d("downloadUrl", data.toString());
                br.close();

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }
}

