package com.teamawsome.awsomeeat.Admin;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Restaurant;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class FirestoreMain extends AppCompatActivity {
    private int count = 0;

    private static final FirestoreMain FirestoreMain = new FirestoreMain();

    public static FirestoreMain getInstance () { return FirestoreMain;
    }

    private List<RestaurantAdmin> itemList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Restaurant restaurant;

    private FirestoreMain () {


    }

        //Lägg till ny restaurang.

        public void addRestaurant (String restaurantName, String restaurantAdress) {


                  restaurant = new Restaurant(restaurantName, restaurantAdress);
                            db.collection("restaurants")
                                    .add(restaurant);



        }

        //Lägger till i vaurkorg
        public void addToCart () {

        }

    //    public RestaurantAdmin ()

        //Hämtar vald restaurangs meny.
        public void getRestaurantMenu () {


        }

        //Söker efter en maträtt
        public void searchFood () {


        }


        //Rensar listan med restauranger.
        public void clearRestaurants (FirebaseFirestore db, RestaurantRecyclerViewAdapter adapter) {



        }


    }




