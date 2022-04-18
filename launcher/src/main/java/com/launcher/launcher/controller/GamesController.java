package com.launcher.launcher.controller;

import com.launcher.launcher.model.entity.User;
import com.launcher.launcher.model.entity.UserGameDTO;
import com.launcher.launcher.service.GamesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class GamesController {

    private final GamesService gamesService;
    private User user;

    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<UserGameDTO> listView;

    public GamesController() {
        this.gamesService = new GamesService();
    }

    void initData(User user) throws IOException {
        this.user = user;
        usernameLabel.setText(user.getPublicUsername());
        getGames();
    }

    private User getUser() {
        return user;
    }

    @FXML
    protected void getGames() throws IOException {
        usernameLabel = new Label();
        usernameLabel.setLabelFor(new Text(getUser().getPublicUsername()));

        List<UserGameDTO> userGames = gamesService.getGames(getUser().getId().toString());
        ObservableList<UserGameDTO> gamesList = FXCollections.observableArrayList();
        gamesList.addAll(userGames);
        listView.setItems(gamesList);
        listView.setCellFactory(param -> new ImageCell());
        listView.setStyle("-fx-control-inner-background: " + "#212529" + ";");
    }

    private class ImageCell extends ListCell<UserGameDTO> {
        @Override
        public void updateItem(UserGameDTO gameDTO, boolean empty) {
            if (gameDTO != null) {
                super.updateItem(gameDTO, empty);
                FlowPane flowPane = new FlowPane();

                javafx.scene.image.Image image = new javafx.scene.image.Image(
                    new ByteArrayInputStream(gameDTO.getImage().getImageData()));
                flowPane.getChildren().add(new ImageView(image));
                flowPane.getChildren().add(new Label(gameDTO.game.gameProfile.title));
                Button downloadButton = new Button();
                downloadButton.setText("Download");
                downloadButton.setScaleX(1.5);
                downloadButton.setScaleY(1.2);
                downloadButton.setStyle("-fx-control-inner-background: " + "#11A13A" + ";");
                downloadButton.setOnMouseClicked(event -> {
                    try {
                        gamesService.downloadGame(gameDTO.getGame());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                flowPane.getChildren().add(downloadButton);
                setGraphic(flowPane);
                setStyle("-fx-control-inner-background: " + "#212529" + ";");
            }
        }
    }
}
