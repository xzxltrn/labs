package edu.course.lab04;

import java.util.HashMap;

public final class SampleRegistry {

    private final HashMap<SampleId, DataSample> samples = new HashMap<>();

    public void put(SampleId id, DataSample sample) {
        if (id == null || sample == null) {
            throw new IllegalArgumentException("id and sample must not be null");
        }

        samples.put(id, sample);
    }

    public DataSample get(SampleId id) {
        return samples.get(id);
    }

    public boolean remove(SampleId id) {
        return samples.remove(id) != null;
    }

    public boolean contains(SampleId id) {
        return samples.containsKey(id);
    }

    public int size() {
        return samples.size();
    }
}