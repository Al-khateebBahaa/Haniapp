package com.ahmedMustafa.Hani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedMustafa.Hani.activity.orders.MyOrders;
import com.ahmedMustafa.Hani.adapter.NearbyResAdapter;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivityFragment extends Fragment {

    @Inject
    NearbyResAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainActivityFragment() {
        // Required empty public constructor
    }


    public static MainActivityFragment newInstance() {
        MainActivityFragment fragment = new MainActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        adapter = new NearbyResAdapter(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        ;
        RecyclerView recyclerView = view.findViewById(R.id.main_recycle);
        view.findViewById(R.id.cardView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyOrders.class));
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}
