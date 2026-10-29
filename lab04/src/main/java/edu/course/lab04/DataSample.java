package edu.course.lab04;

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
}