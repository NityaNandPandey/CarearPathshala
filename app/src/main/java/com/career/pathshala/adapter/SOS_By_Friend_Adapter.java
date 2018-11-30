package com.career.pathshala.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.career.pathshala.Model.SOSbyFriendModel;
import com.career.pathshala.Model.UserSOS_HelpModel;
import com.career.pathshala.R;
import com.career.pathshala.api_call.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by rupesh.m on 3/10/2017.
 */

public class SOS_By_Friend_Adapter extends RecyclerView.Adapter<SOS_By_Friend_Adapter.MyViewHolder> implements View.OnClickListener {

    SOSbyFriendModel soSbyFriendModel;
    ArrayList<UserSOS_HelpModel> SosHelpArray = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private List<SOSbyFriendModel> arrayList;
    private CommonFunctions commonFunctions;
    private UserSOS_HelpModel userSOS_helpModel;
    private Online_Quiz_Adapter adapter;

    public SOS_By_Friend_Adapter(Context mcontext, ArrayList<SOSbyFriendModel> data) {
        this.context = mcontext;
        this.arrayList = data;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_sos_request, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        commonFunctions = new CommonFunctions(context);
        soSbyFriendModel = arrayList.get(position);
        holder.groupmembername.setText(soSbyFriendModel.getUserName());
        holder.batterypercentage.setText(soSbyFriendModel.getBattery() + "%");
        holder.address.setText(soSbyFriendModel.getAddress());

        holder.decline.setOnClickListener(this);
        holder.decline.setTag(position);
        holder.IV_jobDecline.setOnClickListener(this);
        holder.IV_jobDecline.setTag(position);
        holder.accept.setOnClickListener(this);
        holder.accept.setTag(position);
        holder.track.setOnClickListener(this);
        holder.track.setTag(position);
        holder.TV_userHelpFor.setOnClickListener(this);
        holder.TV_userHelpFor.setTag(position);

        holder.RL_item_main_layout.setOnClickListener(this);

        if (soSbyFriendModel.getIsAlertAccept().equals("1")) {
            //  holder.decline.setVisibility(View.GONE);
            holder.accept.setVisibility(View.GONE);
            holder.track.setVisibility(View.VISIBLE);
        } else {
            //  holder.decline.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.VISIBLE);
            holder.track.setVisibility(View.GONE);
        }
        if (Integer.parseInt(soSbyFriendModel.getOnlineStatus()) == 0) {
            holder.online.setText("Offline");
            // holder.online.setText( soSbyFriendModel.getLastSeen());
            holder.online.setTextColor(Color.parseColor("#646e76"));
        } else {
            holder.online.setText("Online");
            holder.online.setTextColor(Color.parseColor("#008000"));
            // holder.groupmembername.setTextColor(Color.parseColor("#008000"));
        }
        if (!soSbyFriendModel.getUserInteractionID().equals("0")) {
            holder.TV_userHelpFor.setVisibility(View.VISIBLE);
            holder.TV_userHelpFor.setText("See who else is helping " + soSbyFriendModel.getUserName());
        }
        // Picasso.with(context).load(soSbyMeModel.getUserimage()).into(holder.userimage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.decline:
                int declinepos = (Integer) view.getTag();
               // DialogAcceptDecline(declinepos, "decline", "Are you sure you want to decline request for help from ");
                break;

            case R.id.IV_jobDecline:
                int declineposn = (Integer) view.getTag();
              //  DialogAcceptDecline(declineposn, "decline", "Are you sure you want to decline request for help from ");
                break;

            case R.id.accept:
                int acceptpos = (Integer) view.getTag();
               // DialogAcceptDecline(acceptpos, "accept", "Are you sure you want to accept request from ");
              //  AcceptApiCall(acceptpos);
                break;
            case R.id.track:
                int trackpos = (Integer) view.getTag();
                googleMapNavigation(arrayList.get(trackpos).getLatitude(), arrayList.get(trackpos).getLongitude());
                //-------for my friends has changed location...NOTIFICATION
                commonFunctions.gc.CanTrack=true;
                commonFunctions.myPreference.setString(context, commonFunctions.gc.TrackingFriendId,arrayList.get(trackpos).getUserInteractionID());
                break;

            case R.id.TV_userHelpFor:
                // Toast.makeText(context,"working.........",Toast.LENGTH_SHORT).show();
                int help_pos = (Integer) view.getTag();
                //DialogWhoIsHelping(arrayList.get(help_pos).getUserInteractionID(), arrayList.get(help_pos).getUserName());
                break;
            default:
                   if(commonFunctions.gc.sosBymeClick.equals("1")){
                            Toast.makeText(context, "Please accept SOS request to track this user.", Toast.LENGTH_SHORT).show();
                   }
                break;
        }
    }

   /* public void DialogAcceptDecline(final int positions, final String AcceptDeclien, String mesg) {
        {
            final Dialog canceljob = new Dialog(context);
            canceljob.setContentView(R.layout.custom_mock_result);
            canceljob.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button ok = (Button) canceljob.findViewById(R.id.ok);

            //TextView TV_Message = (TextView) canceljob.findViewById(R.id.message);
            //String username = arrayList.get(positions).getUserName();
           // TV_Message.setText(mesg + username + "?");
           // Button no = (Button) canceljob.findViewById(R.id.no);
           // TV_Message.setText(mesg + username + "?");

            ok.setVisibility(View.VISIBLE);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    canceljob.dismiss();
                }
            });
            ok.setVisibility(View.VISIBLE);
            ok.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (AcceptDeclien.equals("accept")) {
                      //  AcceptApiCall(positions);
                    } else {
                        //DeclineApiCall(positions);
                    }
                    canceljob.dismiss();
                }
            });
            canceljob.show();
        }
    }*/

   /* public void DialogWhoIsHelping(final String UserInteractionID, final String username) {
        String ASPNETUSERID = commonFunctions.myPreference.getString(context, commonFunctions.gc.ASPNETUSERID);
        new CallWebService(context, UrlList.SOSHelperList, commonFunctions.SOSHelperList(UserInteractionID, ASPNETUSERID,username), new MyServiceListener() {
            @Override
            public void onSuccess(String string) {
                SosHelpArray.clear();
                Log.d("------->SOSHelperList-->", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int Status = jsonObject.optInt("Status");
                    String Message = jsonObject.optString("Message");

                    if (Status == 1) {
                        JSONArray DataArray = jsonObject.optJSONArray("Data");
                        for (int i = 0; i < DataArray.length(); i++) {
                            userSOS_helpModel = new UserSOS_HelpModel();
                            JSONObject Dataobj = DataArray.getJSONObject(i);
                            String UserName = Dataobj.optString("UserName");
                            String MobileNumber = Dataobj.optString("MobileNumber");
                            String CurrentLocation = Dataobj.optString("CurrentLocation");
                            userSOS_helpModel.setName(UserName);
                            userSOS_helpModel.setMobileno(MobileNumber);
                            userSOS_helpModel.setAddress(CurrentLocation);
                            SosHelpArray.add(userSOS_helpModel);
                        }

                     *//*   String namephone="";
                        JSONArray jsonArrayData=jsonObject.getJSONArray("Data");
                        for(int num=0; num < jsonArrayData.length(); num++){
                                JSONObject rowNo=jsonArrayData.getJSONObject(num);
                            namephone=namephone+rowNo.optString("UserName")+"          "+rowNo.optString("MobileNumber")+"\n";
                        }
                      *//*

                        //-------------------------------------------------------------------
                        final Dialog canceljob = new Dialog(context);
                        canceljob.setContentView(R.layout.dialog_user_sos_helper);
                        canceljob.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button ok = (Button) canceljob.findViewById(R.id.ok);
                        TextView TvuserName = (TextView) canceljob.findViewById(R.id.Tv_userName);
                        ListView LV_usersos = (ListView) canceljob.findViewById(R.id.LV_usersos);
                        TvuserName.setText("List of people helping " + username + ".");

                        adapter = new Online_Quiz_Adapter(context, SosHelpArray);
                        LV_usersos.setAdapter(adapter);
                        notifyDataSetChanged();
                    *//*    TextView tv_namePh = (TextView) canceljob.findViewById(R.id.tv_namePh);
                        tv_namePh.setText(namephone);*//*
                        ok.setVisibility(View.VISIBLE);
                        ok.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                canceljob.dismiss();
                            }
                        });
                        canceljob.show();
                        //-------------------------------------------------------------------
                    } else {
                        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }*/

    private void googleMapNavigation(String SneedyLat, String SneedyLong) {
        String Snewlattitude = commonFunctions.myPreference.getString(context, commonFunctions.gc.JOB_NEW_LATITUDE);
        String Snewlongitude = commonFunctions.myPreference.getString(context, commonFunctions.gc.JOB_NEW_LONGITUDE);
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + Snewlattitude + "," + Snewlongitude + "&daddr=" + SneedyLat + "," + SneedyLong));
        //   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

 /*   private void AcceptApiCall(final int acceptpos) {
        String ASPNETUSERID = commonFunctions.myPreference.getString(context, commonFunctions.gc.ASPNETUSERID);
        String Interaction_ID = arrayList.get(acceptpos).getUserInteractionID();
        new CallWebService(context, UrlList.acceptrequest, commonFunctions.acceptrequest("", "","","",""), new MyServiceListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String string) {
                Log.d("------->AcceptSOSRequest", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int Status = jsonObject.optInt("Status");
                    String Message = jsonObject.optString("Message");
                    if (Status == 1) {
                        arrayList.get(acceptpos).setIsAlertAccept("1");
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
            }
        });
    }

    private void DeclineApiCall(final int declinepos) {
        String ASPNETUSERID = commonFunctions.myPreference.getString(context, commonFunctions.gc.ASPNETUSERID);
        String Interaction_ID = arrayList.get(declinepos).getUserInteractionID();

        new CallWebService(context, UrlList.rejectrequest, commonFunctions.rejectrequest("", "","","",""), new MyServiceListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(String string) {
                Log.d("------->DeclineSOSRequest", string);
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int Status = jsonObject.optInt("Status");
                    String Message = jsonObject.optString("Message");
                    if (Status == 1) {
                        arrayList.remove(declinepos);
                        notifyDataSetChanged();

                        //Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView groupmembername, batterypercentage, address, online, TV_userHelpFor;
       // public CircleImageView userimage;
        public Button BT_firstletter;
        public Button decline, accept, track;
        public Button IV_jobDecline;
        public RelativeLayout RL_item_main_layout;

        public MyViewHolder(View view) {
            super(view);
            groupmembername = (TextView) view.findViewById(R.id.groupmembername);
            TV_userHelpFor = (TextView) view.findViewById(R.id.TV_userHelpFor);
            batterypercentage = (TextView) view.findViewById(R.id.batterypercentage);
            address = (TextView) view.findViewById(R.id.address);
//            userimage = (CircleImageView) view.findViewById(R.id.userimage);
            online = (TextView) view.findViewById(R.id.online);
            BT_firstletter = (Button) view.findViewById(R.id.BT_firstletter);
            decline = (Button) view.findViewById(R.id.decline);
            IV_jobDecline = (Button) view.findViewById(R.id.IV_jobDecline);
            accept = (Button) view.findViewById(R.id.accept);
            track = (Button) view.findViewById(R.id.track);
            RL_item_main_layout = (RelativeLayout) view.findViewById(R.id.RL_item_main_layout);

            IV_jobDecline.setVisibility(View.VISIBLE);
        }
    }
}

