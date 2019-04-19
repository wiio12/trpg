package player.small;

import info.Others;
import info.character.Resources;
import info.character.Skill;
import info.character.SkillType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import player.PaneStatus;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SkillPane implements Initializable {

    //技能表部分
    public FlowPane skillFlowPane;

    private ArrayList<SkillTemplate> skillTemplates = new ArrayList<>();

    public PlayerStatisticsSmall getPlayerStatisticsSmall() {
        return playerStatisticsSmall;
    }

    public void setPlayerStatisticsSmall(PlayerStatisticsSmall playerStatisticsSmall) {
        this.playerStatisticsSmall = playerStatisticsSmall;
    }

    private PlayerStatisticsSmall playerStatisticsSmall;

    private PaneStatus paneStatus;
    private String kp_userName;

    //TODO:资源统一
    private ArrayList<Skill> skills;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            skills = Resources.getResources().getSkills();
        }
        Platform.runLater(() ->{
            try {
                initializeStatus();
                initializeSkill();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initializeStatus() {
        if(paneStatus == PaneStatus.KP){
            Others others = Others.getOthersMap().get(kp_userName);
            if(others != null){
                skills = others.getResources().getSkills();
            }else{
                System.out.println("kp:skills");
            }
        }else{
            skills = Resources.getResources().getSkills();
        }
    }

    private void initializeSkill() throws IOException {

        //generateSkillList();


        for(Skill s: skills){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("skill_template.fxml"));
            Parent pane = fxmlLoader.load();
            skillFlowPane.setPrefWrapLength(350);
            skillFlowPane.setVgap(5);
            skillFlowPane.setHgap(10);
            skillFlowPane.getChildren().add(pane);


            SkillTemplate skillTemplate = fxmlLoader.getController();
            skillTemplate.setAll(s.getName(), s.toString());
            skillTemplate.textField.textProperty().bind(s.totalProperty().asString());
            skillTemplate.textField.setEditable(false);
            skillTemplates.add(skillTemplate);


        }

    }

    public SkillTemplate getSkillTemplate(String name){
        for(SkillTemplate s: skillTemplates){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    public Skill getSkillType(String name){
        for(Skill  s: skills){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    public String getKp_userName() {
        return kp_userName;
    }

    public void setKp_userName(String kp_userName) {
        this.kp_userName = kp_userName;
    }

    public PaneStatus getPaneStatus() {
        return paneStatus;
    }

    public void setPaneStatus(PaneStatus paneStatus) {
        this.paneStatus = paneStatus;
    }
}
