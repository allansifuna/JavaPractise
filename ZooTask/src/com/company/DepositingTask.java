package com.company;

public class DepositingTask extends Thread {
    public void run(){
        while (Main.fedAnimals>0) {
            synchronized (Main.newDeposit) {
                if(Main.foodStock<15) {
                    int addValue=Main.getRandomNumber(1, 21);
                    Main.updateFoodStock(addValue);
                    System.out.println("Add "+String.valueOf(addValue)+"\t\t\t\t\t\t\t\t\t\t\t\t\t "+String.valueOf(Main.foodStock));
                }
                Main.newDeposit.notify();
            }
        }
        System.out.println("Finished DepositingTask Thread....");
    }
}
