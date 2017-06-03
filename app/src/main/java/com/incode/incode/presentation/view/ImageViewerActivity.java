package com.incode.incode.presentation.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.incode.incode.R;
import com.incode.incode.model.Image;
import com.incode.incode.presentation.presenter.IPresenter.ImageViewerContract;
import com.incode.incode.presentation.presenter.ImageViewPresenter;

public class ImageViewerActivity extends AppCompatActivity implements ImageViewerContract.View {

    private ImageListFragment mListFragment;
    private ImageDetailsFragment mDetailsFragment;
    private ImageViewerContract.UserActionListener mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        mPresenter = new ImageViewPresenter(this);
        mPresenter.showImagesList();
    }

    @Override
    public void showListFragment(RecyclerView.Adapter adapter) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, getListFragment(adapter), getString(R.string.list_fragment_name));
        transaction.commit();
    }

    @Override
    public void showDetailForImage(Image image) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, getDetailsFragmentForImage(image), getString(R.string.details_fragment_name));
    }

    private Fragment getListFragment(RecyclerView.Adapter adapter){
        if(mListFragment == null){
            mListFragment = ImageListFragment.newInstance(adapter);
        }
        return mListFragment;
    }

    private Fragment getDetailsFragmentForImage(Image image){
        if(mDetailsFragment == null){
            mDetailsFragment = ImageDetailsFragment.newInstance();
        }
        mDetailsFragment.setImage(image);
        return mDetailsFragment;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
