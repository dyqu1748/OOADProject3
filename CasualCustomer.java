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
                chosen++;
            }
            else{
                randChoices.put(curChoice,1);
                chosen++;
            }
        }
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
                    replace++;
                }
                else{
                    desire--;
                    randChoices.put(type,desire);
                }
            }
        }

        //Check if we need to replace rolls that we ordered that were out of stock
        if (replace > 0){
            ArrayList<String> typesCopy = (ArrayList<String>)types.clone();
            for (String type: remove){
                randChoices.remove(type);
                typesCopy.remove(type);
            }
            while (replace > 0 && typesCopy.size() > 0){
                String curRoll = typesCopy.get(0);
                //Roll already previously chosen. See if there's enough additional stock to buy.
                if(randChoices.containsKey(curRoll)){
                    int stockCheck = randChoices.get(curRoll) + 1;
                    if (stockCheck <= stock.get(curRoll)){
                        randChoices.put(curRoll,stockCheck);
                        replace--;
                    }
                }
                else{
                    //Check to see if the roll chosen is in stock
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
