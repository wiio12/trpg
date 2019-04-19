package network.client;

public enum MessageType {
    //通常来说，MESSAGE由服务器发送，客户端收到，不会被客户端发送
    //COMMAND会由客户端发送，服务器收到，不会被服务器发送

    COMM_COMMAND,  // <sendTo> <message body>
    COMM_MESSAGE,  // <sendTo / topicName@user> <message body>
    IMG_COMMAND,   // <sendTo@chat / system / show> <image byte>
    IMG_MESSAGE,  // <sendTo@chat / system / show> <image byte>

    SHOWCASE_IMAGE_COMMAND, // <user> <imageName> <imageType> ->  <image byte>
    SHOWCASE_IMAGE_MESSAGE, // <user> <imageName> <imageType> ->  <image byte>

    OTHERS_COMMAND,  // <user>  -> <myself byte>
    OTHERS_MESSAGE,  // <user>  -> <other byte>

    REQUEST_OTHERS_COMMAND,    // <userName>
    REQUEST_OTHERS_MESSAGE,    // <userName>


    ERROR_MESSAGE,  // <message body>

    ONLINE_PROMPT_MESSAGE,   // <online User's Name> <imgType> -> <image byte>
    OFFLINE_PROMPT_MESSAGE,  // <offline user's name>
    LOGIN_COMMAND,      // <userName> <imgType>  -> <image byte>
    LOGOUT_COMMAND,     // empty


    TOPIC_JOIN_COMMAND,    // <topic Name>
    TOPIC_LEAVE_COMMAND,   // <topic Name>
    TOPIC_CALL_COMMAND,     // <topic Name> <user Name>
    TOPIC_BE_CALL_MESSAGE,  // <topic Name>


    SYSTEM_INFO_MESSAGE,    // <message Body>
    SYSTEM_ERROR_MESSAGE,   // <message Body>
    SYSTEM_QUIT_COMMAND,   // empty


}
