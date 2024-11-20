package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentClassifierMain
{
	private static final Logger logger = LoggerFactory.getLogger(DocumentClassifierMain.class);
	public static void main( String[] args ) throws Exception
	{
		
		InputStream is = null;
		try
		{
			is = new FileInputStream( "models/en-doccat.model" );
		
			DoccatModel m = new DoccatModel(is);
			
			
			String inputText = "What happens if we have declining bottom-line revenue?";
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
			double[] outcomes = myCategorizer.categorize(inputText);
			String category = myCategorizer.getBestCategory( outcomes );
			
            logger.info("Classification process completed successfully.");
			
			
		}
		catch( Exception e )
		{
            logger.error("An error occurred during the classification process", e);
			
		}
		finally
		{
			if( is != null )
			{
				is.close();
			}
		}
		
		System.out.println( "done" );
	}
}
