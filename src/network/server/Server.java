package network.server;

import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import utils.Triplet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Server extends Thread{
	
	private int serverPort;
	private ServerSocket serverSocket;
	private ArrayList<ServerWorker> serverWorkerList = new ArrayList<>();
	private ArrayList<Topic> topicList = new ArrayList<>();
	private ArrayList<Triplet<Image, String, String>> showcaseImageList = new ArrayList<>();
	
	
	public Server(int serverPort) {
		this.serverPort = serverPort;
	}

	
	public List<ServerWorker> getServerWorkerList() {
		return serverWorkerList;
	}
	
	public void removeClient(ServerWorker cHandler) {
		serverWorkerList.remove(cHandler);
	}
	
	public List<Topic> getTopicList() {
		return topicList;
	}
	
	public void addTopic(Topic topic) {
		topicList.add(topic);
	}

	public Topic getTopic(String topicName){
		for (Topic t : topicList){
			if(t.getTopicName().equalsIgnoreCase(topicName)){
				return t;
			}
		}
		return null;
	}

	public ServerWorker getServerWorker(String user){
		for (ServerWorker s: serverWorkerList){
			if(s.getUser() != null && s.getUser().equalsIgnoreCase(user)){
				return s;
			}
		}
		return null;
	}

	
	public void removeTopic(Topic topic) {
		topicList.remove(topic);
	}

	public void closeServer() throws IOException {
		for (ServerWorker c : serverWorkerList){
			c.close();
		}
		serverSocket.close();
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(serverPort);
			while(true) {
				System.out.println("About to accept clinet connection...");
				serverSocket.setReceiveBufferSize(6400000);
				Socket clientSocket = serverSocket.accept();
				clientSocket.setReceiveBufferSize(6400000);
				System.out.println("Accepted connection from " + clientSocket);
				ServerWorker serverWorker = new ServerWorker(this, clientSocket);
				serverWorkerList.add(serverWorker);
				serverWorker.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ArrayList<Triplet<Image, String, String>> getShowcaseImageList() {
		return showcaseImageList;
	}

	public void setShowcaseImageList(ArrayList<Triplet<Image, String, String>> showcaseImageList) {
		this.showcaseImageList = showcaseImageList;
	}
}
