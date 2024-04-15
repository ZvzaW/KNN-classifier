import java.util.ArrayList;
import java.util.List;

public class Iris{

    public enum IrisType {SETOSA,VERSICOLOR,VIRGINICA,UNKNOWN}
    public static List<Iris> trainingData = new ArrayList<>();
    public static List<Iris> testData= new ArrayList<>();
    public static List<Iris> toClassifyData = new ArrayList<>();

    private List<Double> conditionalAttributes;

    private IrisType type;


    public Iris(List<Double> conditionalAttributes, IrisType type) {
        this.conditionalAttributes = conditionalAttributes;
        this.type = type;
    }


    public IrisType getType() {
        return type;
    }

    public void setType(IrisType type) {
        this.type = type;
    }

    public List<Double> getConditionalAttributes(){
        return conditionalAttributes;
    }
}
