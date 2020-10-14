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
        HashMap<String,Integer> randChoices = new HashMap<>();
        Random rand = new Random();
        int randBuy = rand.nextInt(3)+1;
        int chosen = 0;
        while(chosen < randBuy){
            String curChoice = types.get(rand.nextInt(types.size()));
            if(randChoices.containsKey(curChoice)){
                int newAmount =randChoices.get(curChoice) + 1;
                randChoices.put(curChoice,newAmount);
            }
            else{
                randChoices.put(curChoice,1);
            }
            chosen++;
        }
        //Replace will track the number of rolls we may need to replace if what we've chosen is out of stock.
        int replace = 0;
        ArrayList<String> remove = new ArrayList<>();
        for (Map.Entry item: randChoices.entrySet()){
            int desire = (int)item.getValue();
            String type = (String)item.getKey();
            //Not enough of the desired roll(s) is in stock; original order not possible
            if (desire > stock.get(type)){
                this.setOgOrderPossible(false);
                //Desired roll is completely out of stock, remove from order list.
                if(stock.get(type) == 0){
                    remove.add(type);
                    //Increment replace so that we can replace the removed roll later.
                    replace++;
                }
                else{
                    //The number of rolls we want of this type is less than what is available. Set the number that we'll order equal to what is in stock.
                    desire = stock.get(type);
                    randChoices.put(type,desire);
                }
            }
        }

        //Check if we need to replace rolls that we ordered that were out of stock
        if (replace > 0){
            //Make a copy of the types arraylist. This is used to ensure no keys from stock is modified in any way
            ArrayList<String> typesCopy = (ArrayList<String>)types.clone();
            //Remove the out of stock rolls from our choices.
            for (String type: remove){
                randChoices.remove(type);
                typesCopy.remove(type);
            }
            //While we haven't replaced our removed rolls, and while there are still rolls in stock to choose from, replace our removed rolls.
            while (replace > 0 && typesCopy.size() > 0){
                String curRoll = typesCopy.get(0);
                //Roll already previously chosen. See if there's enough additional stock to buy.
                if(randChoices.containsKey(curRoll)){
                    int stockCheck = randChoices.get(curRoll) + 1;
                    if (stockCheck <= stock.get(curRoll)){
                        //There's enough of the roll in stock to order. Add it to the order.
                        randChoices.put(curRoll,stockCheck);
                        replace--;
                    }
                }
                //Roll has not yet been chosen
                else{
                    //Check to see if the roll chosen is in stock so that we can add it to our order.
                    if(stock.get(curRoll) > 0){
                        randChoices.put(curRoll,1);
                        replace--;
                    }
                }
                typesCopy.remove(0);
            }
        }

        //Add all random rolls chosen to order
        for (Map.Entry item: randChoices.entrySet()){
            String randChoice = (String)item.getKey();
            int numBuy = (int)item.getValue();
            for(int i = 0; i < numBuy; i++) {
                order.add(rollOrder(randChoice));
            }
        }
        this.setCurOrder(order);
    }

}
