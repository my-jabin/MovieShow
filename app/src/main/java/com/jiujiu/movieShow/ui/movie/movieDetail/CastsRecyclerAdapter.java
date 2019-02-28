package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Cast;
import com.jiujiu.movieShow.data.remote.API;
import com.jiujiu.movieShow.util.GlideUtils;

import java.util.List;

public class CastsRecyclerAdapter extends RecyclerView.Adapter<CastsRecyclerAdapter.CastViewHolder> {
    private static final String TAG = "GenreRecyclerAdapter";

    private List<Cast> casts;
    private Context mContext;
    private int size = 0;

    public void setCasts(List<Cast> castList) {
        this.casts = castList;
        for (Cast cast : castList) {
            if (TextUtils.isEmpty(cast.getProfilePath())) {
                break;
            } else {
                size++;
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moveit_item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        if (casts != null && casts.size() > 0) {
            Cast cast = casts.get(position);
            holder.castName.setText(cast.getName());
            holder.character.setText(cast.getCharacter());
            Glide.with(mContext)
                    .asBitmap()
                    .load(API.getPosterW300Path(cast.getProfilePath()))
                    .apply(GlideUtils.imageRequestOptions())
                    .into(holder.castImage);
        }
    }

    @Override
    public int getItemCount() {
        return casts == null ? 0 : size;
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder {
        TextView castName;
        ImageView castImage;
        TextView character;

        public CastViewHolder(View itemView) {
            super(itemView);
            castName = itemView.findViewById(R.id.tv_cast_name);
            castImage = itemView.findViewById(R.id.iv_cast_image);
            character = itemView.findViewById(R.id.tv_character);
        }
    }
}
