package com.example.helpinghands.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpinghands.Activity.MainActivity;
import com.example.helpinghands.R;
import com.example.helpinghands.Fragments.Second;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link First#newInstance} factory method to
 * create an instance of this fragment.
 */
public class First extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View inf;

    RadioGroup radioGroup;
    RadioButton selectedbtn;

    public First() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment First.
     */
    // TODO: Rename and change types and number of parameters
    public static First newInstance(String param1, String param2) {
        First fragment = new First();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inf =  inflater.inflate(R.layout.fragment_first, container, false);

        radioGroup=(RadioGroup)inf.findViewById(R.id.radiobtn);
        EditText regnum = (EditText) inf.findViewById(R.id.regnum);
        Space sp = inf.findViewById(R.id.sp);

        First f = new First();
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "This is from activity first");
        f.setArguments(bundle);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId)
                {
                    case R.id.isreceiver:

                        regnum.setVisibility(View.VISIBLE);
                        sp.setVisibility(View.VISIBLE);

                        break;
                    default:
                        regnum.setVisibility(View.INVISIBLE);
                        sp.setVisibility(View.INVISIBLE);
                }
            }
        });
        return inf;
    }

}