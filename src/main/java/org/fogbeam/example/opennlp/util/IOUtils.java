package org.fogbeam.example.opennlp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static void closeQuietly(Closeable resource, String resourceName) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                logger.warn("Failed to close {}: {}", resourceName, e.getMessage());
            }
        }
    }

    public static void logCompletion(Logger logger, String processName) {
        logger.info("{} process completed successfully.", processName);
    }

    public static void logError(Logger logger, String processName, Exception e) {
        logger.error("An error occurred during the {} process", processName, e);
    }
}
