package it.polimi.ingsw.PS19.client.clientaction;

import it.polimi.ingsw.PS19.client.ClientUI;
import it.polimi.ingsw.PS19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.PS19.exceptions.clientexceptions.InvalidInsertionException;
import it.polimi.ingsw.PS19.message.requests.EndTurnMessage;
import it.polimi.ingsw.PS19.message.requests.Request;

/**
 * Class that creates a new EndTurnMessage
 */
public class EndTurnInput extends ClientAction 
{
	/**
	 * Constructor
	 * @param m
	 */
	public EndTurnInput(ClientModel m) 
	{
		model = m;
	}

	@Override
	public boolean isPossible() 
	{
		if(model.getMyPlayer().getMainActionCounter() <= 0)
			return true;
		return false;
	}

	@Override
	public Request execute(ClientUI userInterface) throws InvalidInsertionException 
	{
		return buildMessage();
	}

	@Override
	protected Request buildMessage() 
	{
		return new EndTurnMessage();
	}

}