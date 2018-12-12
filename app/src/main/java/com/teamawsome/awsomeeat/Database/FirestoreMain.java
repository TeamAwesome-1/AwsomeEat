package com.teamawsome.awsomeeat.Database;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.Adapters.CartRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.FoodListRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.Helpers.IdHolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;




public class FirestoreMain {


    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private String category5;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Authentication authentication = Authentication.getInstance();
    private Restaurant restaurant;
    private Food food;
    private List<String> categories;
    private ListenerRegistration listenerforCounter, listenerForCategories, restaurantMenuListener, listenerForCartList,listenerForRestauranList;
    private IdHolder idHolder = IdHolder.getInstance();
    private int counter;


    private static final FirestoreMain FirestoreMain = new FirestoreMain();

    public static FirestoreMain getInstance () { return FirestoreMain;
    }

    private FirestoreMain () {
        getCategoriesFromDatabase();
    }


    public List<String> getCategories(){
        return categories;
    }

    public String getCategory1() {
        if(categories.size()>0) {
            category1 = categories.get(0);
            return category1;
        }
        return "default";
    }

    public String getCategory2() {
        if(categories.size()>1) {
            category2 = categories.get(1);
            return category2;
        }
        return "default";
    }

    public String getCategory3() {
        if(categories.size()>2) {
            category3 = categories.get(2);
            return category3;
        }
        return "default";
    }

    public String getCategory4() {
        if(categories.size()>3) {
            category4 = categories.get(3);
            return category4;
        }
        return "default";
    }

    public String getCategory5() {
        if(categories.size()>4) {
            category5 = categories.get(4);
            return category5;
        }
        return "default";
    }

    public int getCounter(){

        return counter;

    }



    //Lägg till ny restaurang.

    public void addRestaurant (String restaurantName, String restaurantAdress, String phoneNumber, String pictureUrl) {
        restaurant = new Restaurant(restaurantName, restaurantAdress, phoneNumber, pictureUrl);
        db.collection("restaurants")
                .add(restaurant);
        }

    public void updateRestaurant (String restaurantId, Restaurant restaurant) {
        DocumentReference updatedRestaurant = db.collection("restaurants").document(restaurantId);
        updatedRestaurant.set(restaurant);
        }

    public void deleteRestaurant (String restaurantId, Restaurant restaurant) {
            DocumentReference updatedRestaurant = db.collection("restaurants").document(restaurantId);
            updatedRestaurant.delete();

        }

    public void updateCounter(final Runnable r){

         listenerforCounter = db.collection("Cart").whereEqualTo("userId", authentication.getCurrentUser().getUid())
                 .addSnapshotListener(new EventListener<QuerySnapshot>() {
                     @Override
                     public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                         if (e != null) {
                             return;
                         }
                         for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                             if (dc.getType() == DocumentChange.Type.ADDED) {

                                 Order order = dc.getDocument().toObject(Order.class);
                                int i =  Integer.parseInt(order.getQuantity());
                                counter += i;


                             } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                                 Order order = dc.getDocument().toObject(Order.class);
                                 int i =  Integer.parseInt(order.getQuantity());
                                 counter = counter-i;


                             } else if (dc.getType() == DocumentChange.Type.MODIFIED) {


                             }
                         }
                         r.run();

                     }
                 });

     }

    private void getCategoriesFromDatabase(){
        categories = new ArrayList<>();

        listenerForCategories= db.collection("Categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                categories.add(dc.getDocument().getId());

                            } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                                for (int i = 0; i <categories.size() ; i++) {
                                   if(categories.get(i).equals(dc.getDocument().getId())){
                                      categories.remove(i);
                                   }
                                }
                            } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                for (int i = 0; i <categories.size() ; i++) {
                                    if(categories.get(i).equals(dc.getDocument().getId())){
                                        categories.set(i, dc.getDocument().getId());
                                    }
                                }

                            }
                        }

                    }
                });
        }


    public void addToCart (Order order) {

            db.collection("Cart")
                    .add(order);
        }


    public void getCartList(CartRecyclerViewAdapter adapter, String userId){

        listenerForCartList= db.collection("Cart").whereEqualTo("userId", userId)
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

   public void getRestaurantList(RestaurantRecyclerViewAdapter adapter){

            listenerForRestauranList = db.collection("restaurants").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    //Hämtar alla maträtter som hör till en specifik restaurangs matkategori
    public void getMenuForRestaurantCategory(FoodListRecyclerViewAdapter adapter, String restaurantId, String categoryId){

        listenerForCategories = db.collection("Foods").whereEqualTo("restaurantId", restaurantId).whereEqualTo("Category", categoryId)
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

    public void getRestaurantMenu (FoodListRecyclerViewAdapter adapter, String restaurantId) {

            restaurantMenuListener = db.collection("Foods").whereEqualTo("restaurantId", restaurantId)
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

    //Lägger till en ny maträtt i "Foods" collection.
    public void addFood (String name, String price, String category, String picUrl) {
        food = new Food(name, price, idHolder.getRestaurantId(), category, picUrl);

        db.collection("Foods").add(food);
        }

    public void changeFood(Food food ) {
        DocumentReference foods = db.collection("Foods").document(food.getId());
          foods.set(food);
    }

    public void deleteFood (Food food) {
        DocumentReference foods = db.collection("Foods").document(food.getId());
        foods.delete();


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

    public void detachListenerForCounter(){
        listenerforCounter.remove();
    }

    public void detachListenerForCategories() {
        listenerForCategories.remove();
    }

    public void detachListenerForRestaurantMenu() {
        restaurantMenuListener.remove();
    }

    public void detachListenerForCartList(){
        listenerForCartList.remove();
    }

    public void detachListenerForRestaurantList(){
        listenerForRestauranList.remove();
    }
}




