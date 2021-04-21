import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thres;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Index error");
        }
        thres = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                while (per.isOpen(row, col)) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                }
                per.open(row, col);
            }
            thres[i] = 1.0 * per.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thres);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thres);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(thres.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(thres.length);
    }

    // test client
    public static void main(String[] args) {
        PercolationStats perStat = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + perStat.mean());
        System.out.println("stddev = " + perStat.stddev());
        System.out.println("95% confidence interval = [" + perStat.confidenceLo() + ", " + perStat.confidenceHi() + "]");
    }
}
