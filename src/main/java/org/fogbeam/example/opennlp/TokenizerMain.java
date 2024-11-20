
package org.fogbeam.example.opennlp;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import opennlp.tools.tokenize.*;

public class TokenizerMain {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java TokenizerMain <input-dir> <output-file>");
            return;
        }

        String inputDir = args[0];
        String outputFile = args[1];

        InputStream modelIn = new FileInputStream("models/en-token.model");
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
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } finally {
            if (modelIn != null) modelIn.close();
        }
        System.out.println("Tokens written to: " + outputFile);
    }
}



