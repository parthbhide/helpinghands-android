package com.example.helpinghands.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helpinghands.R;

public class ContactUs extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf =  inflater.inflate(R.layout.activity_contact_us, container, false);

        TextView contactadd = (TextView) inf.findViewById(R.id.contact_address);
        contactadd.setText("Bhopal, India");
        contactadd.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        TextView contactnum = (TextView) inf.findViewById(R.id.contact_number);
        contactnum.setText("+918878544603"+"\n"+"Sat and Sun | 9 AM to 5 PM");
        contactnum.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        TextView contactemail = (TextView) inf.findViewById(R.id.contact_mail);
        contactemail.setText("parthbhide391@gmail.com"+"\n"+"Send your query anytime !");
        contactemail.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        return inf;
    }

}