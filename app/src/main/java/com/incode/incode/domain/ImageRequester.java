package com.incode.incode.domain;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incode.incode.model.Image;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kuri on 6/2/17.
 */

public class ImageRequester{

    private static final String BASE_URL = "https://photomaton.herokuapp.com/api/photo";
    private ImageAPI mImageAPI;
    private static ImageRequester mImageRequester;

    private ImageRequester(){
        start();
    };

    public ImageRequester getImageRequester(){
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




    /*
    private static ImageRequester mImageRequester;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private static final String url = "https://photomaton.herokuapp.com/api/photo";
    private final Response.Listener mResponseListener;
    private final Response.ErrorListener mErrorListener;

    private ImageRequester(Context context, final ImagesRequestCallback callback){
        mContext = context;

        mResponseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess();
            }
        };

        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
            }
        };
    }


    public static ImageRequester getmImageRequester(Context context, ImagesRequestCallback callback){
        if(mImageRequester == null){
            synchronized (ImageRequester.class){
                if(mImageRequester == null){
                    mImageRequester = new ImageRequester(context, callback);
                }
            }
        }

        return mImageRequester;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }

    private RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void requestAllImages(final ImagesRequestCallback callback){

        JsonArrayRequest request = new JsonArrayRequest(url, mResponseListener, mErrorListener);
    }
    */
}
