import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CateringCustomer extends Customer {

    public CateringCustomer(){
        this.setCustType("Catering");
        this.setOgOrderPossible(true);
    }

    public void makeOrder(HashMap<String, Integer> stock){
        ArrayList<String> types = new ArrayList<>();
        types.addAll(stock.keySet());
        ArrayList<Integer> randTypes = new ArrayList<>();
        Random rand = new Random();
        //Randomly choose 3 types of rolls to order
        while (randTypes.size() < 3) {
            int posRandType = rand.nextInt(types.size());
            if (!randTypes.contains(posRandType)){
                randTypes.add(posRandType);
            }
        }
        //Check if there are at least 5 rolls of the types we want
        for (int index: randTypes){
            String curType = types.get(index);
            if (stock.get(curType) < 5){
                //Not enough rolls in stock, original order not possible.
                this.setOgOrderPossible(false);
            }
        }
        ArrayList<ArrayList<String>> order = new ArrayList<>();
        //Original order possible
        if (this.isOgOrderPossible()){
            for (int index: randTypes){
                String curType = types.get(index);
                for (int i = 0; i<5; i++){
                    order.add(rollOrder(curType));
                }
            }
        }
        //Original order not possible
        else{
            int numOrdered = 0;
            int typeInd = 0;
            //Add available rolls to order until we've ordered 15 or until there are no rolls left.
            while(numOrdered < 15 && typeInd < types.size()){
                String curType = types.get(typeInd);
                int curStock = stock.get(curType);
                if (numOrdered + curStock <= 15) {
                    for (int i = 0; i < curStock; i++) {
                        order.add(rollOrder(curType));
                        numOrdered++;
                    }
                }
                else{
                    for (int i = 0; i < 15 - numOrdered;i++){
                        order.add(rollOrder(curType));
                        numOrdered++;
                    }
                }
            }

        }
        this.setCurOrder(order);
    }
}
