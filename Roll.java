public abstract class Roll{

    private double cost;
    private String description;

    public void setCost(double cost){
        this.cost=cost;
    }

    public void setDescription(String info){
        this.description = info;
    }

    public double getCost(){
        return cost;
    }

    public String getDescription(){
        return description;
    }
}
