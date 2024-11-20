
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameFinderMain
{
	private static final Logger logger = LoggerFactory.getLogger(NameFinderMain.class);
	/**
	 * @param args
	 */
	public static void main( String[] args ) throws Exception
	{
		InputStream modelIn = new FileInputStream( "models/en-ner-person.model" );
		// InputStream modelIn = new FileInputStream( "models/en-ner-person.bin" );
		
		try
		{
			TokenNameFinderModel model = new TokenNameFinderModel( modelIn );
		
			NameFinderME nameFinder = new NameFinderME(model);
			
			String[] tokens = { //"A", "guy", "named",
								// "Mr.", 
								"Phillip", 
								"Rhodes",
								"is",
								"presenting",
								"at",
								"some",
								"meeting",
								"."};
			
			Span[] names = nameFinder.find( tokens );
		
			for( Span ns : names )
			{
				System.out.println( "ns: " + ns.toString() );
			
				// if you want to actually do something with the name
				// ...
				
			}
		
			nameFinder.clearAdaptiveData();
			logger.info("Name-finder process completed successfully.");

		}
		catch( IOException e )
		{
            logger.error("An error occurred during the name-finder process", e);
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
		}
		
		
		System.out.println( "done" );
	}
}