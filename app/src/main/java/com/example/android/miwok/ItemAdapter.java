package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.WordViewHolder> {

    ArrayList<ItemModel>itemModelArrayList;
    private int categoryColor;
    private Context context;
    OnItemClickListener onItemClickListener;

    public ItemAdapter(Context context,ArrayList<ItemModel> itemModelArrayList, int categoryColor, OnItemClickListener onItemClickListener) {
        this.itemModelArrayList = itemModelArrayList;
        this.categoryColor = categoryColor;
        this.context = context;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent , false);
        WordViewHolder wordViewHolder = new WordViewHolder(view);
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WordViewHolder Holder,int position) {
        Holder.miwokTranslation.setText(itemModelArrayList.get(position).getMiwokTranslation());
        Holder.defaultTranslation.setText(itemModelArrayList.get(position).getDefaultTranslation());
        if (itemModelArrayList.get(position).hasImage()){
            Holder.descriptionImage.setImageResource(itemModelArrayList.get(position).getImageId());
            Holder.descriptionImage.setVisibility(View.VISIBLE);
        }
        else {
            Holder.descriptionImage.setVisibility(View.GONE);
        }
        int color = ContextCompat.getColor(context,categoryColor);
        Holder.textContainer.setBackgroundColor(color);

        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<=itemModelArrayList.size();i++){
                    onItemClickListener.onItemClick(Holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        TextView miwokTranslation ;
        TextView defaultTranslation;
        ImageView descriptionImage ;
        LinearLayout textContainer;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            miwokTranslation = itemView.findViewById(R.id.miwok_text_view);
            defaultTranslation = itemView.findViewById(R.id.default_text_view);
            descriptionImage = itemView.findViewById(R.id.descriptionImage);
            textContainer = itemView.findViewById(R.id.textContainer);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int itemNo);
    }
}
