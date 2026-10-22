package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NormalizationTest {

    // MinMaxNormalization

    @Test
    void minMaxScalesToZeroOneRange() {
        NormalizationStrategy strategy = new MinMaxNormalization();

        double[] result = strategy.normalize(new double[] {2.0, 4.0, 6.0});

        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, result, 1e-9);
    }

    @Test
    void minMaxReturnsZerosWhenAllValuesEqual() {
        NormalizationStrategy strategy = new MinMaxNormalization();

        double[] result = strategy.normalize(new double[] {3.0, 3.0});

        assertArrayEquals(new double[] {0.0, 0.0}, result, 1e-9);
    }

    @Test
    void minMaxDoesNotModifyInput() {
        NormalizationStrategy strategy = new MinMaxNormalization();
        double[] values = {2.0, 4.0, 6.0};

        strategy.normalize(values);

        assertArrayEquals(new double[] {2.0, 4.0, 6.0}, values);
    }

    // MeanCenteringNormalization

    @Test
    void meanCenteringSubtractsMean() {
        NormalizationStrategy strategy = new MeanCenteringNormalization();

        double[] result = strategy.normalize(new double[] {1.0, 2.0, 3.0});

        assertArrayEquals(new double[] {-1.0, 0.0, 1.0}, result, 1e-9);
    }

    @Test
    void meanCenteringDoesNotModifyInput() {
        NormalizationStrategy strategy = new MeanCenteringNormalization();
        double[] values = {1.0, 2.0, 3.0};

        strategy.normalize(values);

        assertArrayEquals(new double[] {1.0, 2.0, 3.0}, values);
    }

    @Test
    void meanCenteringRejectsEmptyArray() {
        NormalizationStrategy strategy = new MeanCenteringNormalization();

        assertThrows(IllegalArgumentException.class, () -> strategy.normalize(new double[] {}));
    }

    // ZScoreNormalization

    @Test
    void zScoreDividesByStandardDeviation() {
        NormalizationStrategy strategy = new ZScoreNormalization();

        double[] result = strategy.normalize(new double[] {2.0, 4.0, 6.0, 8.0});

        double std = Math.sqrt(5.0);
        assertArrayEquals(new double[] {-3.0 / std, -1.0 / std, 1.0 / std, 3.0 / std}, result, 1e-9);
    }

    @Test
    void zScoreReturnsZerosWhenAllValuesEqual() {
        NormalizationStrategy strategy = new ZScoreNormalization();

        double[] result = strategy.normalize(new double[] {5.0, 5.0, 5.0});

        assertArrayEquals(new double[] {0.0, 0.0, 0.0}, result, 1e-9);
    }

    // FeatureProcessor

    @Test
    void processorWorksWithAnyStrategy() {
        double[] features = {1.0, 2.0, 3.0};
        FeatureProcessor minMax = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor centering = new FeatureProcessor(new MeanCenteringNormalization());

        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, minMax.process(features), 1e-9);
        assertArrayEquals(new double[] {-1.0, 0.0, 1.0}, centering.process(features), 1e-9);
    }

    @Test
    void processorRejectsNullStrategy() {
        assertThrows(IllegalArgumentException.class, () -> new FeatureProcessor(null));
    }
}