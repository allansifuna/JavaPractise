package com.company;

public class FeedingTask extends Thread{
    public void run(){
        Main.foodStockUpdated=false;
        while(Main.fedAnimals>0) {
            String animal = Main.getAnimalToFeed();
            String str="\t\t\t\t"+animal +" Got hungry, ";
            int foodAmount = Main.animalFood.get(animal);
            if (foodAmount <= Main.foodStock) {
                System.out.println(str+" feed "+animal +" "+ String.valueOf(foodAmount)+"kgs");
                Main.animalsFeedAmount.replace(animal,Main.animalsFeedAmount.get(animal)+foodAmount);
                Main.animalsFeedCount.replace(animal,Main.animalsFeedCount.get(animal)+1);
                Main.updateFoodStock(-foodAmount);
                Main.fedAnimals=Main.fedAnimals-1;
            } else {
                    try {
                        synchronized (Main.newDeposit){
                            System.out.println(str+" Wait for food..");
                            Main.animalsHungryCount.replace(animal,Main.animalsHungryCount.get(animal)+1);
                            Main.newDeposit.wait();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
        System.out.println("Finished FeedingTask Thread....");
    }
}
