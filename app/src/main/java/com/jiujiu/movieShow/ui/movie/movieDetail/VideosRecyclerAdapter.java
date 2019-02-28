package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Videos;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.util.GlideUtils;

public class VideosRecyclerAdapter extends RecyclerView.Adapter<VideosRecyclerAdapter.VideosViewHolder> {

    private Videos mVideos;
    private Context mContext;
    private TrailerClickListener mListener;

    public void setVideos(Videos videos) {
        this.mVideos = videos;
        notifyDataSetChanged();
    }

    public VideosRecyclerAdapter(TrailerClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_trailer, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {
        if (mVideos.getVideos() != null && mVideos.getTrailers().size() > 0) {
            String key = this.mVideos.getTrailers().get(position).getKey();
            Glide.with(mContext)
                    .asBitmap()
                    .load(API.getYoutubeTrailerImg(key))
                    .apply(GlideUtils.imageRequestOptions())
                    .into(holder.trailerImage);
            holder.layout.setOnClickListener(v -> this.mListener.onTrailerClickListener(key));
        }
    }

    @Override
    public int getItemCount() {
        return this.mVideos == null || this.mVideos.getVideos() == null ? 0 : this.mVideos.getTrailers().size();
    }

    public static class VideosViewHolder extends RecyclerView.ViewHolder {
        ImageView trailerImage;
        RelativeLayout layout;

        public VideosViewHolder(View itemView) {
            super(itemView);
            trailerImage = itemView.findViewById(R.id.iv_movie_trailer_image);
            layout = itemView.findViewById(R.id.movie_trailers_container);
        }
    }

    public interface TrailerClickListener {
        void onTrailerClickListener(String key);
    }
}
