package com.ahmedMustafa.Hani.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.ChatActivity;
import com.ahmedMustafa.Hani.model.ChatModel;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TEXT_ME = 1;
    private final int IMG_ME = 2;
    private final int MAP_ME = 3;
    private final int TEXT_OTHER = 4;
    private final int IMG_OTHER = 5;
    private final int MAP_OTHER = 6;
    private ChatActivity context;
    private List<ChatModel> list;
    private String mId;

    public ChatAdapter(ChatActivity context) {
        this.context = context;
        list = new ArrayList<>();
        mId = new PrefManager(context).getUserId();

    }

    public void addItem(ChatModel item) {
        list.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel item = list.get(position);
        if (item.getSender_user_id().equals(mId)) {
            switch (item.getMessage_type()) {
                case "1":
                    return TEXT_ME;
                case "2":
                    return IMG_ME;
                case "3":
                    return MAP_ME;
            }
        } else {
            switch (item.getMessage_type()) {
                case "1":
                    return TEXT_OTHER;
                case "2":
                    return IMG_OTHER;
                case "3":
                    return MAP_OTHER;
            }
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case TEXT_ME:
                return new TextMeHolder(inflater.inflate(R.layout.text_me_item, parent, false));
            case TEXT_OTHER:
                return new TextOtherHolder(inflater.inflate(R.layout.text_other_item, parent, false));
            case IMG_ME:
                return new ImgMeHolder(inflater.inflate(R.layout.image_me_item, parent, false));
            case IMG_OTHER:
                return new ImgOtherHolder(inflater.inflate(R.layout.img_other_item, parent, false));
            case MAP_ME:
                return new MapMeHolder(inflater.inflate(R.layout.map_me_item, parent, false));
            case MAP_OTHER:
                return new MapOtherHolder(inflater.inflate(R.layout.map_other_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel item = list.get(position);
        switch (getItemViewType(position)) {
            case TEXT_ME:
                bind((TextMeHolder) holder, item);
                break;
            case TEXT_OTHER:
                bind((TextOtherHolder) holder, item);
                break;
            case IMG_ME:
                bind((ImgMeHolder) holder, item);
                break;
            case IMG_OTHER:
                bind((ImgOtherHolder) holder, item);
                break;
            case MAP_ME:
                bind((MapMeHolder) holder, item);
                break;
            case MAP_OTHER:
                bind((MapOtherHolder) holder, item);
                break;
        }
    }

    private void bind(TextMeHolder holder, ChatModel item) {
        holder.textView.setText(item.getMessage_body());
    }

    private void bind(TextOtherHolder holder, ChatModel item) {
        holder.textView.setText(item.getMessage_body());
    }

    private void bind(ImgMeHolder holder, ChatModel item) {
        Picasso.get().load(item.getMessage_body()).into(holder.imageView);
    }

    private void bind(ImgOtherHolder holder, ChatModel item) {
        Picasso.get().load(item.getMessage_body()).into(holder.imageView);
    }

    private void bind(MapMeHolder holder, final ChatModel item) {
        holder.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                String location = item.getMessage_body();
                String[] split = location.split(",");
                LatLng latLng = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[0]));
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.room)));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)     // Sets the center of the map to Mountain View
                        .zoom(30)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private void bind(MapOtherHolder holder,final ChatModel item) {
        holder.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                String location = item.getMessage_body();
                String[] split = location.split(",");
                LatLng latLng = new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[0]));
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.room)));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)     // Sets the center of the map to Mountain View
                        .zoom(30)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TextMeHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TextMeHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    class ImgMeHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImgMeHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    class MapMeHolder extends RecyclerView.ViewHolder {
        private SupportMapFragment mapFragment;

        public MapMeHolder(View itemView) {
            super(itemView);
            mapFragment = (SupportMapFragment) context.getSupportFragmentManager().findFragmentById(R.id.map);
        }
    }

    class TextOtherHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TextOtherHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    class ImgOtherHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImgOtherHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    class MapOtherHolder extends RecyclerView.ViewHolder {
        private SupportMapFragment mapFragment;

        public MapOtherHolder(View itemView) {
            super(itemView);
            mapFragment = (SupportMapFragment) context.getSupportFragmentManager().findFragmentById(R.id.map);
        }
    }
}
