package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataSampleTest {

    @Test
    void createsValidSample() {
        double[] features = {1.0, 2.0, 3.0};

        DataSample sample = new DataSample("s1", "cat", features);

        assertEquals("s1", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertArrayEquals(features, sample.getFeatures());
    }

    @Test
    void constructorRejectsNullId() {
        double[] features = {1.0};

        assertThrows(IllegalArgumentException.class, () -> new DataSample(null, "cat", features));
    }

    @Test
    void constructorRejectsBlankId() {
        double[] features = {1.0};

        assertThrows(IllegalArgumentException.class, () -> new DataSample("   ", "cat", features));
    }

    @Test
    void constructorRejectsNullLabel() {
        double[] features = {1.0};

        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", null, features));
    }

    @Test
    void constructorRejectsBlankLabel() {
        double[] features = {1.0};

        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "", features));
    }

    @Test
    void constructorRejectsNullFeatures() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "cat", null));
    }

    @Test
    void constructorRejectsEmptyFeatures() {
        double[] features = {};

        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "cat", features));
    }

    @Test
    void constructorRejectsNaNFeature() {
        double[] features = {1.0, Double.NaN};

        assertThrows(IllegalArgumentException.class, () -> new DataSample("s1", "cat", features));
    }

    @Test
    void newSampleIsNotReady() {
        DataSample sample = new DataSample("s1", "cat", new double[] {1.0});

        assertFalse(sample.isReady());
    }

    @Test
    void sampleIsReadyAfterChangeStatusToReady() {
        DataSample sample = new DataSample("s1", "cat", new double[] {1.0});

        sample.changeStatus(SampleStatus.READY);

        assertTrue(sample.isReady());
    }

    @Test
    void changeStatusRejectsNull() {
        DataSample sample = new DataSample("s1", "cat", new double[] {1.0});

        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
    }

    @Test
    void averageFeatureReturnsMean() {
        DataSample sample = new DataSample("s1", "cat", new double[] {1.0, 2.0, 4.5});

        double result = sample.averageFeature();

        assertEquals(2.5, result, 1e-9);
    }

    @Test
    void constructorCopiesFeatures() {
        double[] features = {1.0, 2.0};
        DataSample sample = new DataSample("s1", "cat", features);

        features[0] = 999.0;

        assertArrayEquals(new double[] {1.0, 2.0}, sample.getFeatures());
    }

    @Test
    void getFeaturesReturnsCopy() {
        DataSample sample = new DataSample("s1", "cat", new double[] {1.0, 2.0});

        double[] returned = sample.getFeatures();
        returned[0] = 999.0;

        assertArrayEquals(new double[] {1.0, 2.0}, sample.getFeatures());
    }

        @Test
    void normalizedFeaturesScaleToZeroOneRange() {
        DataSample sample = new DataSample("s1", "cat", new double[] {2.0, 4.0, 6.0});

        double[] result = sample.normalizedFeatures();

        assertArrayEquals(new double[] {0.0, 0.5, 1.0}, result, 1e-9);
    }

    @Test
    void normalizedFeaturesDoNotModifySample() {
        DataSample sample = new DataSample("s1", "cat", new double[] {2.0, 4.0, 6.0});

        sample.normalizedFeatures();

        assertArrayEquals(new double[] {2.0, 4.0, 6.0}, sample.getFeatures());
    }

    @Test
    void normalizedFeaturesReturnZerosWhenAllEqual() {
        DataSample sample = new DataSample("s1", "cat", new double[] {3.0, 3.0});

        double[] result = sample.normalizedFeatures();

        assertArrayEquals(new double[] {0.0, 0.0}, result, 1e-9);
    }
}
