package org.example;

public class Statistics {
    private final int maxRequestsPerSecond;//перемнную нельзя будет изменить
    private final double averageRequestsPerSecond;

    public Statistics(int maxRequestsPerSecond, double averageRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
        this.averageRequestsPerSecond = averageRequestsPerSecond;
    }

    public int getMaxRequestsPerSecond() {
        return maxRequestsPerSecond;
    }

    public double getAverageRequestsPerSecond() {
        return averageRequestsPerSecond;
    }

}
