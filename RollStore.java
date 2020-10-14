import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.*;

//Implement Factory pattern in the form of RollStore. RollStore will be used to create and sell rolls. It will also track and display sales data.
public abstract class RollStore {

    //Observer that will keep track of the roll inventory of the store. Will announce when individual types and all types run out.
    private PropertyChangeSupport watcher = new PropertyChangeSupport(this);
    //Stock will be a hashmap with the keys being the roll types and the values being their current stock.
    private HashMap<String, Integer> stock = new HashMap<String, Integer>();
    //Maxstock is used hold the maximum amount of rolls of any type that can be held. Allows us to specify the value at runtime if desired.
    private int maxStock;
    private boolean open;
    private int day = 0;

    private int totalOutageImpacts;
    private double totalCash;
    //Daily outage impacts, cash sales, and rolls sales will all be hashmaps. Their keys will be customer and roll types respectively.
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

            //Increase the money we've made from the roll sale
            double newDailyCash = this.dailyCashSales.get(custType) + roll.getCost();
            this.dailyCashSales.put(custType,newDailyCash);
            this.totalCash+=roll.getCost();

            //Increment the number of this roll type that we've sold.
            int newDailyRoll = this.rollsSoldDaily.get(type) + 1;
            int newTotalRoll = this.rollsSoldTotal.get(type) + 1;
            this.rollsSoldDaily.put(type,newDailyRoll);
            this.rollsSoldTotal.put(type,newTotalRoll);

            return roll;
        }
        else {
            //Roll is out of stock, return null
            //This is mainly used to catch errors in the ordering process from the customer
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

    //Open and close the store. Also announce that the store is open/closed.
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

    //Getter and incrementer for day attribute.
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

    //All of the display... functions will be used to calculate and output statistics at the end of every day and at the end of 30 days.
    //        https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
    //        https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
    public void displayDailyCashSales(){
        DecimalFormat df = new DecimalFormat("0.00");
        //Daily total will hold the total cash made on that day.
        double dailyTotal = 0;
        System.out.println("Total payment for orders today (Day " + this.day + "):");
        for(Map.Entry item: this.dailyCashSales.entrySet()){
            String type = (String)item.getKey();
            double sale = (double)item.getValue();
            //Increase the daily total with the current roll sale
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

    public void displayRollsOrdered(String outType){
        //Useset will be the entryset that will be parsed for output
        Set<Map.Entry<String, Integer>> useSet;
        //Total sold will hold the total amount of rolls sold
        int totalSold = 0;
        if (outType.equals("Daily")){
            useSet  = this.rollsSoldDaily.entrySet();
            System.out.println("Rolls sold today (Day " + this.day + "):");
        }
        else{
            useSet  = this.rollsSoldTotal.entrySet();
        }
        for(Map.Entry item: useSet){
            String type = (String)item.getKey();
            int amount = (int)item.getValue();
            //Increase total rolls sold with the value of the current type
            totalSold+=amount;
            System.out.println(type + " rolls: " + amount + " sold");
        }
        //Only the 30-day output should output the total number of rolls sold.
        if(outType.equals("Total")) {
            System.out.println("Total number of rolls sold overall: " + totalSold + " sold");
        }
    }

//    https://www.journaldev.com/776/string-to-array-java
//    https://www.journaldev.com/18361/java-remove-character-string
//https://www.techiedelight.com/get-subarray-array-specified-indexes-java/
    public void displayOrderDetails(Customer customer, ArrayList<Roll> rollsOrdered){
        double cost = 0.0;
        //Make a copy of rollsOrdered so that the original is not modified in any way
        ArrayList<Roll> orderCopy = (ArrayList<Roll>)rollsOrdered.clone();
        //Order counter will hold the number of rolls by type and extras ordered
        HashMap<String, Integer> orderCounter = new HashMap<>();
        //allRolls will hold every rolls information (how many of each extra they have if some was ordered).
        ArrayList<String> allRolls = new ArrayList<>();
        //Iterate through the rolls ordered
        for(Roll roll: orderCopy){
            //outputOrder will be the string we output when displaying the customer's order information per roll
            String outputOrder = "";
            //Extra counter will hold the number of extras ordered per roll
            HashMap<String, Integer> extraCounter = new HashMap<>();
            String desc = roll.getDescription();
            //Modify the roll description so that it can more easily be parsed
            desc = desc.replace(", ", "/");
            //Hold all of the roll's information in an array to be parsed
            String[] info = desc.split("/");
            String rollType = info[0];
            outputOrder += info[0];
            //Check if the roll we're on has already been counted
            if (orderCounter.containsKey(rollType)) {
                //Increment the already counted roll type
                int newAmount = orderCounter.get(rollType) + 1;
                orderCounter.put(info[0],newAmount);
            }
            else{
                //Add the new roll to our counter hashmap
                orderCounter.put(info[0],1);
            }
            //Check if the roll had any extras added to it (info will have length 1 if only the roll itself was ordered).
            if (info.length > 1) {
                //Create an array to hold only the extras
                String[] curExtra = Arrays.copyOfRange(info, 1, info.length);
                //Iterate through the extras and add them to our counter hashmap
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
            //Iterate through the extra counter and output the number of each extra that was ordered
            for(Map.Entry item: extraCounter.entrySet()){
                String ex = (String)item.getKey();
                int amount = (int)item.getValue();
                outputOrder+= ", " + amount + " " + ex;
            }
            allRolls.add(outputOrder);
            //Add current roll cost to total
            cost+=roll.getCost();
        }
        //Properly output the price with df (only 2 decimal places)
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Customer type: " + customer.getCustType());
        System.out.println("Rolls ordered by type:");
        //Iterate through the order counter and output the number of each roll that was ordered
        for(Map.Entry item: orderCounter.entrySet()){
            String type = (String)item.getKey();
            int amount = (int)item.getValue();
            System.out.println(type + ": " + amount);
        }
        System.out.println("Individual roll information:");
        for(String curRoll: allRolls){
            System.out.println(curRoll);
        }
        System.out.println("Total cost of order: $" + df.format(cost) + "\n");
    }

    public double getTotalCash(){
        return totalCash;
    }
    public int getTotalOutageImpacts(){return totalOutageImpacts;}

    //All of the init... methods below will be used to initialize the attributes below to their default starting values (0).
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

    //Method will be used to increment the number of outages for that day and overall
    public void incOutageImpact(String custType){
        int newDailyImp = this.dailyOutageImpacts.get(custType)+1;
        this.dailyOutageImpacts.put(custType,newDailyImp);
        totalOutageImpacts++;
    }

    //All the reset... methods below will be used to reset the attributes below to their daily default values.
    //These are different from the init... methods as these assume the rolls/customer types have already been defined (Allows for addition/subtraction of customer/roll types).
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
