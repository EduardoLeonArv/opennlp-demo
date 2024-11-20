package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class NameFinderMain
{
    private static final Logger logger = LoggerFactory.getLogger(NameFinderMain.class);

    public static void main(String[] args) throws Exception
    {
        InputStream modelIn = null;
        try
        {
            modelIn = new FileInputStream("models/en-ner-person.model");
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
        
            NameFinderME nameFinder = new NameFinderME(model);

            String[] tokens = { 
                "Phillip", 
                "Rhodes",
                "is",
                "presenting",
                "at",
                "some",
                "meeting",
                "."
            };

            Span[] names = nameFinder.find(tokens);
        
            for (Span ns : names)
            {
                logger.info("Name span found: {}", ns.toString());
            }

            nameFinder.clearAdaptiveData();
        }
        catch (IOException e)
        {
            logger.error("Error occurred while processing the model or finding names", e);
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
        
        logger.info("Processing complete");
    }
}
