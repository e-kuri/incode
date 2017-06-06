package com.incode.incode.presentation.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.Target;
import com.incode.incode.R;
import com.incode.incode.domain.GlideApp;
import com.incode.incode.domain.GlideUrlForRandomImages;
import com.incode.incode.model.Image;

import java.io.File;

/**
 * Created by kuri on 6/3/17.
 */

public class ImageDetailsFragment extends Fragment {

    private Image mImage;
    private ImageView mImageView;
    private TextView mDetailsTextView;
    private static final String IMAGE_KEY = "image_key";
    private Bitmap mBitmap;

    public static ImageDetailsFragment getInstance(Image image){
        ImageDetailsFragment fragment = new ImageDetailsFragment();
        fragment.mImage = image;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState != null && savedInstanceState.containsKey(IMAGE_KEY)){
            mImage = (Image) savedInstanceState.get(IMAGE_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_image_details, menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mImage != null){
            outState.putParcelable(IMAGE_KEY, mImage);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_share:
                final Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                if(mImage.isLocal()){
                    File file = new File(mImage.getPhotoURI().getPath());
                    shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getContext(), "com.incode.incode.fileProvider", file));
                    shareIntent.setType("image/jpeg");
                    startActivity(shareIntent);
                }else{
                    new AsyncTask<Void, Void, File>(){

                        @Override
                        protected File doInBackground(Void... params) {
                            try{
                                File file = GlideApp.with(getContext())
                                        .load(new GlideUrlForRandomImages(mImage.getPhotoURI().toString(), mImage.getId())) // uri to the location on the web where the image originates
                                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                        .get();
                                return file;
                            }catch (Exception e){
                                e.printStackTrace();
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(File file) {
                            if(file != null && file.exists()){
                                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getContext(), "com.incode.incode.fileProvider", file));
                                shareIntent.setType("image/jpeg");
                                startActivity(shareIntent);
                            }else{
                                Toast.makeText(getActivity(), R.string.image_not_show, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        displayImage();
    }

    private void displayImage(){
        if(mImage.isLocal()){
            GlideApp.with(getContext()).load(mImage.getPhotoURI()).into(mImageView);
        }else{
            GlideApp.with(getActivity()).load(new GlideUrlForRandomImages(mImage.getPhotoURI().toString(), mImage.getId())).into(mImageView);
        }
        mDetailsTextView.setText(mImage.getComment());
    }
}
