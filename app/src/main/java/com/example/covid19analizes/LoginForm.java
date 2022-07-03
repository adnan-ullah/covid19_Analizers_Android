package com.example.covid19analizes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jgabrielfreitas.core.BlurImageView;

import javax.xml.datatype.Duration;

public class LoginForm extends AppCompatActivity {
BlurImageView blurImageView;
Button signUp,signIn;
EditText email, pass;
Boolean EditTextEmptyHolder;
DBHandler loginDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        loginDb = new DBHandler(this);

        blurImageView = findViewById(R.id.imageViewLoginbg);

        signUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.signIn);
        email = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            CheckEditTextStatus();

            if(EditTextEmptyHolder==true) {
                if (loginDb.loginStatus(email.getText().toString()).equalsIgnoreCase(pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), StartingActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();

                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please Fill All The Required Fields", Toast.LENGTH_LONG).show();
            }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void CheckEditTextStatus(){

        if(TextUtils.isEmpty(email.getText().toString() ) || TextUtils.isEmpty(pass.getText().toString()) ){
            EditTextEmptyHolder = false ;
        }
        else {

            EditTextEmptyHolder = true ;
        }
    }
}
