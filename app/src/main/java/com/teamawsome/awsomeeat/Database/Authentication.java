package com.teamawsome.awsomeeat.Database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.teamawsome.DelayedProgressDialog;
import com.teamawsome.awsomeeat.Admin.FirestoreMain;
import com.teamawsome.awsomeeat.AwsomeEatActivity;
import com.teamawsome.awsomeeat.Fragments.AdminMainFragment;
import com.teamawsome.awsomeeat.Fragments.RestaurantListFragment;
import com.teamawsome.awsomeeat.Fragments.userFragment;
import com.teamawsome.awsomeeat.MainActivity;
import com.teamawsome.awsomeeat.Model.User;
import com.teamawsome.awsomeeat.R;
import com.teamawsome.awsomeeat.SignIn;
import com.teamawsome.awsomeeat.SignUp;
import com.teamawsome.awsomeeat.SignUpProfileFields;

public class Authentication {

    private static final Authentication Authentication = new Authentication();
    private static final String TAG = "User";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirestoreMain firestoreMain = FirestoreMain.getInstance();
    private CollectionReference userCollection = db.collection("User");
    private static User user;
    private static FirebaseUser currentUser;
    private FirebaseUser dbUser;
    public String adress;
    private static FirebaseAuth mAuth;
    private boolean admin;
    private Fragment fragment;

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }

    private FirebaseAuth.AuthStateListener mAuthListener;

    public void signIn(String email, String password, Context context){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,
                password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               currentUser = FirebaseAuth.getInstance().getCurrentUser();
               mAuth = mAuth = FirebaseAuth.getInstance();
                Log.d(TAG, "OnComplete: ");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupFirebaseAuth(Activity context){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                mAuth = mAuth = FirebaseAuth.getInstance();
                if (currentUser != null){
                    getUserAdress();
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + currentUser.getUid());
                    Intent intent = new Intent(context, AwsomeEatActivity.class);
                    context.startActivity(intent);
                    context.finish();
                }else{
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    public void registerEmail(String email, String password, Activity context){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DelayedProgressDialog progressDialog = new DelayedProgressDialog();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //authentication.loadAuthData();
                            currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            mAuth = FirebaseAuth.getInstance();
                            Intent intent = new Intent(context, SignUpProfileFields.class);
                            context.startActivity(intent);
                            context.finish();
                            Toast.makeText(context, context.getString(R.string.registration_succeeded), Toast.LENGTH_SHORT).show();
                            progressDialog.show( ((AppCompatActivity) context).getSupportFragmentManager(), "Loading");
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, context.getString(R.string.authentication_failer),
                                   Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

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
        DocumentReference docRef = userCollection.document(currentUser.getUid());

        if(docRef!= null) {
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {

                        boolean admin2 = document.getBoolean("admin");
                        setAdmin(admin2);
                        Log.d("User", "UserAdmin: " + admin2);

                        }
                    }
                }
            });
        }

    }

    public void openIntent(Activity context){
        DocumentReference docRef = userCollection.document(currentUser.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Intent intent = new Intent(context, AwsomeEatActivity.class);
                context.startActivity(intent);
                context.finish();

            }
        });
    }

    public void openFragment(FragmentManager fragmentManager){
        DocumentReference docRef = userCollection.document(currentUser.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (getAdress() == null){
                    setAdress("");
                    setAdmin(false);
                    Bundle bundle = new Bundle();
                    bundle.putString("Info_key", "Start");
                    fragment = new userFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragmentinsertlayout, fragment);
                    fragmentTransaction.commit();

                }else{
                    loadAuthData();

                    fragment = new RestaurantListFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragmentinsertlayout, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    public void getUserAdress(){
        DocumentReference docRef = userCollection.document(currentUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        uid = document.getString("uid");
                        String adress2 = document.getString("adress");
                        setAdress(adress2);
                        Log.d("User", "UserAdress: " + adress2);

                    }
                }
            }
        });
    }

    public void editUserAdress(String adress, String uid){
        user = new User(adress, uid);
       user.setAdmin(isAdmin());
        userCollection.document(currentUser.getUid()).set(user);

    }

    public void setUserAdress(String adress, String uid){
        user = new User(adress, uid);
        user.setAdmin(false);
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

    public void setUserName(String name){
        if (currentUser != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build();

            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d(TAG, "onComplete: User updated");

                            }
                        }
                    });
        }
    }

    public void getUserDetails(){
        if (currentUser != null){
            String uid = currentUser.getUid();
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            String properties = "uid: " + uid + " name " + name + " email " + email;

            Log.d(TAG, "Userdetails: " + properties);
        }
    }

    public void loadAuthData(){
        getUserAdress();
        checkAdminState();

    }

    public void checkAuthState(Activity context){
        Log.d(TAG,"checkAuthState");

        if(currentUser == null){
            Log.d(TAG, "user is null, sent back to login");

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            context.finish();
        }else{
            Log.d(TAG, "user is authenticated, user id: " + currentUser.getUid());
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

    public void addStateListener(){
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    public void removeStateListener(){
        if (mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    public void logOut (){
        currentUser = null;
        mAuth = null;
        setAdress("");
        FirebaseAuth.getInstance().signOut();
        //mAuth = null;
        Log.d(TAG, "Signed out:");
    }

}
