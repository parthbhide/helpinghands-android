package com.example.helpinghands.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.helpinghands.Activity.DonorHome;
import com.example.helpinghands.Activity.ReceiverHome;
import com.example.helpinghands.Activity.VolunteerHome;
import com.example.helpinghands.R;
import com.example.helpinghands.Fragments.userDetailsValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.HashMap;


public class RegisterFragment extends Fragment implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{

    ExpandableLayout expandableLayout0;
    ExpandableLayout expandableLayout1;
    ExpandableLayout expandableLayout2;
    ExpandableLayout expandableLayout3;
    RadioGroup radioGroup;
    Button register;

    //Variables for User details
    RadioButton rb;
    EditText firstName;
    EditText lastName;
    EditText regNum;
    EditText address;
    EditText mobNumber;
    EditText email;
    EditText password;
    EditText confPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    ProgressDialog pd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf =  inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(getActivity());

        radioGroup=(RadioGroup)inf.findViewById(R.id.radiobtn);
        EditText regnum = (EditText) inf.findViewById(R.id.regnum);
        Space sp = inf.findViewById(R.id.sp);

        TextView userType = (TextView) inf.findViewById(R.id.user_type);
        TextView personalDetails = (TextView) inf.findViewById(R.id.personal_details);
        TextView contactDetails = (TextView) inf.findViewById(R.id.contact_details);
        TextView userIdPassword = (TextView) inf.findViewById(R.id.user_id_pass);
        TextView regTitle = (TextView) inf.findViewById(R.id.reg_title);

        userType.setText("Choose Your User Type");
        personalDetails.setText("Enter Your Personal Details");
        contactDetails.setText("Enter Your Contact Details");
        userIdPassword.setText("Enter Email and Password");
        regTitle.setText("Lend Your Hand");

        inf.findViewById(R.id.user_type).setOnClickListener(this);
        inf.findViewById(R.id.personal_details).setOnClickListener(this);
        inf.findViewById(R.id.contact_details).setOnClickListener(this);
        inf.findViewById(R.id.user_id_pass).setOnClickListener(this);

        expandableLayout0 = inf.findViewById(R.id.register_expand1);
        expandableLayout1 = inf.findViewById(R.id.register_expand2);
        expandableLayout2 = inf.findViewById(R.id.register_expand3);
        expandableLayout3 = inf.findViewById(R.id.register_expand4);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId)
                {
                    case R.id.isreceiver:
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Registering As Receiver ?")
                                .setMessage("Registration number for the organization is necessary")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        regnum.setVisibility(View.VISIBLE);
                        sp.setVisibility(View.VISIBLE);

                        break;
                    default:
                        regnum.setVisibility(View.INVISIBLE);
                        sp.setVisibility(View.INVISIBLE);
                }
            }
        });

        firstName = inf.findViewById(R.id.firstname);
        lastName = inf.findViewById(R.id.lastname);
        regNum = inf.findViewById(R.id.regnum);
        address = inf.findViewById(R.id.address);
        mobNumber = inf.findViewById(R.id.phone);
        email = inf.findViewById(R.id.user_email);
        password = inf.findViewById(R.id.user_pass);
        confPassword = inf.findViewById(R.id.conf_pass);

        register = inf.findViewById(R.id.reg_btn);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean checked = userDetailsValidation.checkDetails(firstName.getText().toString(),lastName.getText().toString(),
                        address.getText().toString(),mobNumber.getText().toString(),password.getText().toString(),
                        confPassword.getText().toString(),email.getText().toString());


                if(checked == false || (radioGroup.getCheckedRadioButtonId() != R.id.isdonor && radioGroup.getCheckedRadioButtonId() != R.id.isreceiver && radioGroup.getCheckedRadioButtonId() != R.id.isvolunteer) )
                    Toast.makeText(getActivity(),"All fields are mandatory !!", Toast.LENGTH_SHORT).show();
                else
                {
                    if(userDetailsValidation.isValidEmail(email.getText().toString()) == false)
                        Toast.makeText(getActivity(),"Invalid Email Format", Toast.LENGTH_SHORT).show();

                    else if (userDetailsValidation.isValidMobile(mobNumber.getText().toString()) == false)
                        Toast.makeText(getActivity(),"Invalid Phone Format", Toast.LENGTH_SHORT).show();

                    else if (userDetailsValidation.isValidPassword(password.getText().toString(),
                            confPassword.getText().toString()) == false)
                        Toast.makeText(getActivity(),"Invalid Password or Password did not match", Toast.LENGTH_SHORT).show();

                    else if (radioGroup.getCheckedRadioButtonId() == R.id.isreceiver && regNum.getText().toString().isEmpty())
                        Toast.makeText(getActivity(),"Registration number cannot be empty !", Toast.LENGTH_SHORT).show();

                    else
                    {

                        registerUser(firstName.getText().toString(),lastName.getText().toString(),
                                address.getText().toString(),mobNumber.getText().toString(),password.getText().toString(),
                                confPassword.getText().toString(),email.getText().toString(), regNum.getText().toString(), radioGroup.getCheckedRadioButtonId());
                    }
                }


            }

            private void registerUser(String firstName, String lastName, String address, String mobNumber, String password, String confPassword, String email, String regNum, int userType) {

                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                pd.setMessage("Please wait while we save your details !");
                pd.show();



                mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("firstname",firstName);
                        map.put("lastname",lastName);
                        map.put("address",address);
                        map.put("contact",mobNumber);
                        map.put("email",email);
                        switch(userType)
                        {
                            case R.id.isreceiver:
                                map.put("usertype","receiver");
                                map.put("regnumber",regNum);
                                break;
                            case R.id.isdonor:
                                map.put("usertype","donor");
                                break;
                            case R.id.isvolunteer:
                                map.put("usertype","volunteer");
                                break;
                        }
                        map.put("userid",mAuth.getCurrentUser().getUid());

                        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                pd.dismiss();

                                if(task.isSuccessful())
                                    Toast.makeText(getActivity(),"User Details Saved !!", Toast.LENGTH_SHORT).show();

                                switch(userType)
                                {
                                    case R.id.isreceiver:
                                        Intent intent1 = new Intent(getActivity(), ReceiverHome.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent1);
                                        getActivity().finish();
                                        break;
                                    case R.id.isdonor:
                                        Intent intent2 = new Intent(getActivity(), DonorHome.class);
                                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent2);
                                        getActivity().finish();
                                        break;
                                    case R.id.isvolunteer:
                                        Intent intent3 = new Intent(getActivity(), VolunteerHome.class);
                                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent3);
                                        getActivity().finish();
                                        break;
                                }

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();

                        Snackbar.make(getView(),e.getMessage(),Snackbar.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return inf;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.user_type)
        {
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();

            if(expandableLayout0.isExpanded())
                expandableLayout0.collapse();
            else
                expandableLayout0.expand();
        }
        else if(view.getId() == R.id.personal_details)
        {
            if(expandableLayout0.isExpanded())
                expandableLayout0.collapse();
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();

            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            else
                expandableLayout1.expand();
        }
        else if(view.getId() == R.id.contact_details)
        {
            if(expandableLayout0.isExpanded())
                expandableLayout0.collapse();
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();

            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();
            else
                expandableLayout2.expand();
        }
        else
        {
            if(expandableLayout0.isExpanded())
                expandableLayout0.collapse();
            if(expandableLayout1.isExpanded())
                expandableLayout1.collapse();
            if(expandableLayout2.isExpanded())
                expandableLayout2.collapse();

            if(expandableLayout3.isExpanded())
                expandableLayout3.collapse();
            else
                expandableLayout3.expand();
        }

    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {

    }
}

