package com.fiwio.iot.demeter.app;

import com.fiwio.iot.demeter.EndpoitUrlProvider;

/**
 * Created by mharakal on 17.04.17.
 */

public class StringEndpoitUrlProvider implements EndpoitUrlProvider {
    @Override
    public String getUrl() {
        return "http://192.168.3.69:8080";
    }
}
