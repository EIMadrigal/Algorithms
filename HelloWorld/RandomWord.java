import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String ans = "";
        while (!StdIn.isEmpty()) {
            String cur = StdIn.readString();
            ++i;
            if (StdRandom.bernoulli(1.0 / i)) {
                ans = cur;
            }
        }
        System.out.println(ans);
    }
}
