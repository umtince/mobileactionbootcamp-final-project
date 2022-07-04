package com.mobileactionbootcamp.airqualityservice.cls.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClsO3 extends ClsBaseCategories{
    //private String o3;

    @JsonProperty("O3")
    @Override
    public String getChemicalValue() {
        return super.getChemicalValue();
    }

    @JsonProperty("O3")
    @Override
    public void setChemicalValue(String chemicalValue) {
        super.setChemicalValue(chemicalValue);
    }
}
