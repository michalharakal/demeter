package com.fiwio.iot.demeter.app;


/**
 * Created by mharakal on 17.04.17.
 */

public class StringEndpoitUrlProvider implements EndpoitUrlProvider {
    @Override
    public String getUrl() {
        return "http://192.168.2.15:8080";
    }
}
