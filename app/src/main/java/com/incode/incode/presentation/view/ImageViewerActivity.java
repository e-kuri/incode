package com.incode.incode.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.incode.incode.R;
import com.incode.incode.domain.ImageRequester;
import com.incode.incode.model.Image;
import com.incode.incode.presentation.presenter.IPresenter.ImageViewerContract;
import com.incode.incode.presentation.presenter.ImageViewPresenter;

import java.io.File;
import java.io.IOException;

public class ImageViewerActivity extends AppCompatActivity implements ImageViewerContract.View, FragmentActionHandler {

    private ImageViewerContract.UserActionListener mPresenter;
    private static final int PICTURE_REQUEST_CODE = 69;
    private Uri picPath;
    private Fragment mListFragment;
    private String mCurrentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        mPresenter = new ImageViewPresenter(this, ImageRequester.getInstance());
        mPresenter.showImagesList();
        mPresenter.addAllImagesInStorage(getContext());

        FragmentManager fm = getSupportFragmentManager();
        Fragment detailsFragment = fm.findFragmentByTag(getString(R.string.details_fragment_name));
        ImageListFragment listFragment = (ImageListFragment) fm.findFragmentByTag(getString(R.string.list_fragment_name));

        if(listFragment != null){
            listFragment.setAdapter(mPresenter.getAdapter());
            listFragment.setActionHandler(this);
        }

        if(detailsFragment != null){
            showFragment(detailsFragment, getString(R.string.details_fragment_name), false);
        }

    }

    @Override
    public void showListFragment(RecyclerView.Adapter adapter) {
        showFragment(getListFragment(adapter), getString(R.string.list_fragment_name), false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager fm = getSupportFragmentManager();
        Fragment restoreFrag = fm.findFragmentByTag(mCurrentFragmentTag);

        if(restoreFrag != null){
            showFragment(restoreFrag, mCurrentFragmentTag, false);
        }else{
            showFragment(mListFragment, getString(R.string.list_fragment_name), false);
        }
    }

    private void showFragment(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment, tag);
        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        mCurrentFragmentTag = tag;
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().remove(mListFragment).commit();
    }

    @Override
    public void showDetailForImage(Image image) {
        showFragment(getDetailsFragmentForImage(image), getString(R.string.details_fragment_name), true);
    }

    private Fragment getListFragment(RecyclerView.Adapter adapter){
        if(mListFragment == null){
            mListFragment = ImageListFragment.newInstance(adapter, this);
        }
        return mListFragment;
    }

    private Fragment getDetailsFragmentForImage(Image image){
        return ImageDetailsFragment.getInstance(image);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void takePicture() {
        Intent takePictureIntent = new Intent();
        takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = mPresenter.createImageFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.incode.incode.fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                picPath = photoURI;
                startActivityForResult(takePictureIntent, PICTURE_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK){
            mPresenter.addPicture(picPath);
        }
    }

}
