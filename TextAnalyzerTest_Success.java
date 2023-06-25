package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class TextAnalyzerTest_Success {
    private TextAnalyzerUI textAnalyzer;

    @BeforeEach
    public void setup() {
        textAnalyzer = new TextAnalyzerUI();
    }

    @Test
    public void testEmptyFile() throws IOException {
        File file = createFile("");
        Map<String, Integer> wordFrequencies = textAnalyzer.countWordOccurrences(file);
        Assertions.assertTrue(wordFrequencies.isEmpty());
        file.delete();
    }

    @Test
    public void testSingleWord() throws IOException {
        File file = createFile("hello");
        Map<String, Integer> wordFrequencies = textAnalyzer.countWordOccurrences(file);
        Assertions.assertEquals(1, wordFrequencies.size());
        Assertions.assertEquals(1, wordFrequencies.get("hello"));
        file.delete();
    }

    @Test
    public void testMultipleWords() throws IOException {
        File file = createFile("hello world hello");
        Map<String, Integer> wordFrequencies = textAnalyzer.countWordOccurrences(file);
        Assertions.assertEquals(2, wordFrequencies.size());
        Assertions.assertEquals(2, wordFrequencies.get("hello"));
        Assertions.assertEquals(1, wordFrequencies.get("world"));
        file.delete();
    }

    @Test
    public void testPunctuationAndWhitespace() throws IOException {
        File file = createFile("Hello world! How are you? Hello world!");
        Map<String, Integer> wordFrequencies = textAnalyzer.countWordOccurrences(file);
        Assertions.assertEquals(5, wordFrequencies.size());
        Assertions.assertEquals(2, wordFrequencies.get("Hello"));
        Assertions.assertEquals(2, wordFrequencies.get("world!"));
        Assertions.assertEquals(1, wordFrequencies.get("How"));
        Assertions.assertEquals(1, wordFrequencies.get("are"));
        Assertions.assertEquals(1, wordFrequencies.get("you?"));
        file.delete();
    }

    private File createFile(String content) throws IOException {
        File file = File.createTempFile("text_", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
        return file;
    }
}