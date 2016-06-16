package it.polimi.ingsw.PS19.client.clientmodel;

import it.polimi.ingsw.PS19.client.ClientUI;
import it.polimi.ingsw.PS19.client.clientaction.MarketSell;
import it.polimi.ingsw.PS19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.PS19.exceptions.clientexceptions.InvalidInsertionException;
import it.polimi.ingsw.PS19.message.requests.Request;

/**
 * Market selling fase update
 */
public class TimeToMarketUpdate extends ClientUpdate {

	@Override
	public void update(ClientModel model) 
	{
		return;
	}
	
	@Override
	public Request execute(ClientUI userInterface, ClientModel model) throws InvalidInsertionException
	{
		MarketSell marketSell = new MarketSell();
		return marketSell.execute(userInterface);
	}
}
