package com.example.helpinghands.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.helpinghands.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DonorHome extends AppCompatActivity implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    private String name;
    private Integer cloth_qty;
    private Integer footwear_qty;
    private Integer stationary_qty;

    private ExpandableLayout expandableLayout1;
    private ExpandableLayout expandableLayout2;
    private ExpandableLayout expandableLayout3;
    private ExpandableLayout expandableLayout4;
    private Button registerForCollectionDrive;

    ProgressDialog pd;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);
        Toolbar toolbar = findViewById(R.id.donor_toolbar);
        setSupportActionBar(toolbar);

        //GETTING USER DETAILS FORM FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);

        registerForCollectionDrive = findViewById(R.id.reg_for_cd);

        //CHECK IF USER IS ALREADY REGISTERED TO AN DRIVE
        DatabaseReference donatesItemIn = FirebaseDatabase.getInstance().getReference().child("DonatesItemIn");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Date today = new Date();
                today.getTime();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String userid = ds.child("userid").getValue(String.class);
                    String date = ds.child("date").getValue(String.class);
                    if(userid.equals(mAuth.getCurrentUser().getUid()))
                    {
                        SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
                        Date d = null;
                        try {
                            d = sd.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(d != null && today.before(d))
                        {
                            new AlertDialog.Builder(DonorHome.this)
                                    .setTitle("Already Registered !")
                                    .setMessage("You are already registered for a drive on "+date+" !")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).setIcon(android.R.drawable.ic_dialog_alert).show();

                            registerForCollectionDrive.setEnabled(false);
                            registerForCollectionDrive.setBackgroundColor(Color.GRAY);
                            break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        donatesItemIn.addListenerForSingleValueEvent(valueEventListener);

        //END OF CHECK IF USER IS ALREADY REGISTERED TO AN DRIVE


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

        //END OF SETTING UP UI ELEMENTS FOR DONOR LAYOUT

        //SETTING UP BUTTONS

        ImageView lgbtn = findViewById(R.id.donor_logoutbtn);

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


        Button donor_reg = findViewById(R.id.reg_for_cd);

        donor_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.setMessage("Please wait while we register you for a collection drive;");
                pd.show();

                String userEnteredClothsQty = ((TextView) findViewById(R.id.user_cloths_qty)).getText().toString();
                String userEnteredClothsDesc = ((TextView) findViewById(R.id.user_cloths_desc)).getText().toString();
                String userEnteredFootwearQty = ((TextView) findViewById(R.id.user_footwear_qty)).getText().toString();
                String userEnteredFootwearDesc = ((TextView) findViewById(R.id.user_footwear_desc)).getText().toString();
                String userEnteredStationaryQty = ((TextView) findViewById(R.id.user_stationary_qty)).getText().toString();
                String userEnteredStationaryDesc = ((TextView) findViewById(R.id.user_stationary_desc)).getText().toString();
                RadioGroup radioGroup = findViewById(R.id.radio_collection_drive_date);

                if(userEnteredClothsQty.isEmpty() || userEnteredFootwearQty.isEmpty() || userEnteredStationaryQty.isEmpty())
                {
                    pd.dismiss();
                    new AlertDialog.Builder(DonorHome.this)
                            .setTitle("Quantity can't be empty !")
                            .setMessage("Quantity can't be empty, enter zero (0) instead !")
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }

                else if(radioGroup.getCheckedRadioButtonId() != R.id.date1 && radioGroup.getCheckedRadioButtonId() != R.id.date2  &&
                        radioGroup.getCheckedRadioButtonId() != R.id.date3 )
                {
                    pd.dismiss();
                    new AlertDialog.Builder(DonorHome.this)
                            .setTitle("Invalid Date !")
                            .setMessage("Please select a valid date to register !")
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
                else
                {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("clothsqty",Integer.valueOf(userEnteredClothsQty));
                    map.put("clothsdesc",userEnteredClothsDesc);
                    map.put("footwearqty",Integer.valueOf(userEnteredFootwearQty));
                    map.put("footweardesc",userEnteredFootwearDesc);
                    map.put("stationaryqty",Integer.valueOf(userEnteredStationaryQty));
                    map.put("stationarydisc",userEnteredStationaryDesc);
                    map.put("userid",mAuth.getCurrentUser().getUid());
                    map.put("date", ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());

                    String val = mAuth.getCurrentUser().getUid() +":-:"+ ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

                    mRootRef.child("DonatesItemIn").child(val).setValue(map).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    pd.dismiss();
                                   if(task.isSuccessful())
                                   {
                                       Toast.makeText(DonorHome.this,"Registered to Collection Drive !",Toast.LENGTH_SHORT).show();
                                       registerForCollectionDrive.setEnabled(false);
                                       registerForCollectionDrive.setBackgroundColor(Color.GRAY);
                                   }

                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            pd.dismiss();
                            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }



            }
        });

        //END OF SETTING UP BUTTONS


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