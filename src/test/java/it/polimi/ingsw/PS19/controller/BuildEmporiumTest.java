package it.polimi.ingsw.PS19.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polimi.ingsw.PS19.controller.action.SupportMethod;
import it.polimi.ingsw.PS19.message.requests.BuildEmporiumMessage;
import it.polimi.ingsw.PS19.model.Model;
import it.polimi.ingsw.PS19.model.Player;
import it.polimi.ingsw.PS19.model.bonus.Bonus;
import it.polimi.ingsw.PS19.model.card.BusinessCard;
import it.polimi.ingsw.PS19.model.map.City;
import it.polimi.ingsw.PS19.model.parameter.RegionType;

public class BuildEmporiumTest extends SupportMethod{

	@Test
	public void test() 
	{
		Model m = new Model(4);
		GameController g = new GameController(m);
		
		BusinessCard card = m.getMap().getRegionByType(RegionType.HILL).getFirstcard();
		m.getMap().getRegionByType(RegionType.HILL).drowFirstCard();
		
		m.getPlayerById(0).addCardToHand(card);
	
		assertTrue(m.getPlayerById(0).getFreebusinesscard().size() == 1);
		
		//la prima città sulla prima carta
		City c = m.getPlayerById(0).getFreebusinesscard().get(0).getCity().get(0);
		
		BuildEmporiumMessage build = new BuildEmporiumMessage(c, m.getPlayerById(0).getFreebusinesscard().get(0));
		build.setId(0);
	
		g.update(null, build);
		
		assertTrue(m.getPlayerById(0).getFreebusinesscard().size() == 0);
		assertTrue(m.getPlayerById(0).getUsedbusinesscard().size() == 1);
			//controllo che è presente l'Id del player dentro la città
		assertTrue(c.getEmporia().contains(0));
		assertTrue(m.getPlayerById(0).getMyEmporia().contains(c));
		
			//creo un player falso
		Player test = new Player(0);
		test.setMoney(10);
		test.setHelpers(1);
		test.setVictoryPoints(0);
		test.setNobilityPoints(0);
			//applico i bonus al player
		for(Bonus b : c.getBonus())
		{
			b.giveBonus(test);
			checkNobilityPathBonus(m, test);
		}
			//controllo che i risutati coincidano
		assertTrue(test.getMoney() == m.getPlayerById(0).getMoney());
		assertTrue(test.getHelpers() == m.getPlayerById(0).getHelpers());
		assertTrue(test.getVictoryPoints() == m.getPlayerById(0).getVictoryPoints());
		assertTrue(test.getNobilityPoints() == m.getPlayerById(0).getNobilityPoints());
		
	}

}
