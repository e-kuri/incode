package com.incode.incode.presentation.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incode.incode.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton mPictureButton;
    private FragmentActionHandler mActionHandler;

    private View.OnClickListener mPictureButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mActionHandler.takePicture();
        }
    };

    public static ImageListFragment newInstance(RecyclerView.Adapter adapter, FragmentActionHandler actionHandler) {
        ImageListFragment fragment = new ImageListFragment();
        fragment.setAdapter(adapter);
        fragment.mActionHandler = actionHandler;
        return fragment;
    }

    public void setActionHandler(FragmentActionHandler mActionHandler){
        this.mActionHandler = mActionHandler;
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_image_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.images_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(this.mAdapter);
        mPictureButton = (FloatingActionButton)view.findViewById(R.id.picture_button);
        mPictureButton.setOnClickListener(mPictureButtonListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
