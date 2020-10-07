public abstract class RollDecorator extends Roll {
    //Have roll as a protected attribute so that only the decorators can modify it
    protected Roll roll;

    public abstract String getDescription();

}
