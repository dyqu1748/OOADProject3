import java.util.ArrayList;

public class BoulderRollStore extends RollStore {

    public BoulderRollStore(int maxStock){
        String[] rollTypes = {"Spring", "Egg", "Pastry", "Sausage", "Jelly"};
        this.setMaxStock(maxStock);
        for (String type: rollTypes){
            this.setStock(type,maxStock);
        }
        this.openStore();
    }

    public Roll createRoll(String type){
        //Create the roll of the requested type.
        switch (type) {
            case "Spring":
                return new SpringRoll();
            case "Egg":
                return new EggRoll();
            case "Pastry":
                return new PastryRoll();
            case "Sausage":
                return new SausageRoll();
            case "Jelly":
                return new JellyRoll();
            default:
                //Invalid roll type inputted. Print out error statement and return null.
                System.out.println("Invalid roll type");
                return null;
        }
    }

    public Roll addExtra(Roll roll, ArrayList<String> extra){
        //Add requested extras to the roll
        for (String ex: extra){
            switch(ex){
                case "Sauce":
                    roll = new Sauce(roll);
                    break;
                case "Filling":
                    roll = new Filling(roll);
                    break;
                case "Topping":
                    roll = new Topping(roll);
                    break;
            }
        }
        return roll;
    }
}
