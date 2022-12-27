package com.example.scamegg.Orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scamegg.Item.Item;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<OrderItemModel> orderModels;
    ScameggDAO mScameggDAO;
    SharedPreferences mPreferences;

    public OrderRecyclerViewAdapter (Context context, ArrayList<OrderItemModel> orderModels){
        this.context = context;
        this.orderModels = orderModels;
    }

    @NonNull
    @Override
    public OrderRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_recycler_view_card, parent, false);

        mScameggDAO = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();

        mPreferences = context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        return new OrderRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerViewAdapter.MyViewHolder holder, int position) {
        Random random = new Random();
        int rand = random.nextInt(99999999);
        String[] itemIDs = orderModels.get(position).getItemID().split(",");
        String[] itemQuantity = orderModels.get(position).getQuantity().split(",");
        int finalItemQuantity = 0;

        for (String s : itemQuantity) {
            finalItemQuantity += Integer.parseInt(s);
        }

        holder.textViewOrderNumber.setText("#" + rand);
        holder.textViewContents.setText("Contents: " + finalItemQuantity);
        holder.textViewTotal.setText(orderModels.get(position).getOrderTotal());

        holder.returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.returnBTN.setBackgroundColor(Color.parseColor("#5a5a58"));

                int userID = mPreferences.getInt("USER_ID_KEY", -1);

                // Places returned items back into the overall stock.
                for (int i = 0; i < itemIDs.length; i++){
                    String returnedItemID = itemIDs[i];
                    String returnedQuantityString = itemQuantity[i];

                    int returnedQuantity = Integer.parseInt(returnedQuantityString);

                    Item returnedItem = mScameggDAO.getItemByItemID(Integer.parseInt(returnedItemID));
                    returnedItem.setItemQuantity(returnedItem.getItemQuantity() + returnedQuantity);
                    mScameggDAO.update(returnedItem);
                }

                List<Order> orders = mScameggDAO.getAllOrdersByUserID(userID);
                orderModels.remove(holder.getAdapterPosition());
                mScameggDAO.delete(orders.get(holder.getAdapterPosition()));
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderNumber, textViewContents, textViewTotal;
        Button returnBTN;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewOrderNumber = itemView.findViewById(R.id.orderNumber);
            textViewContents = itemView.findViewById(R.id.contents);
            textViewTotal = itemView.findViewById(R.id.orderTotal);

            returnBTN = itemView.findViewById(R.id.returnBTN);

        }
    }
}
