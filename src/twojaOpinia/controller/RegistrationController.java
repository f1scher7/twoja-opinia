package twojaOpinia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static twojaOpinia.util.JavaFXMethods.centerStage;
import static twojaOpinia.util.SaltUtil.generateSalt;

public class RegistrationController {
    private UserDao userDao = new UserDao();
    private int mainInc = 0;

    @FXML
    private VBox mainVBox;
    @FXML
    private ImageView logoImage;
    @FXML
    private Label infoLabel;

    private Label emptyLabel = new Label();

    private HBox hBoxName = new HBox();
    private HBox hBoxSurname = new HBox();
    private VBox vBoxName = new VBox();
    private VBox vBoxSurname = new VBox();
    private Label nameLabel = new Label("Imię");
    private TextField nameField = new TextField();
    private Label errorNameLabel = new Label("Pole 'Imię' nie może być puste");
    private Label surnameLabel = new Label("Nazwisko");
    private TextField surnameField = new TextField();
    private Label errorSurnameLabel = new Label("Pole 'Nazwisko' nie może być puste");


    private HBox hBoxCountry = new HBox();
    private HBox hBoxCity = new HBox();
    private VBox vBoxCountry = new VBox();
    private VBox vBoxCity = new VBox();
    private Label countryLabel = new Label("Kraj");
    private TextField countryField = new TextField();
    private Label errorCountryLabel = new Label("Pole 'Kraj' nie może być puste");
    private Label cityLabel = new Label("Miasto");
    private TextField cityField = new TextField();
    private Label errorCityLabel = new Label("Pole 'Miasto' nie może być puste");


    private HBox hBoxEmail = new HBox();
    private HBox hBoxLogin = new HBox();
    private HBox hBoxPassword = new HBox();
    private VBox vBoxEmail = new VBox();
    private VBox vBoxLogin = new VBox();
    private VBox vBoxPassword = new VBox();
    private Label emailLabel = new Label("Mail");
    private TextField emailField = new TextField();
    private Label emailErrorLabel = new Label("Pole 'Mail' nie może być puste");
    private Label loginLabel = new Label("Login");
    private TextField loginField = new TextField();
    private Label loginErrorLabel = new Label("Pole 'Mail' nie może być puste");
    private Label passwordLabel = new Label("Hasło");
    private TextField passwordField = new TextField();
    private Label passwordErrorLabel = new Label("Pole 'Hasło' nie może być puste");


    private DatePicker userBirthdayDate = new DatePicker();
    private Label errorDateLabel = new Label("Podaj datę urodzenia");
    private ChoiceBox<String> sexChoiceBox = new ChoiceBox<>();
    private Label errorSexLabel = new Label("Podaj płeć");


    private HBox hBoxButtons = new HBox();
    private Button nextButton = new Button();
    private Region region = new Region();
    private Button backButton = new Button();

    @FXML
    public void initialize() {
        nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;");

        backButton.setOnMouseEntered(e -> {backButton.setCursor(Cursor.HAND); backButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);");});
        backButton.setOnMouseExited(e -> {backButton.setCursor(Cursor.DEFAULT); backButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7;-fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;");});

        nextButton.setOnMouseEntered(e -> {nextButton.setCursor(Cursor.HAND); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);");});
        nextButton.setOnMouseExited(e -> {nextButton.setCursor(Cursor.DEFAULT); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7;-fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;");});

        sexChoiceBox.setOnMouseEntered(e -> sexChoiceBox.setCursor(Cursor.HAND));
        sexChoiceBox.setOnMouseExited(e -> sexChoiceBox.setCursor(Cursor.DEFAULT));

        Image image = new Image("file:src/twojaOpinia/iconTwojaOpinia.png");
        logoImage.setImage(image);
        logoImage.setFitHeight(50);
        logoImage.setFitWidth(50);

        backButton.getStyleClass().add("button6");
        backButton.setText("Powrót");

        region.setStyle("-fx-pref-width: 150");

        nextButton.getStyleClass().add("button6");
        nextButton.setText("Dalej");

        hBoxButtons.setAlignment(Pos.CENTER);

        hBoxButtons.getChildren().addAll(backButton, region, nextButton);

        errorNameLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        errorNameLabel.setVisible(false);

        errorSurnameLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        errorSurnameLabel.setVisible(false);

        errorDateLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        errorDateLabel.setVisible(false);

        errorSexLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        errorSexLabel.setVisible(false);

        errorCountryLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        errorCountryLabel.setVisible(false);

        errorCityLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        errorCityLabel.setVisible(false);

        emailErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        emailErrorLabel.setVisible(false);

        loginErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        loginErrorLabel.setVisible(false);

        passwordErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13");
        passwordErrorLabel.setVisible(false);

        sexChoiceBox.setValue("Płeć");
        sexChoiceBox.getItems().addAll("M", "K");
        sexChoiceBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 7; -fx-border-width: 2; -fx-border-color: #bebebe; -fx-border-radius: 7; -fx-pref-width: 200; -fx-pref-height: 40; -fx-font-size: 15");

        vBoxName.getChildren().addAll(nameLabel, nameField, errorNameLabel);
        hBoxName.getChildren().add(vBoxName);

        vBoxSurname.getChildren().addAll(surnameLabel, surnameField, errorSurnameLabel);
        hBoxSurname.getChildren().add(vBoxSurname);

        vBoxCountry.getChildren().addAll(countryLabel, countryField, errorCountryLabel);
        hBoxCountry.getChildren().add(vBoxCountry);

        vBoxCity.getChildren().addAll(cityLabel, cityField, errorCityLabel);
        hBoxCity.getChildren().add(vBoxCity);

        vBoxEmail.getChildren().addAll(emailLabel, emailField, emailErrorLabel);
        hBoxEmail.getChildren().add(vBoxEmail);

        vBoxLogin.getChildren().addAll(loginLabel, loginField, loginErrorLabel);
        hBoxLogin.getChildren().add(vBoxLogin);

        vBoxPassword.getChildren().addAll(passwordLabel, passwordField, passwordErrorLabel);
        hBoxPassword.getChildren().add(vBoxPassword);

        changeNodesForReg();

        nextButton.setOnMouseClicked(e -> {
            if (mainInc == 0) {
                if (nameField.getText().isEmpty()) {
                    errorNameLabel.setVisible(true);
                    errorSurnameLabel.setVisible(false);
                    nameField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                } else if (surnameField.getText().isEmpty()) {
                    surnameField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    errorSurnameLabel.setVisible(true);
                    errorNameLabel.setVisible(false);
                } else {
                    nameField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15;");
                    errorNameLabel.setVisible(false);
                    errorSurnameLabel.setVisible(false);
                    mainInc++;
                    if (mainInc == 1) {
                        mainVBox.getChildren().removeAll(hBoxName, hBoxSurname, emptyLabel, hBoxButtons);
                    } else if (mainInc == 2) {
                        mainVBox.getChildren().removeAll(userBirthdayDate, errorDateLabel, sexChoiceBox, errorSexLabel, emptyLabel, hBoxButtons);
                    } else if (mainInc == 3) {
                        mainVBox.getChildren().removeAll(hBoxCountry, hBoxCity, emptyLabel, hBoxButtons);
                    }
                    changeNodesForReg();
                }
            } else if (mainInc == 1) {
                if (userBirthdayDate.getValue() == null) {
                    errorDateLabel.setVisible(true);
                    errorSexLabel.setVisible(false);
                } else if (sexChoiceBox.getValue().equals("Płeć")) {
                    sexChoiceBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 7; -fx-border-width: 2; -fx-border-color: #bebebe; -fx-border-radius: 7; -fx-pref-width: 200; -fx-pref-height: 40; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    errorSexLabel.setVisible(true);
                    errorDateLabel.setVisible(false);
                } else {
                    sexChoiceBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 7; -fx-border-width: 2; -fx-border-color: #bebebe; -fx-border-radius: 7; -fx-pref-width: 200; -fx-pref-height: 40; -fx-font-size: 15");
                    errorDateLabel.setVisible(false);
                    errorSexLabel.setVisible(false);
                    mainInc++;
                    if (mainInc == 1) {
                        mainVBox.getChildren().removeAll(hBoxName, hBoxSurname, emptyLabel, hBoxButtons);
                    } else if (mainInc == 2) {
                        mainVBox.getChildren().removeAll(userBirthdayDate, errorDateLabel, sexChoiceBox, errorSexLabel, emptyLabel, hBoxButtons);
                    } else if (mainInc == 3) {
                        mainVBox.getChildren().removeAll(hBoxCountry, hBoxCity, emptyLabel, hBoxButtons);
                    }
                    changeNodesForReg();
                }
            } else if (mainInc == 2) {
                if (countryField.getText().isEmpty()) {
                    countryField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    errorCountryLabel.setVisible(true);
                    errorCityLabel.setVisible(false);
                } else if (cityField.getText().isEmpty()) {
                    cityField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    errorCityLabel.setVisible(true);
                    errorCountryLabel.setVisible(false);
                } else {
                    cityField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15;");
                    countryField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15;");

                    errorCountryLabel.setVisible(false);
                    errorCityLabel.setVisible(false);
                    mainInc++;
                    if (mainInc == 1) {
                        mainVBox.getChildren().removeAll(hBoxName, hBoxSurname, emptyLabel, hBoxButtons);
                    } else if (mainInc == 2) {
                        mainVBox.getChildren().removeAll(userBirthdayDate, errorDateLabel, sexChoiceBox, errorSexLabel, emptyLabel, hBoxButtons);
                    } else if (mainInc == 3) {
                        mainVBox.getChildren().removeAll(hBoxCountry, hBoxCity, emptyLabel, hBoxButtons);
                    }
                    changeNodesForReg();
                }
            } else if (mainInc == 3) {
                if (emailField.getText().isEmpty()) {
                    emailField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    emailErrorLabel.setText("Pole 'Mail' nie może być puste");
                    emailErrorLabel.setVisible(true);
                    loginErrorLabel.setVisible(false);
                    passwordErrorLabel.setVisible(false);
                } else if (userDao.isUserDataTaken("email", emailField.getText())) {
                    emailField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    emailErrorLabel.setText("Mail '" + emailField.getText() + "' jest już zajęty");
                    emailErrorLabel.setVisible(true);
                    loginErrorLabel.setVisible(false);
                    passwordErrorLabel.setVisible(false);
                } else if (loginField.getText().isEmpty()) {
                    loginField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    loginErrorLabel.setText("Pole 'Login' nie może być puste");
                    loginErrorLabel.setVisible(true);
                    emailErrorLabel.setVisible(false);
                    passwordErrorLabel.setVisible(false);
                } else if (userDao.isUserDataTaken("login", loginField.getText())) {
                    loginField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    loginErrorLabel.setText("Login '" + loginField.getText() + "' jest już zajęty");
                    loginErrorLabel.setVisible(true);
                    emailErrorLabel.setVisible(false);
                    passwordErrorLabel.setVisible(false);
                }  else if (passwordField.getText().isEmpty()) {
                    passwordField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    passwordErrorLabel.setText("Pole 'Hasło' nie może być puste");
                    passwordErrorLabel.setVisible(true);
                    emailErrorLabel.setVisible(false);
                    loginErrorLabel.setVisible(false);
                } else if (!checkCorrectionPassword(passwordField.getText())) {
                    passwordField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15; -fx-border-color: red; -fx-border-radius: 5; -fx-border-width: 2");
                    passwordErrorLabel.setText("Hasło ma zawierać min. 8 znaków, co najmniej jedną literę lub cyfrę");
                    passwordErrorLabel.setVisible(true);
                    emailErrorLabel.setVisible(false);
                    loginErrorLabel.setVisible(false);
                } else {
                    emailField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15;");
                    loginField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15;");
                    passwordField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15;");

                    emailErrorLabel.setVisible(false);
                    loginErrorLabel.setVisible(false);
                    passwordErrorLabel.setVisible(false);
                    mainInc++;
                    if (mainInc == 1) {
                        mainVBox.getChildren().removeAll(hBoxName, hBoxSurname, emptyLabel, hBoxButtons);
                    } else if (mainInc == 2) {
                        mainVBox.getChildren().removeAll(userBirthdayDate, errorDateLabel, sexChoiceBox, errorSexLabel, emptyLabel, hBoxButtons);
                    } else if (mainInc == 3) {
                        mainVBox.getChildren().removeAll(hBoxCountry, hBoxCity, emptyLabel, hBoxButtons);
                    }
                    changeNodesForReg();

                    User user = new User();
                    user.setName(nameField.getText());
                    user.setSurname(surnameField.getText());
                    user.setSex(sexChoiceBox.getValue());

                    LocalDate date = userBirthdayDate.getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String birthday = date.format(formatter);

                    user.setBirthday(birthday);
                    user.setCountry(countryField.getText());
                    user.setCity(cityField.getText());
                    user.setEmail(emailField.getText());
                    user.setLogin(loginField.getText());
                    user.setPassword(passwordField.getText());
                    user.setSalt(generateSalt());
                    user.setAdmin(false);

                    userDao.insert(user);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("TwojaOpinia");
                    alert.setHeaderText(null);
                    alert.getDialogPane().setPrefHeight(250);
                    alert.getDialogPane().setPrefWidth(350);
                    Image gif = new Image(getClass().getResourceAsStream("/twojaOpinia/view/registration/img/tenor.gif"));
                    ImageView imageView = new ImageView(gif);

                    Label thankYouLabel = new Label("Dziękujemy, że dołączyłeś do nas!");
                    thankYouLabel.setTextFill(Color.WHITE);
                    thankYouLabel.setAlignment(Pos.CENTER);
                    thankYouLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13; -fx-padding: 250 50 0 0; -fx-pref-width: 550");

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(thankYouLabel);

                    alert.getDialogPane().setContent(stackPane);

                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/registration/registrationStyles.css")).toExternalForm());

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                    stage.centerOnScreen();

                    alert.showAndWait();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/LoginView.fxml")));
                        Parent login = fxmlLoader.load();

                        Scene scene = new Scene(login, 400, 420);
                        Stage stage1 = (Stage) nextButton.getScene().getWindow();
                        stage1.setScene(scene);

                        centerStage(stage1);
                    } catch (IOException q) {
                        q.printStackTrace();
                        System.err.println("Błąd podczas ładowania pliku FXML: " + q.getMessage());
                    }
                }
            }
        });

        backButton.setOnMouseClicked(e -> {
            mainInc--;
            if (mainInc == 0) {
                mainVBox.getChildren().removeAll(userBirthdayDate, errorDateLabel, sexChoiceBox, errorSexLabel, emptyLabel, hBoxButtons);
            } else if (mainInc == 1) {
                mainVBox.getChildren().removeAll(hBoxCountry, hBoxCity, emptyLabel, hBoxButtons);
            } else if (mainInc == 2) {
                mainVBox.getChildren().removeAll(hBoxEmail, hBoxLogin, hBoxPassword, emptyLabel, hBoxButtons);
            }
            changeNodesForReg();
        });
    }

    private boolean checkCorrectionPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasLower = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (hasLower && hasDigit) {
                return true;
            }
        }
        return false;
    }

    private void changeNodesForReg() {
        if (mainInc == 0) {
            nextButton.getStyleClass().removeAll();
            nextButton.setText("Dalej");
            nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-pref-width: 100;");
            nextButton.setOnMouseEntered(e -> {nextButton.setCursor(Cursor.HAND); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0); -fx-pref-width: 100;");});
            nextButton.setOnMouseExited(e -> {nextButton.setCursor(Cursor.DEFAULT); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7;-fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-pref-width: 100;");});

            infoLabel.setText("Wpisz swoje imię i nazwisko");
            backButton.setVisible(false);
            backButton.setDisable(true);

            hBoxName.setAlignment(Pos.CENTER);

            hBoxSurname.setAlignment(Pos.CENTER);

            nameLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            nameField.setPromptText("Imię");
            nameField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15");

            surnameLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            surnameField.setPromptText("Nazwisko");
            surnameField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15");

            emptyLabel.setStyle("-fx-font-size: 30");
            mainVBox.getChildren().addAll(hBoxName, hBoxSurname, emptyLabel, hBoxButtons);
        } else if (mainInc == 1) {
            nextButton.getStyleClass().removeAll();
            nextButton.setText("Dalej");
            nextButton.setPrefWidth(100);
            nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-pref-width: 100;");
            nextButton.setOnMouseEntered(e -> {nextButton.setCursor(Cursor.HAND); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0); -fx-pref-width: 100;");});
            nextButton.setOnMouseExited(e -> {nextButton.setCursor(Cursor.DEFAULT); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7;-fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-pref-width: 100;");});

            backButton.setVisible(true);
            backButton.setDisable(false);

            infoLabel.setText("Wpisz datę urodzenia i płeć");

            userBirthdayDate.setPromptText("Data urodzenia");
            userBirthdayDate.setPrefHeight(45);
            userBirthdayDate.setPrefWidth(250);
            userBirthdayDate.setStyle("-fx-background-radius: 3; -fx-background-color: white; -fx-font-size: 15");

            emptyLabel.setStyle("-fx-font-size: 46");
            mainVBox.getChildren().addAll(userBirthdayDate, errorDateLabel, sexChoiceBox, errorSexLabel, emptyLabel, hBoxButtons);
        } else if (mainInc == 2) {
            nextButton.getStyleClass().removeAll();
            nextButton.setText("Dalej");
            nextButton.setPrefWidth(100);
            nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-pref-width: 100;");
            nextButton.setOnMouseEntered(e -> {nextButton.setCursor(Cursor.HAND); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0); -fx-pref-width: 100;");});
            nextButton.setOnMouseExited(e -> {nextButton.setCursor(Cursor.DEFAULT); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7;-fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-pref-width: 100;");});

            infoLabel.setText("Wpisz skąd jesteś");

            backButton.setVisible(true);
            backButton.setDisable(false);

            hBoxCountry.setAlignment(Pos.CENTER);
            hBoxCity.setAlignment(Pos.CENTER);

            countryLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            countryField.setPromptText("Kraj");
            countryField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15");

            cityLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            cityField.setPromptText("Miasto");
            cityField.setStyle("-fx-pref-width: 320; -fx-pref-height: 45; -fx-background-radius: 6; -fx-font-size: 15");

            emptyLabel.setStyle("-fx-font-size: 30");
            mainVBox.getChildren().addAll(hBoxCountry, hBoxCity, emptyLabel, hBoxButtons);
        } else if (mainInc == 3) {
            infoLabel.setText("Wpisz mail, login i hasło dla zakończenia rejestracji");

            backButton.setVisible(true);
            backButton.setDisable(false);

            nextButton.setText("Zakończ rejestracje");
            nextButton.getStyleClass().removeAll();
            nextButton.setPrefWidth(100);
            nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #4cf84e; -fx-text-fill: #FFFFFF; -fx-pref-width: 160;");
            nextButton.setOnMouseEntered(e -> {nextButton.setCursor(Cursor.HAND); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7; -fx-background-color: #4cf84e; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0); -fx-pref-width: 160;");});
            nextButton.setOnMouseExited(e -> {nextButton.setCursor(Cursor.DEFAULT); nextButton.setStyle("-fx-font-size: 14px; -fx-background-radius: 7;-fx-background-color: #4cf84e; -fx-text-fill: #FFFFFF; -fx-pref-width: 160;");});
            hBoxEmail.setAlignment(Pos.CENTER);
            hBoxLogin.setAlignment(Pos.CENTER);
            hBoxPassword.setAlignment(Pos.CENTER);

            emailLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            emailField.setPromptText("Email");
            emailField.setStyle("-fx-pref-width: 320; -fx-pref-height: 40; -fx-background-radius: 6; -fx-font-size: 15");

            loginLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            loginField.setPromptText("Login");
            loginField.setStyle("-fx-pref-width: 320; -fx-pref-height: 40; -fx-background-radius: 6; -fx-font-size: 15");

            passwordLabel.setStyle("-fx-font-size: 13; -fx-text-fill: black");
            passwordField.setPromptText("Hasło");
            passwordField.setStyle("-fx-pref-width: 320; -fx-pref-height: 40; -fx-background-radius: 6; -fx-font-size: 15");

            emptyLabel.setStyle("-fx-font-size: 3");
            mainVBox.getChildren().addAll(hBoxEmail, hBoxLogin, hBoxPassword, emptyLabel, hBoxButtons);
        }
    }
}
