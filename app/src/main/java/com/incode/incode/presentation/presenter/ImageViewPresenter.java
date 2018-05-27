package com.incode.incode.presentation.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;


import com.incode.incode.domain.ImageListAdapter;
import com.incode.incode.domain.ImageRequester;
import com.incode.incode.model.Image;
import com.incode.incode.presentation.presenter.IPresenter.ImageViewerContract;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by kuri on 6/3/17.
 */

public class ImageViewPresenter implements ImageViewerContract.UserActionListener {

    private final ImageViewerContract.View view;
    private ImageListAdapter mAdapter;
    private static final SimpleDateFormat imgDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private ImageRequester mImageRequester;

    public ImageViewPresenter(ImageViewerContract.View view, ImageRequester imageRequester){
        this.view = view;
        this.mImageRequester = imageRequester;
    }

    @Override
    public void onImageClicked(Image image) {
        view.showDetailForImage(image);
    }

    @Override
    public void showImagesList() {
        view.showListFragment(getAdapter());
    }

    public ImageListAdapter getAdapter(){
        if(mAdapter == null){
            mAdapter = new ImageListAdapter(view.getContext(), this);
        }
        return mAdapter;
    }

    @Override
    public void getServerImages(Callback callback) {
        mImageRequester.requestImages(callback);
    }

    @Override
    public void addPicture(Uri uri) {
        Image image = new Image(imgDateFormat.format(new Date()), uri.toString(), uri, "Picture taken by user");
        image.setLocal(true);
        getAdapter().addImage(image);
    }


    public File createImageFile(File directory) throws IOException {
        if(!directory.exists()){
            directory.mkdir();
        }
        String timeStamp = imgDateFormat.format(new Date());
        String imageFileName = timeStamp;
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                 directory     // directory
        );

        return image;
    }

    @Override
    public void addAllImagesInStorage(Context context){
        List<Image> images = new ArrayList<>();
        File picsDir = context.getExternalFilesDir("Pictures");
        if(picsDir.exists() && picsDir.isDirectory()){
            for(File file : picsDir.listFiles()){
                Image image = new Image(file.getName().split("\\.")[0], file.getAbsolutePath(), Uri.fromFile(file), "picture taken by user");
                image.setLocal(true);
                images.add(image);
            }
        }
        getAdapter().addImages(images);
    }

}
