import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    static volatile boolean flag = false;
    static volatile boolean working = true;

    public static void main(String[] args) {

        DataReader.readFile(DataReader.testPath);
        DataReader.readFile(DataReader.trainingPath);

        Scanner scanner = new Scanner(System.in);
        String answer;
        int k = 0;

        System.out.println("Welcome!");

        //MAIN LOOP
        do{
            //OPTIONS LOOP
            do {
                System.out.println();
                System.out.println("Choose option (1-3)" + '\n' +
                                   "1. Do a test and classify irises from test file" + '\n' +
                                   "2. Input own data of iris you want to classify" + '\n' +
                                   "3. Close the program");
                answer = scanner.nextLine();
            }while (!(answer.equals("1") || answer.equals("2") || answer.equals("3")));

            //ANSWER
            switch (answer) {
                //TEST
                case "1" -> {
                    //FOR k LOOP
                    do {
                        flag = false;
                        System.out.println("For how many closest irises do you want to do the test? (Choose 1-" +
                                           Iris.trainingData.size() + ")");

                        try {
                            k = scanner.nextInt();

                            if (!(k >= 1 && k <= Iris.trainingData.size()))
                                throw new InputMismatchException();

                        } catch (InputMismatchException e) {
                            System.out.println("Wrong number or format, try again!");
                            flag = true;
                        }finally {
                            scanner.nextLine();
                        }
                    } while (flag);


                    for (Iris iris : Iris.toClassifyData)
                        iris.setType(Knn.classifyTheIris(k, Iris.trainingData, iris));

                    int correct = 0;
                    int wrong = 0;

                    for (int i = 0; i < Iris.toClassifyData.size(); i++) {
                        if (Iris.toClassifyData.get(i).getType().equals(Iris.testData.get(i).getType()))
                            correct++;
                        else
                            wrong++;
                    }

                    double accuracy = (double) correct / (double) (correct + wrong) * 100;
                    BigDecimal bd = new BigDecimal(Double.toString(accuracy));
                    bd = bd.setScale(2, RoundingMode.HALF_UP);
                    accuracy = bd.doubleValue();

                    System.out.println("Correct answers: " + correct);
                    System.out.println("Wrong answers: " + wrong);
                    System.out.println("Accuracy of the test: " + accuracy + "%");
                }

                //OWN IRIS
                case "2" -> {

                    int howManyValues = Iris.testData.get(0).getConditionalAttributes().size();
                    List<Double> attributes = new ArrayList<>();
                    String[] parts;

                    //FOR VALUES LOOP
                    do {
                        flag = false;

                        System.out.println("Input " + howManyValues +
                                           " values of attributes in format: double double double double ..." + '\n' +
                                           "for example: 1.0 2.0 3.0 4.0");

                        answer = scanner.nextLine();
                        parts = answer.replace(',', '.').split(" ");

                        try {
                            if (parts.length != howManyValues)
                                throw new NumberFormatException();

                            for (String part : parts)
                                attributes.add(Double.parseDouble(part));

                        } catch (NumberFormatException n) {
                            System.out.println("Wrong format, try again!");
                            flag = true;
                        }
                    } while (flag);

                    //FOR k LOOP
                    do {
                        flag = false;

                        System.out.println("For how many closest irises do you want to find the type? (Choose 1-" +
                                            Iris.testData.size() + ")");

                        try {
                            k = scanner.nextInt();

                            if (!(k >= 1 && k <= Iris.testData.size()))
                                throw new InputMismatchException();

                        } catch (InputMismatchException e) {
                            System.out.println("Wrong number or format, try again!");
                            flag = true;

                        }finally {
                            scanner.nextLine();
                        }
                    } while (flag);

                    Iris newIris = new Iris(attributes, null);
                    newIris.setType(Knn.classifyTheIris(k, Iris.testData, newIris));

                    System.out.println("Your iris " + newIris.getConditionalAttributes() +
                                       " was classified as " + newIris.getType());

                }

                //END OF THE PROGRAM
                case "3" -> working = false;

            }
        }while(working);
    }
}
