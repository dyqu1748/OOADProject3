import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.*;

public class CasualCustomer extends Customer {

    public CasualCustomer(){
        this.setCustType("Casual");
        this.setOgOrderPossible(true);
    }


    public void makeOrder(HashMap<String, Integer> stock){
        ArrayList<ArrayList<String>> order = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        types.addAll(stock.keySet());
        Random rand = new Random();
        int randType = rand.nextInt(types.size());
        int randBuy = rand.nextInt(3)+1;
        String randChoice = types.get(randType);
        //Check if original order is possible.
        if (stock.get(randChoice) >= randBuy){
            this.setOgOrderPossible(false);
            //If the roll we want is completely out of stock, choose a different roll type.
            if (stock.get(randChoice) == 0) {
                //rollChoose will be used to notify the loop to stop choosing a roll type once we've found one that is in stock.
                boolean rollChoose = false;
                for (Map.Entry item : stock.entrySet()) {
                    if (!rollChoose) {
                        int amount = (int) item.getValue();
                        if (amount > 0) {
                            randChoice = (String) item.getKey();
                            rollChoose = true;
                        }
                    }
                }
            }
        }
        int amount = stock.get(randChoice);
        //If the number of rolls we want to buy is more than what is in stock, decrease the amount to buy to the amount available.
        if (randBuy > amount){
            randBuy = amount;
        }
        for (int i = 0; i < randBuy;i++){
            order.add(rollOrder(randChoice));
        }
        this.setCurOrder(order);
    }

}
