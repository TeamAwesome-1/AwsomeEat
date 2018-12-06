package com.teamawsome.awsomeeat.Admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.Adapters.CartRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.CategoryListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.idHolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


//TODO Förenkla metoderna och undvik återupprepning av kod. /Sandra

public class FirestoreMain extends AppCompatActivity {
    private int count = 0;

    private String Category1;
    private String Category2;
    private String Category3;
    private String Category4;
    private String Category5;

    private static final FirestoreMain FirestoreMain = new FirestoreMain();

    public static FirestoreMain getInstance () { return FirestoreMain;
    }



    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Restaurant restaurant;

    private Food food;



    private CollectionReference foods = db.collection("foods");

    private ListenerRegistration listenerRegistration;



    private FirestoreMain () {


    }

    public String getCategory1() {
        return "Chinese";
    }

    public String getCategory2() {
        return "European";
    }

    public String getCategory3() {

        return "Pizza";
    }

    public String getCategory4() {
        return "Swedish";
    }

    public String getCategory5() {
        return "Sandwiches";
    }

    //Lägg till ny restaurang.

        public void addRestaurant (String restaurantName, String restaurantAdress, String phoneNumber) {


                  restaurant = new Restaurant(restaurantName, restaurantAdress, phoneNumber);
                            db.collection("restaurants")
                                    .add(restaurant);





        }

        //Lägger till i vaurkorg
        public void addToCart (Order order) {

            db.collection("Cart")
                    .add(order);


        }





        public void getCartList(CartRecyclerViewAdapter adapter, String userId){

        listenerRegistration= db.collection("Cart").whereEqualTo("userId", userId)
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
                            order.setDocumentId(id);
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

            listenerRegistration = db.collection("restaurants").addSnapshotListener(new EventListener<QuerySnapshot>() {
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


            listenerRegistration = db.collection("Categories").whereArrayContains("restaurantId", restaurantId)
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


            listenerRegistration = db.collection("Foods").whereEqualTo("restaurantId", restaurantId)
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


         listenerRegistration = db.collection("Foods").whereEqualTo("restaurantId", restaurantId).whereEqualTo("Category", categoryId)
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
        public void addFood (String name, String price, String Category) {
        food = new Food(name, price, idHolder.getRestaurantId(), Category);

        db.collection("Foods").add(food);
        }

      public void changeFood(Food food ) {
        DocumentReference foods = db.collection("Foods").document(food.getId());
          foods.set(food);
    }


    public void addToOrders(List<Order> cartList) {

        for (int i = 0; i < cartList.size(); i++) {
            db.collection("Orders")
                    .add(cartList.get(i));
        }


    }

    public void clearCartItem ( String documentId){
        db.collection("Cart").document(documentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("AwesomeEat", "onSuccess: Delete successfull");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("AwesomeEat", "onFailure: Delete failed ");
                    }
                });
    }

    public void clearCart(List<Order> cartList){
        for (int i = 0; i < cartList.size(); i++) {
            Order order = cartList.get(i);
            String document  = order.getDocumentId();
            clearCartItem(document);
        }
    }

    public void detachSnapShotListener(){
        listenerRegistration.remove();
    }
}




