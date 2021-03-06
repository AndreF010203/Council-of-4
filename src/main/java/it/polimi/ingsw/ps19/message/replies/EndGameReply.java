package it.polimi.ingsw.ps19.message.replies;

import java.util.List;

import it.polimi.ingsw.ps19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.ps19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.ps19.model.Player;

/**
 * Notifies the player that the game has ended
 */
public class EndGameReply extends SendFullPlayerReply
{
	private static final long serialVersionUID = -4749037445037762674L;

	/**
	 * @param activePlayer
	 * @param result
	 * @param player
	 */
	public EndGameReply(int activePlayer, String result, List<Player> player) 
	{
		super(activePlayer, result, player);
	}
	
	@Override
	public ClientUpdate display(ReplyVisitor replyvisitor) 
	{
		return replyvisitor.display(this);
	}

}
