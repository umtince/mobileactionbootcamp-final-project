package com.mobileactionbootcamp.classificationservice.cls.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClsCategories {
    private ClsBaseComponents co;
    private ClsBaseComponents so2;
    private ClsBaseComponents o3;

    public ClsCategories(){
        this.co = new ClsBaseComponents();
        this.so2 = new ClsBaseComponents();
        this.o3 = new ClsBaseComponents();
    }
}
