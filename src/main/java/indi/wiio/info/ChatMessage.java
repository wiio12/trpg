package indi.wiio.info;

import java.util.Date;

public class ChatMessage {
    public enum MessageType {
        left,
        right,
        mid
    }
    private String topicName;
    private String userName;
    private String characterName;
    private String msgStr;
    private String msgPic;
    private Date date;
    private MessageType messageType;

    public ChatMessage(String topicName, String userName, String msgStr, String msgPic, Date date, MessageType messageType) {
        this.topicName = topicName;
        this.userName = userName;
        this.msgStr = msgStr;
        this.msgPic = msgPic;
        this.date = date;
        this.messageType = messageType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getMsgStr() {
        return msgStr;
    }

    public void setMsgStr(String msgStr) {
        this.msgStr = msgStr;
    }

    public String getMsgPic() {
        return msgPic;
    }

    public void setMsgPic(String msgPic) {
        this.msgPic = msgPic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
