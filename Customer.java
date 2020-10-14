import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Customer {
    //curOrder will hold all of the customer's roll order. This will be passed into RollStore's orderRolls method to order the requested rolls.
    private ArrayList<ArrayList<String>> curOrder = new ArrayList<>();
    //rollsOrdered will hold the rolls ordered from the customer. This will be passed into RollStore's displayOrderDetails method for output of required info.
    private ArrayList<Roll> rollsOrdered = new ArrayList<>();
    private String custType;
    private boolean ogOrderPossible;

    //Method will be used to change if the customer's orginal order was possible or not
    public void setOgOrderPossible(boolean originalOrder){
        this.ogOrderPossible = originalOrder;
    }
    //Method below will be used to track in final output how many original orders weren't possible
    public boolean isOgOrderPossible(){
        return ogOrderPossible;
    }

    //Getters/setters for customer type attribute
    public void setCustType(String type){
        this.custType = type;
    }
    public String getCustType(){
        return custType;
    }

    //Method below is used when customer is ordering, no need to call in main.
    public void setCurOrder(ArrayList<ArrayList<String>> order){
        this.curOrder = order;
    }
    //Method should be used to provide store's orderRolls method arguments. Iterate through arraylists and call orderRolls with current arraylist.
    public ArrayList<ArrayList<String>> getCurOrder(){
        return this.curOrder;
    }

    //Method below should be used in conjunction with RollStore's orderRolls method; add roll returned to customers list of ordered rolls
    public void addRollOrdered(Roll roll){
        rollsOrdered.add(roll);
    }
    //Use this method to possibly get the total cost of the customer's order. Will need to hold total cost in either the store or some other class
    public ArrayList<Roll> getRollsOrdered(){
        return rollsOrdered;
    }

    //Provide method below with the store's stock hashmap so that the customer knows what they can/can't order.
    public abstract void makeOrder(HashMap<String, Integer> stock);

    //Method that will be used to have the customer make their order for the roll passed in.
    public ArrayList<String> rollOrder(String type){
        ArrayList<String> curOrder = new ArrayList<>();
        curOrder.add(type);
        //Combine extras ordered into one singular order
        curOrder.addAll(extraOrder(type));
        return curOrder;
    }

    //This will be automatically called within roll order
    //It will add the appropriate extras to their respective rolls
    public ArrayList<String> extraOrder(String type){
        Random rand = new Random();
        ArrayList<String> extras = new ArrayList<>();
        //Will buy 0 - 2 toppings; randomly decided
        //Topping type will depend on roll type.
        int chanceTop = rand.nextInt(3);
        for (int i = 0; i < chanceTop; i++) {
            switch (type) {
                case "Spring":
                case "Egg":
                    extras.add("MintTopping");
                    break;
                case "Sausage":
                    extras.add("BaconTopping");
                    break;
                case "Pastry":
                case "Jelly":
                    extras.add("WhippedCream");
            }
        }
        //Will buy 0 - 1 fillings; randomly decided
        int chanceFill = rand.nextInt(2);
        for (int i =0; i < chanceFill; i++){
            switch (type) {
                case "Spring":
                case "Egg":
                    extras.add("ShrimpFilling");
                    break;
                case "Sausage":
                    extras.add("CheeseFilling");
                    break;
                case "Pastry":
                case "Jelly":
                    extras.add("FruitFilling");
            }
        }
        //Will buy 0 - 3 sauces; randomly decided
        int chanceSauce = rand.nextInt(4);
        for (int i =0; i < chanceSauce; i++){
            switch (type) {
                case "Spring":
                case "Egg":
                    extras.add("SoySauce");
                    break;
                case "Sausage":
                    extras.add("Ketchup");
                    break;
                case "Pastry":
                case "Jelly":
                    extras.add("FruitSauce");
            }
        }
        return extras;

    }

}
