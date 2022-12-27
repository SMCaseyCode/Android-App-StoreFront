package com.example.scamegg.User;

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

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<UserModel> userModels;

    public UserRecyclerViewAdapter (Context context, ArrayList<UserModel> userModels, RecyclerViewInterface userRecyclerViewInterface){
        this.context = context;
        this.userModels = userModels;
        this.recyclerViewInterface = userRecyclerViewInterface;
    }

    @NonNull
    @Override
    public UserRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_recycler_view_row, parent, false);

        return new UserRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.imageView.setImageResource(userModels.get(position).getIsAdmin());
        holder.textViewName.setText(userModels.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userImage);
            textViewName = itemView.findViewById(R.id.usernameText);

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
