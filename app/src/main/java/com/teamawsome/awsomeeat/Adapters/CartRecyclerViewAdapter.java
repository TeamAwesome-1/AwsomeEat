package com.teamawsome.awsomeeat.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.ViewHolder.CartViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartViewHolder> {


        private List<Order> orderList;
        private TextView total_price;
        private int totalPrice;
        private Locale locale;
        private NumberFormat fmt;

        public CartRecyclerViewAdapter(List<Order> list, TextView total_price) {
            this.orderList = list;
            this.total_price = total_price;
            locale = new Locale("en", "SE");
            fmt = NumberFormat.getCurrencyInstance(locale);
        }

        public List<Order> getOrderList() {
            return orderList;
        }


    @Nonnull
        @Override
        public CartViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View itemView = inflater.inflate(R.layout.cart_layout,viewGroup,false);

            return new CartViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }


        @Override
        public void onBindViewHolder(@Nonnull CartViewHolder cartViewHolder, int position) {

            Order order = orderList.get(position);
            cartViewHolder.setCartItem(order);

        }

        public void addItem(Order orderListItem){
            orderList.add(orderListItem);
            setTotalPrice();
            this.notifyItemInserted(orderList.size()-1);
        }


        public void clearList(){
            orderList.clear();
            this.notifyDataSetChanged();
        }

        public void removeOrderListItem(int position){
            if(position >= 0 && position < orderList.size()){
                orderList.remove(position);
                this.notifyItemRemoved(position);
                setTotalPrice();
            }
        }

        public void removeOrderListItem(String id){
            for (int i = 0; i <orderList.size() ; i++) {
                if(orderList.get(i).getDocumentId().equals(id)){
                    removeOrderListItem(i);
                }
            }
        }

        public void modifyOrderListItem(String id, Order order){

            for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getProductId().equals(id)) {
                        orderList.set(i, order);
                        this.notifyItemChanged(i);
                        return;
                    }
                }
            }

    private void setTotalPrice(){
       int totalPrice = 0;
       for (int i = 0; i < getItemCount(); i++) {
            Order order = orderList.get(i);
            totalPrice += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
       }
       total_price.setText(fmt.format(totalPrice));

    }



}
