package com.company;

import java.util.Objects;

public class EntertainmentItem {
    String description;
    Double price;
    Boolean benefactorDonated;
    public EntertainmentItem(String description,Double price,Boolean benefactorDonated){
        this.description=description;
        this.benefactorDonated=benefactorDonated;
        this.price=price;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getBenefactorDonated() {
        return benefactorDonated;
    }
    public boolean equals(EntertainmentItem item){
        return (this.description.equals(item.getDescription()) && this.price.equals(item.getPrice()) && this.benefactorDonated.equals(item.getBenefactorDonated()));
    }
}
