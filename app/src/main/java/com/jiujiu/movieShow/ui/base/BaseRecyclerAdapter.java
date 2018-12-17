package com.jiujiu.movieShow.ui.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<T,B extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    private Context mContext;
    private B mBinding;
    private List<T> data;

    @NonNull
    @Override
    public BaseRecyclerAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mBinding =  DataBindingUtil.inflate(LayoutInflater.from(mContext), getItemLayourId(), parent, false);
        return new BaseViewHolder(mBinding);
    }

    @LayoutRes
    public abstract int getItemLayourId();

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerAdapter.BaseViewHolder holder, int position) {
        bindViewHolder(holder,holder.binding,position);
    }

    public abstract void bindViewHolder(@NonNull BaseRecyclerAdapter.BaseViewHolder holder, ViewDataBinding binding, int position);

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;
        public BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
