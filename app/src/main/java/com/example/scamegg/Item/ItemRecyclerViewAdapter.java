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

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<ItemModel> itemModels;

    public ItemRecyclerViewAdapter (Context context, ArrayList<ItemModel> itemModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.itemModels = itemModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new ItemRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.imageView.setImageResource(itemModels.get(position).getImage());
        holder.textViewName.setText(itemModels.get(position).getItemName());
        holder.textViewPrice.setText(itemModels.get(position).getItemPrice());
        holder.textViewCategory.setText(itemModels.get(position).getItemCategory());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName, textViewPrice, textViewCategory;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textView);
            textViewPrice = itemView.findViewById(R.id.textView2);
            textViewCategory = itemView.findViewById(R.id.textView3);


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
