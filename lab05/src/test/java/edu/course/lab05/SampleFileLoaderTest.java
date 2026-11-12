package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SampleFileLoaderTest {

    @TempDir
    Path tempDir;

    private final SampleFileLoader loader = new SampleFileLoader();

    private Path createFile(String... rows) throws IOException {
        Path file = tempDir.resolve("samples.txt");

        List<String> lines = new ArrayList<>();
        lines.add("id;label;source;featureCount;status");

        for (String row : rows) {
            lines.add(row);
        }

        Files.write(file, lines, StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void loadsCorrectFile() throws IOException {
        Path file = createFile(
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;64;NEW"
        );

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(2, result.getSamples().size());

        DataSample sample = result.getSamples().get(0);
        assertEquals("S-101", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals("camera-1", sample.getSource());
        assertEquals(128, sample.getFeatureCount());
        assertEquals(SampleStatus.READY, sample.getStatus());

        assertEquals("S-102", result.getSamples().get(1).getId());
        assertEquals(SampleStatus.NEW,
                result.getSamples().get(1).getStatus());
    }

    @Test
    void loadsUnicodeText() throws IOException {
        Path file = createFile(
                "S-101;кошка;камера-1;128;READY"
        );

        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(1, result.getSamples().size());
        assertEquals("кошка", result.getSamples().get(0).getLabel());
        assertEquals("камера-1", result.getSamples().get(0).getSource());
    }

    @Test
    void reportsEmptyField() throws IOException {
        Path file = createFile("S-101;;camera-1;128;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());

        ValidationError error = result.getErrors().get(0);
        assertEquals(2, error.getLineNumber());
        assertEquals("label", error.getField());
        assertFalse(error.getMessage().isBlank());
    }

    @Test
    void reportsInvalidNumber() throws IOException {
        Path file = createFile("S-101;cat;camera-1;bad;READY");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(0).getField());
    }

    @Test
    void reportsUnknownStatus() throws IOException {
        Path file = createFile("S-101;cat;camera-1;128;UNKNOWN");

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void reportsWrongColumnCount() throws IOException {
        Path file = createFile(
                "S-101;cat;camera-1;128",
                "S-102;dog;camera-2;64;NEW;extra"
        );

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(2, result.getErrors().size());

        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("columns", result.getErrors().get(0).getField());

        assertEquals(3, result.getErrors().get(1).getLineNumber());
        assertEquals("columns", result.getErrors().get(1).getField());
    }

    @Test
    void keepsCorrectRowsAndReportsInvalidRows() throws IOException {
        Path file = createFile(
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;bad;NEW",
                "S-103;bird;camera-3;32;REJECTED",
                "S-104;fish;camera-4;16;UNKNOWN"
        );

        LoadResult result = loader.load(file);

        assertEquals(2, result.getSamples().size());
        assertEquals("S-101", result.getSamples().get(0).getId());
        assertEquals("S-103", result.getSamples().get(1).getId());

        assertEquals(2, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(0).getField());
        assertEquals(5, result.getErrors().get(1).getLineNumber());
        assertEquals("status", result.getErrors().get(1).getField());
    }

    @Test
    void missingFileThrowsWithOriginalCause() {
        Path file = tempDir.resolve("missing.txt");

        DataLoadingException exception = assertThrows(
                DataLoadingException.class,
                () -> loader.load(file)
        );

        assertInstanceOf(IOException.class, exception.getCause());
        assertTrue(exception.getMessage().contains("missing.txt"));
    }

    @Test
    void reportsInvalidHeader() throws IOException {
        Path file = tempDir.resolve("wrong-header.txt");
        Files.write(file, List.of(
                "wrong;header",
                "S-101;cat;camera-1;128;READY"
        ), StandardCharsets.UTF_8);

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(1, result.getErrors().get(0).getLineNumber());
        assertEquals("header", result.getErrors().get(0).getField());
    }

    @Test
    void reportsNonPositiveFeatureCount() throws IOException {
        Path file = createFile(
                "S-101;cat;camera-1;0;READY",
                "S-102;dog;camera-2;-5;NEW"
        );

        LoadResult result = loader.load(file);

        assertTrue(result.getSamples().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(0).getField());
        assertEquals(3, result.getErrors().get(1).getLineNumber());
        assertEquals("featureCount", result.getErrors().get(1).getField());
    }
}