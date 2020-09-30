import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class RollStore {

    private PropertyChangeSupport watcher = new PropertyChangeSupport(this);
    private HashMap<String, Integer> inStock = new HashMap<String, Integer>();
    private int maxStock;
    private boolean open;

    //Abstract methods below allow for different stores to sell different rolls/decorators
    protected abstract Roll createRoll(String type, ArrayList<String> extra);

    protected abstract Roll addExtra(Roll roll, ArrayList<String> extra);

    public Roll orderRoll(String type, ArrayList<String> extra){
        if (this.getStock(type) > 0) {
            Roll roll = createRoll(type, extra);

            if (extra.size() > 0) {
                roll = addExtra(roll, extra);
            }

            int curStock = this.getStock(type) - 1;
            this.setStock(type, curStock);

            return roll;
        }
        return null;
    }

    public void setMaxStock(int maxStock){
        this.maxStock = maxStock;
    }

    public int getMaxStock(){
        return(maxStock);
    }

    public void setStock(String type, int stock){
        inStock.put(type,stock);
        //Send announcement letting customers know a type of roll has sold out
        if (stock == 0){
            watcher.firePropertyChange(type,1,0);
            //Check if all rolls sold out
            this.outOfStock();
        }
    }

    public int getStock(String type){
        return(inStock.get(type));
    }

    public void openStore(){
        open=true;
//        https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
        // Restock any rolls that are completely out for the new day
        for (Map.Entry item: inStock.entrySet()){
            String type = (String)item.getKey();
            int stock = (int)item.getValue();
            if (stock == 0){
                this.setStock(type,maxStock);
            }
        }
    }

    public void closeStore(){
        open=false;
    }

    //Note: this method will only run when one roll type sells out. There should be no risk of it firing a property change when the stock is max for all rolls.
    public void outOfStock(){
//        https://dev4devs.com/2018/01/18/java8-how-to-check-if-all-values-in-an-array-are-equal/
        //If 0 is the only distinct value in inStock, that means all rolls have sold out.
        if (inStock.values().stream().distinct().count() == 1) {
            this.closeStore();
            watcher.firePropertyChange("open", true, false);
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
