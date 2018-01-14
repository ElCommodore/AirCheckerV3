package com.aircheckersolutions.airchecker.aircheckerv3;

import android.graphics.drawable.Drawable;
import android.view.View;

public class Pollutant {

    String name;
    float currentValue;
    boolean activated;
    int status;
    float min;
    float max;
    float percentValue;
    int resID;

    public Pollutant(String _name, float _currentValue, boolean _activated, int _resID){

        status = 1;
        name = _name;
        currentValue = _currentValue;
        activated = _activated;
        percentValue = 0.0f;
        min = 0.0f;
        max = 1.0f;
        resID = _resID;

        switch(name){
            case "PM25":
                min = 0.0f;
                max = 300.0f;
                break;
            case "PM10":
                min = 0.0f;
                max = 50.0f;
                break;
            case "Ozone":
                min = 0.0f;
                max = 180.0f;
                break;
            case "Nitrogene Dioxide":
                min = 0.0f;
                max = 200.0f;
                break;
            case "Sulfur Dioxide":
                min = 0.0f;
                max = 125.0f;
                break;
            case "Carbone Monooxide":
                min = 0.0f;
                max = 10000.0f;
                break;
        }

    }


}
