import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

//May need to have the store hold the day,sales numbers, etc. Or we can have a cashier class which will get the attributes from customer and calculate them itself.

public abstract class RollStore {

    private PropertyChangeSupport watcher = new PropertyChangeSupport(this);
    private HashMap<String, Integer> stock = new HashMap<String, Integer>();
    private int maxStock;
    private boolean open;
    private int day = 0;

    private int totalOutageImpacts;
    private double totalCash;
    private HashMap<String,Integer> dailyOutageImpacts = new HashMap<>();
    private HashMap<String, Double> dailyCashSales = new HashMap<>();
    private HashMap<String, Integer> rollsSoldDaily = new HashMap<>();
    private HashMap<String, Integer> rollsSoldTotal = new HashMap<>();

    //Abstract methods below allow for different stores to sell different rolls/decorators
    protected abstract Roll createRoll(String type);

    protected abstract Roll addExtra(Roll roll, ArrayList<String> extra);

    public Roll orderRolls(Customer customer, ArrayList<String> order){
        ArrayList<String> orderCopy = (ArrayList<String>)order.clone();
        //Get the roll type
        String type = orderCopy.get(0);
        //Check if roll is in stock
        if (stock.get(type) > 0) {
            String custType = customer.getCustType();
            //Remove roll type from order so that it only contains the decorations
            orderCopy.remove(0);
            Roll roll = createRoll(type);
            //Check if there are decorations to add to the roll
            if (orderCopy.size() > 0) {
                roll = addExtra(roll, orderCopy);
            }
            //Decrease the stock of the current roll by 1
            int curStock = this.stock.get(type) - 1;
            this.setStock(type, curStock);

            double newDailyCash = this.dailyCashSales.get(custType) + roll.getCost();
            this.dailyCashSales.put(custType,newDailyCash);
            this.totalCash+=roll.getCost();

            int newDailyRoll = this.rollsSoldDaily.get(type) + 1;
            int newTotalRoll = this.rollsSoldTotal.get(type) + 1;
            this.rollsSoldDaily.put(type,newDailyRoll);
            this.rollsSoldTotal.put(type,newTotalRoll);

            return roll;
        }
        else {
            //Roll is out of stock, return null
            //This is mainly used to catch errors in order from the customer
            return null;
        }
    }

    //Set and get the max stock for all rolls. This will be useful for varying maximum roll stock required by the outline.
    public void setMaxStock(int maxStock){
        this.maxStock = maxStock;
    }

    public int getMaxStock(){
        return(maxStock);
    }

    public void setStock(String type, int stock){
        this.stock.put(type,stock);
        //Send announcement letting customers know a type of roll has sold out
        if (stock == 0){
            watcher.firePropertyChange(type,1,0);
            //Check if all rolls sold out
            this.allOutOfStock();

        }
    }

    public HashMap<String, Integer> getStock(){
        return(stock);
    }

    public void openStore(){
        System.out.println("The store is now open!\n");
        open=true;
    }

    public void closeStore(){
        System.out.println("The store is now closed!\n");
        open=false;
    }

    public boolean isOpen(){
        return open;
    }

    public int getDay(){
        return day;
    }
    public void newDay(){
        day++;
    }

    //Note: this method will only run when one roll type sells out. There should be no risk of it firing a property change when the stock is max for all rolls.
    public void allOutOfStock(){
//        https://dev4devs.com/2018/01/18/java8-how-to-check-if-all-values-in-an-array-are-equal/
        //If 0 is the only distinct value in inStock, that means all rolls have sold out.
        if (stock.values().stream().distinct().count() == 1) {
            watcher.firePropertyChange("open", true, false);
            this.closeStore();
        }
    }

    public void restockRolls(){
        //        https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
        // Restock any rolls that are completely out for the new day
        for (Map.Entry item: stock.entrySet()){
            int stockAmount = (int)item.getValue();
            if (stockAmount == 0){
                String type = (String)item.getKey();
                this.setStock(type,maxStock);
            }
        }
    }

    public void displayDailyCashSales(){
//        https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
        DecimalFormat df = new DecimalFormat("0.00");
        double dailyTotal = 0;
        System.out.println("Total payment for orders:");
        for(Map.Entry item: this.dailyCashSales.entrySet()){
            String type = (String)item.getKey();
            double sale = (double)item.getValue();
            dailyTotal+=sale;
            System.out.println(type + " customers: $" + df.format(sale));
        }
        System.out.println("Overall total payment: $" + df.format(dailyTotal));
    }

    public void displayDailyImpacts(){
        System.out.println("Number of orders impacted by roll outages:");
        for(Map.Entry item: this.dailyOutageImpacts.entrySet()){
            String type = (String)item.getKey();
            int amount = (int)item.getValue();
            System.out.println(type + " customers: " + amount + " orders impacted.");
        }
        System.out.println();
    }

    public void displayDailyRollOrders(){
        System.out.println("Inventory orders by roll type:");
        for(Map.Entry item: this.rollsSoldDaily.entrySet()){
            String type = (String)item.getKey();
            int amount = (int)item.getValue();
            System.out.println(type + " rolls: " + amount + " sold");
        }
        System.out.println();
    }

    public void displayTotalRollOrders(){
        int totalSold = 0;
        System.out.println("Total number of rolls sold by type:");
        for(Map.Entry item: this.rollsSoldTotal.entrySet()){
            String type = (String)item.getKey();
            int amount = (int)item.getValue();
            totalSold+=amount;
            System.out.println(type + " rolls: " + amount + " sold");
        }
        System.out.println("Total number of rolls sold overall: " + totalSold + " sold");
    }

    public void displayOrderDetails(Customer customer, ArrayList<Roll> rollsOrdered){
        double cost = 0.0;
        ArrayList<Roll> orderCopy = (ArrayList<Roll>)rollsOrdered.clone();
        HashMap<String, Integer> orderCounter = new HashMap<>();
        HashMap<String, Integer> extraCounter = new HashMap<>();
        for(Roll roll: orderCopy){
            String desc = roll.getDescription();
            desc = desc.replace(", ", "/");
            desc = desc.replace("roll","");
            String[] info = desc.split("/");
            String rollType = info[0];
            if (orderCounter.containsKey(rollType)) {
                int newAmount = orderCounter.get(rollType) + 1;
                orderCounter.put(info[0],newAmount);
            }
            else{
                orderCounter.put(info[0],1);
            }
            if (info.length > 1) {
                //https://www.techiedelight.com/get-subarray-array-specified-indexes-java/
                String[] curExtra = Arrays.copyOfRange(info, 1, info.length - 1);
                for (String ex: curExtra){
                    if(extraCounter.containsKey(ex)){
                        int newExAmount = extraCounter.get(ex)+1;
                        extraCounter.put(ex,newExAmount);
                    }
                    else{
                        extraCounter.put(ex,1);
                    }
                }
            }
            cost+=roll.getCost();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Customer type: " + customer.getCustType());
        System.out.println("Rolls ordered by type:");
        for(Map.Entry item: orderCounter.entrySet()){
            String type = (String)item.getKey();
            int amount = (int)item.getValue();
            System.out.println(type + "roll: " + amount);
        }
        System.out.println("Extras Ordered:");
        for(Map.Entry item: extraCounter.entrySet()){
            String ex = (String)item.getKey();
            int amount = (int)item.getValue();
            System.out.println(ex + " : " + amount);
        }
        System.out.println("Total cost of order: $" + df.format(cost) + "\n");
    }

    public double getTotalCash(){
        return totalCash;
    }

    public int getTotalOutageImpacts(){return totalOutageImpacts;}

    public void initDailyCashSales(String[] custTypes){
        for (String cust: custTypes) {
            this.dailyCashSales.put(cust, 0.0);
        }
    }

    public void initDailyOutageImpacts(String[] custTypes){
        for (String cust: custTypes) {
            this.dailyOutageImpacts.put(cust, 0);
        }
    }

    public void initRollsSold(String[] rollTypes){
        for (String roll: rollTypes) {
            this.rollsSoldDaily.put(roll, 0);
            this.rollsSoldTotal.put(roll, 0);
        }
    }

    public void incOutageImpact(String custType){
        int newDailyImp = this.dailyOutageImpacts.get(custType)+1;
        this.dailyOutageImpacts.put(custType,newDailyImp);
        totalOutageImpacts++;
    }

    public void resetDailyOutages(){
        for(Map.Entry item: this.dailyOutageImpacts.entrySet()){
            String type = (String)item.getKey();
            this.dailyOutageImpacts.put(type,0);
        }
    }

    public void resetDailyCashSales(){
        for(Map.Entry item: this.dailyCashSales.entrySet()){
            String type = (String)item.getKey();
            this.dailyCashSales.put(type,0.0);
        }
    }

    public void resetDailyRollSales(){
        for(Map.Entry item: this.rollsSoldDaily.entrySet()){
            String type = (String)item.getKey();
            this.rollsSoldDaily.put(type,0);
        }
    }

    //Add or remove the observer for the employee
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        watcher.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        watcher.removePropertyChangeListener(listener);
    }

}
