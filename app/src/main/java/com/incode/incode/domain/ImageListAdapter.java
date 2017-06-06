package com.incode.incode.domain;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.incode.incode.model.Image;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.incode.incode.R;
import com.incode.incode.presentation.presenter.IPresenter.ImageViewerContract;

/**
 * Created by kuri on 6/2/17.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private final ImageRequester mImageRequester;
    private List<Image> mImages;
    private final Context mContext;
    private final ImageViewerContract.UserActionListener mPresenter;

    private Callback requestCallback = new Callback<List<Image>>() {
        @Override
        public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
            mImages.addAll(response.body());
            notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<List<Image>> call, Throwable t) {
            Log.e("KURI", "onFailure: " + t.getMessage() );
        }
    };

    public ImageListAdapter(Context context, ImageViewerContract.UserActionListener presenter){
        mImageRequester = ImageRequester.getInstance();
        mContext = context;
        this.mPresenter = presenter;
        mImages = new LinkedList<>();
        getImages();
    }

    public void getImages(){
        mImageRequester.requestImages(requestCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.layout_image_list_item, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Image image = mImages.get(position);
        Log.d("ImageListAdapter", "onBindViewHolder: " + image.getTitle() + " : " + image.getPhotoURI());
        if(image.isLocal()){
            GlideApp.with(mContext).load(image.getPhotoURI())
                    .into(holder.image).onLoadFailed(ContextCompat.getDrawable(mContext, R.drawable.chrome_broken));
        }else{
            GlideApp.with(mContext).load(new GlideUrlForRandomImages(image.getPhotoURI().toString(), image.getId())).into(holder.image)
                    .onLoadFailed(ContextCompat.getDrawable(mContext, R.drawable.chrome_broken));
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onImageClicked(image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.list_image);
        }
    }

    public void addImage(Image image){
        mImages.add(0, image);
        notifyDataSetChanged();
    }

    public void addImages(List<Image> images){
        mImages.addAll(0, images);
        notifyDataSetChanged();
    }
}
