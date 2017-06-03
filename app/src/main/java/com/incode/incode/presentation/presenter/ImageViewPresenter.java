package com.incode.incode.presentation.presenter;

import android.support.v7.widget.RecyclerView;

import com.incode.incode.domain.ImageListAdapter;
import com.incode.incode.model.Image;
import com.incode.incode.presentation.presenter.IPresenter.ImageViewerContract;

/**
 * Created by kuri on 6/3/17.
 */

public class ImageViewPresenter implements ImageViewerContract.UserActionListener {

    private final ImageViewerContract.View view;
    private ImageListAdapter mAdapter;

    public ImageViewPresenter(ImageViewerContract.View view){
        this.view = view;
    }

    @Override
    public void onImageClicked(Image image) {
        view.showDetailForImage(image);
    }

    @Override
    public void showImagesList() {
        view.showListFragment(getAdapter());
    }

    private RecyclerView.Adapter getAdapter(){
        if(mAdapter == null){
            mAdapter = new ImageListAdapter(view.getContext(), this);
        }
        return mAdapter;
    }
}
