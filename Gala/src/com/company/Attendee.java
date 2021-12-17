package com.company;

import java.util.Arrays;

public class Attendee {
    String name;
    int years;
    Giveaway[] giveaways;
    Giveaway[] tempGiveaways;
    public Attendee(String name, int years){
        this.name=name;
        this.years=years;
    }

    public Boolean addGiveaway(Giveaway giveaways) {
        if(years<10 && this.giveaways.length==8){
            return false;
        }
        if(years>10 && this.giveaways.length==12){
            return false;
        }
        this.giveaways = Arrays.copyOf(this.giveaways, this.giveaways.length + 1);
        this.giveaways[this.giveaways.length - 1] = giveaways;
        return true;
    }
    public boolean removeGiveaway(Giveaway giveaway){
        boolean foundOne=false;
        for (Giveaway it:this.giveaways) {
            if(it.equals(giveaway) && !foundOne){
                foundOne=true;
            }else{
                this.tempGiveaways = Arrays.copyOf(this.tempGiveaways, this.tempGiveaways.length + 1);
                this.tempGiveaways[this.tempGiveaways.length - 1] = it;
            }
        }
        this.giveaways=this.tempGiveaways;
        this.tempGiveaways=null;
        return foundOne;
    }

    public Giveaway[] getGiveaways() {
        return giveaways;
    }
    public Double attendanceFee(){
        Double fee= 72.55;
        if(this.years<5){
            return 0.85*fee;
        }
        return fee;
    }
    public String getText(){
        StringBuilder sb =new StringBuilder();
        sb.append(this.name);
        sb.append("-");
        sb.append(String.valueOf(years));
        sb.append("\n");
        for (Giveaway it:this.giveaways) {
            sb.append(String.valueOf(it.getRef_number()));
            sb.append(": ");
            sb.append(it.getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
