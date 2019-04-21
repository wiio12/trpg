package indi.wiio.network.client;

import indi.wiio.info.Others;
import indi.wiio.info.Self;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import indi.wiio.controllers.showcase.ShowcaseItem;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Client {
	private String serverName;
	private int serverPort;
	private Socket socket;
	private InputStream serverIn;
	private OutputStream serverOut;
	private MsgReceiver msgReceiver;
	private String userName;
	
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	private ArrayList<MsgListener> msgListeners = new ArrayList<>();
	private ArrayList<TopicListener> topicListeners = new ArrayList<>();
	private ArrayList<SystemMessageListener> systemMessageListeners = new ArrayList<>();
	private ArrayList<OthersMessageListener> othersMessageListeners = new ArrayList<>();
	private ArrayList<OthersRequestListener> othersRequestListeners = new ArrayList<>();
	private ArrayList<ShowcaseImageListener> showcaseImageListeners = new ArrayList<>();
	
	public Client(String serverName, int serverPort) {
		this.serverName = serverName;
		this.serverPort = serverPort;
	}
	
	
	public void addUserStatusListener(UserStatusListener listener) {
		userStatusListeners.add(listener);
	}
	
	public void removeUserStatusListener(UserStatusListener listener) {
		userStatusListeners.remove(listener);
	}

	public List<UserStatusListener> getUserStatusListeners() {
		return userStatusListeners;
	}



	public void addMsgListener(MsgListener listener) {
		msgListeners.add(listener);
	}
	
	public void removeMsgListener(MsgListener listener) {
		msgListeners.remove(listener);
	}
	
	public List<MsgListener> getMsgListeners (){
		return msgListeners;
	}


	public void addTopicListener(TopicListener listener) {
		topicListeners.add(listener);
	}

	public void removeTopicListener(TopicListener listener) {
		topicListeners.remove(listener);
	}

	public List<TopicListener> getTopicListeners (){
		return topicListeners;
	}



	public void addsystemMessageListener(SystemMessageListener listener) {
		systemMessageListeners.add(listener);
	}

	public void removesystemMessageListener(SystemMessageListener listener) {
		systemMessageListeners.remove(listener);
	}

	public List<SystemMessageListener> getsystemMessageListeners (){
		return systemMessageListeners;
	}


	public void addOthersMessageListener(OthersMessageListener listener) {
		othersMessageListeners.add(listener);
	}

	public void removeOthersMessageListener(OthersMessageListener listener) {
		othersMessageListeners.remove(listener);
	}

	public List<OthersMessageListener> getOthersMessageListeners (){
		return othersMessageListeners;
	}


	public void addOthersRequestListener(OthersRequestListener listener) { othersRequestListeners.add(listener); }

	public void removeOthersRequestListener(OthersRequestListener listener) {
		othersRequestListeners.remove(listener);
	}

	public List<OthersRequestListener> getOthersRequestListener (){
		return othersRequestListeners;
	}


	public void addShowcaseImageListener(ShowcaseImageListener listener) { showcaseImageListeners.add(listener); }

	public void removeShowcaseImageListener(ShowcaseImageListener listener) {
		showcaseImageListeners.remove(listener);
	}

	public List<ShowcaseImageListener> getShowcaseImageListener (){
		return showcaseImageListeners;
	}

	
	
	public void login(String user, Image image, String imageType) throws IOException {
		this.userName = user;
		this.send(user + " " + imageType, MessageType.LOGIN_COMMAND);
		this.sendImg(image, imageType);
		ClientMain.setLogin(true);
	}
	
	public void logout() throws IOException {
		this.send(this.userName, MessageType.LOGOUT_COMMAND);
	}

	public void sendShowCaseImage(ShowcaseItem showcaseItem) throws IOException {
		this.send(userName + " " + showcaseItem.getName() + " " + showcaseItem.getImageType(), MessageType.SHOWCASE_IMAGE_COMMAND);
		this.sendImg(showcaseItem.getImage(), showcaseItem.getImageType());
	}

	public void sendSelf() throws IOException {
		boolean isWritten = Others.writeMySelfToFile();
		if(isWritten){
			this.send(Self.getSelf().getName(), MessageType.OTHERS_COMMAND);
			this.sendFile(Others.getFileMyPath());
		}else{
			System.out.println("Resource is not ready!");
		}

	}

	public void requestPlayerStatistic() throws IOException {
		this.send(Self.getSelf().getName(), MessageType.REQUEST_OTHERS_COMMAND);
	}

	public void joinTopic(String topicName) throws IOException {
		this.send(topicName, MessageType.TOPIC_JOIN_COMMAND);
	}

	public void leaveTopic(String topicName) throws IOException {
		this.send(topicName, MessageType.TOPIC_LEAVE_COMMAND);
	}

	public void callTopic(String topicName, String userName) throws IOException {
		String msg = topicName + " " + userName;
		this.send(msg, MessageType.TOPIC_CALL_COMMAND);
	}
	
	public void msg(String sendTo, String msgBody) throws IOException {
		String msg = sendTo + " " + msgBody + "\n";
		send(msg, MessageType.COMM_COMMAND);
	}
	
	public boolean connect(String serverName, int serverPort) {
		try {
			this.socket = new Socket(serverName, serverPort);
			System.out.println("hit");
			this.serverOut = socket.getOutputStream();
			this.serverIn = socket.getInputStream();
			this.receiveMsg();
			return true;
		} catch (IOException e) {
			System.out.println("CONNECT FAILED");
		}
		return false;
	}

	

	private void receiveMsg() {
		msgReceiver = new MsgReceiver(this, serverIn);
		msgReceiver.setDaemon(true);
		msgReceiver.start();
	}


	@SuppressWarnings("Duplicates")
	private void send(String msg, MessageType type) throws IOException {
		if(serverOut != null) {
			String send = type.toString() + " " + msg.trim() + '\n';
			byte[] sizeAr = ByteBuffer.allocate(4).putInt(send.getBytes().length).array();
			serverOut.write(sizeAr);
			serverOut.write(send.getBytes());
		}

	}

	@SuppressWarnings("Duplicates")
	private void sendImg(Image image, String imgType) throws IOException {
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), imgType, s);
		byte [] buf = s.toByteArray();
		System.out.println("Img Send:" + buf.length);
		byte[] sizeAr = ByteBuffer.allocate(4).putInt(buf.length).array();
		serverOut.write(sizeAr);
		serverOut.write(buf);
		serverOut.flush();
	}

	@SuppressWarnings("Duplicates")
	private void sendFile(String filepath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filepath);
		byte [] fileByte = fileInputStream.readAllBytes();
		byte[] sizeAr = ByteBuffer.allocate(4).putInt(fileByte.length).array();
		System.out.println("File Send:" + fileByte.length);
		serverOut.write(sizeAr);
		serverOut.write(fileByte);
	}

}
