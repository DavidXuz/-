package com.talkover.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.talkover.R;
import com.talkover.model.AppEntity;
import com.talkover.ui.TemplateHomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heleninsa on 2019-07-13.
 *
 * @author heleninsa
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private List<Object> mData;

    public AppAdapter() {
    }

    public <T> void setData(List<T> data) {
        mData = new ArrayList<>();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AppViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_app, viewGroup, false));
    }



    @Override
    public  void onBindViewHolder(@NonNull AppViewHolder appViewHolder, int i) {
        appViewHolder.setData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        private TextView mAppTitle;

        private Object mData;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            mAppTitle = itemView.findViewById(R.id.item_app_title);
            mAppTitle.setOnClickListener(it -> {
                TemplateHomeActivity.startActivity(it.getContext(), mData);
            });
        }

        public void setData(Object data) {
            if (data instanceof String) {
                mAppTitle.setText((String)data);
            } else if (data instanceof AppEntity) {
                mAppTitle.setText(((AppEntity) data).getApp());
            }
            mData = data;
        }
    }

}
