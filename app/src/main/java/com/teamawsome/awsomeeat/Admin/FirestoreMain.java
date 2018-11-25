package com.teamawsome.awsomeeat.Admin;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.teamawsome.awsomeeat.Adapters.CartRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.CategoryListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.R;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FirestoreMain extends AppCompatActivity {
    private int count = 0;

    private static final FirestoreMain FirestoreMain = new FirestoreMain();

    public static FirestoreMain getInstance () { return FirestoreMain;
    }

    private List<RestaurantAdmin> itemList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Restaurant restaurant;

    private Food food = new Food();

    private FirestoreMain () {


    }

        //Lägg till ny restaurang.

        public void addRestaurant (String restaurantName, String restaurantAdress) {


                  restaurant = new Restaurant(restaurantName, restaurantAdress);
                            db.collection("restaurants")
                                    .add(restaurant);

        }

        //Lägger till i vaurkorg
        public void addToCart (Order order) {

            db.collection("Cart")
                    .add(order);


    }

    //    public RestaurantAdmin ()


        public void getCartList(CartRecyclerViewAdapter adapter, String userId){

            db.collection("Cart").whereEqualTo("userId", userId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {

                            String id = dc.getDocument().getId();
                            Order order = dc.getDocument().toObject(Order.class);
                            order.setProductId(id);
                            adapter.addItem(order);

                        } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                            String id = dc.getDocument().getId();
                            adapter.removeOrderListItem(id);

                        } else if (dc.getType() == DocumentChange.Type.MODIFIED) {

                            String id = dc.getDocument().getId();
                            Order order = dc.getDocument().toObject(Order.class);
                            adapter.modifyOrderListItem(id, order);
                    }
                    }

                }
            });



        }

        //Hämtar en lista på alla restauranger
        public void getRestaurantList(RestaurantRecyclerViewAdapter adapter){

            db.collection("restaurants").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            String id = dc.getDocument().getId();
                            Restaurant restaurant = dc.getDocument().toObject(Restaurant.class);
                            restaurant.setId(id);
                            adapter.addItem(restaurant);
                        } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                            String id = dc.getDocument().getId();
                            adapter.removeResturant(id);
                        }else if (dc.getType() == DocumentChange.Type.MODIFIED) {

                            String id = dc.getDocument().getId();
                            Restaurant restaurant = dc.getDocument().toObject(Restaurant.class);
                            restaurant.setId(id);
                            adapter.modifyRestaurant(id, restaurant);
                        }
                    }

                }
            });

        }

         //Hämtar en lista på alla matkategorier som finns att välja på i vald restaurang.
        public void getCategoriesForRestaurant(CategoryListRecyclerViewAdapter adapter, String restaurantId){


        db.collection("Categories").whereArrayContains("restaurantId", restaurantId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }
                            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {

                                    String id = dc.getDocument().getId();
                                    Category category = dc.getDocument().toObject(Category.class);
                                    category.setId(id);
                                    adapter.addCategoryItem(category);

                                } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                    String id = dc.getDocument().getId();
                                    Category category = dc.getDocument().toObject(Category.class);
                                    adapter.modifyItem(id, category);

                                } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                                    String id = dc.getDocument().getId();
                                    adapter.removeMenuItem(id);
                                }
                            }
                        }
                    });

        }


        //Hämtar vald restaurangs meny.
        public void getRestaurantMenu (FoodListRecyclerViewAdapter adapter, String restaurantId) {


            db.collection("Foods").whereArrayContains("restaurantId", restaurantId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null){
                                return;
                            }
                            for (DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED){

                                    String id = dc.getDocument().getId();
                                    Food food = dc.getDocument().toObject(Food.class);
                                    food.setId(id);
                                    adapter.addItem(food);
                                }
                                else if(dc.getType() == DocumentChange.Type.MODIFIED){

                                    String id = dc.getDocument().getId();
                                    Food food = dc.getDocument().toObject(Food.class);
                                    adapter.modifyItem(id, food);
                                }

                                else if (dc.getType() == DocumentChange.Type.REMOVED){
                                    String id = dc.getDocument().getId();
                                    adapter.removeMenuItem(id);
                                }
                            }
                        }
                    });

        }


        //Hämtar alla maträtter som hör till en specifik restaurangs matkategori
         public void getMenuForRestaurantCategory(FoodListRecyclerViewAdapter adapter, String restaurantId, String categoryId){


         db.collection("Foods").whereArrayContains("restaurantId", restaurantId).whereEqualTo("Category", categoryId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null){
                                return;
                            }
                            for (DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                                if (dc.getType() == DocumentChange.Type.ADDED){

                                    String id = dc.getDocument().getId();
                                    Food food = dc.getDocument().toObject(Food.class);
                                    food.setId(id);
                                    adapter.addItem(food);
                                }
                                else if(dc.getType() == DocumentChange.Type.MODIFIED){

                                    String id = dc.getDocument().getId();
                                    Food food = dc.getDocument().toObject(Food.class);
                                    adapter.modifyItem(id, food);
                                }

                                else if (dc.getType() == DocumentChange.Type.REMOVED){
                                    String id = dc.getDocument().getId();
                                    adapter.removeMenuItem(id);
                                }
                            }
                        }
                    });


        }


        //Söker efter en maträtt
        public void searchFood () {


        }


        //Rensar listan med restauranger.
        public void clearRestaurants (FirebaseFirestore db, RestaurantRecyclerViewAdapter adapter) {



        }
        //Lägger till en ny maträtt i "Foods" collection.
        public void addFood (String name, String category) {
        food = new Food(name, category);
        //food.Category = category;
        db.collection("Foods").add(food);
        }


    }




