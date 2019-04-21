package indi.wiio.network.client;

import indi.wiio.info.ChatMessage;

public interface MsgListener {
	
	public void onReceivedMessage(ChatMessage chatMessage);

}
