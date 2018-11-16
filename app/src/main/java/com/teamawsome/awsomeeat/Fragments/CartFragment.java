package com.teamawsome.awsomeeat.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamawsome.awsomeeat.Adapters.CartAdapter;
import com.teamawsome.awsomeeat.Cart;
import com.teamawsome.awsomeeat.Common.Common;
import com.teamawsome.awsomeeat.Database.Database;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Model.Request;
import com.teamawsome.awsomeeat.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;


public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    public CartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_cart, container, false);

        //Firebase
        database= FirebaseDatabase.getInstance();
        requests=database.getReference("Requests") ;

        //Init
        recyclerView = (RecyclerView)view.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)view.findViewById(R.id.total);
        btnPlace = view.findViewById(R.id.btnPlaceOrder);



    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadListFood();

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.size() > 0)
                    showAlertDialog();
                else
                    Toast.makeText(getContext(), "Cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showAlertDialog() {
        if (cart.size() <= 0)  //checking if cart is empty or not
        {
            Toast.makeText(getContext(), "Add some items to cart !", Toast.LENGTH_SHORT).show();


        } else {

            AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
            alertdialog.setTitle("One more step!");
            alertdialog.setMessage("Enter address: ");

            final EditText edtAddress = new EditText(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            edtAddress.setLayoutParams(lp);
            alertdialog.setView(edtAddress); //add edit text to alert dialog
            alertdialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

            alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (edtAddress.getText().toString().length() == 0 || edtAddress.getText().toString().matches("")) {
                        Toast.makeText(getContext(), "Address can't be left blank!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        //create new request
                        Request request = new Request(
                                Common.currentUser.getPhone(),
                                Common.currentUser.getName(),
                                edtAddress.getText().toString(),
                                txtTotalPrice.getText().toString(),
                                cart
                        );
                        //submit to firebase
                        //using  System.CurrentMilli to key
                        requests.child(String.valueOf(System.currentTimeMillis()))
                                .setValue(request);
                        //delete cart
                        new Database(getContext()).cleanCart();
                        Toast.makeText(getContext(), "Order placed succesfully!", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }

                }
            });


            alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alertdialog.show();

        }


    }


    private void loadListFood() {
        cart = new Database(getContext()).getCarts();
        adapter = new CartAdapter(cart,getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //calculate total amount + currency
        int total= 0;
        for(Order order:cart)
            total+=(Integer.parseInt( order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","SE");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {

        //remove item at List<Order>
        cart.remove(position);
        //delete old data from SQLite
        new Database(getContext()).cleanCart();
        //finally, update new data from List<Order> to SQLite
        for (Order item:cart)
            new Database(getContext()).addToCart(item);
        //refresh
        loadListFood();

    }
}