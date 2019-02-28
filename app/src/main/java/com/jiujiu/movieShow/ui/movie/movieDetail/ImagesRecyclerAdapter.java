package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Images;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.util.GlideUtils;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerAdapter.ImageViewHolder> {

    private Images mImages;
    private Context mContext;

    public void setImages(Images images) {
        this.mImages = images;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (mImages.getBackdrops() != null && mImages.getBackdrops().size() > 0) {
            String path = this.mImages.getBackdrops().get(position).getFilePath();
            Glide.with(mContext)
                    .asBitmap()
                    .load(API.getBackdropPath(path))
                    .apply(GlideUtils.imageRequestOptions())
                    .into(holder.movieImages);
        }
    }

    @Override
    public int getItemCount() {
        return this.mImages == null || this.mImages.getBackdrops() == null ? 0 : this.mImages.getBackdrops().size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImages;

        public ImageViewHolder(View itemView) {
            super(itemView);
            movieImages = itemView.findViewById(R.id.iv_movie_image);
        }
    }
}
