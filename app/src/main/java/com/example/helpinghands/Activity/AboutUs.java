package com.example.helpinghands.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.helpinghands.R;





public class AboutUs extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf =  inflater.inflate(R.layout.activity_about_us, container, false);
        TextView abouttext3 = (TextView) inf.findViewById(R.id.about_textview3);
        TextView abouttext4 = (TextView) inf.findViewById(R.id.about_textview4);
        TextView abouttext5 = (TextView) inf.findViewById(R.id.about_textview5);
        TextView abouttext6 = (TextView) inf.findViewById(R.id.about_textview6);
        abouttext3.setText("Helping Hands provides you with the most convenient and easy way to donate those items that are just lying at some corner of your house," +
                "gathering dust and taking up some much-needed space."+"\n\n"+
                "It's time to donate them and give those books, toys, clothes etc a new life." + "\n\n");

        abouttext4.setText("There are many benefits to donating your old stuff to NGOs."+"\n\n"+
                "Items you donate support many great charitable causes" +"\n\n"+
                "Keep your stuff out of a landfill. Donations help the environment!" +"\n\n");

        abouttext5.setText("Items you donate support many great charitable causes" +"\n\n"+
                "Keep your stuff out of a landfill. Donations help the environment!" +"\n\n"+
               "Get organized! Clean out the clutter in your closets and garage." +"\n\n");

        abouttext6.setText("The feel good factor we all know the happiness we get by spreading happiness" +"\n\n"+
        "Get some amazing gifts from our brand partners" +"\n\n"+
        "… And much more !");

        abouttext3.setTypeface(null, Typeface.BOLD);
        abouttext3.setTextColor(Color.WHITE);
        abouttext4.setTypeface(null, Typeface.BOLD);
        abouttext4.setTextColor(Color.RED);
        abouttext5.setTypeface(null, Typeface.BOLD);
        abouttext5.setTextColor(Color.WHITE);
        abouttext6.setTypeface(null, Typeface.BOLD);
        abouttext6.setTextColor(Color.RED);

        return inf;
    }

}