package indi.wiio.network.server;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import indi.wiio.network.client.MessageType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import indi.wiio.utils.Triplet;

import javax.imageio.ImageIO;

public class ServerWorker extends Thread {
	
	private Socket clientSocket;
	private Server server;
	private String user;
	private Image profileImg;
	private String profileImgType;
	private OutputStream outputStream;
	private InputStream inputStream;
	

	
	public ServerWorker(Server server, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.server = server;
	}

	public void close() throws IOException {
		clientSocket.close();
	}

	@Override
	public void run() {
		try {
			handleClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("Duplicates")
	private void handleClient() throws IOException {
		inputStream = clientSocket.getInputStream();
		outputStream = clientSocket.getOutputStream();


		byte[] sizeAr = new byte[4];   //建立四个字节读取长度
		while( inputStream.read(sizeAr) != -1 ) {  //读取，返回的是实际读取的字节数
			int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();  //建立一个读成整型
			byte [] msgAr = new byte[size];  //用size建立新的数组用来存储读取的东西
			int len = inputStream.read(msgAr);
			//TODO:处理EOF情况？
			String line = new String(msgAr, 0, len);
			System.out.println("SERVER::"+line);

			String [] tokens = StringUtils.split(line);
			if(tokens != null && tokens.length > 0) {
				String messageType = tokens[0];
				if( MessageType.LOGOUT_COMMAND.toString().equalsIgnoreCase(messageType)
						|| MessageType.SYSTEM_QUIT_COMMAND.toString().equalsIgnoreCase(messageType)) {
					//Handle logout 
					handleLogout();
					break;
				}else if(MessageType.LOGIN_COMMAND.toString().equalsIgnoreCase(messageType)) {
					//Handle login
					handleLogin(tokens);
				}else if(MessageType.COMM_COMMAND.toString().equalsIgnoreCase(messageType)) {
					//Handle direct communication
					String [] msgTokens = StringUtils.split(line, " ", 3);
					handleMsg(msgTokens);
				}else if(MessageType.TOPIC_LEAVE_COMMAND.toString().equalsIgnoreCase(messageType)) {
					//handle leaving the topic
					handleLeave(tokens);
				}else if(MessageType.TOPIC_JOIN_COMMAND.toString().equalsIgnoreCase(messageType)) {
					//join topic
					handleJoinTopic(tokens);
				}else if(MessageType.TOPIC_CALL_COMMAND.toString().equalsIgnoreCase(messageType)) {
					handleCallTopic(tokens);
				}else if(MessageType.OTHERS_COMMAND.toString().equalsIgnoreCase(messageType)) {
					handleOthers(tokens);
				}else if(MessageType.REQUEST_OTHERS_COMMAND.toString().equalsIgnoreCase(messageType)) {
					handleRequestCommand(tokens);
				}else if(MessageType.SHOWCASE_IMAGE_COMMAND.toString().equalsIgnoreCase(messageType)) {
					handleShowcaseImage(tokens);
				}else {
					handleError("unknown_message_type", ErrorType.COMMAND_FORMAT);
				}
				
						
			}
		}
		//clientSocket.close();
	}

	private void handleShowcaseImage(String[] tokens) throws IOException {
		if(tokens.length != 4){
			handleError("showcase_image", ErrorType.COMMAND_FORMAT);
			return;
		}

		String userName = tokens[1];
		String imageName = tokens[2];
		String imageType = tokens[3];

		//&& !sw.getUser().equalsIgnoreCase(userName)

		Triplet<Image, byte[], Integer> triplet = receiveImg();
		if(triplet != null){
			server.getShowcaseImageList().add(new Triplet<>(triplet.getLeft(), imageName, imageType));
			for(ServerWorker sw: server.getServerWorkerList()){
				if(sw.getUser() != null && !sw.getUser().equalsIgnoreCase(userName)){
					sw.send(userName + " " + imageName + " " + imageType, MessageType.SHOWCASE_IMAGE_MESSAGE );
					sw.sendImg(triplet.getMiddle(), triplet.getRight());
				}
			}

		}



	}

	private void handleRequestCommand(String[] tokens) throws IOException {
		if(tokens.length != 2){
			handleError("request_others", ErrorType.COMMAND_FORMAT);
			return;
		}

		String userName = tokens[1];

		for(ServerWorker sw: server.getServerWorkerList()){
			if(sw.getUser() != null && !sw.getUser().equalsIgnoreCase(userName)){
				sw.send(userName, MessageType.REQUEST_OTHERS_MESSAGE);
			}
		}

	}

	private void handleOthers(String [] tokens) throws IOException {
		if(tokens.length != 2){
			handleError("others", ErrorType.COMMAND_FORMAT);
			return;
		}

		String userName = tokens[1];

		Triplet<File, byte[], Integer> fileIntegerTriplet = receiveFile();

		if(fileIntegerTriplet != null){
			for(ServerWorker sw: server.getServerWorkerList()){
				if(sw.getUser() != null && !sw.getUser().equalsIgnoreCase(userName)){
					sw.send(userName, MessageType.OTHERS_MESSAGE);
					sw.sendFile(fileIntegerTriplet.getLeft().getAbsolutePath());
				}
			}
		}else{
			handleError("receive_file_error", ErrorType.COMMAND_FORMAT);
		}


	}

	private void handleCallTopic(String[] tokens) throws IOException {
		if(tokens.length != 3){
			handleError("topic_call", ErrorType.COMMAND_FORMAT);
			return;
		}

		String topic = tokens[1];
		String userName = tokens[2];

		Topic t = server.getTopic(topic);
		ServerWorker sw = server.getServerWorker(userName);

		if(t == null || sw == null){
			if(t == null) this.send("Topic not exist", MessageType.ERROR_MESSAGE);
			if(sw == null) this.send("User not exist", MessageType.ERROR_MESSAGE);
			return;
		}


		if(!t.memberContains(sw)){
			t.join(sw);
			sw.send(topic, MessageType.TOPIC_BE_CALL_MESSAGE);
			this.send("Call successful", MessageType.SYSTEM_INFO_MESSAGE);
		}else{
			this.send("Member already contains", MessageType.SYSTEM_INFO_MESSAGE);
		}

	}

	private void handleLeave(String[] tokens) throws IOException {
		if(tokens.length != 2 || tokens[1].charAt(0) != '#'){
			handleError("leave_topic", ErrorType.COMMAND_FORMAT);
		}

		String topicName = tokens[1];
		Topic t = server.getTopic(topicName);
		if(t != null){
			t.remove(this);
			//TODO:最后一位成员的时候怎么办
			this.send("Leave topic succeed\n", MessageType.SYSTEM_INFO_MESSAGE);
		}else{
			this.send("Topic does not exist", MessageType.SYSTEM_INFO_MESSAGE);
		}

	}

	private void handleJoinTopic(String[] tokens) throws IOException {
		if(tokens.length != 2 || tokens[1].charAt(0) != '#') {
			handleError("join_topic", ErrorType.COMMAND_FORMAT);
		}

		String topicName = tokens[1];
		Topic t = server.getTopic(topicName);
		if(t != null){
			t.join(this);
			this.send("Join topic succeed.\n", MessageType.SYSTEM_INFO_MESSAGE);
		}else {
			Topic topic = new Topic(server, topicName);
			server.addTopic(topic);
			topic.join(this);
			this.send("Topic created.\n", MessageType.SYSTEM_INFO_MESSAGE);
		}
	}

	private void handleMsg(String[] tokens) throws IOException {
		// msg <user> text...

		if(tokens.length != 3){  // check the command
			handleError("message", ErrorType.COMMAND_FORMAT);
			return;
		}

		String sendTo = tokens[1];
		String body = tokens[2];
		
		if(sendTo.charAt(0) == '#') { // for group message

			Topic t = server.getTopic(sendTo);
			if(t != null && t.memberContains(this)){
				List<ServerWorker> cList = t.getMemberList();
				for(ServerWorker c : cList) {
					String msg = sendTo + "@" + this.user + " " + body + "\n";
					if(!c.getUser().equalsIgnoreCase(this.user)) {
						c.send(msg, MessageType.COMM_MESSAGE);
					}
				}
			}else{
				this.send("Topic doesn't exist or your are not an member of this topic", MessageType.ERROR_MESSAGE);
			}

		}else {

			ServerWorker sw = server.getServerWorker(sendTo);
			if(sw != null){
				String msg = this.user + " " + body + "\n";
				sw.send(msg, MessageType.COMM_MESSAGE);
			}else{
				this.send("User doesn't exist", MessageType.ERROR_MESSAGE);
			}

		}

	}

	private void handleLogout() throws IOException {
		List<ServerWorker> cList = server.getServerWorkerList();
		for(ServerWorker c : cList ) {
			String msg = this.user + '\n';
			c.send(msg, MessageType.OFFLINE_PROMPT_MESSAGE);
		}
		server.removeClient(this);
		clientSocket.close();
	}

	private void handleLogin(String[] tokens) throws IOException {
		if (tokens.length != 3) {
			handleError("login", ErrorType.COMMAND_FORMAT);
			return;
		}

		String user = tokens[1];
		String imageType = tokens[2];

		Triplet<Image, byte[], Integer> triplet = receiveImg();
		if(triplet != null){
			profileImg = triplet.getLeft();
			profileImgType = imageType;
		} else {
			this.send("img receive error", MessageType.ERROR_MESSAGE);
			return;
		}

		if(server.getServerWorker(user) != null){
			this.send("User already exist", MessageType.ERROR_MESSAGE);
			return;
		}


		String msg = "Login successful!\n";
		this.send(msg, MessageType.SYSTEM_INFO_MESSAGE);
		this.user = user;



		//TODO:清理
		System.out.println(this.user + " has login");

		String onlineMsg = this.user + " " + imageType +'\n';
		List<ServerWorker> serverWorkerList = server.getServerWorkerList();
		for (ServerWorker c : serverWorkerList) {
			if(c.getUser() != null ) {
				c.send(onlineMsg, MessageType.ONLINE_PROMPT_MESSAGE);
				c.sendImg(triplet.getMiddle(), triplet.getRight());
			}
		}



		for (ServerWorker c : serverWorkerList) {
			if(c.getUser() != null && !c.getUser().equals(this.user)){
				System.out.println("hit");
				onlineMsg = c.getUser() + " " + c.getProfileImgType() + "\n";
				this.send(onlineMsg, MessageType.ONLINE_PROMPT_MESSAGE);
				if(c.getProfileImg() == null || c.getProfileImgType() == null){
					System.out.println("为什么会这样啊");
				}
				this.sendImg(c.getProfileImg(), c.getProfileImgType());
			}
		}


	}

	private void handleError(String name, ErrorType type) throws IOException {
		String errMsg = type.toString() + " error <" + name + ">\n";
		send(errMsg, MessageType.SYSTEM_ERROR_MESSAGE);
	}


	@SuppressWarnings("Duplicates")
	private void send(String msg, MessageType type) throws IOException {
		if(outputStream != null) {
			String send = type.toString() + " " + msg.trim() + '\n';
			byte[] size = ByteBuffer.allocate(4).putInt(send.getBytes().length).array();
			outputStream.write(size);
			outputStream.write(send.getBytes());
		}
		
	}

	@SuppressWarnings("Duplicates")
	private void sendImg(Image image, String imgType) throws IOException {
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), imgType, s);
		byte [] buf = s.toByteArray();
		System.out.println("Send:" + buf.length);
		byte[] sizeAr = ByteBuffer.allocate(4).putInt(buf.length).array();
		outputStream.write(sizeAr);
		outputStream.write(buf);
		outputStream.flush();
	}

	private void sendImg(byte [] buffer, int size) throws IOException {
		byte[] sizeAr = ByteBuffer.allocate(4).putInt(size).array();
		outputStream.write(sizeAr);
		outputStream.write(buffer);
	}

	public String getUser() {
		return user;
	}


	public Image getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(Image profileImg) {
		this.profileImg = profileImg;
	}

	public String getProfileImgType() {
		return profileImgType;
	}

	public void setProfileImgType(String profileImgType) {
		this.profileImgType = profileImgType;
	}

	@SuppressWarnings("Duplicates")
	private Triplet<Image, byte[], Integer> receiveImg() throws IOException {
		byte[] sizeAr = new byte[4];
		byte [] msgAr = null;
		int size = 0;
		if(inputStream.read(sizeAr) != -1){
			size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
			msgAr = new byte[size];
			int len;
			int pos = 0;
			while(true){
				len = inputStream.read(msgAr,pos,size - pos);
				if(len != 0) pos += len;
				else {
					pos += len;
					break;
				}
			}
			return new Triplet<>(new Image(new ByteArrayInputStream(msgAr)), msgAr, size);
		}
		return null;
	}

	@SuppressWarnings("Duplicates")
	private Triplet<File, byte[], Integer> receiveFile() throws IOException {
		byte[] sizeAr = new byte[4];
		byte [] msgAr = null;
		int size = 0;
		if(inputStream.read(sizeAr) != -1){
			size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
			msgAr = new byte[size];
			int len;
			int pos = 0;
			while(true){
				len = inputStream.read(msgAr,pos,size - pos);
				if(len != 0) pos += len;
				else {
					pos += len;
					break;
				}
			}
			File file = new File("user/temp/other.ser");
			FileUtils.writeByteArrayToFile(file, msgAr);
			return new Triplet<>(file, msgAr, size);
		}
		return null;
	}


	@SuppressWarnings("Duplicates")
	private void sendFile(String filepath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filepath);
		byte [] fileByte = fileInputStream.readAllBytes();
		byte[] sizeAr = ByteBuffer.allocate(4).putInt(fileByte.length).array();
		System.out.println("File Send:" + fileByte.length);
		outputStream.write(sizeAr);
		outputStream.write(fileByte);
	}



}
