/*
 * @Author Andrea Milanta
 */
package it.polimi.ingsw.ps19.view.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

import it.polimi.ingsw.ps19.exceptions.viewexceptions.ReaderException;
import it.polimi.ingsw.ps19.exceptions.viewexceptions.WriterException;
import it.polimi.ingsw.ps19.message.Message;
import it.polimi.ingsw.ps19.server.Constants;

/**
 * Class for socket connection
 */
public class SocketConnection extends Connection
{
	private Socket clientSocket;
	private SocketReader reader;
	
	
	/**
	 * Fake constructor for test purposes
	 * @param fake
	 */
	public SocketConnection(boolean fake)
	{
		
	}
	
	/**
	 * Constructor
	 * @param client: socket
	 * @param exec: executor service to read
	 * @throws IOException
	 */
	public SocketConnection(Socket client, ExecutorService exec) throws IOException
	{
		clientSocket = client;
		writer = new SocketWriter(clientSocket); 
		reader = new SocketReader(clientSocket);
		setExecutor(exec);
	}
	
	/**
	 * Constructor
	 * @param client: socket
	 * @throws IOException
	 */
	public SocketConnection(Socket client) throws IOException
	{
		clientSocket = client;
		writer = new SocketWriter(clientSocket); 
		reader = new SocketReader(clientSocket);
	}
	
	@Override
	public Integer write(Message message) throws WriterException
	{
		if(status == ConnectionStatus.DISCONNECTED)
			return -1;
		writer.setMessage(message);
		Integer result = null;
		try {
			result = writer.call();
		} catch (WriterException e) 
		{
			ConnectionLogger.log.log(e);
			throw new WriterException();
		}
		return result;
	}

	@Override
	public Message read(long timeOut) throws TimeoutException, InterruptedException, ReaderException
	{
		Message mex;
		try 
		{
			Future<Message> waitMex = executor.submit(reader);
			if(timeOut < 0)
				mex = waitMex.get();
			else
				mex = waitMex.get(Constants.getPlayerTimeout(), TimeUnit.SECONDS);
		} catch (ExecutionException | CancellationException e) 
		{
			ConnectionLogger.log.log(e);
			throw new TimeoutException();
		}
		return mex;
	}

	@Override
	public void close() 
	{
		if(clientSocket == null || clientSocket.isClosed())
			return;
		try {
			clientSocket.close();
		} catch (IOException e) {
			ConnectionLogger.log.log(e);
		}
	}
}
