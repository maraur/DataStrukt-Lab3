import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by fredrik on 2016-02-19.
 */
public class TestSetCorrectness {
    public static void main(String[] args) {

        Random rand = new Random();

        final int implnumber = Integer.parseInt(args[0]);
        final int numbOfRestarts = Integer.parseInt(args[1]);
        final int numbOfRandOps = Integer.parseInt(args[2]);
        final int numbOfInts = Integer.parseInt(args[3]);

        for( int i = 0 ; i < numbOfRestarts; i++){
            SimpleSet<Integer> set =
                    implnumber == 1 ? new SortedLinkedListSet<Integer>() : new SplayTreeSet<Integer>();
            Set<Integer> testSet = new TreeSet<Integer>();
            for(int j = 0; j < numbOfRandOps; j++){
                int operation = new Integer(rand.nextInt(4));
                int randValue = new Integer(rand.nextInt(numbOfInts));
                boolean result = false;
                switch(operation){
                    case 0:
                        result = (set.add(randValue) == testSet.add(randValue));
                        break;
                    case 1:
                        result = (set.remove(randValue) == testSet.remove(randValue));
                        break;
                    case 2:
                        result = (set.contains(randValue) == testSet.contains(randValue));
                        break;
                    case 3:
                        result = (set.size() == testSet.size());
                        break;
                }
                if(!result){
                    System.out.println("Test failed on restart " + i + " operation " + j);
                    System.out.println("Failed on operation type " + operation);
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
