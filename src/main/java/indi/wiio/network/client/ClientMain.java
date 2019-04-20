package indi.wiio.network.client;

import java.awt.SecondaryLoop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.security.auth.login.LoginContext;

public class ClientMain {
	private static Client client;
	private static boolean login;
	private static boolean player;
	private static boolean keeper;
	
	public static void startClient() {
		//TODO:修改为对应的服务器的地址
		client = new Client("localhost", 8818);
//		if(!client.connect()) {
//			System.err.println("connection failed");
//			return;
//		}
//		else
//			System.out.println("connection successed");

//		try {
//
//			//client.login(userName);
//			//client.joinTopic("#COMMON");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		try {
			
			
			
//			UserStatusListener userStatusListener = new UserStatusListener() {
//				@Override
//				public void online(String user) {
//					System.out.println("SYSTEM-MSG-ONLINE:" + user);
//				}
//
//				@Override
//				public void offline(String user) {
//					System.out.println("SYSTEM-MSG-OFFLINE:" + user);
//				}
//			};
//			client.addUserStatusLinstener(userStatusListener);
//
//			MsgListener msgListener = new MsgListener() {
//
//				@Override
//				public void onReceivedMessage(String fromUser, String msgBody) {
//					System.out.println(fromUser + ": " + msgBody);
//				}
//			};
//			client.addMsgListener(msgListener);
//
//			client.login("guest", "guest");
//
//
//			while(true) {
//				Scanner scanner = new Scanner(System.in);
//				String msg = scanner.nextLine();
//
//				client.msg("ben", msg);
//			}
////
//
//
//
//
//
//		} catch (IOException   e) {
//			e.printStackTrace();
//		}
		
	}

	public static Client getClient() {
		return client;
	}

	public static boolean isLogin() {
		return login;
	}

	public static void setLogin(boolean login) {
		ClientMain.login = login;
	}

	public static boolean isPlayer() {
		return player;
	}

	public static void setPlayer(boolean player) {
		ClientMain.player = player;
	}

	public static boolean isKeeper() {
		return keeper;
	}

	public static void setKeeper(boolean keeper) {
		ClientMain.keeper = keeper;
	}
}
