package it.polimi.ingsw.PS19.message;

public class PlayerDisconnectedMessage extends Message 
{
	private static final long serialVersionUID = 1600487378262939610L;
	
	public PlayerDisconnectedMessage(Integer iD) 
	{
		id = iD;
		type = MessageType.PLAYER_DISCONNECTED;
	}
	
	@Override
	public String getString() 
	{
		return null;
	}

}
