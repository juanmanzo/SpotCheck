package com.ucsb.cs48.spotcheck.SCFirebaseInterface;


import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ucsb.cs48.spotcheck.SCLocalObjects.ParkingSpot;
import com.ucsb.cs48.spotcheck.SCLocalObjects.SpotCheckUser;

import java.util.UUID;

public class SCFirebase {

    private DatabaseReference scDatabase;

    public SCFirebase() {
        scDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /// MARK - Parking Spot Interface
    // Create a new parking spot in the database
    public String createNewSpot(ParkingSpot spot) {
        String newSpotID = "spot-" + UUID.randomUUID().toString();

        scDatabase.child("parking_spots").child(newSpotID).setValue(spot);

        return newSpotID;
    }

    // Get a parking spot from the data base
    public void getParkingSpot(String spotID,
        @NonNull final SCFirebaseCallback<ParkingSpot> finishedCallback) {

        DatabaseReference myRef = scDatabase.child("parking_spots/");

        myRef.child(spotID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ParkingSpot spot = dataSnapshot.getValue(ParkingSpot.class);
                finishedCallback.callback(spot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }


    /// MARK - User Interface
    // Create a new user in database
    // SHOULD ONLY BE USED WHEN REGISTERING A NEW USER
    public void uploadUser(SpotCheckUser user) {
        scDatabase.child("users").child(user.getUserID()).setValue(user);
    }

    // Get a user from the database
    public void getSCUser(String userID,
                          @NonNull final SCFirebaseCallback<SpotCheckUser> finishedCallback) {

        DatabaseReference myRef = scDatabase.child("users/");

        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SpotCheckUser user = dataSnapshot.getValue(SpotCheckUser.class);
                finishedCallback.callback(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }
}
