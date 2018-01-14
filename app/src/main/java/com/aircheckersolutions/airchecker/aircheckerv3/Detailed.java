package com.aircheckersolutions.airchecker.aircheckerv3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Detailed extends Fragment {

    ListView listview;
    ListView listViewPollen;
    View view;
    View viewMain;
    ListAdapterPollutant adapter;
    ListAdapterPollen adapterPollen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.detailed, container, false);
        viewMain = inflater.inflate(R.layout.activity_main, container, false);
        listview = (ListView) view.findViewById(R.id.lv_pollutant);
        listViewPollen = (ListView) view.findViewById(R.id.lv_pollen);
        //listview.setAdapter(new ListAdapterPollutant(getActivity(), CreateData()));
        adapter = new ListAdapterPollutant(getActivity(), ((MainActivity)getActivity()).GetPollutants() );
        adapterPollen = new ListAdapterPollen(getActivity(), ((MainActivity)getActivity()).GetPollen() );
        //listview.setAdapter(new ListAdapterPollutant(getActivity(), ((MainActivity)getActivity()).GetPollutants() ));
        listview.setAdapter(adapter);
        listViewPollen.setAdapter(adapterPollen);


        return view;
    }

}
