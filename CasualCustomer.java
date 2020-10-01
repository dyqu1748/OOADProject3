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
        if (stock.get(randChoice) > 0){
            for (int i = 0; i < randBuy;i++){
                order.add(rollOrder(randChoice));
            }
        }
        else{
            this.setOgOrderPossible(false);
            boolean rollChoose = false;
            for(Map.Entry item: stock.entrySet()){
                if (!rollChoose) {
                    int amount = (int) item.getValue();
                    if (amount > 0) {
                        randChoice = (String) item.getKey();
                        rollChoose = true;
                    }
                }
            }
            for (int i = 0; i < randBuy;i++){
                order.add(rollOrder(randChoice));
            }
        }
        this.setCurOrder(order);
    }

}
