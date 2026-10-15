package edu.course.lab02;

import java.util.Arrays;

public final class DataSample {

    private final String id;
    private final String label;
    private final double[] features;
    private SampleStatus status;

    public DataSample(String id, String label, double[] features) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("label must not be null or blank");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("features must not be null or empty");
        }
        for (int i = 0; i < features.length; i++) {
            if (!Double.isFinite(features[i])) {
                throw new IllegalArgumentException("features must be finite numbers");
            }
        }

        this.id = id;
        this.label = label;
        this.features = Arrays.copyOf(features, features.length);
        this.status = SampleStatus.NEW;
    }

    
    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("status must not be null");
        }

        this.status = newStatus;
    }


    public boolean isReady() {
        return status == SampleStatus.READY;
    }


    public double averageFeature() {
        double sum = 0;
        for (int i = 0; i < features.length; i++) {
            sum = sum + features[i];
        }

        return sum / features.length;
    }


    public String getId() {
        return id;
    }


    public String getLabel() {
        return label;
    }


    public SampleStatus getStatus() {
        return status;
    }


    public double[] getFeatures() {
        return Arrays.copyOf(features, features.length);
    }

        public double[] normalizedFeatures() {
        double min = features[0];
        double max = features[0];
        for (int i = 1; i < features.length; i++) {
            if (features[i] < min) {
                min = features[i];
            }
            if (features[i] > max) {
                max = features[i];
            }
        }

        double[] result = new double[features.length];
        if (max == min) {
            return result;
        }

        for (int i = 0; i < features.length; i++) {
            result[i] = (features[i] - min) / (max - min);
        }

        return result;
    }
}