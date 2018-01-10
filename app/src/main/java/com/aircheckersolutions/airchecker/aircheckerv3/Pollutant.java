package com.aircheckersolutions.airchecker.aircheckerv3;


public class Pollutant {

    String name;
    float currentValue;
    boolean activated;

    public Pollutant(String _name, float _currentValue, boolean _activated){
        name = _name;
        currentValue = _currentValue;
        activated = _activated;
    }


}
