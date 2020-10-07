public abstract class RollDecorator extends Roll {
    protected Roll roll;

    public abstract String getDescription();

    public String getBaseType(){
        return (this.roll.getBaseType());
    }
}
