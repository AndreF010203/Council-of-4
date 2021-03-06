package it.polimi.ingsw.ps19.message.replies;

import it.polimi.ingsw.ps19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.ps19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.ps19.model.Market;

/**
 * Gives the player the market with all orders available
 */
public class CompleteMarketReply extends Reply 
{
	private static final long serialVersionUID = 2012056866792056947L;
	private Market market;
	
	/**
	 * Constructor
	 * @param market
	 * @param result
	 * @param activePlayer: player who can buy
	 */
	public CompleteMarketReply(Market market, String result, int activePlayer) 
	{
		super(activePlayer, result);
		this.market = market;
	}
	
	public Market getMarket() 
	{
		return market;
	}
	@Override
	public ClientUpdate display(ReplyVisitor replyvisitor) 
	{
		return replyvisitor.display(this);
	}

}
