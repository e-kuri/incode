package com.incode.incode.domain.service;

import com.incode.incode.domain.ImageRequester;
import com.incode.incode.domain.service.IService.IImageService;
import com.incode.incode.domain.service.callback.ImageServiceCallback;
import com.incode.incode.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kuri on 6/3/17.
 */

public class ImageServiceImpl implements IImageService {

    private ImageRequester mImageRequester;
    /*
    private Callback requestCallback = new Callback<List<Image>>() {
        @Override
        public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {

        }

        @Override
        public void onFailure(Call<List<Image>> call, Throwable t) {

        }
    };
    */

    @Override
    public List<Image> getAllImages(ImageServiceCallback callback) {
        return null;
    }
    /*

    @Override
    public void getAllImages(Callback callback) {


        return  mImageRequester.requestImages(requestCallback);
    }
    */
}
