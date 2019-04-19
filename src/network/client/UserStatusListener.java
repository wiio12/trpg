package network.client;


import javafx.scene.image.Image;

public interface UserStatusListener {
	
	void onUserOnline(String user, Image image);
	void onUserOffline(String user);

}
