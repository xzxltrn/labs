package edu.course.lab03;

public final class ZScoreNormalization implements NormalizationStrategy {

    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("values must not be null or empty");
        }

        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum = sum + values[i];
        }
        double mean = sum / values.length;

        double squares = 0;
        for (int i = 0; i < values.length; i++) {
            squares = squares + (values[i] - mean) * (values[i] - mean);
        }
        double std = Math.sqrt(squares / values.length);

        double[] result = new double[values.length];
        if (std == 0) {
            return result;
        }

        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - mean) / std;
        }

        return result;
    }
}