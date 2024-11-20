package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

public class DocumentClassifierMain
{
    private static final Logger logger = LoggerFactory.getLogger(DocumentClassifierMain.class);

    public static void main(String[] args) throws Exception
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream("models/en-doccat.model");
            DoccatModel m = new DoccatModel(is);

            String inputText = "What happens if we have declining bottom-line revenue?";
            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
            double[] outcomes = myCategorizer.categorize(inputText);
            String category = myCategorizer.getBestCategory(outcomes);

            logger.info("Input classified as: {}", category);
        }
        catch (Exception e)
        {
            logger.error("An error occurred during document classification", e);
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (Exception e)
                {
                    logger.warn("Error closing the input stream", e);
                }
            }
        }

        logger.info("Processing complete");
    }
}
