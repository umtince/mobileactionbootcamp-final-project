package com.mobileactionbootcamp.airpollutionservice.geo.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "lat",
        "lon",
})
@Generated("jsonschema2pojo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoGeocoding {

    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private BigDecimal lat;
    @JsonProperty("lon")
    private BigDecimal lon;

}