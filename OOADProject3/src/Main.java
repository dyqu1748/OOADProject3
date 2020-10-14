//import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

//https://www.w3schools.com/java/java_user_input.asp

public class Main {

    public static void main(String[] args) {
        try {
            MyUnitTest test = new MyUnitTest();
            Scanner userInput = new Scanner(System.in);
            System.out.println("Hello store manager! Please enter the maximum stock for the rolls of your store.");
            boolean gotInput = false;
            int maxStock = 0;
            //Take in user input to get the maximum stock of the rolls (user should enter 30 to meet project requirements).
            //Enables us to change max stock every run (no need to change code).
            while (!gotInput) {
                String input = userInput.nextLine();
                try {
                    maxStock = Integer.parseInt(input);
//                    https://stackoverflow.com/questions/5502548/checking-if-a-number-is-an-integer-in-java
                    if (maxStock == (int) maxStock && maxStock > 0) {
                        gotInput = true;
                    } else {
                        System.out.println("Please enter a valid number for the maximum stock.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number for the maximum stock.");
                }
            }
            System.out.println("Thank you. We will now start up operation of your brand-new roll store!");
            //Output will now be written to the output file.
            FileOutputStream outFile = new FileOutputStream("output.txt", false);
            System.setOut(new PrintStream(outFile));
            StockAnnouncer sa = new StockAnnouncer();
            BoulderRollStore brs = new BoulderRollStore(maxStock, sa);
            test.testRollCreation(brs);
            //Set the types of customers our store will have (different stores may have different customer types).
            ArrayList<String> custTypes = new ArrayList<>();
            custTypes.add("Casual");
            custTypes.add("Business");
            custTypes.add("Catering");
            //Run the store for 30 days
            while (brs.getDay() < 30) {
                brs.newDay();
                //Restock any out of stock rolls and reset the stock announcer's status if stock ran out
                if (sa.isStockOut()) {
                    sa.resetStock();
                }
                brs.restockRolls();
                //Reset all daily values back to 0
                brs.resetDailyCashSales();
                brs.resetDailyOutages();
                brs.resetDailyRollSales();
                //Print out the initial stock for the day
                HashMap<String, Integer> initStock = brs.getStock();
                System.out.println("Day " + brs.getDay());
                System.out.println("Rolls in inventory at the start of the day:");
                for (Map.Entry item : initStock.entrySet()) {
                    String rollType = (String) item.getKey();
                    int amount = (int) item.getValue();
                    System.out.println(rollType + " roll: " + amount + " in stock");
                }
                //Make copy of customer types as to not modify the original list (that way on every loop we can get a full copy of the customers).
                ArrayList<String> custCopy = (ArrayList<String>) custTypes.clone();
                Random rand = new Random();
                int randCas = rand.nextInt(12) + 1;
                int randBus = rand.nextInt(3) + 1;
                int randCat = rand.nextInt(3) + 1;
                brs.openStore();
                while (brs.isOpen()) {
                    test.testCloseNoStock(brs, sa);
                    //While not every customer has ordered, let them order their rolls
                    if (randCat > 0 || randBus > 0 || randCas > 0) {
//                        System.out.println(custCopy);
                        //Chose a random customer to order next
                        String curCust = custCopy.get(rand.nextInt(custCopy.size()));
                        switch (curCust) {
                            case "Casual":
                                if (randCas > 0) {
                                    CasualCustomer casCust = new CasualCustomer();
                                    performOrder(casCust, brs);
                                    test.testCasualCustomerOrder(casCust.getCurOrder());
                                    test.testExtraSauce(casCust.getCurOrder());
                                    test.testExtraFilling(casCust.getCurOrder());
                                    test.testExtraTopping(casCust.getCurOrder());
                                    randCas--;
                                } else {
                                    //All casual customers have ordered for the day, remove them from the customer choices arraylist.
                                    custCopy.remove("Casual");
                                }
                                break;
                            case "Business":
                                if (randBus > 0) {
                                    BusinessCustomer busCust = new BusinessCustomer();
                                    performOrder(busCust, brs);
                                    test.testBusinessCustomerOrderSize(busCust.getCurOrder());
                                    test.testBusinessCustomerOrderContents(busCust.getCurOrder());
                                    test.testExtraSauce(busCust.getCurOrder());
                                    test.testExtraFilling(busCust.getCurOrder());
                                    test.testExtraTopping(busCust.getCurOrder());
                                    randBus--;
                                } else {
                                    //All business customers have ordered for the day, remove them from the customer choices arraylist.
                                    custCopy.remove("Business");
                                }
                                break;
                            case "Catering":
                                if (randCat > 0) {
                                    CateringCustomer catCust = new CateringCustomer();
                                    performOrder(catCust, brs);
                                    test.testCateringCustomerOrderSize(catCust.getCurOrder());
                                    test.testCateringCustomerOrderContents(catCust.isOgOrderPossible(), catCust.getCurOrder());
                                    test.testExtraSauce(catCust.getCurOrder());
                                    test.testExtraFilling(catCust.getCurOrder());
                                    test.testExtraTopping(catCust.getCurOrder());
                                    randCat--;
                                } else {
                                    //All catering customers have ordered for the day, remove them from the customer choices arraylist.
                                    custCopy.remove("Catering");
                                }
                                break;
                        }
                    }
                    //All customers have ordered, close the store
                    else{
                        brs.closeStore();
                    }
                }
                //allOut will hold the rolls that are out of stock. Will display what roll need to be restocked for the next day.
                ArrayList<String> allOut = new ArrayList<>();
                //Print out the roll inventory for the end of the day.
                HashMap<String, Integer> endStock = brs.getStock();
                System.out.println("Rolls in inventory at the end of the day: (Day " + brs.getDay() + "):");
                for (Map.Entry item : endStock.entrySet()) {
                    int amount = (int) item.getValue();
                    String rollType = (String) item.getKey();
                    System.out.println(rollType + " roll: " + amount + " in stock");
                    if(amount == 0){
                        allOut.add(rollType);
                    }
                }
                //Check if any rolls ran out. If so, print out what rolls need to be restocked.
                if(allOut.size() > 0){
                    String restockRolls = "";
                    for (String rollType: allOut){
                        restockRolls += maxStock + " " + rollType + " Rolls, ";
                    }
                    System.out.println("Inventory will restock " + restockRolls + "at the beginning of the next day.\n");
                }
                //Display the daily sales statistics
                brs.displayDailyCashSales();
                brs.displayDailyImpacts();
                brs.displayRollsOrdered("Daily");
                //If the store closed due to stock completely running out, print that out.
                if (sa.isStockOut()) {
                    System.out.println("\nStore closed on day " + brs.getDay() + " due to lack of inventory.");
                }
                System.out.println();
            }

            //Modify output to properly output money format
            DecimalFormat df = new DecimalFormat("0.00");
            //Print out the 30-day statistics
            System.out.println("Store statistics after 30 days:");
            brs.displayRollsOrdered("Total");
            System.out.println("Total money in sales: $" + df.format(brs.getTotalCash()));
            System.out.println("Total number of outage impacts: " + brs.getTotalOutageImpacts());
            outFile.close();
        } catch (IOException err){
            err.printStackTrace();
        }
    }

    //Method will be used to order rolls for all types of customers
    private static void performOrder(Customer customer, RollStore rollStore) {
        //Have the customer make their order, taking into account the stock of the store (stock will only be used to check if original order is possible and to modify said order if it is not)
        customer.makeOrder(rollStore.getStock());
        ArrayList<ArrayList<String>> curOrder = customer.getCurOrder();
        //Iterate through the rolls of the customer's order and order them.
        //Add the ordered roll to the customers rollsOrdered arraylist.
        for (ArrayList<String> order: curOrder){
            customer.addRollOrdered(rollStore.orderRolls(customer,order));
        }
        //If the business customer is unable to make their original order, the whole order is cancelled. Return to avoid printing nothing for the order summary.
        if(customer.getCustType() == "Business" && !customer.isOgOrderPossible()){
            return;
        }
        //Output summary of the customer's order
        System.out.println("Individual customer order:");
        rollStore.displayOrderDetails(customer,customer.getRollsOrdered());
        if(!customer.isOgOrderPossible()){
            rollStore.incOutageImpact(customer.getCustType());
        }
    }

}
