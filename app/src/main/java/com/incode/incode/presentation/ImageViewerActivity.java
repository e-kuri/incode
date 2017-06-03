package com.incode.incode.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.incode.incode.R;

public class ImageViewerActivity extends AppCompatActivity {

    private ImageListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        showList();
    }

    private void showList(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, getListFragment(), getString(R.string.list_fragment_name));
        transaction.addToBackStack(getString(R.string.list_fragment_name));
        transaction.commit();
    }

    private Fragment getListFragment(){
        if(mListFragment == null){
            mListFragment = ImageListFragment.newInstance();
        }
        return mListFragment;
    }
}
