package com.talkover.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.talkover.R;
import com.talkover.model.TemplateEntity;

import java.util.List;

/**
 * Created by heleninsa on 2019-07-13.
 *
 * @author heleninsa
 */
public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder> {

    private List<TemplateEntity> mList;

    public TemplateAdapter(List<TemplateEntity> list) {
        mList = list;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TemplateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_template, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder templateViewHolder, int i) {
        templateViewHolder.setData(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class TemplateViewHolder extends RecyclerView.ViewHolder {

        private TextView mTemplateName;

        private TextView mTemplateDesc;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            mTemplateName = itemView.findViewById(R.id.item_template_name);
            mTemplateDesc = itemView.findViewById(R.id.item_template_desc);
        }

        public void setData(TemplateEntity data) {
            mTemplateName.setText(data.getName());
            mTemplateDesc.setText(data.getDesc());
        }
    }

}
