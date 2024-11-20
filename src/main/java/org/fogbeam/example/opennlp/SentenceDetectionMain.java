package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetectionMain
{
    private static final Logger logger = LoggerFactory.getLogger(SentenceDetectionMain.class);

    public static void main(String[] args) throws Exception
    {
        InputStream modelIn = null;
        InputStream demoDataIn = null;
        try
        {
            modelIn = new FileInputStream("models/en-sent.model");
            demoDataIn = new FileInputStream("demo_data/en-sent1.demo");

            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

            String demoData = convertStreamToString(demoDataIn);

            String[] sentences = sentenceDetector.sentDetect(demoData); // Cambio en la declaración de arreglos.

            for (String sentence : sentences)
            {
                if (logger.isInfoEnabled()) {
                    logger.info("Detected sentence: {}", sentence);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("Error occurred while processing the sentence detection model or data", e);
        }
        finally
        {
            closeStream(modelIn, "model input stream");
            closeStream(demoDataIn, "demo data input stream");
        }

        if (logger.isInfoEnabled()) {
            logger.info("Processing complete");
        }
    }

    static String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private static void closeStream(InputStream stream, String streamName)
    {
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
                logger.warn("Error closing {}", streamName, e);
            }
        }
    }
}
