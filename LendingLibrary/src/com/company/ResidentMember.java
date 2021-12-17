package com.company;

import java.util.Arrays;

public class ResidentMember {
    String name;
    int unitNumber;
    String phoneNumber;
    static int membershipNumber=499999;
    int mn;
    EntertainmentItem[] signedOutItems=new EntertainmentItem[0];
    EntertainmentItem[] tempSignedOutItems = new EntertainmentItem[0];
    public ResidentMember(String name,int unitNumber,String phoneNumber){
        this.name=name;
        this.unitNumber=unitNumber;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMembershipNumber() {
        membershipNumber++;
        mn=membershipNumber;
        return mn;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public EntertainmentItem[] getSignedOutItems() {
        return signedOutItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }
    public boolean signOut(EntertainmentItem item){
        if(signedOutItems.length==7){
            return false;
        }
        signedOutItems = Arrays.copyOf(signedOutItems, signedOutItems.length + 1);
        signedOutItems[signedOutItems.length - 1] = item;
        return true;
    }
    public boolean returnItem(EntertainmentItem item){
        boolean foundOne=false;
        for (EntertainmentItem it:signedOutItems) {
            if(it.equals(item) && !foundOne){
                foundOne=true;
            }else{
                tempSignedOutItems = Arrays.copyOf(tempSignedOutItems, tempSignedOutItems.length + 1);
                tempSignedOutItems[tempSignedOutItems.length - 1] = it;
            }
        }
        signedOutItems=tempSignedOutItems;
        tempSignedOutItems=new EntertainmentItem[0];
        return foundOne;
    }
}
