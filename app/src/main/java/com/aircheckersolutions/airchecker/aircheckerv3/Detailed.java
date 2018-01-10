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
        listview.setAdapter(new ListAdapterPollutant(getActivity(), CreateData()));

        return view;
    }

    private Pollutant[] CreateData(){
        Pollutant[] pollutants = new Pollutant[6];

        pollutants[0] = new Pollutant("PM25",0.25f,true);
        pollutants[1] = new Pollutant("PM10",0.54f,true);
        pollutants[2] = new Pollutant("Ozone",0.22f,true);
        pollutants[3] = new Pollutant("Nitrogene Dioxide",0.11f,true);
        pollutants[4] = new Pollutant("Sulfur Dioxide",0.78f,true);
        pollutants[5] = new Pollutant("Carbone Monooxide",0.42f,true);

        return pollutants;
    }

}
