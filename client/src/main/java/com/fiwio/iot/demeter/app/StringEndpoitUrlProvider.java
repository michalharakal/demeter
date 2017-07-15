package com.fiwio.iot.demeter.app;


import android.text.TextUtils;

public class StringEndpoitUrlProvider implements EndpoitUrlProvider {

    private String url = null;

    @Override
    public String getUrl() {
        return TextUtils.isEmpty(url) ? "http://192.168.1.5:8080" : url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;

    }
}
