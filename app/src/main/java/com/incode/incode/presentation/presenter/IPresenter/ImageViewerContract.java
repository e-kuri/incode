package com.incode.incode.presentation.presenter.IPresenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.incode.incode.model.Image;

/**
 * Created by kuri on 6/3/17.
 */

public interface ImageViewerContract {

    interface View{
        Context getContext();
        void showListFragment(RecyclerView.Adapter adapter);
        void showDetailForImage(Image image);
    }

    interface UserActionListener{
        void onImageClicked(Image image);
        void showImagesList();
    }


}
