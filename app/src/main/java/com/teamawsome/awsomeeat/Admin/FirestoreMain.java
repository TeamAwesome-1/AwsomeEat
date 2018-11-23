package com.teamawsome.awsomeeat.Admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.awsomeeat.Adapters.RestaurantRecyclerViewAdapter;
import com.teamawsome.awsomeeat.Database.UserInformation;
import com.teamawsome.awsomeeat.Model.Restaurant;
import com.teamawsome.awsomeeat.Model.User;

import java.util.ArrayList;
import java.util.List;

public class FirestoreMain extends AppCompatActivity {
    private int count = 0;

    private static final FirestoreMain FirestoreMain = new FirestoreMain();

    public static FirestoreMain getInstance () { return FirestoreMain;
    }

    private List<RestaurantAdmin> itemList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection("user2");
    private Restaurant restaurant;
    private static User user;
    FirebaseUser dbUser;

    public String getUid() {
        return uid;
    }

    private String uid;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String adress;

    public UserInformation getUserInformation() {
        Log.d("User", "userInformation: " + userInformation);
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    UserInformation userInformation;

    private FirestoreMain () {


    }

        //Lägg till ny restaurang.

        public void addRestaurant (String restaurantName, String restaurantAdress) {


                  restaurant = new Restaurant(restaurantName, restaurantAdress);
                            db.collection("restaurants")
                                    .add(restaurant);



        }

        public void getUserAdress(){
            dbUser = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference docRef = db.collection("user2").document(dbUser.getUid());

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            uid = document.getString("uid");
                            //if (uid.equals(user)){
                                String adress2 = document.getString("adress");
                                setAdress(adress2); // Blir null
                                Log.d("User", "UserAdress: " + adress2); // adress2 skriver ut adressen från databasen i loggen

                            //}
                        }
                }
                }
            });
        }

        public void setUserAdress(String adress, String uid){
            //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //String id = user.getUid();

            // userModel = new User(adress, id);
            dbUser = FirebaseAuth.getInstance().getCurrentUser();
            user = new User(adress, uid);
            userCollection.document(dbUser.getUid()).set(user);

        }

        public void setUserId(String name){
            dbUser = FirebaseAuth.getInstance().getCurrentUser();
            if (dbUser != null){
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();

                dbUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("User", "onComplete: User name: " + dbUser.getDisplayName());

                                }
                            }
                        });
            }
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




