package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

public class ChunkerMain
{
    private static final Logger logger = LoggerFactory.getLogger(ChunkerMain.class);

    public static void main( String[] args ) throws Exception
    {
        InputStream modelIn = null;
        ChunkerModel model = null;
        try
        {
            modelIn = new FileInputStream( "models/en-chunker.model" );
            model = new ChunkerModel( modelIn );
            
            ChunkerME chunker = new ChunkerME(model);
            
            /* Ordinarily you'd use a Tokenizer to do this */
            String[] sent = new String[] { "Rockwell", "International", "Corp.", "'s",
                    "Tulsa", "unit", "said", "it", "signed", "a", "tentative", "agreement",
                    "extending", "its", "contract", "with", "Boeing", "Co.", "to",
                    "provide", "structural", "parts", "for", "Boeing", "'s", "747",
                    "jetliners", "." };
            
            /* and then use the POS Tagger to do this */
            String[] pos = new String[] { "NNP", "NNP", "NNP", "POS", "NNP", "NN",
                    "VBD", "PRP", "VBD", "DT", "JJ", "NN", "VBG", "PRP$", "NN", "IN",
                    "NNP", "NNP", "TO", "VB", "JJ", "NNS", "IN", "NNP", "POS", "CD", "NNS",
                    "." };

            String[] tag = chunker.chunk(sent, pos);            
            
            double[] probs = chunker.probs();
                
            /*
               The chunk tags contain the name of the chunk type, for 
               example I-NP for noun phrase words and I-VP for verb 
               phrase words. Most chunk types have two types of chunk 
               tags, B-CHUNK for the first word of the chunk and I-CHUNK 
               for each other word in the chunk.
             */
            
            for( int i = 0; i < sent.length; i++ )
            {
                logger.info("Token [{}] has chunk tag [{}] with probability = {}", sent[i], tag[i], probs[i]);
            }
            
        }
        catch( IOException e )
        {
            logger.error("Error while loading the model", e);
        }
        finally
        {
            if( modelIn != null )
            {
                try
                {
                    modelIn.close();
                }
                catch( IOException e )
                {
                    logger.warn("Error while closing the model input stream", e);
                }
            }
        }
        
        logger.info("Processing complete");
    }
}
