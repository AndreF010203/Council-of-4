/*
 *@Author Andrea Milanta 
 */
package it.polimi.ingsw.PS19.view.connection;

import java.util.concurrent.Callable;

import it.polimi.ingsw.PS19.exceptions.viewexceptions.ReaderException;
import it.polimi.ingsw.PS19.message.Message;


/*
 * Abstract class to read a message.
 * Runs on a different Thread
 */
public abstract class Reader implements Callable<Message> 
{
	@Override
	public Message call() throws ReaderException
	{
		try
		{
			return read();
		}
		catch(Exception e)
		{
			throw new ReaderException();
		}
	}

	/*
	 * Actual reading method. different for different types of communication 
	 */
	protected abstract Message read() throws Exception;
}