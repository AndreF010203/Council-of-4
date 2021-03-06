package it.polimi.ingsw.ps19.model.map;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps19.model.parameter.ColorManager;

/**
 * Class to rappresent councillor not in balconies
 */
public class AvailableCouncillor implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Map<Color,Integer> councillor;
	private ColorManager listofcolors;
	
	/**
	 * Sets the number of councillor for color
	 * @param numberoffreecouncillor
	 * @param colors
	 */
	public AvailableCouncillor(int numberoffreecouncillor, ColorManager colors) 
	{
		listofcolors = colors;
		councillor = new HashMap<>();
		
		for (Color color : listofcolors.getColors())
			councillor.put(color, numberoffreecouncillor);
	}
	
	/**
	 * decrement the number of councillor of the given color available
	 * @param color
	 * @return true <==> there was at least one councillor of that color
	 */
	public boolean decrement(Color color)
	{
		Integer numberofcouncillor = councillor.get(color);
		if(numberofcouncillor.intValue() == 0)
			return false;
		numberofcouncillor--;
		councillor.put(color, numberofcouncillor);
		return true;
		
	}
	
	public void increment(Color color)
	{
		Integer numberofcouncillor = councillor.get(color);
		numberofcouncillor++;
		councillor.put(color, numberofcouncillor);
	}
	
	public List<Color> getAvailableColors()
	{
		List<Color> availableColors = new ArrayList<>();
		for (Color c : listofcolors.getColors())
			if(councillor.get(c) > 0)
				availableColors.add(c);
		return availableColors;
	}
	
	public ColorManager getListofcolors() {
		return listofcolors;
	}
	public boolean findColor(Color color)
	{
		for (Color c : listofcolors.getColors())
			if(c.equals(color))
				return true;
		return false;
	}	
}
