package it.polimi.ingsw.PS19.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.PS19.model.card.BusinessCard;
import it.polimi.ingsw.PS19.model.card.PoliticsCard;
import it.polimi.ingsw.PS19.model.map.City;
import it.polimi.ingsw.PS19.model.map.FileReader;
import it.polimi.ingsw.PS19.model.parameter.Costants;

public class Player implements Serializable
{

	private static final int EMPORIUM_SIZE=10;
	private static final long serialVersionUID = -505551466312267551L;
	
	private int id;
	
	private int money;
	private int helpers;
	private int victoryPoints;
	private int nobilityPoints;
	
	private int startingPoliticCard;
		
	private int mainActionCounter;
	private int fastActionCounter;
	
	private int politicCardToDraw;
		
	private List<BusinessCard> freebusinesscard;
	private List<BusinessCard> usedbusinesscard;
	private List<PoliticsCard> politiccard;
	
	private int maxemporia;
	private List<City> myEmporia;
	
	private boolean businessCardRequest;
	private boolean cityBonusRequest;
	
	//TODO cambiare da public a protected il costruttore
	public Player(int id) 
	{
		this.id = id;
		myEmporia = new ArrayList<>();
		freebusinesscard = new ArrayList<>();
		usedbusinesscard = new ArrayList<>();
		politiccard = new ArrayList<>();
		mainActionCounter = 1;
		fastActionCounter = 1;
		businessCardRequest = false;
		cityBonusRequest = false;
	}
	
	public void setStartingAction()
	{
		mainActionCounter = 1;
		fastActionCounter = 1;
		politicCardToDraw = 1;
	}
	
	public static List<Player> setStartingItems(List<Player> p, String xmlfile){
		
		NodeList nList = FileReader.XMLReader(xmlfile, "starting");
		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) nNode;
			int money=Integer.parseInt(e.getElementsByTagName("money").item(0).getTextContent());
			int helpers = Integer.parseInt(e.getElementsByTagName("helpers").item(0).getTextContent());
			int politiccards = Integer.parseInt(e.getElementsByTagName("politiccards").item(0).getTextContent());		
			
			for(Player player : p)
			{
				player.startingPoliticCard=politiccards;
				player.money=money+p.indexOf(player);
				player.helpers=helpers+p.indexOf(player);
				player.maxemporia=EMPORIUM_SIZE;
				player.politicCardToDraw = 1;
			}
		}
		return p;
	}
	
	public void addCardToHand(PoliticsCard c)
	{
		politiccard.add(c);
	}
	public void removeCardToHand(PoliticsCard c)
	{
		for (PoliticsCard card : politiccard) 
			if(card.getColor().equals(c.getColor()))
			{
				politiccard.remove(card);
				return;
			}
	}
	
	public void addCardToHand(BusinessCard c)
	{
		freebusinesscard.add(c);
	}
	public List<PoliticsCard> getPoliticcard() 
	{
		return Costants.clonePoliticCard(politiccard);
	}
	
	/**
	 * Get player Id
	 * @return the id of this player
	 */
	public int getId() 
	{
		return id;
	}	
	/*
	 * START GETTER AND SETTER OF MAIN AND FAST ACTION
	 */
	public int getMainActionCounter() 
	{
		return mainActionCounter;
	}
	
	public void setMainActionCounter(int mainActionCounter) 
	{
		this.mainActionCounter = mainActionCounter;
	}
	
	public int getFastActionCounter() 
	{
		return fastActionCounter;
	}
	
	public void setFastActionCounter(int fastActionCounter) 
	{
		this.fastActionCounter = fastActionCounter;
	}
	/*
	 * END GETTER AND SETTER OF MAIN AND FAST ACTION
	 * 
	 * START GETTER AND SETTER OF MONEY - NOBILITY POINTS - VICTORY POINTS - HELPERS
	 */
	
	public int getNobilityPoints() 
	{
		return nobilityPoints;
	}
	public void setNobilityPoints(int nobilityPoints) 
	{
		this.nobilityPoints = nobilityPoints;
	}
	public int getVictoryPoints() 
	{
		return victoryPoints;
	}
	public void setVictoryPoints(int victoryPoints) 
	{
		this.victoryPoints = victoryPoints;
	}
	public int getMoney()
	{
		return money;
	}
	public void setMoney(int money) 
	{
		this.money = money;
	}
	public int getHelpers() 
	{
		return helpers;
	}
	public void setHelpers(int helpers) 
	{
		this.helpers = helpers;
	}
	
	/*
	 * END GETTER AND SETTER OF MONEY - VICTORY POINTS - NOBILITY POINTS - HELPERS
	 */
		
	@Override
	public String toString() {
		String s = "";
		s += "ID:  " + id + "\n";
		s += "Money:  " + money + "\n";
		s += "Helpers:  " + helpers + "\n";
		s += "victoryP:  " + victoryPoints + "\n";
		s += "NobilityP:  " + nobilityPoints + "\n";
		s += "POLITICS CARD: ";
		for (PoliticsCard p : politiccard) 
		{
			s += p.toString() + "   ";
		}
		s += "\nFREE BUSINESS CARD: ";
		for (BusinessCard businessCard : freebusinesscard) {
			s += businessCard.toString() + "   ";
		}
		
		s += "\nUSED BUSINESS CARD: ";
		for (BusinessCard businessCard : usedbusinesscard) {
			s += businessCard.toString() + "   ";
		}
		return s;
	}
	
	public boolean findPoliticsCard(PoliticsCard card)
	{
		for (PoliticsCard politics : politiccard) 
		{
			if(politics.getColor().equals(card.getColor()))
				return true;
		}
		return false;
		
	}

	public void addToMyEmporia(City c)
	{
		myEmporia.add(c);
		maxemporia=maxemporia-1;
	}

	/**
	 * @return the myEmporia
	 */
	public List<City> getMyEmporia() 
	{
		return myEmporia;
	}

	public Boolean findMyEmporiaById(int id)
	{
		for (City c : myEmporia) 
			if(c.getId() == id)
				return true;
		return false;
	}

	public List<BusinessCard> getFreebusinesscard() 
	{
		List<BusinessCard> list = new ArrayList<>();
		list.addAll(freebusinesscard);
		return list;
	}
	
	public BusinessCard removeFreebusinesscardById(int id) 
	{
		BusinessCard b = null;
		for (BusinessCard card : freebusinesscard)
			if(card.getId() == id)
				b = card;
		freebusinesscard.remove(b);
		return b;
	}
	
	
	public List<BusinessCard> getUsedbusinesscard() 
	{
		List<BusinessCard> list = new ArrayList<>();
		list.addAll(usedbusinesscard);
		return list;
	}
	
	public void addUsedBusinessCard(BusinessCard card)
	{
		usedbusinesscard.add(card);
	}


	/**
	 * @return the maxemporia
	 */
	public int getMaxemporia() {
		return maxemporia;
	}


	/**
	 * @param maxemporia the maxemporia to set
	 */
	public void setMaxemporia(int maxemporia) {
		this.maxemporia = maxemporia;
	}


	/**
	 * Get Number of Politic card to Draw AFTER SET
	 * THIS NUMBER AT 0
	 * @return the politicCardToDraw
	 */
	public int getPoliticCardToDraw() 
	{
		int app = politicCardToDraw;
		politicCardToDraw = 0;
		return app;
	}

	/**
	 * @param politicCardToDraw the politicCardToDraw to set
	 */
	public void setPoliticCardToDraw(int politicCardToDraw) 
	{
		this.politicCardToDraw = politicCardToDraw;
	}
	
	public int getStartingPoliticCard() 
	{
		return startingPoliticCard;
	}
	
	
	public void setBusinessCardRequest(boolean businessCardRequest) {
		this.businessCardRequest = businessCardRequest;
	}
	public void setCityBonusRequest(boolean cityBonusRequest) {
		this.cityBonusRequest = cityBonusRequest;
	}
	public boolean isBusinessCardRequest() {
		return businessCardRequest;
	}
	public boolean isCityBonusRequest() {
		return cityBonusRequest;
	}
	
}
