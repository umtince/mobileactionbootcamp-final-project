package com.mobileactionbootcamp.airqualityservice.cls.model;

import com.fasterxml.jackson.annotation.JsonProperty;




public class ClsCo extends ClsBaseCategories{
    //private String co;

    @JsonProperty("CO")
    @Override
    public String getChemicalValue() {
        return super.getChemicalValue();
    }

    @JsonProperty("CO")
    @Override
    public void setChemicalValue(String chemicalValue) {
        super.setChemicalValue(chemicalValue);
    }
}
