public abstract class Roll{

    private double cost;
    private String description;
    private String baseType;

    public void setCost(double cost){
        this.cost=cost;
    }

    public void setBaseType(String base){
        this.baseType = base;
    }

    public String getBaseType(){
        return baseType;
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
