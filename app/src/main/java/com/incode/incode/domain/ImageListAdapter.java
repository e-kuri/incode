package com.incode.incode.domain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.incode.incode.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.incode.incode.R;

/**
 * Created by kuri on 6/2/17.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private final ImageRequester mImageRequester;
    private List<Image> mImages;
    private Context mContext;

    private Callback requestCallback = new Callback<List<Image>>() {
        @Override
        public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
            mImages = response.body();
            notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<List<Image>> call, Throwable t) {
            Log.e("KURI", "onFailure: " + t.getMessage() );
        }
    };

    public ImageListAdapter(Context context){
        mImageRequester = ImageRequester.getInstance();
        mContext = context;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mImages.get(position).getPhotoURL()).into(holder.image);
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
}
