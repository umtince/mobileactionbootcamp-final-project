package com.mobileactionbootcamp.airqualityservice.cls.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public class ClsCo extends ClsBaseCategories{

    @Field("CO")
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
