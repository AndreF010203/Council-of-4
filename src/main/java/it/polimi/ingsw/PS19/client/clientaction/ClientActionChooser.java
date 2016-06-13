package it.polimi.ingsw.PS19.client.clientaction;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.PS19.client.ClientUI;
import it.polimi.ingsw.PS19.client.clientmodel.clientdata.ClientModel;

/**
 * Class that rapresent a type of action
 */
public abstract class ClientActionChooser
{
	int avail = 1;
	ClientModel model;
	List<ClientAction> actions = new ArrayList<>();
	
	/**
	 * Constructor
	 * @param m
	 */
	public ClientActionChooser(ClientModel m) 
	{
		model = m;
	}
	
	public abstract boolean isPossible();
		
	/**
	 * Makes the user choose one action from the ones available in the class
	 * @param userInterface
	 * @return clientaction
	 */
	public ClientAction getAction(ClientUI userInterface)
	{
		return userInterface.getAction(this.possibleActions());
	}
	
	/**
	 * Calculate which actions are available of the one included in the class
	 * @return List of available actions
	 */
	public List<ClientAction> possibleActions() 
	{
		ArrayList<ClientAction> availableActions = new ArrayList<>();
		for(ClientAction a : actions)
		{
			if(a.isPossible())
				availableActions.add(a);
		}
		return availableActions;
	}
}
