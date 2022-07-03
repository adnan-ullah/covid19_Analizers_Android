package com.example.covid19analizes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    Button signUp,signIn;
    DBHandler registerDB;
    EditText name, email, pass, confirmpass;
    Boolean EditTextEmptyHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        signUp = findViewById(R.id.registerSignUp);
        signIn = findViewById(R.id.registerSignIn);

        registerDB = new DBHandler(this);

        name = findViewById(R.id.nameUser);
        email = findViewById(R.id.emailUser);
        pass = findViewById(R.id.passUser);
        confirmpass = findViewById(R.id.confirmPassUser);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> ar;
                ar= new ArrayList<>();



                ar.add(name.getText().toString());
                ar.add(email.getText().toString());
                ar.add(pass.getText().toString());
                ar.add(confirmpass.getText().toString());

                CheckEditTextStatus();
                if(EditTextEmptyHolder==true)
                {
                    if(registerDB.checkEmail(email.getText().toString()).equalsIgnoreCase("Email Found"))
                    {
                        Toast.makeText(getApplicationContext(),"Email already exist!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(pass.getText().toString().equalsIgnoreCase(confirmpass.getText().toString()))
                        {
                            if(isEmailValid(email.getText().toString()))
                            {
                                registerDB.addUser(ar);
                                Toast.makeText(getApplicationContext(),"User Registered Successfully", Toast.LENGTH_SHORT).show();
                                EmptyEditTextAfterDataInsert();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Make sure email address is valid or not!", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Password and Confirm Password not matched!", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Fill All The Required Fields", Toast.LENGTH_SHORT).show();
                }
                showingData();
            }
        });


    }




    public void CheckEditTextStatus(){

        if(TextUtils.isEmpty(name.getText().toString() ) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty( pass.getText().toString())|| TextUtils.isEmpty(confirmpass.getText().toString())){
            EditTextEmptyHolder = false ;
        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void EmptyEditTextAfterDataInsert(){

        name.getText().clear();

        email.getText().clear();

        pass.getText().clear();
        confirmpass.getText().clear();

    }


    public void showingData() {

        Cursor cursor = registerDB.readAdminData();

            while (cursor.moveToNext()) {
                for (int i = 0; i < 4; i++) {
                    Log.d("key1233", cursor.getString(i));
                }


            }

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }







}