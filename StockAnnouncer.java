import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

//Implement observer pattern for the program in the form of StockAnnouncer. StockAnnouncer will announce a roll or all rolls run out.
public class StockAnnouncer implements PropertyChangeListener {
    //Attribute below will be used to output whether or not the store closed due to all rolls running out in Main.
    private boolean stockOut;

    public StockAnnouncer(){
        stockOut = false;
    }

    //Reset the stock for the next day if it ran out the previous day.
    public void resetStock(){
        stockOut= false;
    }
    //Check if the store closed due to running out of stock.
    public boolean isStockOut(){
        return stockOut;
    }

    public void propertyChange(PropertyChangeEvent evt){
        //Check to see if the store is no longer open. This means that the store closed due to lack of inventory.
        if (evt.getPropertyName() == "open"){
            System.out.println("Attention all customers, we have run out of all rolls for the day. The store will now close. We apologize for the inconvenience.\n");
            stockOut = true;
        }
        //Otherwise, announce that the specific roll has run out.
        else{
            System.out.println("Attention all customers, we have run out of " + evt.getPropertyName() + " rolls for the day. We apologize for the inconvenience.\n");
        }
    }
}
