package it.polimi.ingsw.PS19.model.bonus;

import it.polimi.ingsw.PS19.model.Player;

public class MoreMoney implements Bonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3640073227737356797L;
	int howMany; //how many money to give to the player

	public MoreMoney(int n) {
		howMany=n;
		
	}
	
	@Override
	public void giveBonus(Player p) {
		p.setMoney(p.getMoney()+howMany);
		
	}

	@Override
	public String toString(){
		return "gain "+howMany+" Money";
	}
}