package com.example.helpinghands.Fragments;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

public class userDetailsValidation {


    public static boolean checkDetails(String fName, String lName, String add, String mob, String pass,
                                       String cnfPass, String email)
    {
        if(fName.isEmpty() || lName.isEmpty() || add.isEmpty() || mob.isEmpty() || pass.isEmpty() || cnfPass.isEmpty()
            || email.isEmpty())
            return false;

        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static boolean isValidPassword(String pass, String cnfpass)
    {
        if(pass.length() > 5 && pass.equals(cnfpass))
            return true;

        return false;
    }

    public static boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }


}
