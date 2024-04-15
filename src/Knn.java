import java.util.*;

public class Knn {

    public static double getDistance(Iris iris1, Iris iris2) {
        double distance = 0;


        for (int i = 0; i < iris1.getConditionalAttributes().size(); i++)
            distance += Math.pow(iris1.getConditionalAttributes().get(i) - iris2.getConditionalAttributes().get(i), 2);

        return Math.sqrt(distance);
    }


    public static Iris.IrisType classifyTheIris(int k, List<Iris> dataSet, Iris irisToClassify) {

        Map<Double, List<Iris>> neighboursMap = new TreeMap<>();
        double key;

        for (Iris iris : dataSet) {
            key = getDistance(irisToClassify, iris);
            List<Iris> neighbours = neighboursMap.getOrDefault(key, new ArrayList<>());
            neighbours.add(iris);
            neighboursMap.put(key, neighbours);
        }

        Map<Iris.IrisType,Integer> typesMap = new TreeMap<>();
        int counter = 0;

        for(Map.Entry<Double, List<Iris>> neighbour : neighboursMap.entrySet()){
            for(Iris iris : neighbour.getValue()){
                typesMap.put(iris.getType(), typesMap.getOrDefault(iris.getType(),0) + 1);
                counter++;
                if (counter == k)
                    break;
            }

            if (counter == k)
                break;
        }

        Optional<Map.Entry<Iris.IrisType,Integer>> maxEntry = typesMap.entrySet().stream().max(Map.Entry.comparingByValue());

        return maxEntry.get().getKey();
    }
}
