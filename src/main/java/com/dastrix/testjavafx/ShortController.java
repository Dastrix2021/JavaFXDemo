package com.dastrix.testjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortController {
    @FXML
    private TextField poln_ss;

    @FXML
    private TextField sokr_ss;

    @FXML
    private Button btn_add;

    @FXML
    private Label lbl;

    @FXML
    private VBox vbox = null;

    private DB db = new DB();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        ResultSet res = db.getShorts();
        Node node = null;

        while(res.next()) {

            try {
                node = FXMLLoader.load(getClass().getResource("/com/dastrix/testjavafx/short-url.fxml"));
                Label sokr_ss = (Label) node.lookup("#sokr_ss");
                sokr_ss.setText(res.getString("sokr_ss"));

            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            vbox.getChildren().add(hBox);
            vbox.setSpacing(10);
        }

        btn_add.setOnAction(event -> {

            try {
                boolean isShort = db.addUrl(poln_ss.getCharacters().toString(), sokr_ss.getCharacters().toString());
                if(isShort) {

                    lbl.setText("Готово!");
                    poln_ss.setText("");
                    sokr_ss.setText("");

                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/com/dastrix/testjavafx/short.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setTitle("Великий сокращатель!");
                    primaryStage.setScene(new Scene(root, 600, 285));
                    primaryStage.show();

                } else
                    lbl.setText("Используйте другое сокращение!");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

    }

}