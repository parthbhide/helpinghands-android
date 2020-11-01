 package com.example.helpinghands.Fragments.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.helpinghands.R;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import net.cachapa.expandablelayout.ExpandableLayout;

public class HomeFragment extends Fragment implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{

    ExpandableLayout expandableLayout0;
    ExpandableLayout expandableLayout1;
    ExpandableLayout expandableLayout;
    ImageView expandButton;
    ExpandableLayout expandableLayout2;
    ImageView expandButton2;
    ExpandableLayout expandableLayout3;
    ImageView expandButton3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = (TextView) root.findViewById(R.id.hometextview);

        textView.setText("At Helping Hands we believe that" + "\n" + "all in the world have the right" + "\n" + "to be");
        textView.animate();

        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) root.findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        Rotatable rotatable = new Rotatable(Color.parseColor("#ff404c"), 1000, "CARED", "PROTECTED", "HAPPY");
        rotatable.setSize(25);
        rotatable.setCenter(true);
        rotatable.setAnimationDuration(350);

        rotatingTextWrapper.setContent("?", rotatable);

        expandableLayout0 = root.findViewById(R.id.expandable_layout_0);
        expandableLayout1 = root.findViewById(R.id.expandable_layout_1);

        TextView ddbtn = (TextView) root.findViewById(R.id.donation_date_btn);
        TextView cdbtn = (TextView) root.findViewById(R.id.collection_drive_date);

        TextView ddtxt = (TextView) root.findViewById(R.id.donation_date_text);
        TextView cdtxt = (TextView) root.findViewById(R.id.collection_drive_text);

        ddbtn.setText("Upcoming Donation Drive Dates");
        cdbtn.setText("Upcoming Collection Drive Dates");

        ddtxt.setText("No Dates Scheduled !");
        cdtxt.setText("No Dates Scheduled !");

        root.findViewById(R.id.donation_date_btn).setOnClickListener(this);
        root.findViewById(R.id.collection_drive_date).setOnClickListener(this);


        expandableLayout = root.findViewById(R.id.expandable_layout1);
        expandButton = root.findViewById(R.id.expand_button1);

        expandableLayout2 = root.findViewById(R.id.expandable_layout2);
        expandButton2 = root.findViewById(R.id.expand_button2);

        expandableLayout3 = root.findViewById(R.id.expandable_layout3);
        expandButton3 = root.findViewById(R.id.expand_button3);


        TextView exptx1 = (TextView) root.findViewById(R.id.exptxt1);
        TextView exptx2 = (TextView) root.findViewById(R.id.exptxt2);
        TextView exptx3 = (TextView) root.findViewById(R.id.exptxt3);

        exptx1.setText("Enter Your details, item details and schedule a pickup."+"\n"+"You are done ! Let us do the rest !");
        exptx2.setText("Our Volunteer will reach you once a collection drive is scheduled !");
        exptx3.setText("Inspires others to support causes they care about !");

        expandableLayout.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout2.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandableLayout3.setOnExpansionUpdateListener((ExpandableLayout.OnExpansionUpdateListener) this);
        expandButton.setOnClickListener(this);
        expandButton2.setOnClickListener(this);
        expandButton3.setOnClickListener(this);


        return  root;
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {

        expandButton.setRotation(expansionFraction * 360);
        expandButton2.setRotation(expansionFraction * 360);
        expandButton3.setRotation(expansionFraction * 360);
    }

    @Override
    public void onClick(View view) {

       if(view.getId() == R.id.expand_button1 || view.getId() == R.id.expand_button2 || view.getId() == R.id.expand_button3)
       {
           if(expandableLayout0.isExpanded())
               expandableLayout0.collapse();
           if(expandableLayout1.isExpanded())
               expandableLayout1.collapse();

           if (view.getId() == R.id.expand_button1)
           {
               expandableLayout.toggle();
               if(expandableLayout2.isExpanded())
                   expandableLayout2.toggle();
               if(expandableLayout3.isExpanded())
                   expandableLayout3.toggle();
           }

           else if(view.getId() == R.id.expand_button2)
           {
               if(expandableLayout.isExpanded())
                   expandableLayout.toggle();
               if(expandableLayout3.isExpanded())
                   expandableLayout3.toggle();
               expandableLayout2.toggle();
           }

           else
           {
               if(expandableLayout.isExpanded())
                   expandableLayout.toggle();
               if(expandableLayout2.isExpanded())
                   expandableLayout2.toggle();
               expandableLayout3.toggle();
           }
       }

        else
        {
            if(expandableLayout.isExpanded())
                expandableLayout.toggle();
            if(expandableLayout2.isExpanded())
                expandableLayout2.toggle();
            if(expandableLayout3.isExpanded())
                expandableLayout3.toggle();

            if (view.getId() == R.id.donation_date_btn) {
                if(expandableLayout0.isExpanded())
                    expandableLayout0.collapse();
                else
                {
                    expandableLayout0.expand();
                    expandableLayout1.collapse();
                }

            }
            else {
                if(expandableLayout1.isExpanded())
                    expandableLayout1.collapse();
                else
                {
                    expandableLayout0.collapse();
                    expandableLayout1.expand();
                }

            }
        }

    }
}