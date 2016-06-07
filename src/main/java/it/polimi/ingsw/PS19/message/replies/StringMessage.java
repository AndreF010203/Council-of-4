package it.polimi.ingsw.PS19.message.replies;

import it.polimi.ingsw.PS19.client.ReplyVisitor;
import it.polimi.ingsw.PS19.client.clientmodel.ClientUpdate;

public class StringMessage extends Reply 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1555049360643133272L;
	String text;

	public StringMessage(String s) 
	{
		text = s;
	}

	public String toString() 
	{
		return text;
	}

	@Override
	public ClientUpdate display(ReplyVisitor replyvisitor) {
		
		return null;
		
	}

}