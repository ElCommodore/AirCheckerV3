package com.aircheckersolutions.airchecker.aircheckerv3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Detailed extends Fragment {

    ListView listview;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.detailed, container, false);
        listview = (ListView) view.findViewById(R.id.lv_pollutant);
        //listview.setAdapter(new ListAdapterPollutant(getActivity(), CreateData()));
        listview.setAdapter(new ListAdapterPollutant(getActivity(), ((MainActivity)getActivity()).GetPollutants() ));

        return view;
    }

//    private Pollutant[] CreateData(){
//
//
//
//
//        return pollutants;
//    }

}
