package org.fogbeam.example.opennlp.training;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.chunker.ChunkSample;
import opennlp.tools.chunker.ChunkSampleStream;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.chunker.DefaultChunkerContextGenerator;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class ChunkerTrainer
{
    private static final Logger logger = LoggerFactory.getLogger(ChunkerTrainer.class);

    public static void main(String[] args) throws Exception
    {
        Charset charset = Charset.forName("UTF-8");

        ObjectStream<String> lineStream = new PlainTextByLineStream(
                new FileInputStream("training_data/conll2000-chunker.train"), charset);
        ObjectStream<ChunkSample> sampleStream = new ChunkSampleStream(lineStream);

        ChunkerModel model = null;
        try
        {
            logger.info("Starting training of the chunker model...");
            model = ChunkerME.train("en", sampleStream,
                    new DefaultChunkerContextGenerator(),
                    TrainingParameters.defaultParams());
            logger.info("Training completed successfully.");
        }
        finally
        {
            sampleStream.close();
        }

        String modelFile = "models/en-chunker.model";
        try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(modelFile)))
        {
            logger.info("Saving the trained model to file: {}", modelFile);
            model.serialize(modelOut);
            logger.info("Model saved successfully.");
        }
        catch (Exception e)
        {
            logger.error("Error while saving the model to file: {}", modelFile, e);
        }

        logger.info("Processing complete.");
    }
}
