package com.example.helpinghands.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.helpinghands.Activity.DonorHome;
import com.example.helpinghands.Activity.ReceiverHome;
import com.example.helpinghands.Activity.VolunteerHome;
import com.example.helpinghands.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {

    EditText email;
    EditText password;
    Button loginButton;

    private FirebaseAuth auth;
    private FirebaseAuth mAuth;

    ProgressDialog pd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf =  inflater.inflate(R.layout.fragment_login, container, false);

        email = inf.findViewById(R.id.login_email);
        password = inf.findViewById(R.id.login_password);
        loginButton = inf.findViewById(R.id.login_btn);

        auth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(getActivity());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                    Snackbar.make(getView(),"All fields are mandatory !!",Snackbar.LENGTH_LONG).show();

                else
                {
                    pd.setMessage("Please wait while we log you in !");
                    pd.show();
                    loginUser(email.getText().toString(), password.getText().toString());
                }
            }
        });

        return inf;
    }

    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

                Query getUser = reference.orderByChild("userid").equalTo(mAuth.getCurrentUser().getUid());

                getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String userType = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("usertype").getValue(String.class);
                        switch(userType)
                        {
                            case "receiver":
                                pd.dismiss();
                                Intent intent1 = new Intent(getActivity(), ReceiverHome.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                getActivity().finish();
                                break;
                            case "donor":
                                pd.dismiss();
                                Intent intent2 = new Intent(getActivity(), DonorHome.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent2);
                                getActivity().finish();
                                break;
                            case "volunteer":
                                pd.dismiss();
                                Intent intent3 = new Intent(getActivity(), VolunteerHome.class);
                                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent3);
                                getActivity().finish();
                                break;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        pd.dismiss();

                    }
                });




//                Toast.makeText(getActivity(),"Login Scucessful !",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), DonorHome.class);
//                startActivity(intent);
//                getActivity().finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();
                Snackbar.make(getView(),e.getMessage(),Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}