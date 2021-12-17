package com.company;

public class OvernightAtendee extends Attendee{
    Boolean usesTaxi;
    public OvernightAtendee(String name, int years) {
        super(name, years);
    }
    public OvernightAtendee(String name, int years, Boolean usesTaxi) {
        super(name, years);
        this.usesTaxi=usesTaxi;
    }

    public void setUsesTaxi(Boolean usesTaxi) {
        this.usesTaxi = usesTaxi;
    }

    public Boolean getUsesTaxi() {
        return usesTaxi;
    }

    @Override
    public Double attendanceFee() {
        if (usesTaxi){
            return super.attendanceFee()+118+11.25;
        }
        return super.attendanceFee()+118;
    }
}
