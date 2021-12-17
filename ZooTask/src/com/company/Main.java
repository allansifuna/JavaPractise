/*Course: PROG36859
  Assignment No.: 3
  Member 1: <Your name>, <Student Id>
  Member 2: <Your name>, <Student Id>
  Instructor’s name: Syed Tanbee
 */
package com.company;

import java.sql.*;
import java.util.*;

public class Main {
    public static LinkedHashMap<String,Integer> animalFood = new LinkedHashMap<>();
    public static LinkedHashMap<String,Integer> animalsFeedAmount = new LinkedHashMap<>();
    public static LinkedHashMap<String,Integer> animalsFeedCount = new LinkedHashMap<>();
    public static LinkedHashMap<String,Integer> animalsHungryCount = new LinkedHashMap<>();
    static int foodStock=0;
    static int fedAnimals=10;
    static final Object newDeposit = new Object();
    static boolean foodStockUpdated=false;

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, SQLException {
        populateAnimalFood();
        populateAnimalsFed();
        populateAnimalsFeedCount();
        populateAnimalsHungryCount();
        Thread feeding = new FeedingTask();
        Thread depositing= new DepositingTask();
        System.out.println("Deposit Food\tFeed Animals\t\t\t\t\t\t\tStock(kgs");
        feeding.start();
        depositing.start();
        feeding.join();
        depositing.join();
        createTable();
//        addAnimalData();
        displayInfo();
    }

    private static void displayInfo() throws SQLException {
        String str = " SELECT * FROM FeedingData;";
        PreparedStatement st= getConn().prepareStatement(str);
        ResultSet rs=st.executeQuery();
        LinkedHashMap<String, int[]> dbInfo = new LinkedHashMap<>();
        LinkedHashMap<String, int[]> dbInfos = new LinkedHashMap<>();
        String animalName = rs.getMetaData().getColumnName(2);
        String feedCount = rs.getMetaData().getColumnName(3);
        String hungryCount = rs.getMetaData().getColumnName(4);
        System.out.println(animalName+"\t"+feedCount+"\t"+hungryCount);
        while (rs.next()){
            String name=rs.getString(2);
            int feedC=rs.getInt(3) ;
            int feedCn=rs.getInt(3) * animalFood.get(name);
            int hungryC=rs.getInt(4);
            int[] comb={feedC,hungryC};
            int[] combs={feedCn,hungryC};
            dbInfo.put(name,comb);
            dbInfos.put(name,combs);
            System.out.println(name+"\t\t"+String.valueOf(feedC)+"\t\t\t\t"+String.valueOf(hungryC));
        }
        List<Map.Entry<String, int[]> > nlist
                = new ArrayList<Map.Entry<String, int[]> >(dbInfos.entrySet());

        Collections.sort(
                nlist,
                new Comparator<Map.Entry<String, int[]> >() {
                    public int compare(
                            Map.Entry<String, int[]> entry1,
                            Map.Entry<String, int[]> entry2)
                    {
                        return entry1.getValue()[0]
                                - entry2.getValue()[0];
                    }
                });
        System.out.println("Highest amount of food consumed by: "+ nlist.get(nlist.size()-1).getKey());
        List<Map.Entry<String, int[]> > list
                = new ArrayList<Map.Entry<String, int[]> >(dbInfo.entrySet());

        Collections.sort(
                list,
                new Comparator<Map.Entry<String, int[]> >() {
                    public int compare(
                            Map.Entry<String, int[]> entry1,
                            Map.Entry<String, int[]> entry2)
                    {
                        return entry1.getValue()[1]
                                - entry2.getValue()[1];
                    }
                });
        System.out.println("The hungriest animal: "+ list.get(list.size()-1).getKey());

        int totalFoodAmount=0;
        for (Map.Entry<String,int[]> animal:dbInfo.entrySet()) {
            totalFoodAmount+=animal.getValue()[0]* animalFood.get(animal.getKey());
        }
        System.out.println("Total Food consumed by all animals "+String.valueOf(totalFoodAmount)+" kgs");
    }

    private static void addAnimalData() throws SQLException {
        for (String animal:animalFood.keySet()){
            String str = "INSERT INTO FeedingData(animalName,feedingCount,hungryCount) VALUES(?,?,?)";
            PreparedStatement st= getConn().prepareStatement(str);
            st.setString(1,animal);
            st.setInt(2,animalsFeedCount.get(animal));
            st.setInt(3,animalsHungryCount.get(animal));
            st.execute();
        }
    }

    public static Connection getConn() throws SQLException {
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/ZooDB_starivln");
        return conn;
    }
    private static void createTable() throws SQLException {
        String str= "CREATE TABLE IF NOT EXISTS FeedingData(id INT AUTO_INCREMENT, animalName VARCHAR(100) NOT NULL, feedingCount INT NOT NULL,hungryCount INT NOT NULL, PRIMARY KEY(id));";
        PreparedStatement st = getConn().prepareStatement(str);
        st.execute();
    }
    public static  void updateFoodStock(int value) {
            foodStock=foodStock+value;

    }

    public static String getAnimalToFeed(){
        int index = getRandomNumber(0,5);
        return (String) (animalFood.keySet().toArray())[ index ];
    }
    private static void populate(LinkedHashMap<String,Integer> map){
        map.put("Hippo",0);
        map.put("Rhino",0);
        map.put("Lion",0);
        map.put("Zebra",0);
        map.put("Monkey",0);
    }
    private static void populateAnimalsHungryCount() {
        populate(animalsHungryCount);
    }
    private static void populateAnimalsFeedCount() {
        populate(animalsFeedCount);
    }
    private static void populateAnimalsFed() {
        populate(animalsFeedAmount);
    }
    private static void populateAnimalFood() {
        animalFood.put("Hippo",getRandomNumber(1,20));
        animalFood.put("Rhino",getRandomNumber(1,20));
        animalFood.put("Lion",getRandomNumber(1,20));
        animalFood.put("Zebra",getRandomNumber(1,20));
        animalFood.put("Monkey",getRandomNumber(1,20));
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}

