package com.teamawsome.awsomeeat.Database;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.awsomeeat.Model.User;
import com.teamawsome.awsomeeat.SignIn;

public class Authentication extends AppCompatActivity {

    private static final Authentication Authentication = new Authentication();
    private static final String TAG = "User";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection("User");
    private static User user;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseUser dbUser;
    public String adress;
    private boolean admin;

    public static Authentication getInstance() { return Authentication;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin(){
        return admin;
    }

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

    public UserInformation getUserInformation() {
        Log.d("User", "userInformation: " + userInformation);
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    UserInformation userInformation;

    public void checkAdminState(){
        dbUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("User").document(dbUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        boolean admin2 = document.getBoolean("admin");
                        setAdmin(admin2);
                        Log.d("User", "UserAdmin: " + admin2);

                        //}
                    }
                }
            }
        });
    }

    public void getUserAdress(){
        dbUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("User").document(dbUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        uid = document.getString("uid");
                        //if (uid.equals(user)){
                        String adress2 = document.getString("adress");
                        setAdress(adress2);
                        Log.d("User", "UserAdress: " + adress2);

                        //}
                    }
                }
            }
        });
    }

    public void setAdminDB(boolean b){
        user = new User(currentUser.getUid(), b);
        userCollection.document(currentUser.getUid()).set(user);
    }

    public void setUserAdress(String adress, String uid){
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String id = user.getUid();

        // userModel = new User(adress, id);
        dbUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User(adress, uid);
        userCollection.document(currentUser.getUid()).set(user);

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

    public String getUserName(){
        String name = "";
        if (currentUser != null){
            name = currentUser.getDisplayName();
        }
        return name;
    }

    public String getUserEmail(){
        String email = "";
        if (currentUser != null){
            email = currentUser.getEmail();
        }
        return email;
    }

    public void setUserDetails(String name){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "onComplete: User updated");

                                getUserDetails();
                            }
                        }
                    });
        }
    }

    public void getUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            String uid = user.getUid();
            String name = user.getDisplayName();
            String email = user.getEmail();


            String properties = "uid: " + uid + " name " + name + " email " + email;

            Log.d(TAG, "Userdetails: " + properties);
        }
    }

    public void loadAuthData(){
        setAdminDB(false);
        //getUserAdress();
        //checkAdminState();

    }

    public void checkAuthState(Activity activity){
        Log.d(TAG,"checkAuthState");

        if(currentUser == null){
            Log.d(TAG, "user is null, sent back to login");

            Intent intent = new Intent(activity, SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "user is authenticated");
        }
    }

    private void sendResetPassword(Activity activity){
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Reset Password Link sent");
                            Toast.makeText(activity, "Sent Reset Password Link to Email", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG, "onComplete: No User with that email");
                            Toast.makeText(activity, "No user with that email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
