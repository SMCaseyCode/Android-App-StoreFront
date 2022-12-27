package com.example.scamegg.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scamegg.R;
import com.example.scamegg.db.RecyclerViewInterface;

import java.util.ArrayList;

public class EditItemRecyclerViewAdapter extends RecyclerView.Adapter<EditItemRecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<ItemModel> itemModels;

    public EditItemRecyclerViewAdapter (Context context, ArrayList<ItemModel> itemModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.itemModels = itemModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public EditItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_item_recycler_view_row, parent, false);

        return new EditItemRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull EditItemRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.imageView.setImageResource(itemModels.get(position).getImage());
        holder.textViewName.setText(itemModels.get(position).getItemName());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImage);
            textViewName = itemView.findViewById(R.id.itemName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
