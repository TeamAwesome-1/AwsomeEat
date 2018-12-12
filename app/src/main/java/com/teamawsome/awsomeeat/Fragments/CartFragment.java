package com.teamawsome.awsomeeat.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.teamawsome.awsomeeat.Adapters.CartRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Database.FirestoreMain;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.R;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;


public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView txtTotalPrice;
    private Button btnPlace;
    private List<Order> cartList = new ArrayList<>();
    private CartRecyclerViewAdapter adapter;
    private FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private static Authentication authentication = Authentication.getInstance();


    public CartFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_cart, container, false);

        //Init
        recyclerView = view.findViewById(R.id.listCart);
        txtTotalPrice = view.findViewById(R.id.total);
        btnPlace = view.findViewById(R.id.btnPlaceOrder);
        //set the adapter for the recyclerview
        adapter = new CartRecyclerViewAdapter(cartList, txtTotalPrice);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Load Cartlist from firestore
        firestoreMain.getCartList(adapter, authentication.getCurrentUser().getUid());

        //Set clicklistener for the placeOrderButton
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartList.size() > 0)
                    showAlertDialog();
                else
                    Toast.makeText(getContext(), getString(R.string.cart_is_empty), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showAlertDialog() {
        if (cartList.size() <= 0)  //checking if cart is empty or not
        {
            Toast.makeText(getContext(), getString(R.string.add_some_items_to_cart), Toast.LENGTH_SHORT).show();


        } else {
            //Displays a AlertDialog with a question if current user surely wants to place the orders
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
            alertdialog.setTitle(R.string.place_order);
            alertdialog.setMessage(R.string.do_you_want_to_place_order);
            alertdialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

            //Inserts a Yes-button with a clicklistener
            alertdialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    placeOrder();
                    Toast.makeText(getContext(), R.string.Order_placed_successfully, Toast.LENGTH_SHORT).show();
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



    //Moves all cartItems to the Order-collection in database and clears the cart
    private void placeOrder() {
        //Adds all orders in cartlist to Order-collection i firestore
        firestoreMain.addToOrders(cartList);
        //Clears the Cartobjects for currentuser
        firestoreMain.clearCart(cartList);
    }

   @Override
   public void onStop() {
       super.onStop();
       adapter.clearList();
       firestoreMain.detachListenerForCartList();
   }

    @Override
    public void onResume() {
        super.onResume();
        authentication.checkAuthState(getActivity());

    }

}
