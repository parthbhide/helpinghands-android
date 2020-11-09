package com.example.helpinghands.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.HashMap;

public class VolunteerHome extends AppCompatActivity implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    private String name;

    private ExpandableLayout expandableLayout1;
    private ExpandableLayout expandableLayout2;
    private Button volunteerDonationDriveReg;
    private Button volunteerCollectionDriveReg;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        Toolbar toolbar = findViewById(R.id.volunteer_toolbar);
        setSupportActionBar(toolbar);

        //GETTING USER DETAILS FORM FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);

        DatabaseReference user_reference = FirebaseDatabase.getInstance().getReference().child("Users");

        Query getUser = user_reference.orderByChild("userid").equalTo(mAuth.getCurrentUser().getUid());

        getUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    name = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("firstname").getValue(String.class);
                    TextView tx = (TextView) findViewById(R.id.volunteer_name);
                    tx.setText("Welcome, "+name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query getCollectionDriveDates = reference.orderByChild("CollectionDrive");
        getCollectionDriveDates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RadioButton rb1 = findViewById(R.id.date7);
                rb1.setText(dataSnapshot.child("CollectionDrive").child("date1").getValue(String.class));
                RadioButton rb2 = findViewById(R.id.date8);
                rb2.setText(dataSnapshot.child("CollectionDrive").child("date2").getValue(String.class));
                RadioButton rb3 = findViewById(R.id.date9);
                rb3.setText(dataSnapshot.child("CollectionDrive").child("date3").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query getDonationDriveDates = reference.orderByChild("DonationDrive");
        getDonationDriveDates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                RadioButton rb1 = findViewById(R.id.date10);
                rb1.setText(dataSnapshot.child("DonationDrive").child("date1").getValue(String.class));
                RadioButton rb2 = findViewById(R.id.date11);
                rb2.setText(dataSnapshot.child("DonationDrive").child("date2").getValue(String.class));
                RadioButton rb3 = findViewById(R.id.date12);
                rb3.setText(dataSnapshot.child("DonationDrive").child("date3").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //END OF GETTING USER DETAILS FORM FIREBASE


        //SETTING UP UI ELEMENTS FOR VOLUNTEER LAYOUT
        expandableLayout1 = findViewById(R.id.volunteer_expandable_layout_0);
        expandableLayout2 = findViewById(R.id.volunteer_expandable_layout_1);
        TextView collectionDriveDates = (TextView) findViewById(R.id.volunteer_collection_drive_date);
        TextView donationDriveDates = (TextView) findViewById(R.id.volunteer_donation_drive_date);
        ConstraintLayout vcl = findViewById(R.id.volunteer_cl);
        volunteerCollectionDriveReg = findViewById(R.id.volunteer_reg_cd);
        volunteerDonationDriveReg = findViewById(R.id.volunteer_reg_dd);

        collectionDriveDates.setText("Select date to register in collection drive");
        donationDriveDates.setText("Select date to register in donation drive");
        volunteerDonationDriveReg.setText("Register for Donation Drive");
        volunteerCollectionDriveReg.setText("Register for Collection Drive");

        findViewById(R.id.volunteer_collection_drive_date).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.volunteer_donation_drive_date).setOnClickListener((View.OnClickListener) this);
        findViewById(R.id.volunteer_cl).setOnClickListener((View.OnClickListener) this);

        expandableLayout1.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout2.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);

        //END OF SETTING UP UI ELEMENTS FOR VOLUNTEER LAYOUT

        //SETTING UP BUTTONS

        ImageView lgbtn = findViewById(R.id.volunteer_logoutbtn);

        lgbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(VolunteerHome.this,"Logged Out !",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(VolunteerHome.this, App_Drawer.class);
                        startActivity(i);
                        finish();
                    }
                }
        );



        volunteerCollectionDriveReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                pd.setMessage("Please wait while we register you for a drive;");
                pd.show();

                RadioGroup radioGroup1 = findViewById(R.id.volunteer_radio_collection_drive_date);

                if(radioGroup1.getCheckedRadioButtonId() != R.id.date7 && radioGroup1.getCheckedRadioButtonId() != R.id.date8  &&
                        radioGroup1.getCheckedRadioButtonId() != R.id.date9)
                {
                    pd.dismiss();
                    new AlertDialog.Builder(VolunteerHome.this)
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
                        map.put("userid", mAuth.getCurrentUser().getUid());
                        map.put("date", ((RadioButton) findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString());
                        String val = mAuth.getCurrentUser().getUid() +":-:"+ ((RadioButton) findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString();

                        mRootRef.child("CollectedBy").child(val).setValue(map).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        if(task.isSuccessful())
                                        {
                                            pd.dismiss();
                                            Toast.makeText(VolunteerHome.this,"Registered to a Drive !",Toast.LENGTH_SHORT).show();
                                            volunteerCollectionDriveReg.setEnabled(false);
                                            volunteerCollectionDriveReg.setBackgroundColor(Color.GRAY);

                                        }

                                    }
                                }
                        ).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                pd.dismiss();
                                Snackbar.make(view1,e.getMessage(),Snackbar.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



                }

            }
        });

        volunteerDonationDriveReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.setMessage("Please wait while we register you for a drive;");
                pd.show();

                RadioGroup radioGroup2 = findViewById(R.id.volunteer_radio_donation_drive_date);
                if(radioGroup2.getCheckedRadioButtonId() != R.id.date10 && radioGroup2.getCheckedRadioButtonId() != R.id.date11  &&
                        radioGroup2.getCheckedRadioButtonId() != R.id.date12)
                {
                    pd.dismiss();
                    new AlertDialog.Builder(VolunteerHome.this)
                            .setTitle("Invalid Date !")
                            .setMessage("Please select a valid date to register !")
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
                else {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userid", mAuth.getCurrentUser().getUid());
                    map.put("date", ((RadioButton) findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString());
                    String val = mAuth.getCurrentUser().getUid()
                            + ":-:"
                            + ((RadioButton) findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString();
                    mRootRef.child("DonatedBy").child(val).setValue(map).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        Toast.makeText(VolunteerHome.this, "Registered to a Drive !", Toast.LENGTH_SHORT).show();
                                        volunteerDonationDriveReg.setEnabled(false);
                                        volunteerDonationDriveReg.setBackgroundColor(Color.GRAY);

                                    }

                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            pd.dismiss();
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
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

        if(view.getId() == R.id.volunteer_cl)
        {
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();

            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
        }

        else if (view.getId() == R.id.volunteer_collection_drive_date)
        {
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();

            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            else
                expandableLayout1.expand();
        }
        else
        {
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();

            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            else
                expandableLayout2.expand();
        }

    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {

    }
}