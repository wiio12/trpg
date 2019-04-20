package indi.wiio.network.server;

import java.util.ArrayList;
import java.util.List;


public class Topic {
	private String topicName;
	private ArrayList<ServerWorker> memberList = new ArrayList<ServerWorker>();
	private Server server;
	
	public Topic(Server server, String topicName) {
		this.topicName = topicName;
		this.server = server;
	}
	
	public void join(ServerWorker serverWorker) {
		if(!memberList.contains(serverWorker))
			memberList.add(serverWorker);
	}
	
	public boolean memberContains(ServerWorker serverWorker) {
		return memberList.contains(serverWorker);
	}
	
	public void remove(ServerWorker serverWorker) {
		if(memberList.contains(serverWorker))
			memberList.remove(serverWorker);
	}

	public List<ServerWorker> getMemberList() {
		return memberList;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	
}
