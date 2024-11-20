package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class ParserMain
{
    private static final Logger logger = LoggerFactory.getLogger(ParserMain.class);

    public static void main(String[] args) throws Exception
    {
        InputStream modelIn = null;
        try
        {
            modelIn = new FileInputStream("models/en-parser-chunking.bin");
            ParserModel model = new ParserModel(modelIn);

            Parser parser = ParserFactory.create(model);

            String sentence = "The quick brown fox jumps over the lazy dog .";

            Parse[] topParses = ParserTool.parseLine(sentence, parser, 1);

            if (topParses.length > 0) {
                Parse parse = topParses[0];
                if (logger.isInfoEnabled()) {
                    logger.info("Parse Tree: {}", parse.toString());
                }

                parse.showCodeTree(); // Assuming this is necessary; adjust if not.
            } else {
                logger.warn("No parse could be generated for the sentence: {}", sentence);
            }

        }
        catch (IOException e)
        {
            logger.error("Error occurred while parsing the sentence", e);
        }
        finally
        {
            if (modelIn != null)
            {
                try
                {
                    modelIn.close();
                }
                catch (IOException e)
                {
                    logger.warn("Error closing the model input stream", e);
                }
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("Processing complete");
        }
    }
}
