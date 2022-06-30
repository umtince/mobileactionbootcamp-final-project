
package com.mobileactionbootcamp.airpollutionservice.aip.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "co",
    "no",
    "no2",
    "o3",
    "so2",
    "pm2_5",
    "pm10",
    "nh3"
})
@Generated("jsonschema2pojo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Components {

    @JsonProperty("co")
    private BigDecimal co;
    @JsonProperty("no")
    private BigDecimal no;
    @JsonProperty("no2")
    private BigDecimal no2;
    @JsonProperty("o3")
    private BigDecimal o3;
    @JsonProperty("so2")
    private BigDecimal so2;
    @JsonProperty("pm2_5")
    private BigDecimal pm25;
    @JsonProperty("pm10")
    private BigDecimal pm10;
    @JsonProperty("nh3")
    private BigDecimal nh3;
}
