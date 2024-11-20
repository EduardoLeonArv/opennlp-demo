package org.fogbeam.example.opennlp;

import org.fogbeam.example.opennlp.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.InputStream;

public class PartOfSpeechTaggerMain {
    private static final Logger logger = LoggerFactory.getLogger(PartOfSpeechTaggerMain.class);

    public static void main(String[] args) {
        InputStream modelIn = null;
        try {
            modelIn = new FileInputStream("models/en-pos-maxent.bin");
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);

            String[] sent = {"Most", "large", "cities", "in", "the", "US", "had", "morning", "and", "afternoon", "newspapers", "."};
            String[] tags = tagger.tag(sent);
            double[] probs = tagger.probs();

            for (int i = 0; i < sent.length; i++) {
                logger.info("Token [{}] has POS [{}] with probability = {}", sent[i], tags[i], probs[i]);
            }

            IOUtils.logCompletion(logger, "POS tagging");
        } catch (Exception e) {
            IOUtils.logError(logger, "POS tagging", e);
        } finally {
            IOUtils.closeQuietly(modelIn, "POS model input stream");
        }
    }
}
