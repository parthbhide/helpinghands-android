package com.example.helpinghands.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.helpinghands.R;
import com.example.helpinghands.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class DonorHome extends AppCompatActivity implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{

    private FirebaseAuth mAuth;
    private String name;
    private Integer cloth_qty;
    private Integer footwear_qty;
    private Integer stationary_qty;

    private ExpandableLayout expandableLayout1;
    private ExpandableLayout expandableLayout2;
    private ExpandableLayout expandableLayout3;
    private ExpandableLayout expandableLayout4;
    private Button registerForCollectionDrive;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);
        Toolbar toolbar = findViewById(R.id.donor_toolbar);
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
                    TextView tx = (TextView) findViewById(R.id.donor_name);
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
                    TextView dbClothsQty = (TextView) findViewById(R.id.db_cloths_qty);
                    dbClothsQty.setText("Available Qty : "+ Integer.toString(cloth_qty));

                    footwear_qty = dataSnapshot.child("Stock").child("footwear").getValue(Integer.class);
                    TextView dbFootwearQty = (TextView) findViewById(R.id.db_footwear_qty);
                    dbFootwearQty.setText("Available Qty : "+ Integer.toString(footwear_qty));

                    stationary_qty = dataSnapshot.child("Stock").child("stationary").getValue(Integer.class);
                    TextView dbStationaryQty = (TextView) findViewById(R.id.db_stationary_qty);
                    dbStationaryQty.setText("Available Qty : "+ Integer.toString(stationary_qty));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        Query getCollectionDriveDates = reference.orderByChild("CollectionDrive");
        getCollectionDriveDates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RadioButton rb1 = findViewById(R.id.date1);
                rb1.setText(dataSnapshot.child("CollectionDrive").child("date1").getValue(String.class));
                RadioButton rb2 = findViewById(R.id.date2);
                rb2.setText(dataSnapshot.child("CollectionDrive").child("date2").getValue(String.class));
                RadioButton rb3 = findViewById(R.id.date3);
                rb3.setText(dataSnapshot.child("CollectionDrive").child("date3").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //GETTING USER DETAILS FORM FIREBASE ENDS

        //SETTING UP UI ELEMENTS FOR DONOR LAYOUT
        expandableLayout1 = findViewById(R.id.donor_expandable_layout_0);
        expandableLayout2 = findViewById(R.id.donor_expandable_layout_1);
        expandableLayout3 = findViewById(R.id.donor_expandable_layout_2);
        expandableLayout4 = findViewById(R.id.donor_expandable_layout_3);
        TextView donateCloths = (TextView) findViewById(R.id.donate_cloths);
        TextView donateFootwear = (TextView) findViewById(R.id.donate_footwear);
        TextView donateStationary = (TextView) findViewById(R.id.donate_stationary);
        TextView collectionDriveDates = (TextView) findViewById(R.id.donor_collection_drive_date);
        registerForCollectionDrive = findViewById(R.id.reg_for_cd);
        ConstraintLayout dcl = findViewById(R.id.donor_cl);

        donateCloths.setText("Enter details to donate cloths");
        donateFootwear.setText("Enter details to donate footwear");
        donateStationary.setText("Enter details to donate stationary");
        collectionDriveDates.setText("Select Date For Collection");
        registerForCollectionDrive.setText("Register");

        findViewById(R.id.donate_cloths).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.donate_footwear).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.donate_stationary).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.donor_collection_drive_date).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.donor_cl).setOnClickListener((View.OnClickListener) this);

        expandableLayout1.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout2.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout3.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout4.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);

        ImageView lgbtn = findViewById(R.id.donor_logoutbtn);

        //END OF SETTING UP UI ELEMENTS FOR DONOR LAYOUT

        lgbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(DonorHome.this,"Logged Out !",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(DonorHome.this, App_Drawer.class);
                        startActivity(i);
                        finish();
                    }
                }
        );


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.donor_cl)
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
        else if(view.getId() == R.id.donate_cloths)
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
        else if (view.getId() == R.id.donate_footwear)
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
        else if (view.getId() == R.id.donate_stationary)
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