
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import org.fogbeam.example.opennlp.ChunkerMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentClassifierTrainer
{
	 private static final Logger logger = LoggerFactory.getLogger(ChunkerMain.class);
	public static void main( String[] args ) throws Exception
	{
		DoccatModel model = null;
		InputStream dataIn = null;
		try
		{
			dataIn = new FileInputStream( "training_data/en-doccat.train" );
			ObjectStream<String> lineStream = new PlainTextByLineStream(
					dataIn, "UTF-8" );
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(
					lineStream );
			model = DocumentCategorizerME.train( "en", sampleStream );
		}
		catch( IOException e )
		{
			// Failed to read or parse training data, training failed
			logger.error("An error occurred during the chunker process", e);
		}
		finally
		{
			if( dataIn != null )
			{
				try
				{
					dataIn.close();
				}
				catch( IOException e )
				{
					// Not an issue, training already finished.
					// The exception should be logged and investigated
					// if part of a production system.
					logger.error("An error occurred during the chunker process", e);
				}
			}
		}
		OutputStream modelOut = null;
		String modelFile = "models/en-doccat.model";
		try
		{
			modelOut = new BufferedOutputStream( new FileOutputStream(
					modelFile ) );
			model.serialize( modelOut );
		}
		catch( IOException e )
		{
			// Failed to save model
			logger.error("An error occurred during the chunker process", e);
		}
		finally
		{
			if( modelOut != null )
			{
				try
				{
					modelOut.close();
				}
				catch( IOException e )
				{
					// Failed to correctly save model.
					// Written model might be invalid.
					logger.error("An error occurred during the chunker process", e);
				}
			}
		}
		
		
		System.out.println( "done" );
	}
}
