package com.jiujiu.movieShow.ui.movie.movieDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiujiu.movieShow.R;
import com.jiujiu.movieShow.data.model.Genre;

import java.util.List;

public class GenreRecyclerAdapter extends RecyclerView.Adapter<GenreRecyclerAdapter.GenreViewHolder> {
    private static final String TAG = "GenreRecyclerAdapter";

    private List<Genre> genreList;

    public void setGenres(List<Genre> genreList) {
        this.genreList = genreList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        if (genreList != null && genreList.size() > 0) {
            holder.genreButton.setText(genreList.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return genreList == null ? 0 : genreList.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreButton;
        public GenreViewHolder(View itemView) {
            super(itemView);
            genreButton = itemView.findViewById(R.id.genre_button);
        }
    }
}
