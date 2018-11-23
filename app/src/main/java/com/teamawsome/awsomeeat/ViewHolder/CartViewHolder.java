package com.teamawsome.awsomeeat.ViewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.R;

import java.text.NumberFormat;
import java.util.Locale;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        public TextView txt_cart_name, txt_price;
        public ImageView img_cart_count;
        public ItemClickListener itemClickListener;
        private String itemId;



        public void setTxt_cart_name(TextView txt_cart_name) {
            this.txt_cart_name = txt_cart_name;
        }



        public CartViewHolder(View itemView) {
            super(itemView);

            txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
            txt_price = (TextView) itemView.findViewById(R.id.cart_item_price);
            img_cart_count = (ImageView)itemView.findViewById(R.id.cart_item_count);

            itemView.setOnClickListener(this);
        }

        public void setCartItem(Order orderInfo){

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(""+ orderInfo.getQuantity(), Color.RED);
            img_cart_count.setImageDrawable(drawable);
            Locale locale = new Locale("en","SE");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            //TODO ordeitem needs to have a price registred (=not null) for this to work/ Sandra
            /*int price = (Integer.parseInt(orderInfo.getPrice()))*(Integer.parseInt(orderInfo.getQuantity()));
            txt_price.setText(fmt.format(price));*/

            txt_cart_name.setText(orderInfo.getProductName());

        }


         @Override
        public void onClick(View view) {

         }

         //TODO Not working
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select action");
            menu.add(0,0, getAdapterPosition(), Common.DELETE);
        }

}
