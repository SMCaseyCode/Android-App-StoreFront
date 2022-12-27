package com.example.scamegg.Cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scamegg.Item.Item;
import com.example.scamegg.Orders.Order;
import com.example.scamegg.Orders.OrderActivity;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder>{

    View mView;
    Context context;
    ArrayList<CartItemModel> itemModels;
    ScameggDAO mScameggDAO;
    SharedPreferences mPreferences;

    public CartRecyclerViewAdapter (View view, Context context, ArrayList<CartItemModel> itemModels){
        this.mView = view;
        this.context = context;
        this.itemModels = itemModels;
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_recycler_view_row, parent, false);

        mScameggDAO = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();

        mPreferences = context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        return new CartRecyclerViewAdapter.MyViewHolder(view, itemModels, mScameggDAO, mPreferences, mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.MyViewHolder holder, int position) {

        TextView finalSubtotal, finalTax, finalTotal;
        Button submitBTN;
        submitBTN = mView.findViewById(R.id.submitBTN);
        finalSubtotal = mView.findViewById(R.id.cardNumSubtotal);
        finalTax = mView.findViewById(R.id.cardNumTax);
        finalTotal = mView.findViewById(R.id.cardNumTotal);

        String[] tempArray = setTotals();
        finalSubtotal.setText(tempArray[0]);
        finalTax.setText(tempArray[1]);
        finalTotal.setText(tempArray[2]);

        holder.imageView.setImageResource(itemModels.get(position).getImage());
        holder.textViewName.setText(itemModels.get(position).getItemName());
        holder.textViewPrice.setText(itemModels.get(position).getItemPrice());
        holder.textViewQuantity.setText("x" + itemModels.get(position).getQuantity());

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewQuantity.setText("x" + addQuantity(holder.getAdapterPosition()));

                String[] tempArray = setTotals();
                finalSubtotal.setText(tempArray[0]);
                finalTax.setText(tempArray[1]);
                finalTotal.setText(tempArray[2]);
            }
        });

        holder.subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int quant = subtractQuantity(holder.getAdapterPosition());

                if (quant > 0){
                    holder.textViewQuantity.setText("x" + quant);
                } else {
                    remove(holder.getAdapterPosition());
                }


                String[] tempArray = setTotals();
                finalSubtotal.setText(tempArray[0]);
                finalTax.setText(tempArray[1]);
                finalTotal.setText(tempArray[2]);

            }
        });

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tempArray = setTotals();
                int userID = mPreferences.getInt("USER_ID_KEY", -1);
                String itemIDArray = "";
                String itemQuantityArray = "";

                for (int i = 0; i < itemModels.size(); i++){
                    if (i == 0){

                        Item item = mScameggDAO.getItemByName(itemModels.get(i).getItemName());
                        itemIDArray += item.getItemID();
                        itemQuantityArray += itemModels.get(i).getQuantity();

                    } else {
                        Item item = mScameggDAO.getItemByName(itemModels.get(i).getItemName());
                        itemIDArray += "," + item.getItemID();
                        itemQuantityArray += "," + itemModels.get(i).getQuantity();
                    }

                }

                Order order = new Order(userID, itemIDArray, itemQuantityArray, tempArray[2]);
                mScameggDAO.insert(order);

                List<Cart> cartList = mScameggDAO.getAllCartsByUserID(userID);

                for (int i = 0; i < cartList.size(); i++){
                    Cart cart = cartList.get(i);
                    mScameggDAO.delete(cart);
                }

                Intent intent = new Intent(context, OrderActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName, textViewPrice, textViewQuantity;
        FloatingActionButton addButton, subtractButton;


        public MyViewHolder(@NonNull View itemView, ArrayList<CartItemModel> itemModels, ScameggDAO mScameggDAO, SharedPreferences mPreferences, View view) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImage);
            textViewName = itemView.findViewById(R.id.itemName);
            textViewPrice = itemView.findViewById(R.id.itemPrice);
            textViewQuantity = itemView.findViewById(R.id.itemQuantity);

            addButton = itemView.findViewById(R.id.addQuantityBTN);
            subtractButton = itemView.findViewById(R.id.subtractQuantityBTN);

        }
    }

    private int addQuantity(int position){

        int userID = mPreferences.getInt("USER_ID_KEY", -1);
        CartItemModel cartModel = itemModels.get(position);
        Item item = mScameggDAO.getItemByName(cartModel.getItemName());
        int newQuant = -1;

        if (item.getItemQuantity() > 0){
            List<Cart> cartList = mScameggDAO.getAllCartsByUserID(userID);

            for (int i = 0; i < cartList.size(); i++){
                Cart tempCart = cartList.get(i);

                if (tempCart.getItemID() == item.getItemID()){
                    tempCart.setQuantity(tempCart.getQuantity() + 1);
                    newQuant = tempCart.getQuantity();
                    mScameggDAO.update(tempCart);
                    item.setItemQuantity(item.getItemQuantity() - 1);
                    mScameggDAO.update(item);

                    int cartQuantity = cartModel.getQuantity();
                    cartModel.setQuantity(cartQuantity + 1);
                    itemModels.set(position, cartModel);
                }
            }

        } else {
            Toast.makeText(context, "No More Stock Available", Toast.LENGTH_SHORT).show();
            newQuant = cartModel.getQuantity();
        }

        return newQuant;
    }

    private int subtractQuantity(int position){
        int userID = mPreferences.getInt("USER_ID_KEY", -1);
        CartItemModel cartModel = itemModels.get(position);
        Item item = mScameggDAO.getItemByName(cartModel.getItemName());
        int newQuant = -1;

        List<Cart> cartList = mScameggDAO.getAllCartsByUserID(userID);

        for (int i = 0; i < cartList.size(); i++){

            Cart tempCart = cartList.get(i);

            if (tempCart.getItemID() == item.getItemID()){
                tempCart.setQuantity(tempCart.getQuantity() - 1);
                newQuant = tempCart.getQuantity();
                mScameggDAO.update(tempCart);
                item.setItemQuantity(item.getItemQuantity() + 1);
                mScameggDAO.update(item);

                int cartQuantity = cartModel.getQuantity();
                cartModel.setQuantity(cartQuantity - 1);
                itemModels.set(position, cartModel);
            }

            if (tempCart.getQuantity() == 0){
                mScameggDAO.delete(tempCart);
            }
        }

        return newQuant;
    }

    private void remove(int position) {
        itemModels.remove(position);
        notifyItemRemoved(position);
    }

    private String[] setTotals(){

        double subTotal = 0;
        double tax;
        double total;

        for (int i = 0; i < itemModels.size(); i++){
            String tempString = itemModels.get(i).getItemPrice();
            int itemQuantity = itemModels.get(i).getQuantity();
            tempString = tempString.replace('$', ' ');

            double itemPrice = Double.parseDouble(tempString.trim());
            itemPrice *= itemQuantity;

            subTotal += itemPrice;

        }

        tax = (subTotal * .0775);
        total = subTotal + tax;

        DecimalFormat df = new DecimalFormat("#.00");

        String finalSubtotal = "$" + df.format(subTotal);
        String finalTax = "$" + df.format(tax);
        String finalTotal = "$" + df.format(total);

        return new String[]{finalSubtotal, finalTax, finalTotal};
    }

}
