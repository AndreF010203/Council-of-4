package it.polimi.ingsw.ps19.controller;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps19.controller.action.Action;
import it.polimi.ingsw.ps19.controller.action.ActionMessages;
import it.polimi.ingsw.ps19.controller.action.DrawPoliticsCard;
import it.polimi.ingsw.ps19.controller.action.MessageInterpreterVisitor;
import it.polimi.ingsw.ps19.controller.action.MessageInterpreterVisitorImp;
import it.polimi.ingsw.ps19.message.replies.GetBusinessCardOrCityBonusReply;
import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.message.replies.SendFullPlayerReply;
import it.polimi.ingsw.ps19.message.replies.TimeToMarketReply;
import it.polimi.ingsw.ps19.message.requests.Request;
import it.polimi.ingsw.ps19.model.Model;
import it.polimi.ingsw.ps19.model.Player;
import it.polimi.ingsw.ps19.model.parameter.Costants;;

/**
 * The Class GameController.
 */
public class GameController implements Observer
{
	
	private static final int TIME_TO_MARKET_ACTIVE_PLAYER = -1;

	/** The model. */
	private Model model;
	
	/** The reply. */
	private Reply reply;
	private Request m;
	
	private Action action;
	
	/**
	 * Instantiates a new game controller. The game Started!
	 *
	 * @param m the m
	 */
	public GameController(Model m) 
	{
		model = m;
		drawStartingPoliticsCard();
	}
	
	/**
	 * Draw POLITICS_CARD for each player.
	 *
	 * @param m is the model of the game
	 */
	private void drawStartingPoliticsCard() 
	{
		for (Player p : model.getPlayer()) 
			drawForSinglePlayer(p, p.getStartingPoliticCard());
		//Fa pescare una carta al primo player
		
		politicCardToDrawToCurrentPlayer();
	}

	private void politicCardToDrawToCurrentPlayer() 
	{
		int numberOfPoliticCards = model.getPlayerById(model.getCurrentState().getPlayerTurnId()).getPoliticCardToDraw();
		if( numberOfPoliticCards !=0)
				drawForSinglePlayer(model.getPlayerById(model.getCurrentState().getPlayerTurnId()), numberOfPoliticCards);
	}

	private void drawForSinglePlayer(Player p, int numberOfPoliticCards ) 
	{
		for(int i=0;i<numberOfPoliticCards;i++)
		{
			DrawPoliticsCard drawPoliticsCard = new DrawPoliticsCard(p.getId());
			drawPoliticsCard.execute(model);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable view, Object message) 
	{
		/*
		 *the messageInterpreter implements vistior pattern to read all message
		 *See MessageInterpreterVisitor
		 */
		reply = null;
		MessageInterpreterVisitor messageInterpreter = new MessageInterpreterVisitorImp();
		
		if(!(message instanceof Request))
			return;
		m = (Request) message;
		
		action = m.accept(messageInterpreter);
		
		boolean isPossible = action.isPossible(model);
		if(isPossible)
		{
			action.execute(model);
			politicCardToDrawToCurrentPlayer();
		}
		reply = createReply();
		
		if(model.getCurrentState().isTimeToMarket())
			checkModelStatusInMarketTime();
		else
			checkModelStatus();
		
		model.sendMessage(reply);

		checkTimeToMarket();
		
	}
	private void checkModelStatusInMarketTime() 
	{
		if(model.getCurrentState().isTimeToEndMarket())
		{
			model.getCurrentState().setTimeToMarket(false);
			reply = new SendFullPlayerReply(model.getCurrentState().getNumberOfPlayer(), 
					reply.getResult(), model.getPlayer());
			
			changeTurn();	
			
		}
		
	}

	public Reply getReply() 
	{
		return reply;
	}
	
	/**
	 * Check model status.
	 */
	private void checkModelStatus()
	{
		
		setTimeToMarket();
		checkAlreadyTurn();
		
	}
	
	/**
	 * Check already turn.
	 * The turn change only if MainAction & FastAction are at 0.
	 * If plyer doesn't play fast action the EndTurn Action set FastAction to 0
	 *	TODO scrivere se player è disconnesso
	 */
	private void checkAlreadyTurn()
	{
		int id = model.getCurrentState().getPlayerTurnId();
		if(model.getPlayerById(id).getMainActionCounter() == 0 && model.getPlayerById(id).getFastActionCounter()==0
				&& !model.getPlayerById(id).isCityBonusRequest() && !model.getPlayerById(id).isBusinessCardRequest())
		{
			model.getCurrentState().setPlayerTurnId(model.getCurrentState().giveNextCorrectId(model.getCurrentState().getPlayerTurnId()));
			changeTurn();
		}
	}

	private void changeTurn() 
	{
		model.getPlayerById(model.getCurrentState().getPlayerTurnId()).setStartingAction();
		
		//draw one card
		DrawPoliticsCard drawPoliticsCard = new DrawPoliticsCard(model.getCurrentState().getPlayerTurnId());
		drawPoliticsCard.execute(model);
		
		//set active player
		reply.setActivePlayer(model.getCurrentState().getPlayerTurnId());
	}
	
	/**
	 * Sets the time to market.
	 * this time is true if all player have main and fast action to zero
	 * TODO tenere conto dei player disconnessi
	 */
	private void setTimeToMarket()
	{
		for (Player p : model.getPlayer()) 
			if((p.getMainActionCounter()!=0 || p.getFastActionCounter()!=0) && 
					model.getCurrentState().isConnectedById(p.getId()))
			{
				model.getCurrentState().setTimeToMarket(false);
				model.getCurrentState().setTimeToMarketSended(false);
				return;
			}
		model.getCurrentState().setTimeToMarket(true);
		model.getCurrentState().setTimeToMarketSended(false);
		reply.setActivePlayer(TIME_TO_MARKET_ACTIVE_PLAYER);
	}
	
	/**
	 * Check time to market.
	 * This method was called after was sended reply message. 
	 * This method send TimeToMarket Message.
	 * 
	 */
	private void checkTimeToMarket()
	{
		if(model.getCurrentState().isTimeToMarket() && !model.getCurrentState().isTimeToMarketSended())
		{
			model.getCurrentState().setTimeToMarketSended(true);
			Reply r = new TimeToMarketReply(Costants.NO_ACTIVE_PLAYER, ActionMessages.TIME_TO_MARKET);
			r.setId(Costants.BROADCAST_MESSAGE);				//send to all player
			model.sendMessage(r);
		}
	}
	
	/**
	 * Check business card & city Bonus.
	 * If first or second are true create a GetBusinessCardOrCityBonusReply
	 * and server send to players all game
	 */
	private Reply createReply()
	{
		int playerTurnId = model.getCurrentState().getPlayerTurnId();
		Reply r;
		if(model.getPlayerById(playerTurnId).isBusinessCardRequest() || 
				model.getPlayerById(playerTurnId).isCityBonusRequest())
		{
			r = new GetBusinessCardOrCityBonusReply(
					model.getCurrentState().getPlayerTurnId(), 
					ActionMessages.BUSINESS_CARD_REQUEST, 
					model.getPlayer(), 
					model.getMap().getListaRegioni(), 
					model.getMap().getKing(),
					model.getMap().getAvailableCouncillor(),
					model.getPlayerById(playerTurnId).isBusinessCardRequest(),
					model.getPlayerById(playerTurnId).isCityBonusRequest());
		}
		else
			r = action.createReplyMessage(model);
		r.setId(Costants.BROADCAST_MESSAGE);
		return r;
			
	}	
}
