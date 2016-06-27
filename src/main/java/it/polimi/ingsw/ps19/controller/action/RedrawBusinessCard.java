package it.polimi.ingsw.ps19.controller.action;

import it.polimi.ingsw.ps19.message.replies.DrawBusinessCardReply;
import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.model.Model;
import it.polimi.ingsw.ps19.model.card.BusinessCard;
import it.polimi.ingsw.ps19.model.parameter.Costants;
import it.polimi.ingsw.ps19.model.parameter.RegionType;

public class RedrawBusinessCard implements Action 
{

	private RegionType region;
	private int playerId;
	private String result;
	
	public RedrawBusinessCard(RegionType r, int id) 
	{
		region = r;
		playerId = id;
	}
	
	@Override
	public Boolean execute(Model model) 
	{
		model.getPlayerById(playerId).setHelpers(model.getPlayerById(playerId).getHelpers() - 1);
		BusinessCard first = model.getMap().getRegionByType(region).getFirstcard();
		BusinessCard second = model.getMap().getRegionByType(region).getSecondcard();
		
		model.getMap().getRegionByType(region).getBusinessdeck().addToDeckRandom(first);
		model.getMap().getRegionByType(region).getBusinessdeck().addToDeckRandom(second);
		
		model.getMap().getRegionByType(region).drowFirstCard();
		model.getMap().getRegionByType(region).drowSecondCard();
		
		model.getPlayerById(playerId).setFastActionCounter(model.getPlayerById(playerId).getFastActionCounter() - Costants.N_OF_ACTION_TO_ADD);
		result = ActionMessages.EVERYTHING_IS_OK;
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
		if(model.getPlayerById(playerId).getFastActionCounter() < Costants.N_OF_ACTION_TO_ADD)
		{
			result = ActionMessages.NO_ACTION_TO_DO_IT;
			return false;
		}
		result = ActionMessages.NO_HELPERS;
		if(model.getPlayerById(playerId).getHelpers() < 1)
			return false;
		result = ActionMessages.EVERYTHING_IS_OK;
		return true;
	}

	@Override
	public Reply createReplyMessage(Model model) 
	{
		return new DrawBusinessCardReply(model.getCurrentState().getPlayerTurnId(), result,
				model.getPlayer(), model.getMap().getRegionList());
	}

}
