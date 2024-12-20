
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentenceDetectionMain
{
	private static final Logger logger = LoggerFactory.getLogger(SentenceDetectionMain.class);

	public static void main( String[] args ) throws Exception
	{
		InputStream modelIn = new FileInputStream( "models/en-sent.model" );
		InputStream demoDataIn = new FileInputStream( "demo_data/en-sent1.demo" );
		
		
		
		try
		{
			SentenceModel model = new SentenceModel( modelIn );
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			
			String demoData = convertStreamToString( demoDataIn );
			
			String sentences[] = sentenceDetector.sentDetect( demoData );
			
			for( String sentence : sentences )
			{
				System.out.println( sentence + "\n" );
			}
			
			
			
		}
		catch( IOException e )
		{
            logger.error("An error occurred during the sentence detection process", e);
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
				}
			}
			
			
			if( demoDataIn != null )
			{
				try
				{
					demoDataIn.close();
				}
				catch( IOException e )
				{
				}
			}
			
			
		}
		
		
		System.out.println( "done" );
		
	}
	
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}
