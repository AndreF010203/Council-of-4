package it.polimi.ingsw.PS19.client.clientmodel;

import it.polimi.ingsw.PS19.message.replies.ChangeKingPositionReply;
import it.polimi.ingsw.PS19.message.replies.DrawBusinessCardReply;
import it.polimi.ingsw.PS19.message.replies.ElectCouncillorReply;
import it.polimi.ingsw.PS19.message.replies.EndTurnReply;
import it.polimi.ingsw.PS19.message.replies.GameStartedMessage;
import it.polimi.ingsw.PS19.message.replies.PlayerDisconnectedReply;
import it.polimi.ingsw.PS19.message.replies.SendFullGameReply;
import it.polimi.ingsw.PS19.message.replies.SendFullPlayerReply;
import it.polimi.ingsw.PS19.message.replies.TimeToMarketReply;
import it.polimi.ingsw.PS19.message.replies.WaitingPlayerForMarketReply;

/**
 * Implementation of the reply visitor
 */
public class ReplyVisitorImpl implements ReplyVisitor 
{

	@Override
	public ClientUpdate display(SendFullGameReply message) 
	{
		return new SendFullGameUpdate(message.getResult(), message.getKing(), message.getRegions(), message.getPlayer(), message.getActivePlayer(), message.getAvailableCouncillor(), message.getNobilitypath());
	}
	@Override
	public ClientUpdate display(DrawBusinessCardReply message) 
	{
		return new DrawBusinessCardUpdate(message.getPlayer(), message.getRegion());
	}

	@Override
	public ClientUpdate display(SendFullPlayerReply message) 
	{
		return new SendFullPlayerUpdate(message.getPlayer(), message.getResult(), message.getActivePlayer());		
	}

	@Override
	public ClientUpdate display(ElectCouncillorReply message) 
	{
		return new ElectCouncillorUpdate(message.getResult(), message.getRegion(), message.getKing(), message.getAvailableCouncillor(),message.getPlayer(), message.getActivePlayer());
	}

	@Override
	public ClientUpdate display(GameStartedMessage message) 
	{
		return null;
	}
	
	@Override
	public ClientUpdate display(PlayerDisconnectedReply message) {
		return new PlayerDisconnectedUpdate(message.getResult());
	}
	
	@Override
	public ClientUpdate display(EndTurnReply message) 
	{
		return new EndTurnUpdate(message.getActivePlayer(), message.getPlayer());
	}
	
	@Override
	public ClientUpdate display(ChangeKingPositionReply message) 
	{
		return new ChangeKingPositionUpdate(message.getPlayer(), message.getKing(), message.getResult(), message.getActivePlayer());
	}
	@Override
	public ClientUpdate display(TimeToMarketReply message) 
	{
		return new TimeToMarketUpdate();
	}
	@Override
	public ClientUpdate display(WaitingPlayerForMarketReply message) 
	{
		return new WaitingPlayerForMarketUpdate(message.getResult());
	}

}
