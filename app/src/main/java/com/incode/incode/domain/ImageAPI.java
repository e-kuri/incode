package com.incode.incode.domain;

import com.incode.incode.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kuri on 6/2/17.
 */

public interface ImageAPI {

    @GET("photo")
    Call<List<Image>> getImages();

}
