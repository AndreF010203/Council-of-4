package it.polimi.ingsw.ps19.message.requests;

import it.polimi.ingsw.ps19.controller.action.Action;
import it.polimi.ingsw.ps19.controller.action.MessageInterpreterVisitor;

/**
 * Notifies that the player has bought an helper (quick action)
 */
public class BuyHelperMessage extends Request 
{
	private static final long serialVersionUID = 5953738823072831707L;

	@Override
	public Action accept(MessageInterpreterVisitor messageInterpreter) 
	{
		return messageInterpreter.visit(this);
	}

}
