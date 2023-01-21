package com.launcher.launcher.controller;

import com.launcher.launcher.model.entity.User;
import com.launcher.launcher.model.entity.UserGameDTO;
import com.launcher.launcher.service.GamesService;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.fonts.Fonts;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GamesController {

  private static final String INNER_BACKGROUND = "-fx-control-inner-background: ";

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
    listView.setFocusTraversable(false);
    listView.getSelectionModel().select(-1);
    listView.setCellFactory(param -> new ImageCell());
    listView.setStyle(INNER_BACKGROUND + "#212529" + ";");
  }

  private class ImageCell extends ListCell<UserGameDTO> {

    @Override
    public void updateItem(UserGameDTO gameDTO, boolean empty) {
      if (gameDTO != null) {
        super.updateItem(gameDTO, empty);
        FlowGridPane panel = new FlowGridPane(2, 2);
        Tile progressBar = TileBuilder.create()
            .skinType(Tile.SkinType.CIRCULAR_PROGRESS)
            .prefSize(20, 20)
            .backgroundColor(Color.rgb(33, 37, 41))
            .textVisible(false)
            .sectionsVisible(false)
            .trendVisible(false)
            .valueVisible(false)
            .thresholdVisible(false)
            .maxMeasuredValueVisible(false)
            .averageVisible(false)
            .tickLabelsXVisible(false)
            .scaleX(0.3)
            .scaleY(0.3)
            .build();
        progressBar.setVisible(false);
        progressBar.setCustomFont(Fonts.latoRegular(12.0));
        panel.setMinWidth(300);
        panel.setPadding(new Insets(15, 15, 15, 15));

        ImageView imageView = new ImageView(new javafx.scene.image.Image(
            new ByteArrayInputStream(gameDTO.getImage().getImageData())));
        imageView.setFitHeight(215);
        imageView.setFitWidth(460);
        panel.getChildren().add(imageView);
        Label title = new Label(gameDTO.game.gameProfile.title + "\n"
            + gameDTO.game.gameProfile.briefDescription);
        title.setPadding(new Insets(20));
        title.setTextAlignment(TextAlignment.LEFT);
        title.setWrapText(true);
        title.setMaxWidth(380);
        panel.getChildren().add(title);

        Button downloadButton = new Button();
        downloadButton.setText("Download");
        downloadButton.setPadding(new Insets(7));
        downloadButton.setStyle(INNER_BACKGROUND + "#11A13A" + ";");
        downloadButton.setOnMouseClicked(event -> {

          try {
            long bytesLength = gamesService.downloadGame(gameDTO.getGame(), progressBar);
            progressBar.setMaxValue(bytesLength);
            progressBar.setVisible(true);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
        panel.getChildren().add(downloadButton);
        panel.getChildren().add(progressBar);

        setGraphic(panel);
        setStyle("-fx-control-inner-background: " + "#212529" + ";");
      }
    }
  }
}
