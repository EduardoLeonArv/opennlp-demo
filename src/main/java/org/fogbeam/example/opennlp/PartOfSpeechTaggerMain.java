package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class PartOfSpeechTaggerMain
{
    private static final Logger logger = LoggerFactory.getLogger(PartOfSpeechTaggerMain.class);

    public static void main(String[] args)
    {
        InputStream modelIn = null;
        try
        {
            modelIn = new FileInputStream("models/en-pos-maxent.bin");
            POSModel model = new POSModel(modelIn);

            POSTaggerME tagger = new POSTaggerME(model);

            String[] sent = new String[]{
                "Most", "large", "cities", "in", "the", "US", "had",
                "morning", "and", "afternoon", "newspapers", "."
            };
            String[] tags = tagger.tag(sent);
            double[] probs = tagger.probs();

            for (int i = 0; i < sent.length; i++) {
                if (logger.isInfoEnabled()) {
                    logger.info("Token [{}] has POS [{}] with probability = {}", sent[i], tags[i], probs[i]);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("Model loading failed or error during processing", e);
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
