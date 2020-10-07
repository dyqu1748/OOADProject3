import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class StockAnnouncer implements PropertyChangeListener {
    private boolean stockOut;

    public StockAnnouncer(){
        stockOut = false;
    }

    public void resetStock(){
        stockOut= true;
    }

    public boolean isStockOut(){
        return stockOut;
    }

    public void propertyChange(PropertyChangeEvent evt){
        if (evt.getPropertyName() == "open"){
            System.out.println("Attention all customers, we have run out of all rolls for the day. The store will now close. We apologize for the inconvenience.\n");
            stockOut = true;
        }
        else{
            System.out.println("Attention all customers, we have run out of " + evt.getPropertyName() + " rolls for the day. We apologize for the inconvenience.\n");
        }
    }
}
