package com.mobileactionbootcamp.airqualityservice.cls.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClsCategories {

    @JsonProperty("")
    private ClsCo co;
    @JsonProperty("")
    private ClsSo2 so2;
    @JsonProperty("")
    private ClsO3 o3;

    public ClsCategories(){
        this.co = new ClsCo();
        this.so2 = new ClsSo2();
        this.o3 = new ClsO3();
    }
}
