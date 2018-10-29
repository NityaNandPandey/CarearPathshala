package com.career.pathshala.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.R;
import com.career.pathshala.activity.MainActivity;
import com.career.pathshala.api_call.AppUtil;
import com.career.pathshala.api_call.CommonFunctions;
import com.career.pathshala.api_call.FieldUtils;
import com.career.pathshala.api_call.GlobalConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by omprakash.m on 4/10/2017.
 */

public class MangeProfile_Fragment extends Fragment {
    public static Toolbar toolbar;
    public String name, mail, phone, organization_name = "", personalorganizationname, countycode;
    @Bind(R.id.TV_ChangePass)
    TextView TVChangePass;
    @Bind(R.id.pencil)
    ImageView pencil;
    @Bind(R.id.ET_Name)
    EditText ETName;
    @Bind(R.id.ET_Phone)
    EditText ETPhone;
    @Bind(R.id.ET_Mail)
    EditText ETMail;
    @Bind(R.id.srollview)
    ScrollView srollview;
    @Bind(R.id.BT_Update)
    Button BTUpdate;
    @Bind(R.id.IV_delete)
    ImageView IVdelete;
    @Bind(R.id.IV_deactiveaccount)
    ImageView IVdeactiveaccount;
    @Bind(R.id.TV_countrycode)
    TextView TVcountrycode;


    String ReferralCode;
    private CommonFunctions cmf;
    private String inviteestatusid, CountryCodeId;
    @Bind(R.id.BT_Bank)
    TextView BTBank;
    private String beforeDeleteMsg, beforeDeactivateMsg, referralHelpMsg = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fr, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intview();
    }

    private void Intview() {
        MainActivity.toolbar.setTitle("Profile");
        cmf = new CommonFunctions(getActivity());

        // GetProfileAPICall();
        // setView();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.TV_ChangePass, R.id.pencil, R.id.BT_Update, R.id.IV_delete, R.id.IV_deactiveaccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.TV_ChangePass:
                ResetPaasword();
                break;
            case R.id.pencil:
                ResetPaasword();
                make_edit();
                break;

            case R.id.BT_Update:
                break;
            case R.id.IV_delete:
                DeleteAccount();
                break;
            case R.id.IV_deactiveaccount:
                DeactivateAccount();
                break;


        }
    }

    private void make_edit() {
        TVChangePass.setVisibility(View.VISIBLE);
        BTUpdate.setVisibility(View.VISIBLE);
    }

/*
    private void APICallForUPDATE() {
        if (validate()) {
            new CallWebService(getActivity(), cmf.urlList.EditProfile, cmf.EditProfile(UserID, mail, phone, "0", CountryCodeId), new MyServiceListener() {
                @Override
                public void onSuccess(String string) {
                    Log.d("-------->Profile_fr---API--EditProfile-->", string);
                    try {
                        JSONObject obj = new JSONObject(string);
                        String Status = obj.optString("Status");
                        String message = obj.optString("Message");
                        if (Status.equals("0")) {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            //Tst_Snake(findViewById(R.id.tv_SignUp), "Please provide correct credentials.");
                            return;
                        } else {
                            String cntycd = cmf.myPreference.getString(getActivity(), GlobalConstants.COUNTYCD);

                            cmf.myPreference.setString(getActivity(), GlobalConstants.PHONE_NUMBER, cntycd + " " + phone);
                            cmf.myPreference.setString(getActivity(), GlobalConstants.MOBILE_only, phone);

                            cmf.myPreference.setString(getActivity(), GlobalConstants.EMAIL, mail);
                            setprofileData(name, phone, mail, InvitationCode, PaymentStatusCode);
                        }
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed() {

                }
            });

        }
    }
*/

    public boolean validate() {
        mail = ETMail.getText().toString();
        phone = ETPhone.getText().toString();
        if (FieldUtils.isBlank(mail)) {
            Toast.makeText(getActivity(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(getActivity(), "Please enter a valid email address..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (FieldUtils.isBlank(phone) || phone.length() > 11 || phone.length() < 9) {
            Toast.makeText(getActivity(), "Please enter a valid mobile number.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //-----------------------------------------------------------------forget password
    private void ResetPaasword() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.reset_password_dialog);
        final ImageView pencil = (ImageView) dialog.findViewById(R.id.pencil);
        final Button lay11 = (Button) dialog.findViewById(R.id.lay11);

        final Button send = (Button) dialog.findViewById(R.id.send);
        final Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText ETold_pass = (EditText) dialog.findViewById(R.id.edt_oldpass);
        final EditText edt_pass = (EditText) dialog.findViewById(R.id.edt_pass);
        final EditText ETConfirm_pass = (EditText) dialog.findViewById(R.id.edt_con_pass);
        lay11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String oldpass = ETold_pass.getText().toString();
                String pass = edt_pass.getText().toString();
                String Confirm_pass = ETConfirm_pass.getText().toString();

                if (TextUtils.isEmpty(oldpass)) {
                    Toast.makeText(getActivity(), "Please enter the current password.", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getActivity(), "Please enter the new password", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(Confirm_pass)) {
                    Toast.makeText(getActivity(), "Please reconfirm the entered password. ", Toast.LENGTH_LONG).show();
                    return;
                } else if (!pass.equals(Confirm_pass)) {
                    Toast.makeText(getActivity(), "Passwords entered do not match. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }  /*else{
                    // call service listener..
                    new CallWebService(getActivity(), cmf.urlList.ChangePassword, cmf.ChangePassword(AspnetUserID, oldpass, pass, Confirm_pass), new MyServiceListener() {
                        @Override
                        public void onSuccess(String string) {
                            try {
                                JSONObject flag = new JSONObject(string);
                                String status = flag.optString("Status");
                                String message = flag.optString("Message");
                                if (status.equals("1")) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Signout();
                                } else {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException je) {
                                // Tst(getApplication(), "The request could not be completed. Please try again.");
                                dialog.dismiss();
                            } catch (Exception ex) {
                            }
                        }

                        @Override
                        public void onFailed() {
                            dialog.dismiss();
                        }
                    });
                }*/
            }
        });
        dialog.show();
    }

/*    private void Signout() {
        String Devicetoken = cmf.myPreference.getString(getActivity(), GlobalConstants.Firebasetoken);
        cmf.myPreference.clearSharedPreference(getActivity());
        final Intent itnt = new Intent(getActivity(), CollegeJobService.class);
        itnt.putExtra(cmf.gc.fromPage, "stopService");
        getActivity().startService(itnt);
        cmf.myPreference.setString(getActivity(), GlobalConstants.Firebasetoken, Devicetoken);
        cmf.myPreference.setString(getActivity(), GlobalConstants.NOTIFICATION, "0");
        cmf.myPreference.setString(getActivity(), GlobalConstants.LEGALAGREEMENTCHECK, "1");
        AppUtil.startActivityWithAnimation(getActivity(), new Intent(getActivity(), SignInActivity.class));
    }*/


    private void DeleteAccount() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.forgetpassword_dialog);
        final ImageView pencil = (ImageView) dialog.findViewById(R.id.pencil);
        pencil.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.deactivate_account));

        final TextView account = (TextView) dialog.findViewById(R.id.account);
        final Button lay11 = (Button) dialog.findViewById(R.id.lay11);

        final Button send = (Button) dialog.findViewById(R.id.send);
        final Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText edt_email = (EditText) dialog.findViewById(R.id.edt_email);
        //   edt_email.setError("Enter Your Password");
        edt_email.setHint("Enter Your Password.");

        account.setText(beforeDeleteMsg);
        edt_email.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);


        lay11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String oldpass = edt_email.getText().toString();


                if (TextUtils.isEmpty(oldpass)) {
                    Toast.makeText(getActivity(), "Please enter the password.", Toast.LENGTH_LONG).show();
                    return;
                } /*else {
                    // call service listener..
                    new CallWebService(getActivity(), cmf.urlList.DeleteAccount, cmf.DeletedMyAccount(AspnetUserID, oldpass, "DELETE"), new MyServiceListener() {
                        @Override
                        public void onSuccess(String string) {
                            Log.e("Delete Account------->", string);
                            try {
                                JSONObject flag = new JSONObject(string);
                                String status = flag.optString("Status");
                                String message = flag.optString("Message");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                if (status.equals("1")) {
                                    dialog.dismiss();
                                    Signout();
                                }
                            } catch (JSONException je) {
                                // Tst(getApplication(), "The request could not be completed. Please try again.");
                                dialog.dismiss();
                            } catch (Exception ex) {
                            }
                        }

                        @Override
                        public void onFailed() {
                            dialog.dismiss();
                        }
                    });
                }*/
            }
        });
        dialog.show();
    }

    private void DeactivateAccount() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.forgetpassword_dialog);
        final ImageView pencil = (ImageView) dialog.findViewById(R.id.pencil);
        pencil.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.deactive_icon));

        final TextView account = (TextView) dialog.findViewById(R.id.account);
        final Button lay11 = (Button) dialog.findViewById(R.id.lay11);

        final Button send = (Button) dialog.findViewById(R.id.send);
        final Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText edt_email = (EditText) dialog.findViewById(R.id.edt_email);
        //   edt_email.setError("Enter Your Password");
        edt_email.setHint("Enter your password.");

        account.setText(beforeDeactivateMsg);
        edt_email.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);


        lay11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String oldpass = edt_email.getText().toString();


                if (TextUtils.isEmpty(oldpass)) {
                    Toast.makeText(getActivity(), "Please enter the password.", Toast.LENGTH_LONG).show();
                    return;
                } /*else {
                    // call service listener..
                    new CallWebService(getActivity(), cmf.urlList.DeleteAccount, cmf.DeletedMyAccount(AspnetUserID, oldpass, "DEACTIVE"), new MyServiceListener() {
                        @Override
                        public void onSuccess(String string) {
                            Log.e("Deactive Account------->", string);
                            try {
                                JSONObject flag = new JSONObject(string);
                                String status = flag.optString("Status");
                                String message = flag.optString("Message");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                if (status.equals("1")) {
                                    dialog.dismiss();
                                    Signout();
                                }
                            } catch (JSONException je) {
                                // Tst(getApplication(), "The request could not be completed. Please try again.");
                                dialog.dismiss();
                            } catch (Exception ex) {
                            }
                        }

                        @Override
                        public void onFailed() {
                            dialog.dismiss();
                        }
                    });
                }*/
            }
        });
        dialog.show();
    }

    private void Dialog_referral() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_referralinfo);
        final Button BT_cancel = (Button) dialog.findViewById(R.id.BT_cancel);
        final TextView referral_text = (TextView) dialog.findViewById(R.id.referral_text);
        referral_text.setText(referralHelpMsg);
        BT_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*private void GetProfileAPICall() {
        new CallWebService(getActivity(), cmf.urlList.GetProfile, cmf.UserID(UserID), new MyServiceListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("-------->Profile_fr---API--GetProfile-->", string);
                try {
                    JSONObject obj = new JSONObject(string);
                    String Status = obj.optString("Status");
                    String message = obj.optString("Message");
                    beforeDeleteMsg = obj.optString("beforeDeleteMsg");
                    beforeDeactivateMsg = obj.optString("beforeDeactivateMsg");
                    referralHelpMsg = obj.optString("referralHelpMsg");
                    if (Status.equals("0")) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        JSONArray jsonArray = obj.getJSONArray("Data");
                        JSONObject jsonObject = jsonArray.optJSONObject(0);
                        String UserName = jsonObject.optString("UserName");
                        String MobileNumber = jsonObject.optString("MobileNumber");
                        String EmailID = jsonObject.optString("EmailID");
                        InvitationCode = jsonObject.optString("InvitationCode");
                        PaymentStatusCode = jsonObject.optString("PaymentStatusCode");
                        inviteestatusid = jsonObject.optString("InviteeStatusID");
                        ReferralCode = jsonObject.optString("ReferralCode");
                        et_YourReferalCode.setText(ReferralCode);
                        setprofileData(UserName, MobileNumber, EmailID, InvitationCode, PaymentStatusCode);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }*/


    private void setprofileData(String UserName, String MobileNumber, String EmailID, String InvitationCode, String PaymentStatusCode) {
        ETName.setText(UserName);
        ETPhone.setText(MobileNumber);
        ETMail.setText(EmailID);
        make_edit();
    }


}
