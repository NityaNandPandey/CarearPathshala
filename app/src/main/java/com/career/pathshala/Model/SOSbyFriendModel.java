package com.career.pathshala.Model;

/**
 * Created by rupesh.m on 3/10/2017.
 */

public class SOSbyFriendModel {

    String AspnetUserID;
    String UserName;
    String MobileNumber;
    String Latitude;
    String Longitude;
    String Address;
    String Battery;
    String SOSStatus;
    String OnlineStatus;
    String IsAlertAccept;
    String IsAlertCancel;
    String UserInteractionID;
    String LastSeen;

    public String getLastSeen() {
        return LastSeen;
    }

    public void setLastSeen(String lastSeen) {
        LastSeen = lastSeen;
    }



    public String getAspnetUserID() {
        return AspnetUserID;
    }

    public void setAspnetUserID(String aspnetUserID) {
        AspnetUserID = aspnetUserID;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getSOSStatus() {
        return SOSStatus;
    }

    public void setSOSStatus(String SOSStatus) {
        this.SOSStatus = SOSStatus;
    }

    public String getOnlineStatus() {
        return OnlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        OnlineStatus = onlineStatus;
    }

    public String getIsAlertAccept() {
        return IsAlertAccept;
    }

    public void setIsAlertAccept(String isAlertAccept) {
        IsAlertAccept = isAlertAccept;
    }

    public String getIsAlertCancel() {
        return IsAlertCancel;
    }

    public void setIsAlertCancel(String isAlertCancel) {
        IsAlertCancel = isAlertCancel;
    }
    public String getUserInteractionID() {
        return UserInteractionID;
    }

    public void setUserInteractionID(String userInteractionID) {
        UserInteractionID = userInteractionID;
    }
}
