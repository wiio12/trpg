package network.client;

public interface SystemMessageListener {
     void onReceivedSystemInfo(String msg);
     void onReceivedSystemError(String msg);
}
