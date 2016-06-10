package it.polimi.ingsw.ps19.controller.action;

import it.polimi.ingsw.ps19.message.replies.EndTurnReply;
import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.model.Model;

public class EndTurn implements Action 
{
	private int playerId;
	private String result;
	
	public EndTurn(int id) 
	{
		playerId = id;
	}
	
	@Override
	public Boolean execute(Model model) 
	{
		if(model.getNumberofplayer()-1 == playerId)
			model.getCurrentState().setPlayerTurnId(0);
		else 
			model.getCurrentState().setPlayerTurnId(playerId + 1);
		return true;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		if(Action.checkPlayerTurn(playerId, model))
		{
			result = ActionMessages.NOT_YOUR_TURN;
			return false;
		}
		
		if(model.getPlayerById(playerId).getMainActionCounter() != 0)
		{
			result = ActionMessages.MAIN_ACTION;
			return false;
		}
		result = ActionMessages.EVERYTHING_IS_OK;
		return true;
	}
	
	@Override
	public String getStringResult() 
	{
		return result;
	}
	
	
	@Override
	public Reply createReplyMessage(Model model) 
	{
		return new EndTurnReply(model.getCurrentState().getPlayerTurnId(), result);
	}

}