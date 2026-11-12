package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SampleFileLoader {

    private static final String HEADER =
            "id;label;source;featureCount;status";

    public LoadResult load(Path path) {
        List<DataSample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            String header = reader.readLine();

            if (!HEADER.equals(header)) {
                errors.add(new ValidationError(
                        1, "header", "Expected header: " + HEADER));
                return new LoadResult(samples, errors);
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(";", -1);

                if (fields.length != 5) {
                    errors.add(new ValidationError(
                            lineNumber, "columns", "Expected 5 fields"));
                    continue;
                }

                String[] fieldNames = {
                        "id", "label", "source", "featureCount", "status"
                };

                boolean hasEmptyField = false;

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].strip();

                    if (fields[i].isEmpty()) {
                        errors.add(new ValidationError(
                                lineNumber, fieldNames[i],
                                "Field must not be empty"));
                        hasEmptyField = true;
                    }
                }

                if (hasEmptyField) {
                    continue;
                }

                int featureCount;

                try {
                    featureCount = Integer.parseInt(fields[3]);
                } catch (NumberFormatException cause) {
                    errors.add(new ValidationError(
                            lineNumber, "featureCount",
                            "Expected an integer"));
                    continue;
                }

                if (featureCount <= 0) {
                    errors.add(new ValidationError(
                            lineNumber, "featureCount",
                            "Feature count must be positive"));
                    continue;
                }

                SampleStatus status;

                try {
                    status = SampleStatus.valueOf(fields[4]);
                } catch (IllegalArgumentException cause) {
                    errors.add(new ValidationError(
                            lineNumber, "status", "Unknown status"));
                    continue;
                }

                DataSample sample = new DataSample(
                        fields[0], fields[1], fields[2],
                        featureCount, status);

                samples.add(sample);
            }

        } catch (IOException cause) {
            throw new DataLoadingException("Cannot read " + path, cause);
        }

        return new LoadResult(samples, errors);
    }
}