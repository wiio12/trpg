package indi.wiio;

import indi.wiio.info.Others;
import indi.wiio.info.Self;
import indi.wiio.main.KpMainWindowPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import indi.wiio.network.client.ClientMain;
import indi.wiio.network.server.ServerMain;
import indi.wiio.start.CreateRoomPane;
import indi.wiio.start.StartPane;

import java.io.IOException;


public class App extends Application {
    private FXMLLoader fxmlLoader;
    private Parent root;
    private static KpMainWindowPane kpMainWindowPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Self.getSelf().setName("");
        ClientMain.getClient().addOthersMessageListener((userName, filePath) -> {
            Others.readFromFile(userName, filePath);
            if(ClientMain.isKeeper() && kpMainWindowPane != null){
                Platform.runLater(()-> kpMainWindowPane.refreshPlayerTab());
            }
        });
        ClientMain.getClient().addOthersRequestListener(user -> {
           // boolean isWrited = Others.writeMySelfToFile();
            try {
                ClientMain.getClient().sendSelf();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



//        StackPane pane = new StackPane();
//        Scene scene = new Scene(pane, 600,800);
//        primaryStage.setScene(scene);
//        VBox box = new RichText().initialize(primaryStage);
//        pane.getChildren().add(box);
//        primaryStage.setTitle("Hello World");
//
//        primaryStage.show();


        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/start/start_pane.fxml"));
        root = fxmlLoader.load();

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/start/create_room_pane.fxml"));   //提前load，让监听器设置好
        Parent parent = fxmlLoader2.load();
        CreateRoomPane createRoomPane = fxmlLoader2.getController();

        StartPane startPane = fxmlLoader.getController();
        startPane.setParentStage(primaryStage);
        startPane.setCreateRoomPaneParentPair(new Pair<>(createRoomPane,parent));
        Scene scene = new Scene(root);
        startPane.setScene(scene);

        primaryStage.setOnCloseRequest(windowEvent -> {
            System.out.println("they quit!");
            if(ClientMain.isLogin()){
                try {
                    ClientMain.getClient().logout();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();






//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/main_window_pane.fxml"));
//        Parent parent = fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(parent));
//        stage.show();

//
//
//
//        //TODO:前后台控制分离！！！！
        // 这里直接调用了controller里面的handleSendL。
//        ClientMain.getClient().addMsgListener(this::handleMsg);
//        handleMsg("#COMMON@ben", "23333");

        //

    }

//    private void handleMsg(String fromUser , String msg) {
//        mainChatPane = fxmlLoader.getController();
//        Platform.runLater(()-> mainChatPane.handleSendL(fromUser, msg));
//
//    }


    public static void main(String[] args) {
        ServerMain.startServer(8818);    // 当作为客户端时不启用，作为房主的时候启动该服务器
        ClientMain.startClient();


        launch(args);
    }

    public static KpMainWindowPane getKpMainWindowPane() {
        return kpMainWindowPane;
    }

    public static void setKpMainWindowPane(KpMainWindowPane kpMainWindowPane) {
        App.kpMainWindowPane = kpMainWindowPane;
    }
}
