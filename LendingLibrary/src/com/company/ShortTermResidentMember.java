package com.company;

import java.util.Arrays;

public class ShortTermResidentMember {
    String name;
    int unitNumber;
    String phoneNumber;
    String departureDate;
    int membershipNumber;
    EntertainmentItem[] signedOutItems = new EntertainmentItem[0];
    EntertainmentItem[] tempSignedOutItems = new EntertainmentItem[0];
    public ShortTermResidentMember(String name,int unitNumber,String phoneNumber,String departureDate){
        this.name=name;
        this.unitNumber=unitNumber;
        this.phoneNumber=phoneNumber;
        this.departureDate=departureDate;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMembershipNumber() {
        ResidentMember.membershipNumber++;
        membershipNumber=ResidentMember.membershipNumber;
        return membershipNumber;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public EntertainmentItem[] getSignedOutItems() {
        return signedOutItems;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public boolean signOut(EntertainmentItem item){
        if(signedOutItems.length==7){
            return false;
        }
        if(item.getBenefactorDonated()){
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
        tempSignedOutItems=new EntertainmentItem[7];
        return foundOne;
    }
}
