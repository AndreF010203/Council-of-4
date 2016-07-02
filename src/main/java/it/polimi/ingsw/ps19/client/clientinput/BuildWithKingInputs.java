package it.polimi.ingsw.ps19.client.clientinput;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.polimi.ingsw.ps19.client.ClientUI;
import it.polimi.ingsw.ps19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.ps19.client.language.Language;
import it.polimi.ingsw.ps19.exceptions.clientexceptions.InvalidInsertionException;
import it.polimi.ingsw.ps19.message.requests.ChangeKingPositionMessage;
import it.polimi.ingsw.ps19.message.requests.Request;
import it.polimi.ingsw.ps19.model.map.City;
import it.polimi.ingsw.ps19.model.parameter.Costants;

/**
 * Class that creates a BuilWithKingMessage from user inputs and local model
 */
public class BuildWithKingInputs extends SatisfyCouncilInput 
{
	City city;
	List<Color> colors;
	
	/**
	 * Constructor
	 * @param m
	 */
	public BuildWithKingInputs(ClientModel m) 
	{
		model = m;
	}

	@Override
	public boolean isPossible() 
	{
		if(!getCities().isEmpty() && kingAvailable())
			return true;
		return false;
	}
	
	@Override
	public Request execute(ClientUI userInterface) throws InvalidInsertionException 
	{
		colors = satisfyCouncil(model.getKing().getBalcony(), userInterface);
		city = userInterface.getCity(getCities());
		return buildMessage();
	}

	@Override
	protected Request buildMessage() 
	{
		return new ChangeKingPositionMessage(city, colors);
	}
	
	private Map<City, Integer> getCities()
	{
		List<City> allCities = Costants.clone(model.getAllCities());
		for(City city : model.getMyPlayer().getMyEmporia())
		{
			int i = 0;
			while(i < allCities.size())
			{
				if(city.getId() == allCities.get(i).getId())
					allCities.remove(i);
				else
					i++;
			}
		}
		return allCities.stream().filter(c -> getCost(c) < model.getMyPlayer().getMoney() &&  c.getEmporia().size() < model.getMyPlayer().getHelpers()).collect(Collectors.toMap(c -> c, c -> getCost(c)));
		/*
		for(City c: allCities)
		{
			int moneyToMoveKing = Costants.JUMPCOST * (Costants.calculateShorterPath(model.getKing().getCurrentcity(), c, model.getRegions()).size() - 1);
			if(	!(model.getMyPlayer().getMyEmporia().contains(c)) &&  c.getEmporia().size() < model.getMyPlayer().getHelpers() 
				&&	moneyToMoveKing < model.getMyPlayer().getMoney())
				availableCities.put(c, moneyToMoveKing);
		}
		*/
	}
	
	private int getCost(City c)
	{
		return Costants.JUMPCOST * (Costants.calculateShorterPath(model.getKing().getCurrentcity(), c, model.getRegions()).size() - 1);
	}

	@Override
	public String toString(Language l)
	{
		return l.getString(this);
	}
}