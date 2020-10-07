import com.sun.deploy.util.StringUtils;

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
            Scanner userInput = new Scanner(System.in);
            System.out.println("Hello store manager! Please enter the maximum stock for the rolls of your store.");
            boolean gotInput = false;
            int maxStock = 0;
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
            FileOutputStream outFile = new FileOutputStream("output.txt", false);
            System.setOut(new PrintStream(outFile));
            StockAnnouncer sa = new StockAnnouncer();
            BoulderRollStore brs = new BoulderRollStore(maxStock, sa);
            ArrayList<String> custTypes = new ArrayList<>();
            custTypes.add("Casual");
            custTypes.add("Business");
            custTypes.add("Catering");
            while (brs.getDay() < 30) {
                brs.newDay();
                if (sa.isStockOut()) {
                    sa.resetStock();
                }
                brs.restockRolls();
                brs.resetDailyCashSales();
                brs.resetDailyOutages();
                brs.resetDailyRollSales();
                HashMap<String, Integer> initStock = brs.getStock();
                System.out.println("Day " + brs.getDay());
                System.out.println("Rolls in inventory at the start of the day:");
                for (Map.Entry item : initStock.entrySet()) {
                    String rollType = (String) item.getKey();
                    int amount = (int) item.getValue();
                    System.out.println(rollType + " roll: " + amount + " in stock");
                }
                ArrayList<String> custCopy = (ArrayList<String>) custTypes.clone();
                Random rand = new Random();
                int randCas = rand.nextInt(12) + 1;
                int randBus = rand.nextInt(3) + 1;
                int randCat = rand.nextInt(3) + 1;
                boolean allCust = false;
                brs.openStore();
                while (brs.isOpen()) {
                    if (!allCust) {
//                        System.out.println(custCopy);
                        String curCust = custCopy.get(rand.nextInt(custCopy.size()));
                        switch (curCust) {
                            case "Casual":
                                if (randCas > 0) {
                                    CasualCustomer casCust = new CasualCustomer();
                                    performOrder(casCust, brs);
                                    randCas--;
                                } else {
                                    custCopy.remove("Casual");
                                }
                                break;
                            case "Business":
                                if (randBus > 0) {
                                    BusinessCustomer busCust = new BusinessCustomer();
                                    performOrder(busCust, brs);
                                    randBus--;
                                } else {
                                    custCopy.remove("Business");
                                }
                                break;
                            case "Catering":
                                if (randCat > 0) {
                                    CateringCustomer catCust = new CateringCustomer();
                                    performOrder(catCust, brs);
                                    randCat--;
                                } else {
                                    custCopy.remove("Catering");
                                }
                                break;
                        }
                        if (randCat == 0 && randBus == 0 && randCas == 0) {
                            allCust = true;
                            brs.closeStore();
                        }
                    }
                }
                HashMap<String, Integer> endStock = brs.getStock();
                System.out.println("Rolls in inventory at the end of the day:");
                for (Map.Entry item : endStock.entrySet()) {
                    int amount = (int) item.getValue();
                    String rollType = (String) item.getKey();
                    System.out.println(rollType + " roll: " + amount + " in stock");
                }
                brs.displayDailyCashSales();
                brs.displayDailyImpacts();
                brs.displayRollsOrdered("Daily");
                if (sa.isStockOut()) {
                    System.out.println("Store closed on day " + brs.getDay() + " due to lack of inventory.\n");
                }
            }

            DecimalFormat df = new DecimalFormat("0.00");

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
