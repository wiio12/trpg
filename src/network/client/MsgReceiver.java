package network.client;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import utils.Triplet;
import utils.Univ;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;

public class MsgReceiver extends Thread{
	
	private InputStream inputStream;
	private Client client;

	public MsgReceiver(Client client, InputStream inputStream) {
		this.inputStream = inputStream;
		this.client = client;
	}
	
	@Override
	@SuppressWarnings("Duplicates")
	public void run() {
		try {
			byte[] sizeAr = new byte[4];   //建立四个字节读取长度
			while (inputStream.read(sizeAr) != -1) {  //读取，返回的是实际读取的字节数
				int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();  //建立一个读成整型
				byte[] msgAr = new byte[size];  //用size建立新的数组用来存储读取的东西
				int len = inputStream.read(msgAr);
				String line = new String(msgAr, 0, len);

				System.out.println("Client::" + line);

				String[] tokens = StringUtils.split(line);
				if (tokens != null && tokens.length > 0) {
					String messageType = tokens[0];
					if (MessageType.ONLINE_PROMPT_MESSAGE.toString().equalsIgnoreCase(messageType)) {
						handlerOnline(tokens);
					} else if (MessageType.OFFLINE_PROMPT_MESSAGE.toString().equalsIgnoreCase(messageType)) {
						handleOffline(tokens);
					} else if (MessageType.COMM_MESSAGE.toString().equalsIgnoreCase(messageType)) {
						String[] msgTokens = StringUtils.split(line, null, 3);
						handleMsg(msgTokens);
					} else if (MessageType.TOPIC_BE_CALL_MESSAGE.toString().equalsIgnoreCase(messageType)){
						handleTopicBeCall(tokens);
					} else if (MessageType.SYSTEM_INFO_MESSAGE.toString().equalsIgnoreCase(messageType)){
						String[] msgTokens = StringUtils.split(line, null, 2);
						handleSystemInfo(msgTokens);
					} else if (MessageType.SYSTEM_ERROR_MESSAGE.toString().equalsIgnoreCase(messageType)){
						String[] msgTokens = StringUtils.split(line, null, 2);
						handleSystemErrorMessage(msgTokens);
					} else if (MessageType.OTHERS_MESSAGE.toString().equalsIgnoreCase(messageType)){
						handleOthers(tokens);
					} else if (MessageType.REQUEST_OTHERS_MESSAGE.toString().equalsIgnoreCase(messageType)){
						handleRequestOthers(tokens);
					} else if (MessageType.SHOWCASE_IMAGE_MESSAGE.toString().equalsIgnoreCase(messageType)){
						handleShowCaseImage(tokens);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleShowCaseImage(String[] tokens) throws IOException {
		if(tokens.length != 4){
			handleError("handle_showcase_image");
			return;
		}

		String user = tokens[1];
		String imageName = tokens[2];
		String imageType = tokens[3];

		Triplet<Image, byte[], Integer> triplet = receiveImg();
		if(triplet != null){
			File file = new File("user/showcase/" + imageName + "-showcase."+imageType);
			ImageIO.write(SwingFXUtils.fromFXImage(triplet.getLeft(), null), imageType, file);

			for(ShowcaseImageListener s: client.getShowcaseImageListener()){
				s.onShowcaseImageCome(triplet.getLeft(), imageName, imageType);
			}

		}
	}

	private void handleRequestOthers(String[] tokens) {
		if(tokens.length != 2){
			handleError("handle_request_other_error");
			return;
		}

		String user = tokens[1];

		for(OthersRequestListener o: client.getOthersRequestListener()){
			o.onOthersRequested(user);
		}
	}

	private void handleOthers(String [] tokens) throws IOException {
		if(tokens.length != 2){
			handleError("handle_other_error");
			return;
		}

		String userName = tokens[1];

		Triplet<File, byte[], Integer> fileIntegerTriplet = receiveFile(userName);
		if(fileIntegerTriplet != null){
			for(OthersMessageListener o: client.getOthersMessageListeners()){
				o.onOthersMessage(userName, fileIntegerTriplet.getLeft().getAbsolutePath());
			}
		}else{
			handleError("file_receive_error");
		}


	}

	private void handleSystemErrorMessage(String[] tokens) {
		if(tokens.length != 2){
			handleError("handle_system_error");
			return;
		}
		String msg = tokens[1];
		List<SystemMessageListener> systemMessageListeners = client.getsystemMessageListeners();
		for(SystemMessageListener s: systemMessageListeners){
			s.onReceivedSystemError(msg);
		}
	}

	private void handleSystemInfo(String[] tokens) {
		if(tokens.length != 2){
			handleError("handle_system_info");
			return;
		}
		String msg = tokens[1];
		List<SystemMessageListener> systemMessageListeners = client.getsystemMessageListeners();
		for(SystemMessageListener s: systemMessageListeners){
			s.onReceivedSystemInfo(msg);
		}

	}

	private void handleTopicBeCall(String[] tokens) {
		if(tokens.length != 2){
			handleError("handle_topic_be_call");
			return;
		}
		String topicName = tokens[1];
		List<TopicListener> topicListeners = client.getTopicListeners();
		for(TopicListener t: topicListeners){
			t.onTopicBeCall(topicName);
		}
	}



	private void handleMsg(String[] tokens) {
		String user = tokens[1];
		String msgBody = tokens[2];
		List<MsgListener> msgListeners = client.getMsgListeners();
		for(MsgListener m : msgListeners) {
			m.onReceivedMessage(user, msgBody);
		}
	}

	private void handleOffline(String[] tokens) {
		String user = tokens[1];
		List<UserStatusListener> statusListeners = this.client.getUserStatusListeners();
		for( UserStatusListener u : statusListeners) {
			u.onUserOffline(user);
		}
	}

	private void handlerOnline(String[] tokens) throws IOException {
		if(tokens.length != 3){
			handleError("handel_online");
			return;
		}

		String user = tokens[1];
		String imgType = tokens[2];

		Triplet<Image, byte[],Integer> triplet = receiveImg();
		if(triplet != null){
			File file = new File("./user/head/" + user + "-pic." + imgType);
			ImageIO.write(SwingFXUtils.fromFXImage(triplet.getLeft(), null), imgType, file);

			List<UserStatusListener> statusListeners = this.client.getUserStatusListeners();
			for( UserStatusListener u : statusListeners) {
				u.onUserOnline(user, triplet.getLeft());
			}
		}else{
			System.err.println("Receive Image ERROR");
		}
	}


	private void handleError(String where) {
		Univ.printErrMessage("Error " + where);
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
	private Triplet<File, byte[], Integer> receiveFile(String userName) throws IOException {
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
			File file = new File("user/temp/"+userName+"-others.ser");
			FileUtils.writeByteArrayToFile(file, msgAr);
			return new Triplet<>(file, msgAr, size);
		}
		return null;
	}
	
}
