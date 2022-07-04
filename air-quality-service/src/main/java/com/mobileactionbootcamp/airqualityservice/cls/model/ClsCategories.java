package com.mobileactionbootcamp.airqualityservice.cls.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClsCategories {
    private String date;
    private ClsCo co;
    private ClsSo2 so2;
    private ClsO3 o3;

    public ClsCategories(){
        this.co = new ClsCo();
        this.so2 = new ClsSo2();
        this.o3 = new ClsO3();
    }
}
