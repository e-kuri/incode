package com.incode.incode.domain;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;

import java.net.URL;

/**
 * Created by kuri on 6/5/17.
 */

public class GlideUrlForRandomImages extends GlideUrl {

    private String cachedKey;

    public GlideUrlForRandomImages(URL url, String id ) {
        super(url);
        setCachedKey(url.toString(), id);
    }

    public GlideUrlForRandomImages(String url, String id) {
        super(url);
        setCachedKey(url, id);
    }

    public GlideUrlForRandomImages(URL url, Headers headers, String id) {
        super(url, headers);
        setCachedKey(url.toString(), id);
    }

    public GlideUrlForRandomImages(String url, Headers headers, String id) {
        super(url, headers);
        setCachedKey(url, id);
    }

    private void setCachedKey(String url, String id){
        this.cachedKey = url + "?id=" + id;
    }

    @Override
    public String getCacheKey() {
        return cachedKey;
    }
}
