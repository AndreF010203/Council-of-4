/*
 * @author Andrea Milanta
 */
package it.polimi.ingsw.PS19.message;

import java.io.Serializable;

import it.polimi.ingsw.PS19.controller.action.Action;

/*
 * Abstract class that implements a message
 */
public abstract class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	protected MessageType type;
	protected Action action;
	
	public Message(){}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setAction(Action action) 
	{
		this.action = action;
	}
	
	public Action getAction() 
	{
		return action;
	}
	
	public MessageType getType()
	{
		return type;
	}
	
	public abstract Action accept(MessageInterpreterVisitor messageInterpreter);
	
	public abstract String getString();
}
