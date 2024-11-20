package org.fogbeam.example.opennlp.training;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseModelTrainer<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseModelTrainer.class);

    protected abstract T trainModel() throws Exception;

    protected abstract void serializeModel(T model, OutputStream modelOut) throws IOException;

    public void executeTraining(String outputFile) {
        T model;
        try {
            logger.info("Starting training...");
            model = trainModel();
            logger.info("Training completed successfully.");
        } catch (Exception e) {
            logger.error("Error during training", e);
            return;
        }

        try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            logger.info("Saving model to: {}", outputFile);
            serializeModel(model, modelOut);
            logger.info("Model saved successfully.");
        } catch (IOException e) {
            logger.error("Error saving the model to: {}", outputFile, e);
        }
    }
}
