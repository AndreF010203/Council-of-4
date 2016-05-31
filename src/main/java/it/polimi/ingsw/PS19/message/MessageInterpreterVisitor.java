package it.polimi.ingsw.PS19.message;

import it.polimi.ingsw.PS19.controller.action.Action;
import it.polimi.ingsw.PS19.message.requests.SendFullGameMessage;

public interface MessageInterpreterVisitor 
{
	public Action visit(ElectCouncillorMessage message);
	
	public Action visit(SendFullGameMessage message);
	
	public Action visit(BuyHelperMessage message);
	
	public Action visit(GetBusinessCardMessage message);
	
	public Action visit(DrawPoliticsCardMessage message);
	
}