package com.teamawsome.awsomeeat.ViewHolder;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Interface.ItemClickListener;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Model.Request;
import com.teamawsome.awsomeeat.R;

import java.text.NumberFormat;
import java.util.Locale;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private TextView txt_cart_name, txt_price;
        private ImageView img_cart_count;
        public ItemClickListener itemClickListener;
        private String itemId;
        private FirestoreMain firestoreMain = FirestoreMain.getInstance();



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
            //TODO ordeitem needs to have a price registred (=not null) for this to work/ Sandra kollar upp
            int price = (Integer.parseInt(orderInfo.getPrice()))*(Integer.parseInt(orderInfo.getQuantity()));
            txt_price.setText(fmt.format(price));
            itemId = orderInfo.getDocumentId();
            txt_cart_name.setText(orderInfo.getProductName());
        }


         @Override
        public void onClick(View view) {

            showDeleteDialog(view, itemId);

         }

         //TODO Not working/Sandra och Shahin
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select action");
            menu.add(0,0, getAdapterPosition(), Common.DELETE);
        }


        public void showDeleteDialog(View view, String documentId){
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
