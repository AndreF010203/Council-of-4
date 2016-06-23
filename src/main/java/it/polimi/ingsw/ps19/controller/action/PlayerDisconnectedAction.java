package it.polimi.ingsw.ps19.controller.action;

import it.polimi.ingsw.ps19.message.replies.PlayerDisconnectedReply;
import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.model.Model;

public class PlayerDisconnectedAction implements Action {

	private int playerId;
	private String result;
	private boolean newTurn = false;
	
	public PlayerDisconnectedAction(int id) 
	{
		playerId = id;
	}
	@Override
	public Boolean execute(Model model) 
	{
			result = ActionMessages.PLAYER_DISCONNECTED + playerId;

			int actualPlayerId = model.getCurrentState().getPlayerTurnId();
			
			int nextTurn = model.getCurrentState().giveNextCorrectId(playerId);
			model.getCurrentState().setPlayerTurnId(nextTurn);
			
			if(nextTurn != actualPlayerId)
			{
				model.getPlayerById(nextTurn).setStartingAction();
				newTurn = true;
			}
			return true;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		return true;
	}

	@Override
	public Reply createReplyMessage(Model model) 
	{
		return new PlayerDisconnectedReply(model.getCurrentState().getPlayerTurnId(), result, newTurn);
	}

}
