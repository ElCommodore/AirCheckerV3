package com.aircheckersolutions.airchecker.aircheckerv3;

import android.graphics.drawable.Drawable;

public class Pollen {

    String name;
    String currentValue;
    int status;
    boolean activated;
    int resID;

    public Pollen(String _name, String _currentValue, boolean _activated, int _resID){
        status = 0;
        name = _name;
        currentValue = _currentValue;
        activated = _activated;
        resID = _resID;
    }
}
