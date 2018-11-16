package com.teamawsome.awsomeeat;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Model.Restaurant;

import javax.annotation.Nullable;

public class FirestoreMain extends AppCompatActivity {
    private int count = 0;

    private static final FirestoreMain FirestoreMain = new FirestoreMain();

    public static FirestoreMain getInstance () {
        return FirestoreMain;
    }

    private FirestoreMain () {

    }

        //Lägg till ny restaurang.
    //TODO: Lägg till möjlighet att manuellt skriva in restaurangnamn och adress.
    //TODO: Fixa bugg som gör att metoden körs i oändlighet.
        public void addRestaurant (FirebaseFirestore db, RestaurantRecyclerViewAdapter adapter) {


            db.collection("restaurants").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null){
                        return;
                    }

                    for (DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){

                            String id = dc.getDocument().getId();
                            Restaurant restaurant = dc.getDocument().toObject(Restaurant.class);
                            restaurant.id = id;
                            adapter.addItem(restaurant);
                            count++;
                            Restaurant r = new Restaurant("test"+ count, "Gamla sjövägen","http://medifoods.my/images/menu/p1_ginger_pao.jpg");
                            db.collection("restaurants")
                                    .add(r);
                            return;
                        }
                        else if (dc.getType() == DocumentChange.Type.REMOVED){
                            String id = dc.getDocument().getId();
                            adapter.removeResturant(id);
                        }
                    }


                }
            });


        }

        //Lägger till i vaurkorg
        public void addToCart () {

        }

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




