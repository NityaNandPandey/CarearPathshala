package com.career.pathshala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.career.pathshala.R;
import com.career.pathshala.api_call.CallWebForService;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.GlobalConstants;
import com.career.pathshala.api_call.MyServiceListener;

import org.json.JSONObject;

/**
 * Created by rupesh.m on 12/16/2016.
 */

public class SignInActivity extends AppCompatActivity {
    private Button BT_login, BT_sign_up, BT_Guest;
    private EditText mobile_number, user_password;
    private CommonFunctions commonFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initialization();
    }

    private void initialization() {
        commonFunctions = new CommonFunctions(SignInActivity.this);
        BT_sign_up = (Button) findViewById(R.id.BT_sign_up);
        BT_login = (Button) findViewById(R.id.BT_login);
        mobile_number = (EditText) findViewById(R.id.mobile_number);
        user_password = (EditText) findViewById(R.id.user_password);
        BT_Guest = (Button) findViewById(R.id.BT_Guest);


        BT_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobilenumber = mobile_number.getText().toString();
                String userpassword = user_password.getText().toString();
                if (mobile_number.getText().toString().equals("")) {
                    Toast.makeText(SignInActivity.this, "Please enter a mobile no.", Toast.LENGTH_SHORT).show();
                } else if (user_password.getText().toString().equals("")) {
                    Toast.makeText(SignInActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                } else {
                    LoginAPI(mobilenumber, userpassword);
                }
            }
        });

        BT_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
        BT_Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        });
    }

    private void LoginAPI(String mobile, String password) {
        new CallWebForService("Loading...", SignInActivity.this, commonFunctions.urlList.LOGIN, commonFunctions.LogIn(mobile, password), new MyServiceListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("Login--->", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("CommandResult");

                    JSONObject LoginData = jsonObject2.getJSONObject("data");

                    String user_id = LoginData.getString("id");
                    String firstname = LoginData.getString("fullname");
                    String IsActive = LoginData.getString("IsActive");
                    String Mobile = LoginData.getString("Mobile");
                    String Email = LoginData.getString("Email");
                    String success = jsonObject2.optString("success");
                    String Message = jsonObject2.optString("message");

                    if (success.equals("1")) {
                        commonFunctions.myPreference.setString(SignInActivity.this, GlobalConstants.USER_ID, user_id);
                        commonFunctions.myPreference.setString(SignInActivity.this, GlobalConstants.FIRSTNAME, firstname);
                        commonFunctions.myPreference.setString(SignInActivity.this, GlobalConstants.ISACTIVE, IsActive);
                        commonFunctions.myPreference.setString(SignInActivity.this, GlobalConstants.MOBILE, Mobile);
                        commonFunctions.myPreference.setString(SignInActivity.this, GlobalConstants.EMAIL, Email);
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));

                    } else {
                        Toast.makeText(SignInActivity.this, Message, Toast.LENGTH_SHORT).show();
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

