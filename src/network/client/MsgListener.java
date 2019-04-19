package network.client;

public interface MsgListener {
	
	public void onReceivedMessage(String fromUser, String msgBody);

}
