package com.mobileactionbootcamp.airqualityservice.cls.model;

import com.fasterxml.jackson.annotation.JsonProperty;



public class ClsSo2 extends ClsBaseCategories{
    //private String so2;

    @JsonProperty("SO2")
    @Override
    public String getChemicalValue() {
        return super.getChemicalValue();
    }

    @JsonProperty("SO2")
    @Override
    public void setChemicalValue(String chemicalValue) {
        super.setChemicalValue(chemicalValue);
    }
}
