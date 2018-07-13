package com.example.android.tabswithswipes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by qawbecrdteyf on 08-09-2018.
 */


class FireBaseMethods {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();
    //DatabaseReference myRef2 = database.getReference("user_info");
    static int usernameexists = 0;
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    static boolean isUnique = true;
    static String[] returnedvalues2;
    static ArrayList<Friends> returnedFriends;


    public static void insertInDataBase(final String getEmailId, final String getusername, final String getFullName) {
        Log.d("Enter", "yes");
        Models user = new Models(getEmailId, getusername);
        Models2 userinfo = new Models2(getEmailId, getFullName, getusername);
        String UserID = auth.getCurrentUser().getUid();
        Log.d("userid", UserID);
        //Log.d("tree", myRef.child("users").child("YkH8avdKK3TJ9h9YxsWq5zqPCTH2").child("email_adr").toString());
        myRef.child("users").child(UserID).setValue(user);
        myRef.child("user_info").child(UserID).setValue(userinfo);
        Log.d("added", "account is added in db");
        //return isUnique;
    }

    public static boolean CheckForUsernameInDataBase(final String getusername) {


        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("check username unique", getusername);

                Models usertemp = new Models();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("snapshot", ds.toString());

                    usertemp.setUsername(ds.getValue(Models.class).getUsername());
                    Log.d("username of temp", usertemp.getUsername());
                    if (usertemp.getUsername().equals(getusername)) {
                        Log.d("entring", "shit");
                        usernameexists = 1;
                        isUnique = false;
                        break;
                    }

                }

                /*if(usernameexists == 0){

                }else{
                    Log.d("usernameexists", "User name is taken");
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        return isUnique;
    }


    public static void reinit() {
        isUnique = true;
    }

    public static void updateName(String nameofperson) {
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("name").setValue(nameofperson);
        //myRef.child("user")
    }

    public static void updateprofpic(String s) {
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("profile_photo").setValue(s);
    }

    public static void updateBio(String biotext) {
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("bio").setValue(biotext);

    }

    public static void getData(final String[] returnedvalues) {
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("bio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues[2] = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("profile_photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues[1] = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues[0] = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public static String[] readData(final FirebaseCallback firebaseCallback){
        returnedvalues2 = new String[5];
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues2[0] = (String) dataSnapshot.child("bio").getValue();
                returnedvalues2[1] = (String) dataSnapshot.child("email_adr").getValue();
                returnedvalues2[2] = (String) dataSnapshot.child("name").getValue();
                returnedvalues2[3] = (String) dataSnapshot.child("profile_photo").getValue();
                returnedvalues2[4] = (String) dataSnapshot.child("username").getValue();
                firebaseCallback.onCallback(returnedvalues2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return returnedvalues2;

    }

    public static ArrayList<Friends> readFriends(final FirebaseCallback2 firebaseCallback2){
        returnedFriends = new ArrayList<Friends>();
        myRef.child("user_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("snapshot", ds.getKey().toString());
                    Log.d("snapshot2", auth.getCurrentUser().getUid());
                    if(!(ds.getKey().equals(auth.getCurrentUser().getUid()))){
                        Friends currentFriend = new Friends();
                        currentFriend.setProfile_photo(ds.getValue(Friends.class).getProfile_photo());
                        Log.d("FRIENDSFORDS", ds.getValue(Models2.class).getUsername());
                        currentFriend.setName(ds.getValue(Friends.class).getName());
                        currentFriend.setUsername(ds.getValue(Friends.class).getUsername());
                        //Log.d("username of temp", currentFriend.getUsername());
                        returnedFriends.add(currentFriend);
                    }
                }

                firebaseCallback2.onCallback(returnedFriends);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        return returnedFriends;
    }


    /*public static String[] readData(final FirebaseCallback firebaseCallback) {
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues2[3] = (String) dataSnapshot.getValue();
                firebaseCallback.onCallback(returnedvalues2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("bio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues2[1] = (String) dataSnapshot.getValue();
                firebaseCallback.onCallback(returnedvalues2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("profile_photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues2[2] = (String) dataSnapshot.getValue();
                firebaseCallback.onCallback(returnedvalues2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues2[0] = (String) dataSnapshot.getValue();
                Log.d("nameretrivingfirst", returnedvalues2[0]);
                firebaseCallback.onCallback(returnedvalues2[]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("email_adr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedvalues2[4] = (String) dataSnapshot.getValue();
                firebaseCallback.onCallback(returnedvalues2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //firebaseCallback.onCallback(returnedvalues2);
        Log.d("retrievedvalues", returnedvalues2[0] + returnedvalues2[1]);
        return returnedvalues2;

    }*/


    /*public static String readData2(final FirebaseCallback firebaseCallback) {
        myRef.child("user_info").child(auth.getCurrentUser().getUid()).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                returnedString = (String) dataSnapshot.getValue();
                firebaseCallback.onCallback(returnedString);
                Log.d("FIREBASECALLACK", returnedString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Log.d("MSGIN READDATA2", returnedString);
        return returnedString;
    }*/
}
