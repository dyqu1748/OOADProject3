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

    public Roll createRoll(String type, ArrayList<String> extra){
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
                System.out.println("Invalid roll type");
                return null;
        }
    }

    public Roll addExtra(Roll roll, ArrayList<String> extra){
        for (String ex: extra){
            switch(ex){
                case "sauce":
                    roll = new Sauce(roll);
                    break;
                case "filling":
                    roll = new Filling(roll);
                    break;
                case "topping":
                    roll = new Topping(roll);
                    break;
            }
        }
        return roll;
    }
}
