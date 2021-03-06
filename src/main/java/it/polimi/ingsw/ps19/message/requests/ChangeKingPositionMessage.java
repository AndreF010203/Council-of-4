package it.polimi.ingsw.ps19.message.requests;

import java.awt.Color;
import java.util.List;

import it.polimi.ingsw.ps19.controller.action.Action;
import it.polimi.ingsw.ps19.controller.action.MessageInterpreterVisitor;
import it.polimi.ingsw.ps19.model.map.City;

/**
 * Message to notify of a king movement and consequent emporia to build
 */
public class ChangeKingPositionMessage extends Request 
{
	private static final long serialVersionUID = -7024597380056383106L;

	private City city;
	private List<Color> politicCard;
	
	/**
	 * Constructor
	 * @param c: destination city
	 * @param politic: king's council's satisfying color
	 */
	public ChangeKingPositionMessage(City c, List<Color> politic) 
	{
		city = c;
		politicCard = politic;
	}
	public City getCity() 
	{
		return city;
	}
	
	public List<Color> getPoliticCard() 
	{
		return politicCard;
	}
	
	@Override
	public Action accept(MessageInterpreterVisitor messageInterpreter) {
		return messageInterpreter.visit(this);
	}


}
