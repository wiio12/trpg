package indi.wiio.network.server;


public class ServerMain {
	public static Server server;

	public static void startServer(int port) {
		server = new Server(port);
		server.setDaemon(true);
		server.start();
	}

	public static void closeServer(){
		try {
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
