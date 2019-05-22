package com.example.mp3player.Model.Host;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"ten",
"hinhnen",
"icon"
})
public class Playlist implements Serializable {

@JsonProperty("id")
private String id;
@JsonProperty("ten")
private String ten;
@JsonProperty("hinhnen")
private String hinhnen;
@JsonProperty("icon")
private String icon;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("ten")
public String getTen() {
return ten;
}

@JsonProperty("ten")
public void setTen(String ten) {
this.ten = ten;
}

@JsonProperty("hinhnen")
public String getHinhnen() {
return hinhnen;
}

@JsonProperty("hinhnen")
public void setHinhnen(String hinhnen) {
this.hinhnen = hinhnen;
}

@JsonProperty("icon")
public String getIcon() {
return icon;
}

@JsonProperty("icon")
public void setIcon(String icon) {
this.icon = icon;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}