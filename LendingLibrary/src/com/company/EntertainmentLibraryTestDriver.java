package com.company;

import com.company.ResidentMember;

import java.text.NumberFormat;

/** 
  Test driver class to test the ResidentMember, ShortTermResidentMember 
  and EntertainmentItem classes.
*/
public class EntertainmentLibraryTestDriver {
  public static void main (String[] args) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
    //*********************************************************************************
    
    System.out.println
      ("\n*** Test case #1: Create a ResidentMember object & test accessors");
    ResidentMember maria = new ResidentMember ("Maria Lopez", 163,"555-1234");
    System.out.println("Name:     " + maria.getName()
                   + "\nUnit #:   " + maria.getUnitNumber()
                   + "\nPhone:    " + maria.getPhoneNumber()
                   + "\nMember #: " + maria.getMembershipNumber());
    
    EntertainmentItem[] mariasItemList = maria.getSignedOutItems();
    if (mariasItemList.length == 0) {
      System.out.println ("Correct result: Maria has zero entertainment items.");
    }
    else {
      System.out.println (">> ERROR: Maria has more than zero entertainment items.");
    }
    
    //*********************************************************************************
    
    System.out.println
      ("\n*** Test case #2: Create a ShortTermResidentMember object & test accessors");
    ShortTermResidentMember tommy = new ShortTermResidentMember ("Tommy MacDonald",
                                                                 306,
                                                                 "555-8642",
                                                                 "Dec. 17, 2021");
    System.out.println ("Name:     " + tommy.getName()
                    + "\nUnit #:   " + tommy.getUnitNumber()
                    + "\nPhone:    " + tommy.getPhoneNumber()
                    + "\nMember #: " + tommy.getMembershipNumber()
                    + "\nDeparts:  " + tommy.getDepartureDate());
    
    EntertainmentItem[] tommysItemList = tommy.getSignedOutItems();
    if (tommysItemList.length == 0) {
      System.out.println ("Correct result: Tommy has zero entertainment items.");
    }
    else {
      System.out.println (">> ERROR: Tommy has more than zero entertainment items.");
    }


    //*********************************************************************************
    
    System.out.println ("\n*** Test case #3: Automatically generate a member number");
    ResidentMember owen = new ResidentMember ("Owen King", 228, "455-1111");
    if (owen.getMembershipNumber() == 500002) {
      System.out.println ("Correct result: 500002 is the correct member number.");
    }
    else {
      System.out.println(">> ERROR: Invalid member number: " 
                         + owen.getMembershipNumber());
    }
    
    //*********************************************************************************
    
    System.out.println
      ("\n*** Test case #4: Create an EntertainmentItem object & test accessors");
    
    // Create several EntertainmentItem objects and test the first one
    final int MAXITEMS = 7;
    EntertainmentItem[] testItemList = new EntertainmentItem[MAXITEMS + 1];
    String[] testItemDescription = 
      { "Uno - Card Game",
        "Connect 4 - Board Game",
        "Skip-Bo - Card Game",
        "Harmonica - Musical Instrument",
        "Scrabble - Board Game",
        "Codenames - Card Game",
        "Ukulele - Musical Instrument",
        "Pandemic - Board Game" };
    for (int i=0; i<=MAXITEMS; i++) {
      testItemList[i] = 
        new EntertainmentItem (testItemDescription[i],
                               12.00 + (i * 3.25),  // Made-up prices
                               (i%2) == 0);  // Every 2nd item was donated
    } // end for loop
    
    System.out.println 
         ("Description:        " + testItemList[0].getDescription()
      + "\nOriginal Price:     " + formatter.format(testItemList[0].getPrice())
      + "\nBenefactor Donated: " + testItemList[0].getBenefactorDonated());
    
    //*********************************************************************************
    
    System.out.println 
      ("\n*** Test case #5: Change phone number for both resident types");
    String testPhone1 = "555-8226";
    String testPhone2 = "555-9595";
    maria.setPhoneNumber(testPhone1);
    tommy.setPhoneNumber(testPhone2);

    if (maria.getPhoneNumber().equals(testPhone1)) {
      System.out.println ("Correct result: Maria's phone number successfully changed.");
    }
    else {
      System.out.println (">> ERROR: Maria's phone number is incorrect: "
                          + maria.getPhoneNumber());
    }

    if (tommy.getPhoneNumber().equals(testPhone2)) {
      System.out.println ("Correct result: Tommy's phone number successfully changed.");
    }
    else {
      System.out.println (">> ERROR: Tommy's phone number is incorrect: "
                          + tommy.getPhoneNumber());
    }
    
    //*********************************************************************************
    
    System.out.println ("\n*** Test case #6: Sign out one EntertainmentItem");
    
    if (maria.signOut(testItemList[0])) {
      System.out.println ("Correct result: Maria signed out an item successfully.");
      mariasItemList = maria.getSignedOutItems();
      if (mariasItemList.length == 1) {
        System.out.println ("Correct result: Maria has one entertainment item.");
      }
      else {
        System.out.println (">> ERROR: Maria has other than one entertainment item.");
      }
    }
    else {
      System.out.println (">> ERROR: Maria was unable to sign out an item.");
    }

    //*********************************************************************************
   
    System.out.println ("\n*** Test case #7: Sign out multiple EntertainmentItems");
    
    boolean successfulSignOut = true;
    for (int i=1; i<=2; i++) {
      successfulSignOut = successfulSignOut && maria.signOut(testItemList[i]);
    }
    if (successfulSignOut) {
      System.out.println ("Correct result: "
        + "Maria signed out two more items successfully.");
      mariasItemList = maria.getSignedOutItems();
      if (mariasItemList.length == 3) {
        System.out.println ("Correct result: Maria has three entertainment items.");
      }
      else {
        System.out.println (">> ERROR: Maria has other than three entertainment items.");
      }
    }
    else {
      System.out.println (">> ERROR: Maria was unable to sign out two more items.");
    }
    
    //*********************************************************************************
    
    System.out.println ("\n*** Test case #8: Intentionally exceed the sign out limit");
    
    for(int i=3; i<MAXITEMS; i++) {
      maria.signOut(testItemList[i]);
    }
    if (!maria.signOut(testItemList[MAXITEMS])) {
      System.out.println ("Correct result: "
                          + "Maria was prevented from signing out more than "
                          + MAXITEMS + " entertainment items.");
    }
    else {
      System.out.println (">> ERROR: Maria was able to sign out more than "
                          + MAXITEMS + " entertainment items.");
    }

    //*********************************************************************************
    
    System.out.println ("\n*** Test case #9: "
      + "A short-term resident tries to sign out items");
    
    EntertainmentItem tommysItem = null;
    
    for(int i=0; i<2; i++) {
      if(tommy.signOut(testItemList[i])) {
        tommysItem = testItemList[i];        // Remember this for Test case #10
        if (testItemList[i].getBenefactorDonated()) {
          System.out.println (">> ERROR: "
            + "Tommy was able to sign out a benefactor-donated item.");
        }
        else {
          System.out.println ("Correct result: "
            + "Tommy was able to sign out a non-benefactor-donated item.");
        }
      }
      else {
        if (testItemList[i].getBenefactorDonated()) {
          System.out.println ("Correct result: "
            + "Tommy was prevented from signing out a benefactor-donated item.");
        }
        else {
          System.out.println (">> ERROR: "
            + "Tommy was prevented from signing out a non-benefactor-donated item.");
        }
      }
    }//end for

    //*********************************************************************************
    
    System.out.println 
      ("\n*** Test case #10: Returning the only item that was signed out");
    
    int tommyListLength = tommy.getSignedOutItems().length;

    if (tommy.returnItem(tommysItem)) {
      System.out.println ("Correct result: Tommy's item was successfully returned.");
    }
    else {
      System.out.println (">> ERROR: Tommy's item was not successfully returned.");
    }

    if(tommy.getSignedOutItems().length == tommyListLength - 1) {
      System.out.println ("Correct result: Tommy's list length changed appropriately.");
    }
    else {
      System.out.println (">> ERROR: Tommy's list length did not change appropriately.");
    }


    //*********************************************************************************
    
    System.out.println ("\n*** Test case #11: Returning an item that was not signed out");
    
    if (tommy.returnItem(testItemList[3])) {
      System.out.println (">> ERROR: Returned an item that was not signed out.");
    }
    else {
      System.out.println ("Correct result: "
        + "Unsuccessful attempt to return an item that was not signed out.");
    }
  
    //*********************************************************************************
    
    System.out.println
      ("\n*** Test case #12: Returning the first item that was signed out");
    
    int mariaListLength = maria.getSignedOutItems().length;

    if (maria.returnItem(testItemList[0])) {
      System.out.println ("Correct result: "
        + "Maria's first item was successfully returned.");
    }
    else {
      System.out.println (">> ERROR: Maria's first item was not successfully returned.");
    }

    if (maria.getSignedOutItems().length == mariaListLength - 1) {
      System.out.println ("Correct result: Maria's list length changed appropriately.");
    }
    else {
      System.out.println (">> ERROR: Maria's list length did not change appropriately.");
    }

    System.out.println
      ("\nConfirm return: Uno should be absent from the following list:");
    mariasItemList = maria.getSignedOutItems();
    for (int i=0; i<mariasItemList.length; i++) {
      System.out.println (mariasItemList[i].getDescription());
    }


    //*********************************************************************************
    
    System.out.println("\n*** Test case #13: Returning a mid-list item");
    
    mariaListLength = maria.getSignedOutItems().length;

    if (maria.returnItem(testItemList[2])) {
      System.out.println ("Correct result: Skip-Bo was successfully returned.");
    }
    else {
      System.out.println (">> ERROR: Skip-Bo was not successfully returned.");
    }

    if (maria.getSignedOutItems().length == mariaListLength - 1) {
      System.out.println ("Correct result: Maria's list length changed appropriately.");
    }
    else {
      System.out.println (">> ERROR: Maria's list length did not change appropriately.");
    }

    System.out.println
      ("\nConfirm return: Skip-Bo should be absent from the following list:");
    mariasItemList = maria.getSignedOutItems();
    for (int i=0; i<mariasItemList.length; i++) {
      System.out.println (mariasItemList[i].getDescription());
    }

    //*********************************************************************************
    
    System.out.println("\n************* End of Test Cases ***************\n");
    
  } // end main method


} // end EntertainmentTestDriver class