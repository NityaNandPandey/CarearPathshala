package com.career.pathshala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.R;
import com.career.pathshala.api_call.CallWebForService;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.GlobalConstants;
import com.career.pathshala.api_call.MyServiceListener;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    private TextView signup_loginText;
    private EditText user_name, user_email, user_mobile, user_password, user_confirm_pass;
    private CommonFunctions commonFunctions;
    private RelativeLayout signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        commonFunctions = new CommonFunctions(SignUpActivity.this);
        initialization();

    }

    private void initialization() {
        signup_loginText = (TextView) findViewById(R.id.signup_loginText);
        user_name = (EditText) findViewById(R.id.user_name);
        user_email = (EditText) findViewById(R.id.user_email);
        user_mobile = (EditText) findViewById(R.id.user_mobile);
        user_password = (EditText) findViewById(R.id.user_password);
        user_confirm_pass = (EditText) findViewById(R.id.user_confirm_pass);
        signupBtn = (RelativeLayout) findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user_name.getText().toString();
                String useremail = user_email.getText().toString();
                String usermobile = user_mobile.getText().toString();
                String userpassword = user_password.getText().toString();
                String userconfirm_pass = user_confirm_pass.getText().toString();
                if (user_name.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter a user name.", Toast.LENGTH_SHORT).show();
                } else if (user_email.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter a email.", Toast.LENGTH_SHORT).show();
                } else if (user_mobile.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter a mobile no.", Toast.LENGTH_SHORT).show();
                } else if (user_password.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                } else if (user_confirm_pass.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
                } else if (!user_password.getText().toString().equals(user_confirm_pass.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Password doesn't match.", Toast.LENGTH_SHORT).show();
                }


                else {
                    String Firebasetoken=commonFunctions.myPreference.getString(SignUpActivity.this,GlobalConstants.Firebasetoken);
                    RegisterAPI(username, useremail, usermobile, userpassword, userconfirm_pass,Firebasetoken);
                }
            }
        });

        signup_loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }

    private void RegisterAPI(String username, String useremail, String usermobile, String userpassword, String userconfirm_pass,String Firebasetoken) {
        new CallWebForService("Loading...", SignUpActivity.this, commonFunctions.urlList.REGISTER, commonFunctions.Register(username, useremail,usermobile,userpassword,userconfirm_pass,Firebasetoken), new MyServiceListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("Register--->", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("CommandResult");
                    String success = jsonObject2.optString("success");
                    String Message = jsonObject2.optString("message");

                    if (success.equals("1")) {
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, Message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
