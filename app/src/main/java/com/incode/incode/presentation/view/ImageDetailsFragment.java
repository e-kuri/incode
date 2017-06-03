package com.incode.incode.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.incode.incode.R;
import com.incode.incode.model.Image;

/**
 * Created by kuri on 6/3/17.
 */

public class ImageDetailsFragment extends Fragment {

    private Image mImage;
    private ImageView mImageView;
    private TextView mDetailsTextView;

    public static ImageDetailsFragment newInstance(){
        ImageDetailsFragment fragment = new ImageDetailsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.img);
        mDetailsTextView = (TextView)view.findViewById(R.id.details);
    }

    public void setImage(Image image){
        this.mImage = image;
        displayImage();
    }

    private void displayImage(){
        
    }

}
