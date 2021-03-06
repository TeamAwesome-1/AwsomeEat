package com.teamawsome.awsomeeat.ViewHolder;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.teamawsome.awsomeeat.Database.FirestoreMain;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.R;
import java.text.NumberFormat;
import java.util.Locale;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txt_cart_name, txt_price;
        private ImageView img_cart_count;
        private String itemId;
        private FirestoreMain firestoreMain = FirestoreMain.getInstance();


        public CartViewHolder(View itemView) {
            super(itemView);

            txt_cart_name = itemView.findViewById(R.id.cart_item_name);
            txt_price = itemView.findViewById(R.id.cart_item_price);
            img_cart_count = itemView.findViewById(R.id.cart_item_count);

            itemView.setOnClickListener(this);
        }

        public void setCartItem(Order orderInfo){

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(""+ orderInfo.getQuantity(), Color.RED);
            img_cart_count.setImageDrawable(drawable);
            Locale locale = new Locale("en","SE");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            int price = (Integer.parseInt(orderInfo.getPrice()))*(Integer.parseInt(orderInfo.getQuantity()));
            txt_price.setText(fmt.format(price));
            itemId = orderInfo.getDocumentId();
            txt_cart_name.setText(orderInfo.getProductName());
        }


         @Override
        public void onClick(View view) {
            showDeleteDialog(view, itemId);
         }


        private void showDeleteDialog(View view, String documentId){
            //Displays a AlertDialog with a question if current user surely wants to place the orders
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(view.getContext());
            alertdialog.setTitle(R.string.Delete);
            alertdialog.setMessage(R.string.do_you_want_to_delete_item);
            alertdialog.setIcon(R.drawable.ic_orders);

            //Inserts a Yes-button with a clicklistener
            alertdialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                   firestoreMain.clearCartItem(documentId);
                }
            });

            //Inserts a No-button with a clicklistener to the alertdialog
            alertdialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            //displays the alerDialog
            alertdialog.show();

        }

}
