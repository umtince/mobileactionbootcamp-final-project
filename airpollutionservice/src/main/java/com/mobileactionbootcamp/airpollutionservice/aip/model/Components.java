
package com.mobileactionbootcamp.airpollutionservice.aip.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double co;
    @JsonProperty("no")
    private Double no;
    @JsonProperty("no2")
    private Double no2;
    @JsonProperty("o3")
    private Double o3;
    @JsonProperty("so2")
    private Double so2;
    @JsonProperty("pm2_5")
    private Double pm25;
    @JsonProperty("pm10")
    private Double pm10;
    @JsonProperty("nh3")
    private Double nh3;
}
