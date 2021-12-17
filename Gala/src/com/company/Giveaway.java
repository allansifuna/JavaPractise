package com.company;

import java.util.Objects;

public class Giveaway {
    String description;
    static int ref_start=10;
    int ref_number;
    public Giveaway(String description){
        this.description=description;
        ref_start++;
        ref_number=ref_start;
    }

    public String getDescription() {
        return description;
    }

    public int getRef_number() {
        return ref_number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Giveaway)) return false;
        Giveaway giveaway = (Giveaway) o;
        return getRef_number() == giveaway.getRef_number() && Objects.equals(getDescription(), giveaway.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getRef_number());
    }
}
