package com.teamawsome.awsomeeat.Admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
import com.teamawsome.awsomeeat.AwsomeEatActivity;
import com.teamawsome.awsomeeat.Database.Authentication;
import com.teamawsome.awsomeeat.EventHandler;
import com.teamawsome.awsomeeat.Fragments.CartFragment;
import com.teamawsome.awsomeeat.Model.Category;
import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Order;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.idHolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


//TODO Förenkla metoderna och undvik återupprepning av kod. kolla på addmetoderna /Sandra

public class FirestoreMain extends AppCompatActivity {


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
    private CollectionReference foods = db.collection("foods");
    private ListenerRegistration listenerRegistration;
    private ListenerRegistration listenerforCounter, listenerForCategories, restaurantMenuListener, listenerForCartList,listenerForRestauranList;
    private int counterIcon;
    private EventHandler eventHandler = EventHandler.getInstance();
    private View view;
    private int counter;

    public int getCounterIcon() {
        return counterIcon;
    }

    public void setCounterIcon(int counterIcon) {
        this.counterIcon = counterIcon;
    }

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


    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter(){

        return counter;

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

    /**
     * Adds items to Cart-collection
     * @param order -The orderobjekt that will be added to cart
     */
    public void addToCart (Order order) {

            db.collection("Cart")
                    .add(order);
        }

    /**
     * Loads the cart for currentuser
      * @param adapter - carts RecyclerViewAdpater
     * @param userId- current user id.
     */
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

    /**
     * Loads a list of all restaurants from database
      * @param adapter -the RestaurantRecyclerViewAdapter that will display the list of restaurants
     */
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

    //TODO ska dehär vara med? /Sandra
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

    /**
     * Loads a foodlist for a specific restaurant from database
     * @param adapter- FoodlistRecyclerViewAdapter that will display the list
     * @param restaurantId- the id of the restaurant
     */
    //OK
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

    //OK    //Lägger till en ny maträtt i "Foods" collection.
  public void addFood (String name, String price, String Category, String picUrl) {
        food = new Food(name, price, idHolder.getRestaurantId(), Category, picUrl);

        db.collection("Foods").add(food);
        }
    //OK
    public void changeFood(Food food ) {
        DocumentReference foods = db.collection("Foods").document(food.getId());
          foods.set(food);
    }
    //OK
    public void deleteFood (Food food) {
        DocumentReference foods = db.collection("Foods").document(food.getId());
        foods.delete();


    }
    //OK
    public void addToOrders(List<Order> cartList) {

        for (int i = 0; i < cartList.size(); i++) {
            db.collection("Orders")
                    .add(cartList.get(i));
        }
    }
    //OK
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
    //ok
    public void clearCart(List<Order> cartList){
        for (int i = 0; i < cartList.size(); i++) {
            Order order = cartList.get(i);
            String document  = order.getDocumentId();
            clearCartItem(document);
        }
    }

    public void detachSnapShotListener(){
        //ANVÄNDS för kategorilistan ---Ska bort
        listenerRegistration.remove();
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




