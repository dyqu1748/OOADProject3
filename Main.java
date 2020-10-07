import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        try {
            FileOutputStream outFile = new FileOutputStream("output.txt", false);
            System.setOut(new PrintStream(outFile));
            StockAnnouncer sa = new StockAnnouncer();
            BoulderRollStore brs = new BoulderRollStore(30, sa);
            ArrayList<String> custTypes = new ArrayList<>();
            custTypes.add("Casual");
            custTypes.add("Business");
            custTypes.add("Catering");
            while (brs.getDay() < 30) {
                brs.newDay();
                if(sa.isStockOut()){
                    sa.resetStock();
                }
                brs.restockRolls();
                brs.resetDailyCashSales();
                brs.resetDailyOutages();
                brs.resetDailyRollSales();
                HashMap<String, Integer> initStock = brs.getStock();
                System.out.println("\nDay " + brs.getDay());
                System.out.println("Rolls in inventory at the start of the day:");
                for(Map.Entry item: initStock.entrySet()){
                    String rollType = (String)item.getKey();
                    int amount = (int)item.getValue();
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
                                    casCust.makeOrder(brs.getStock());
                                    ArrayList<ArrayList<String>> curOrder = casCust.getCurOrder();
                                    for (ArrayList<String> order: curOrder){
                                        casCust.addRollOrdered(brs.orderRolls(casCust,order));
                                    }
                                    System.out.println("Individual customer order:");
                                    brs.displayOrderDetails(casCust,casCust.getRollsOrdered());
                                    if(!casCust.isOgOrderPossible()){
                                        brs.incOutageImpact(casCust.getCustType());
                                    }
                                    randCas--;
                                } else {
                                    custCopy.remove("Casual");
                                }
                                break;
                            case "Business":
                                if (randBus > 0) {
                                    BusinessCustomer busCust = new BusinessCustomer();
                                    busCust.makeOrder(brs.getStock());
                                    ArrayList<ArrayList<String>> curOrder = busCust.getCurOrder();
                                    for (ArrayList<String> order: curOrder){
                                        busCust.addRollOrdered(brs.orderRolls(busCust,order));
                                    }
                                    System.out.println("Individual customer order:");
                                    brs.displayOrderDetails(busCust,busCust.getRollsOrdered());
                                    if(!busCust.isOgOrderPossible()){
                                        brs.incOutageImpact(busCust.getCustType());
                                    }
                                    randBus--;
                                } else {
                                    custCopy.remove("Business");
                                }
                                break;
                            case "Catering":
                                if (randCat > 0) {
                                    CateringCustomer catCust = new CateringCustomer();
                                    catCust.makeOrder(brs.getStock());
                                    ArrayList<ArrayList<String>> curOrder = catCust.getCurOrder();
                                    for (ArrayList<String> order: curOrder){
                                        catCust.addRollOrdered(brs.orderRolls(catCust,order));
                                    }
                                    System.out.println("Individual customer order:");
                                    brs.displayOrderDetails(catCust,catCust.getRollsOrdered());
                                    if(!catCust.isOgOrderPossible()){
                                        brs.incOutageImpact(catCust.getCustType());
                                    }
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
                for(Map.Entry item: endStock.entrySet()){
                    int amount = (int)item.getValue();
                    String rollType = (String)item.getKey();
                    System.out.println(rollType + " roll: " + amount + " in stock");
                }
                brs.displayDailyCashSales();
                brs.displayDailyImpacts();
                brs.displayDailyRollOrders();
                if (sa.isStockOut()){
                    System.out.println("Store closed on day " + brs.getDay() + " due to lack of inventory.");
                }
            }

            DecimalFormat df = new DecimalFormat("0.00");

            System.out.println("Store statistics after 30 days:");
            brs.displayTotalRollOrders();
            System.out.println("Total money in sales: $" + df.format(brs.getTotalCash()));
            System.out.println("Total number of outage impacts: " + brs.getTotalOutageImpacts());
            outFile.close();
        } catch (IOException err){
            err.printStackTrace();
        }
    }

}
