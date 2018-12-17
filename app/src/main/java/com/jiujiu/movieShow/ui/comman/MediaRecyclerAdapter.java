package com.jiujiu.movieShow.ui.comman;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Movie;
import com.jiujiu.movieShow.data.model.TvShow;
import com.jiujiu.movieShow.data.model.vo.Media;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.databinding.MediaItemBinding;
import com.jiujiu.movieShow.ui.base.BaseRecyclerAdapter;
import com.jiujiu.movieShow.util.GlideUtils;

import java.util.List;
import java.util.Objects;

public class MediaRecyclerAdapter<T extends Media> extends BaseRecyclerAdapter<T, MediaItemBinding> {

    private static final String TAG = "MediaRecyclerAdapter";
    private MediaItemBinding mBinding;
    private MediaClickListener mOnMediaClickListener;

    public void setMedia(List<T> data) {
        if (data == null || data.size() == 0) return;
        if (this.getData() == null || this.getData().size() == 0) {
            setData(data);
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return getData().size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return getData().get(oldItemPosition).getId().equals(data.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                   T oldMedia =  getData().get(oldItemPosition);
                   T newMedia = data.get(newItemPosition);
                    return oldMedia.getId().equals(newMedia.getId()) &&
                            Objects.equals(oldMedia.getAverageVote(), newMedia.getAverageVote()) &&
                            Objects.equals(oldMedia.getTitle(), newMedia.getTitle()) &&
                            Objects.equals(oldMedia.getPosterPath(), newMedia.getPosterPath()) &&
                            Objects.equals(oldMedia.getBackDropPath(), newMedia.getBackDropPath()) ;
                }
            });
//            this.mMedia = data;
            setData(data);
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public int getItemLayourId() {
        return R.layout.media_item;
    }

    @Override
    public void bindViewHolder(@NonNull BaseViewHolder holder, ViewDataBinding binding, int position) {
        mBinding = (MediaItemBinding) binding;
        if(getData()!= null && getData().size() != 0){
            T media = getData().get(position);
            mBinding.tvRank.setText(getContext().getString(R.string.str_rank_number, position + 1));
            mBinding.voteAverage.setText(String.valueOf(media.getAverageVote()));
            holder.itemView.setOnClickListener(v -> {
                Log.d(TAG, "bindViewHolder: ");
                if(mOnMediaClickListener != null){
                    mOnMediaClickListener.onMediaClickListener(media.getId());
                }
            });

            Glide.with(getContext())
                    .asBitmap()
                    .load(API.getPosterW300Path(media.getPosterPath()))
                    .apply(GlideUtils.imageRequestOptions())
                    .into(mBinding.popularMovieItemPoster);
        }
    }

    public void setOnMediaClickListener(MediaClickListener listener){
        this.mOnMediaClickListener = listener;
    }

}
