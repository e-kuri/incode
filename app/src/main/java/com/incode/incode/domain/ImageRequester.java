package com.incode.incode.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incode.incode.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kuri on 6/2/17.
 */

public class ImageRequester{

    private static final String BASE_URL = "https://photomaton.herokuapp.com/api/";
    private ImageAPI mImageAPI;
    private static ImageRequester mImageRequester;

    private ImageRequester(){
        start();
    };

    public static ImageRequester getInstance(){
        if(mImageRequester == null){
            synchronized (ImageRequester.class){
                    if(mImageRequester == null){
                    mImageRequester = new ImageRequester();
                }
            }
        }

        return mImageRequester;
    }

    private void start(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mImageAPI = retrofit.create(ImageAPI.class);
    }


    public void requestImages(Callback callback){
        Call<List<Image>> call = mImageAPI.getImages();
        call.enqueue(callback);
    }
}
