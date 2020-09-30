import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class StockAnnouncer implements PropertyChangeListener {

    public StockAnnouncer(){

    }

    public void propertyChange(PropertyChangeEvent evt){
        if (evt.getPropertyName() == "open"){
            System.out.println("Attention all customers, we have run out of all rolls for the day. The store will now close. We apologize for the inconvenience.");
        }
        else{
            System.out.println("Attention all customers, we have run out of " + evt.getPropertyName() + " rolls for the day. We apologize for the inconvenience.");
        }
    }
}
