import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BusinessCustomer extends Customer {

    public BusinessCustomer(){
        this.setCustType("Business");
        this.setOgOrderPossible(true);
    }

    public void makeOrder(HashMap<String, Integer> stock){
        for (Map.Entry item: stock.entrySet()){
            int amount = (int)item.getValue();
            //If there isn't enough rolls in stock, cancel the order completely
            if (amount < 2){
                this.setOgOrderPossible(false);
                return;
            }
        }
        //Original order possible, make the order.
        ArrayList<ArrayList<String>> order = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        types.addAll(stock.keySet());
        for(String type: types){
            for(int i = 0; i<2;i++){
                order.add(rollOrder(type));
            }
        }
        this.setCurOrder(order);
    }
}
