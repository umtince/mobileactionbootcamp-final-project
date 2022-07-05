package com.mobileactionbootcamp.airqualityservice.cls.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;


public class ClsSo2 extends ClsBaseCategories{
    @Field("SO2")
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
