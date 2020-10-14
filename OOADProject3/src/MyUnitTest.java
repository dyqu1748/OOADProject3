import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class MyUnitTest {

    //Test to make sure that all casual customers' orders contain 1-3 rolls.
    @Test
    public void testCasualCustomerOrder(ArrayList<ArrayList<String>> order)
    {

        int customerOrderSize = order.size();
        int[] validOrderAmount = {1, 2, 3};
        boolean valid = false;
        for(int element:validOrderAmount)
        {
            if(customerOrderSize == element)
            {
                valid = true;
                break;
            }
        }
        assertTrue(valid);
    }

    //Test to make sure that all business customers' orders contain either 10 rolls or no rolls.
    @Test
    public void testBusinessCustomerOrderSize(ArrayList<ArrayList<String>> order)
    {
        int customerOrderSize = order.size();
        boolean valid = true;
        if(customerOrderSize != 10)
        {
            valid = false;
            if (customerOrderSize == 0)
            {
                valid = true;
            }
        }
        assertTrue(valid);
    }

    //Test to make sure that all business customers' orders contain 2 rolls of every type.
    @Test
    public void testBusinessCustomerOrderContents(ArrayList<ArrayList<String>> order)
    {
        ArrayList<String> validOrderTypes = new ArrayList<String>();
        for (int i = 0; i < 2; i++)
        {
            validOrderTypes.add("Egg");
            validOrderTypes.add("Jelly");
            validOrderTypes.add("Pastry");
            validOrderTypes.add("Sausage");
            validOrderTypes.add("Spring");
        }
        Collections.sort(validOrderTypes);
        ArrayList<String> baseOrder = new ArrayList<String>();
        for (ArrayList<String> element:order)
        {
            String baseRoll = element.get(0);
            baseOrder.add(baseRoll);
        }
        Collections.sort(baseOrder);
        boolean valid = validOrderTypes.equals(baseOrder);
        if (baseOrder.size() == 0)
        {
            valid = true;
        }
        assertTrue(valid);
    }

    //Test to make sure that catering customers' orders contain no more than 15 rolls.
    @Test
    public void testCateringCustomerOrderSize(ArrayList<ArrayList<String>> order)
    {
        int customerOrderSize = order.size();
        boolean valid = true;
        if(customerOrderSize > 15)
        {
            valid = false;
        }
        assertTrue(valid);
    }

    /*Test to make sure the catering customers' orders contain 5 rolls for each of 3 types unless the original order did not hold.
     If the original order did not hold, then the order can be filled with anything.*/
    @Test
    public void testCateringCustomerOrderContents(boolean ogOrderPossible, ArrayList<ArrayList<String>> order)
    {
        boolean valid = true;
        if(ogOrderPossible)
        {
            ArrayList<String> baseOrder = new ArrayList<String>();
            for (ArrayList<String> element : order) {
                String baseRoll = element.get(0);
                baseOrder.add(baseRoll);
            }
            Collections.sort(baseOrder);
            ArrayList<String> distinctEntries = (ArrayList<String>) baseOrder.stream().distinct().collect(Collectors.toList());
            if (distinctEntries.size() > 3) {
                valid = false;
            }
            for (String element : distinctEntries) {
                int i = 0;
                for (int j = 0; j < baseOrder.size(); j++) {
                    if (baseOrder.get(j).equals(element)) {
                        i++;
                    }
                }
                if (i > 5) {
                    valid = false;
                }
            }
        }
        assertTrue(valid);
    }

    //Test to make sure that only up to 3 extra sauces are applied to a roll.
    @Test
    public void testExtraSauce(ArrayList<ArrayList<String>> order)
    {
        boolean valid = true;
        for (ArrayList<String> element:order)
        {
            int i = 0;
            for (String details:element)
            {
                if (details.equals("SoySauce"))
                {
                    i++;
                }
                else if (details.equals("Ketchup"))
                {
                    i++;
                }
                else if (details.equals("FruitSauce"))
                {
                    i++;
                }
                else
                {
                    i += 0;
                }
            }
            if (i > 3)
            {
                valid = false;
            }
            assertTrue(valid);
        }
    }

    //Test to make sure that only up to 1 extra filling is applied to a roll.
    @Test
    public void testExtraFilling(ArrayList<ArrayList<String>> order)
    {
        boolean valid = true;
        for (ArrayList<String> element:order)
        {
            int i = 0;
            for (String details:element)
            {
                if (details.equals("ShrimpFilling"))
                {
                    i++;
                }
                else if (details.equals("CheeseFilling"))
                {
                    i++;
                }
                else if (details.equals("FruitFilling"))
                {
                    i++;
                }
                else
                {
                    i += 0;
                }
            }
            if (i > 1)
            {
                valid = false;
            }
            assertTrue(valid);
        }
    }

    //Test to make sure that only up to 2 extra toppings are applied to a roll.
    @Test
    public void testExtraTopping(ArrayList<ArrayList<String>> order)
    {
        boolean valid = true;
        for (ArrayList<String> element:order)
        {
            int i = 0;
            for (String details:element)
            {
                if (details.equals("MintTopping"))
                {
                    i++;
                }
                else if (details.equals("BaconTopping"))
                {
                    i++;
                }
                else if (details.equals("WhippedCream"))
                {
                    i++;
                }
                else
                {
                    i += 0;
                }
            }
            if (i > 2)
            {
                valid = false;
            }
            assertTrue(valid);
        }
    }

    //Test to make sure that roll objects are instantiated properly.
    @Test
    public void testRollCreation(BoulderRollStore brs)
    {
        boolean valid = false;
        Roll roll = brs.createRoll("Spring");
        valid = roll instanceof SpringRoll;
        assertTrue(valid);
        roll = brs.createRoll("Egg");
        valid = roll instanceof EggRoll;
        assertTrue(valid);
        roll = brs.createRoll("Pastry");
        valid = roll instanceof PastryRoll;
        assertTrue(valid);
        roll = brs.createRoll("Sausage");
        valid = roll instanceof SausageRoll;
        assertTrue(valid);
        roll = brs.createRoll("Jelly");
        valid = roll instanceof JellyRoll;
        assertTrue(valid);
    }

    //Test to make sure that when the stock of rolls runs out, the store is not open.
    @Test
    public void testCloseNoStock(BoulderRollStore brs, StockAnnouncer sa)
    {
        assertEquals(brs.isOpen(), !sa.isStockOut());
    }
}
