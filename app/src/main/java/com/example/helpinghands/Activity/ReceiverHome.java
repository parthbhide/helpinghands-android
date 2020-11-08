package com.example.helpinghands.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpinghands.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ReceiverHome extends AppCompatActivity implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{

    private FirebaseAuth mAuth;

    private String name;
    private Integer cloth_qty;
    private Integer footwear_qty;
    private Integer stationary_qty;

    private ExpandableLayout expandableLayout1;
    private ExpandableLayout expandableLayout2;
    private ExpandableLayout expandableLayout3;
    private ExpandableLayout expandableLayout4;
    private Button registerForDonationDrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_home);
        Toolbar toolbar = findViewById(R.id.receiver_toolbar);
        setSupportActionBar(toolbar);

        //GETTING USER DETAILS FORM FIREBASE
        mAuth = FirebaseAuth.getInstance();

        DatabaseReference user_reference = FirebaseDatabase.getInstance().getReference().child("Users");

        Query getUser = user_reference.orderByChild("userid").equalTo(mAuth.getCurrentUser().getUid());

        getUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    name = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("firstname").getValue(String.class);
                    TextView tx = (TextView) findViewById(R.id.receiver_name);
                    tx.setText("Welcome, "+name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query getStock = reference.orderByChild("Stock");

        getStock.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cloth_qty = dataSnapshot.child("Stock").child("cloths").getValue(Integer.class);
                TextView dbClothsQty = (TextView) findViewById(R.id.receiver_db_cloths_qty);
                dbClothsQty.setText("Available Qty : "+ Integer.toString(cloth_qty));

                footwear_qty = dataSnapshot.child("Stock").child("footwear").getValue(Integer.class);
                TextView dbFootwearQty = (TextView) findViewById(R.id.receiver_db_footwear_qty);
                dbFootwearQty.setText("Available Qty : "+ Integer.toString(footwear_qty));

                stationary_qty = dataSnapshot.child("Stock").child("stationary").getValue(Integer.class);
                TextView dbStationaryQty = (TextView) findViewById(R.id.receiver_db_stationary_qty);
                dbStationaryQty.setText("Available Qty : "+ Integer.toString(stationary_qty));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query getDonationDriveDates = reference.orderByChild("DonationDrive");
        getDonationDriveDates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RadioButton rb1 = findViewById(R.id.date4);
                rb1.setText(dataSnapshot.child("DonationDrive").child("date1").getValue(String.class));
                RadioButton rb2 = findViewById(R.id.date5);
                rb2.setText(dataSnapshot.child("DonationDrive").child("date2").getValue(String.class));
                RadioButton rb3 = findViewById(R.id.date6);
                rb3.setText(dataSnapshot.child("DonationDrive").child("date3").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //GETTING USER DETAILS FORM FIREBASE ENDS

        //SETTING UP UI ELEMENTS FOR RECEIVER LAYOUT
        expandableLayout1 = findViewById(R.id.receiver_expandable_layout_0);
        expandableLayout2 = findViewById(R.id.receiver_expandable_layout_1);
        expandableLayout3 = findViewById(R.id.receiver_expandable_layout_2);
        expandableLayout4 = findViewById(R.id.receiver_expandable_layout_3);
        TextView donateCloths = (TextView) findViewById(R.id.receiver_cloths);
        TextView donateFootwear = (TextView) findViewById(R.id.receiver_footwear);
        TextView donateStationary = (TextView) findViewById(R.id.receiver_stationary);
        TextView collectionDriveDates = (TextView) findViewById(R.id.receiver_donation_drive_date);
        registerForDonationDrive = findViewById(R.id.reg_for_dd);
        ConstraintLayout rcl = findViewById(R.id.receiver_cl);

        donateCloths.setText("Enter details to receive cloths");
        donateFootwear.setText("Enter details to receive footwear");
        donateStationary.setText("Enter details to receive stationary");
        collectionDriveDates.setText("Select Date For Donation");
        registerForDonationDrive.setText("Register");

        findViewById(R.id.receiver_cloths).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.receiver_footwear).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.receiver_stationary).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.receiver_donation_drive_date).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.receiver_cl).setOnClickListener((View.OnClickListener) this);

        expandableLayout1.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout2.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout3.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout4.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);

        //END OF SETTING UP UI ELEMENTS FOR RECEIVER LAYOUT


        ImageView lgbtn = findViewById(R.id.receiver_logoutbtn);

        lgbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(ReceiverHome.this,"Logged Out !",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ReceiverHome.this, App_Drawer.class);
                        startActivity(i);
                        finish();
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.receiver_cl)
        {
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();
            if(expandableLayout4.isExpanded())
                expandableLayout4.collapse();
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
        }
        else if(view.getId() == R.id.receiver_cloths)
        {
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();
            if(expandableLayout4.isExpanded())
                expandableLayout4.collapse();

            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            else
                expandableLayout1.expand();
        }
        else if (view.getId() == R.id.receiver_footwear)
        {
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();
            if(expandableLayout4.isExpanded())
                expandableLayout4.collapse();

            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            else
                expandableLayout2.expand();
        }
        else if (view.getId() == R.id.receiver_stationary)
        {
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            if(expandableLayout4.isExpanded())
                expandableLayout4.collapse();

            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();
            else
                expandableLayout3.expand();
        }
        else
        {
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();

            if(expandableLayout4.isExpanded())
                expandableLayout4.collapse();
            else
                expandableLayout4.expand();
        }


    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {

    }
}