package it.polimi.ingsw.PS19.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.PS19.controller.GameController;
import it.polimi.ingsw.PS19.message.requests.RedrawBusinessCardMessage;
import it.polimi.ingsw.PS19.model.Model;
import it.polimi.ingsw.PS19.model.card.BusinessCard;
import it.polimi.ingsw.PS19.model.parameter.RegionType;

public class RedrawBusinessCard 
{

	@Test
	public void test() 
	{
		/**
		 * Controllo che le carte iniziali e le carte dopo aver eseguito la mossa
		 * redraw siano diverse.
		 */

		Model m = new Model(2);
		BusinessCard first = m.getMap().getRegionByType(RegionType.PLAIN).getFirstcard();
		BusinessCard second = m.getMap().getRegionByType(RegionType.PLAIN).getSecondcard();
		//System.out.print(m.getMap().getRegionByType(RegionType.PLAIN).toString());
		GameController g = new GameController(m);
		RedrawBusinessCardMessage message = new RedrawBusinessCardMessage(RegionType.PLAIN);
		
		message.setId(0);
		
		g.update(null, message);
		
		BusinessCard firstNew = m.getMap().getRegionByType(RegionType.PLAIN).getFirstcard();
		BusinessCard secondNew = m.getMap().getRegionByType(RegionType.PLAIN).getSecondcard();
		//System.out.print(m.getMap().getRegionByType(RegionType.PLAIN).toString());
		assertTrue(first.getId() != firstNew.getId());
		assertTrue(second.getId() != secondNew.getId());
	}
}
