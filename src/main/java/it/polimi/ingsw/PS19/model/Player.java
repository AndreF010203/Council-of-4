package it.polimi.ingsw.PS19.model;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.PS19.model.card.BusinessCard;
import it.polimi.ingsw.PS19.model.card.PoliticsCard;
import it.polimi.ingsw.PS19.model.map.FileReader;

public class Player {
	int money;
	int helpers;
	int victoryPoints;
	int nobilityPoints;
	int mainActionCounter;
	int fastActionCounter;
	int startingPoliticCard;
	
	ArrayList<BusinessCard> freebusinesscard;
	ArrayList<BusinessCard> usedbusinesscard;
	ArrayList<PoliticsCard> politiccard;
	
	
	
	
	//methods
	
	public void setStartingAction(){
		mainActionCounter = 1;
		fastActionCounter = 1;
	}
	
	
	public ArrayList<Player> setStartingItems(ArrayList<Player> p, String xmlfile){
		
		NodeList nList = FileReader.XMLReader(xmlfile, "starting");
		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) nNode;
			int mone=Integer.parseInt(e.getElementsByTagName("money").item(0).getTextContent());
			int helper = Integer.parseInt(e.getElementsByTagName("helpers").item(0).getTextContent());
			int politiccards = Integer.parseInt(e.getElementsByTagName("politiccards").item(0).getTextContent());		
			
			for(Player player : p){
				player.startingPoliticCard=politiccards;
				player.money=mone+p.indexOf(player);
				player.helpers=helper+p.indexOf(player);
			}
		}
		return p;
	}
	
	public void addCardToHand(PoliticsCard c){
		politiccard.add(c);
	}
	
	public void addCardToHand(BusinessCard c){
		freebusinesscard.add(c);
	}
	
	//getter and setter
	
	public int getMainActionCounter() {
		return mainActionCounter;
	}
	
	public void setMainActionCounter(int mainActionCounter) {
		this.mainActionCounter = mainActionCounter;
	}
	
	public int getFastActionCounter() {
		return fastActionCounter;
	}
	
	public void setFastActionCounter(int fastActionCounter) {
		this.fastActionCounter = fastActionCounter;
	}
	
	public int getNobilityPoints() {
		return nobilityPoints;
	}
	public void setNobilityPoints(int nobilityPoints) {
		this.nobilityPoints = nobilityPoints;
	}
	public int getVictoryPoints() {
		return victoryPoints;
	}
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int m) {
		this.money = m;
	}
	public int getHelpers() {
		return helpers;
	}
	public void setHelpers(int helpers) {
		this.helpers = helpers;
	}
	
	public boolean findPoliticsCard(PoliticsCard card)
	{
		for (PoliticsCard politics : politiccard) 
		{
			if(politics.equals(card))
				return true;
		}
		return false;
		
	}
	
	
}
