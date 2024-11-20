package org.fogbeam.example.opennlp;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.tokenize.*;

public class TokenizerMain {
    private static final Logger logger = LoggerFactory.getLogger(TokenizerMain.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            logger.error("Usage: java TokenizerMain <input-dir> <output-file>");
            return;
        }

        String inputDir = args[0];
        String outputFile = args[1];

        InputStream modelIn = null;
        try {
            modelIn = new FileInputStream("models/en-token.model");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                TokenizerModel model = new TokenizerModel(modelIn);
                Tokenizer tokenizer = new TokenizerME(model);
                Files.list(Paths.get(inputDir))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try (Stream<String> lines = Files.lines(file)) {
                            lines.forEach(line -> {
                                String[] tokens = tokenizer.tokenize(line);
                                try {
                                    writer.write(String.join(" ", tokens));
                                    writer.newLine();
                                } catch (IOException e) {
                                    logger.error("Error writing tokens to file: {}", outputFile, e);
                                }
                            });
                        } catch (IOException e) {
                            logger.error("Error reading file: {}", file, e);
                        }
                    });
            }
        } catch (IOException e) {
            logger.error("Error loading the tokenization model or processing files", e);
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                    logger.warn("Error closing model input stream", e);
                }
            }
        }

        logger.info("Tokens written to: {}", outputFile);
    }
}
