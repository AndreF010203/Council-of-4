/*
 * @Author Andrea Milanta
 */
package it.polimi.ingsw.PS19.view.connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import it.polimi.ingsw.PS19.exceptions.viewexceptions.WriterException;
import it.polimi.ingsw.PS19.message.Message;


/*
 * Abstract class that covers communication with one user
 */
public abstract class Connection
{
	protected ConnectionStatus status;
	protected ExecutorService executor;
	Writer writer;
	Reader reader;
	
	public void setExecutor(ExecutorService exec)
	{
		executor = exec;
	}
	public abstract void setActive();
	public abstract void setInactive();
	public abstract void setDisconnected();
	
	public ConnectionStatus getStatus()
	{
		return status;
	}
	
	/*
	 * Writes a message
	 * returns a future reference to the number of attempts required
	 */
	public abstract Integer write(Message message) throws WriterException;
	
	/*
	 * Reads a message
	 * returns a future reference to the read message
	 */
	public abstract Future<Message> read();
}