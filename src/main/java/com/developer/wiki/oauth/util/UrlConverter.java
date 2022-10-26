package com.developer.wiki.oauth.util;

public class UrlConverter {
    private static final String HTTP="http://";
    private static final String HTTPS="https://";

    public static String of(String url){
        if(url.startsWith("localhost")) {
            return HTTP+url;
        }
        return HTTPS+url;
    }
}
