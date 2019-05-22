package com.example.mp3player.Model.Host;

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
"idalbum",
"idtheloai",
"idplaylist",
"ten",
"hinh",
"tencasy",
"link"
})
public class Song {

@JsonProperty("id")
private String id;
@JsonProperty("idalbum")
private Object idalbum;
@JsonProperty("idtheloai")
private Object idtheloai;
@JsonProperty("idplaylist")
private String idplaylist;
@JsonProperty("ten")
private String ten;
@JsonProperty("hinh")
private String hinh;
@JsonProperty("tencasy")
private String tencasy;
@JsonProperty("link")
private String link;
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

@JsonProperty("idalbum")
public Object getIdalbum() {
return idalbum;
}

@JsonProperty("idalbum")
public void setIdalbum(Object idalbum) {
this.idalbum = idalbum;
}

@JsonProperty("idtheloai")
public Object getIdtheloai() {
return idtheloai;
}

@JsonProperty("idtheloai")
public void setIdtheloai(Object idtheloai) {
this.idtheloai = idtheloai;
}

@JsonProperty("idplaylist")
public String getIdplaylist() {
return idplaylist;
}

@JsonProperty("idplaylist")
public void setIdplaylist(String idplaylist) {
this.idplaylist = idplaylist;
}

@JsonProperty("ten")
public String getTen() {
return ten;
}

@JsonProperty("ten")
public void setTen(String ten) {
this.ten = ten;
}

@JsonProperty("hinh")
public String getHinh() {
return hinh;
}

@JsonProperty("hinh")
public void setHinh(String hinh) {
this.hinh = hinh;
}

@JsonProperty("tencasy")
public String getTencasy() {
return tencasy;
}

@JsonProperty("tencasy")
public void setTencasy(String tencasy) {
this.tencasy = tencasy;
}

@JsonProperty("link")
public String getLink() {
return link;
}

@JsonProperty("link")
public void setLink(String link) {
this.link = link;
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