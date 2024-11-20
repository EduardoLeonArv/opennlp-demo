package org.fogbeam.example.opennlp;

import org.fogbeam.example.opennlp.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import java.io.FileInputStream;
import java.io.InputStream;

public class ParserMain {
    private static final Logger logger = LoggerFactory.getLogger(ParserMain.class);

    public static void main(String[] args) {
        InputStream modelIn = null;
        try {
            modelIn = new FileInputStream("models/en-parser-chunking.bin");
            ParserModel model = new ParserModel(modelIn);
            Parser parser = ParserFactory.create(model);

            String sentence = "The quick brown fox jumps over the lazy dog .";
            Parse[] topParses = ParserTool.parseLine(sentence, parser, 1);

            logger.info("Parsed sentence: {}", topParses[0].toString());

            IOUtils.logCompletion(logger, "parser");
        } catch (Exception e) {
            IOUtils.logError(logger, "parser", e);
        } finally {
            IOUtils.closeQuietly(modelIn, "Parser model input stream");
        }
    }
}
