package edu.course.lab04;

import java.util.Objects;

final class SampleId {

    private final String datasetName;
    private final int index;

    public SampleId(String datasetName, int index) {
        if (datasetName == null || datasetName.isBlank()) {
            throw new IllegalArgumentException("datasetName must not be null or blank");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index must not be negative");
        }

        this.datasetName = datasetName;
        this.index = index;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SampleId other = (SampleId) obj;
        return index == other.index && datasetName.equals(other.datasetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasetName, index);
    }

    @Override
    public String toString() {
        return datasetName + "#" + index;
    }
}