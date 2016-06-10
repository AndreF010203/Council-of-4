package it.polimi.ingsw.ps19.model.parameter;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import java.util.Random;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps19.model.map.FileReader;

public class ColorManager implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 477874054352268861L;
	private static final String COLOR_MARKER = "color";
	private static final String COLORS_MARKER = "colors";
	
	
	private ArrayList<Color> colors;
	
	private int size = 0;
	
	public ColorManager(String pathfile) 
	{
		colors = new ArrayList<>();
		try {
			
			NodeList numberofcolors = FileReader.XMLReader(pathfile, COLOR_MARKER);
			
			NodeList nList = FileReader.XMLReader(pathfile,COLORS_MARKER);
			Element eElement = (Element) nList.item(0);
						
			for(int k=0; k<numberofcolors.getLength(); k++)
			{
				Color c = Color.decode(eElement.getElementsByTagName(COLOR_MARKER).item(k).getTextContent());
				
				colors.add(c);
				size++;	
			}

		} catch (Exception e) 
		    	{
		    	e.printStackTrace();
		    	}
	}
	
	public ColorManager(List<String> listofcolors)
	{
		colors = new ArrayList<>();
		for(String s : listofcolors)
		{
				colors.add(Color.decode(s));
				size++;	
		}
	}
	
	public ColorManager(Color color)
	{
		colors = new ArrayList<>();
		colors.add(color);
		size++;	
	}
	
	public void addColor(Color color)
	{
		colors.add(color);
		size++;
	}
@Override
	public String toString() 
	{
		String s = "SIZE: " + size + "\n";
		for (Color color : colors) 
			s = s + color + " ";
		return s;
			
	}
	public int getSize() {
		return size;
	}
	public List<Color> getColors() 
	{
		return colors;
	}
	public Color getRandomColor()
	{
		Random rand = new Random();
		
		return colors.get(rand.nextInt(size));
	}
}