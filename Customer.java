import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Customer {
    private ArrayList<ArrayList<String>> curOrder = new ArrayList<>();
    private ArrayList<Roll> rollsOrdered = new ArrayList<>();
    private String custType;
    private boolean ogOrderPossible;

    public void setOgOrderPossible(boolean originalOrder){
        this.ogOrderPossible = originalOrder;
    }

    public boolean isOgOrderPossible(){
        return ogOrderPossible;
    }

    public void setCustType(String type){
        this.custType = type;
    }

    public String getCustType(){
        return custType;
    }

    public void setCurOrder(ArrayList<ArrayList<String>> order){
        this.curOrder = order;
    }

    public ArrayList<ArrayList<String>> getCurOrder(){
        return curOrder;
    }

    public void addRollOrdered(Roll roll){
        rollsOrdered.add(roll);
    }

    public ArrayList<Roll> getRollsOrdered(){
        return rollsOrdered;
    }

    public abstract void makeOrder(HashMap<String, Integer> stock);

    public ArrayList<String> rollOrder(String type){
        ArrayList<String> curOrder = new ArrayList<>();
        curOrder.add(type);
        curOrder.addAll(extraOrder());
        return curOrder;
    }

    public ArrayList<String> extraOrder(){
        Random rand = new Random();
        ArrayList<String> extras = new ArrayList<>();
        int chanceTop = rand.nextInt(100)+1;
        int chanceFill = rand.nextInt(100)+1;
        int chanceSauce = rand.nextInt(100)+1;
        if (chanceSauce <= 25){
            chanceSauce = 0;
        }
        else if (chanceSauce > 25 && chanceSauce <=50){
            chanceSauce = 1;
        }
        else if (chanceSauce >50 && chanceSauce <=75){
            chanceSauce = 2;
        }
        else{
            chanceSauce = 3;
        }

        if (chanceFill <=50){
            chanceFill = 0;
        }
        else{
            chanceFill = 1;
        }

        if (chanceTop <= 33){
            chanceTop = 0;
        }
        else if (chanceTop > 33 && chanceTop <=66){
            chanceTop = 1;
        }
        else{
            chanceTop = 2;
        }


        for (int i =0; i < chanceSauce; i++){
            extras.add("Sauce");
        }
        for (int i =0; i < chanceFill; i++){
            extras.add("Filling");
        }
        for (int i =0; i < chanceTop; i++){
            extras.add("Topping");
        }

        return extras;

    }

}
