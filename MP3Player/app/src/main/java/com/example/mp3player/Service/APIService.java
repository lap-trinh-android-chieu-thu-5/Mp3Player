package com.example.mp3player.Service;

public class APIService {
    private static  String base_url = "https://appnhacnhom12.000webhostapp.com/server/";

    public static DataService getService(){
        return APIRetrofitClient.geteClient(base_url).create(DataService.class);
    }
}
