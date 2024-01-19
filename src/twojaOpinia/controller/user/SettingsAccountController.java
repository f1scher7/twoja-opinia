package twojaOpinia.controller.user;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;

import java.io.IOException;
import java.util.Objects;
import twojaOpinia.util.SHA256;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class SettingsAccountController {
    private User user;
    private UserDao userDao = new UserDao();
    private String userLogin;

    @FXML
    private VBox mainVBoxHistorySurveys;
    @FXML
    private TextField UserLoginField;
    @FXML
    private Button changeLoginButton;
    @FXML
    private VBox loginErrorVBox;
    @FXML
    private Label loginError;

    @FXML
    private TextField UserNameField;
    @FXML
    private Button changeNameButton;

    @FXML
    private TextField UserSurnameField;
    @FXML
    private Button changeSurnameButton;

    @FXML
    private TextField UserEmailField;
    @FXML
    private Button changeEmailButton;
    @FXML
    private VBox emailErrorVBox;
    @FXML
    private Label emailError;

    @FXML
    private TextField UserCountryField;
    @FXML
    private Button changeCountryButton;

    @FXML
    private TextField UserCityField;
    @FXML
    private Button changeCityButton;

    @FXML
    private TextField UserNewPasswordField;
    @FXML
    private TextField UserOldPasswordField;
    @FXML
    private VBox oldPasswordErrorVBox;
    @FXML
    private Label oldPasswordError;
    @FXML
    private Button changePasswordButton;
    @FXML
    private VBox newPasswordErrorVBox;
    @FXML
    private Label newPasswordError;

    @FXML
    private Label twojaOpiniaLabel;
    @FXML
    private Circle userAvatar;
    @FXML
    private Label userLoginLabel;
    @FXML
    private Button availableSurveysButtonMenu;
    @FXML
    private Button surveysHistoryButtonMenu;
    @FXML
    private Button accountSettingsButtonMenu;
    @FXML
    private Button backToDashboardButtonMenu;
    @FXML
    private Button logoutButtonMenu;

    public SettingsAccountController(String login) {
        this.userLogin = login;
        this.user = userDao.getUserDataByLogin(this.userLogin);

    }

    @FXML
    public void initialize () {
        userLoginLabel.setText(this.userLogin);

        availableSurveysButtonMenu.setOnMouseEntered(e -> availableSurveysButtonMenu.setCursor(Cursor.HAND));
        availableSurveysButtonMenu.setOnMouseExited(e -> availableSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        surveysHistoryButtonMenu.setOnMouseEntered(e -> surveysHistoryButtonMenu.setCursor(Cursor.HAND));
        surveysHistoryButtonMenu.setOnMouseExited(e -> surveysHistoryButtonMenu.setCursor(Cursor.DEFAULT));

        backToDashboardButtonMenu.setOnMouseEntered(e -> backToDashboardButtonMenu.setCursor(Cursor.HAND));
        backToDashboardButtonMenu.setOnMouseExited(e -> backToDashboardButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));

        animateElementsSequentially(mainVBoxHistorySurveys);

        loginError.setManaged(false);
        loginError.setVisible(false);
        loginErrorVBox.setManaged(false);

        emailError.setManaged(false);
        emailError.setVisible(false);
        emailErrorVBox.setManaged(false);

        oldPasswordErrorVBox.setManaged(false);
        oldPasswordError.setManaged(false);
        oldPasswordError.setVisible(false);
        newPasswordErrorVBox.setManaged(false);
        newPasswordError.setManaged(false);
        newPasswordError.setVisible(false);

        changeLoginButton.setDisable(true);
        changeNameButton.setDisable(true);
        changeSurnameButton.setDisable(true);
        changeEmailButton.setDisable(true);
        changeCountryButton.setDisable(true);
        changeCityButton.setDisable(true);

        UserLoginField.setText(this.user.getLogin());
        UserNameField.setText(this.user.getName());
        UserSurnameField.setText(this.user.getSurname());
        UserEmailField.setText(this.user.getEmail());
        UserCountryField.setText(this.user.getCountry());
        UserCityField.setText(this.user.getCity());

        UserLoginField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isLoginSame = this.user.getLogin().equals(UserLoginField.getText());
            boolean isLoginTaken = userDao.isUserDataTaken("login", UserLoginField.getText());

            changeLoginButton.setDisable(isLoginSame || isLoginTaken);
            loginError.setText("Użytkownik o loginie '" + UserLoginField.getText() + "' już istnieje!");
            loginErrorVBox.setManaged(!isLoginSame && isLoginTaken);
            loginError.setVisible(!isLoginSame && isLoginTaken);
            loginError.setManaged(!isLoginSame && isLoginTaken);

            if (isLoginTaken && !isLoginSame) {
                UserLoginField.setStyle("-fx-font-size: 14; -fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 3; -fx-background-radius: 7");
            } else {
                UserLoginField.setStyle("-fx-font-size: 14; -fx-background-radius: 7");
            }
        });

        UserNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.user.getName().equals(UserNameField.getText())) {
                changeNameButton.setDisable(true);
            } else {
                changeNameButton.setDisable(false);
            }
        });

        UserSurnameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.user.getSurname().equals(UserSurnameField.getText())) {
                changeSurnameButton.setDisable(true);
            } else {
                changeSurnameButton.setDisable(false);
            }
        });

        UserEmailField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isEmailSame = this.user.getEmail().equals(UserEmailField.getText());
            boolean isEmailTaken = userDao.isUserDataTaken("email", UserEmailField.getText());

            changeEmailButton.setDisable(isEmailSame || isEmailTaken);
            emailErrorVBox.setManaged(!isEmailSame && isEmailTaken);
            emailError.setVisible(!isEmailSame && isEmailTaken);
            emailError.setManaged(!isEmailSame && isEmailTaken);

            if (isEmailTaken && !isEmailSame) {
                UserEmailField.setStyle("-fx-font-size: 14; -fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 3; -fx-background-radius: 7");
            } else {
                UserEmailField.setStyle("-fx-font-size: 14; -fx-background-radius: 7");
            }
        });

        UserCountryField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.user.getCountry().equals(UserCountryField.getText())) {
                changeCountryButton.setDisable(true);
            } else {
                changeCountryButton.setDisable(false);
            }
        });

        UserCityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.user.getCity().equals(UserCityField.getText())) {
                changeCityButton.setDisable(true);
            } else {
                changeCityButton.setDisable(false);
            }
        });
    }

    private void animateElementsSequentially(VBox vbox) {
        SequentialTransition seqTransition = new SequentialTransition();
        for (Node node : vbox.getChildren()) {
            FadeTransition ft = new FadeTransition(Duration.millis(200), node);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            seqTransition.getChildren().add(ft);
        }
        seqTransition.play();
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

    private void showAlert(String dataType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TwojaOpinia");
        alert.setHeaderText(null);
        if (dataType.equals("Login") || dataType.equals("Mail") || dataType.equals("Kraj")) {
            alert.setContentText(dataType + " został pomyślnie zmieniony!");
        } else {
            alert.setContentText(dataType + " zostało pomyślnie zmienione!");
        }
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
        stage.centerOnScreen();

        alert.showAndWait();
    }
    @FXML
    private void changeLogin() {
        String newLogin = UserLoginField.getText();
        userDao.changeUserData("login", newLogin, this.userLogin);
        this.userLogin = newLogin;
        this.user.setLogin(this.userLogin);
        userLoginLabel.setText(this.userLogin);
        UserLoginField.setText(this.userLogin);
        showAlert("Login");
        changeLoginButton.setDisable(true);
    }

    @FXML
    private void changeName() {
        String newName = UserNameField.getText();
        userDao.changeUserData("name", newName, this.userLogin);
        UserNameField.setText(newName);
        this.user.setName(newName);
        showAlert("Imię");
        changeNameButton.setDisable(true);
    }

    @FXML
    private void changeSurname() {
        String newSurname = UserSurnameField.getText();
        userDao.changeUserData("surname", newSurname, this.userLogin);
        UserSurnameField.setText(newSurname);
        this.user.setSurname(newSurname);
        showAlert("Nazwisko");
        changeSurnameButton.setDisable(true);
    }

    @FXML
    private void changeEmail() {
        String newEmail = UserEmailField.getText();
        userDao.changeUserData("email", newEmail, this.userLogin);
        UserEmailField.setText(newEmail);
        this.user.setEmail(newEmail);
        showAlert("Mail");
        changeEmailButton.setDisable(true);
    }

    @FXML
    private void changeCountry() {
        String newCountry = UserCountryField.getText();
        userDao.changeUserData("country", newCountry, this.userLogin);
        UserCountryField.setText(newCountry);
        this.user.setCountry(newCountry);
        showAlert("Kraj");
        changeCountryButton.setDisable(true);
    }

    @FXML
    private void changeCity() {
        String newCity = UserCityField.getText();
        userDao.changeUserData("city", newCity, this.userLogin);
        UserCityField.setText(newCity);
        this.user.setCity(newCity);
        showAlert("Miasto");
        changeCityButton.setDisable(true);
    }

    @FXML
    private void changePassword() {
        String oldPassword = UserOldPasswordField.getText();
        String newPassword = UserNewPasswordField.getText();
        if (!user.getPassword().equals(SHA256.toSHA256(oldPassword + user.getSalt())) && !checkCorrectionPassword(newPassword)) {
            oldPasswordErrorVBox.setManaged(true);
            oldPasswordError.setManaged(true);
            oldPasswordError.setVisible(true);
            newPasswordErrorVBox.setManaged(true);
            newPasswordError.setManaged(true);
            newPasswordError.setVisible(true);
            UserNewPasswordField.setStyle("-fx-font-size: 14; -fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 3; -fx-background-radius: 7");
            UserOldPasswordField.setStyle("-fx-font-size: 14; -fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 3; -fx-background-radius: 7");
        }
        if (user.getPassword().equals(SHA256.toSHA256(oldPassword + user.getSalt()))) {
            if (checkCorrectionPassword(newPassword)) {
                userDao.changeUserPassword(newPassword, this.userLogin);
                UserOldPasswordField.setText("");
                UserNewPasswordField.setText("");
                showAlert("Hasło");
                UserOldPasswordField.setStyle("-fx-font-size: 14; -fx-background-radius: 7");
                UserNewPasswordField.setStyle("-fx-font-size: 14; -fx-background-radius: 7");

                newPasswordErrorVBox.setManaged(false);
                newPasswordError.setManaged(false);
                newPasswordError.setVisible(false);

                oldPasswordErrorVBox.setManaged(false);
                oldPasswordError.setManaged(false);
                oldPasswordError.setVisible(false);
            } else {
                UserNewPasswordField.setStyle("-fx-font-size: 14; -fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 3; -fx-background-radius: 7");
                newPasswordErrorVBox.setManaged(true);
                newPasswordError.setManaged(true);
                newPasswordError.setVisible(true);

                UserOldPasswordField.setStyle("-fx-font-size: 14; -fx-background-radius: 7");
                oldPasswordErrorVBox.setManaged(false);
                oldPasswordError.setManaged(false);
                oldPasswordError.setVisible(false);

            }
        } else {
            UserOldPasswordField.setStyle("-fx-font-size: 14; -fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 3; -fx-background-radius: 7");
            oldPasswordErrorVBox.setManaged(true);
            oldPasswordError.setManaged(true);
            oldPasswordError.setVisible(true);

            UserNewPasswordField.setStyle("-fx-font-size: 14; -fx-background-radius: 7");
            newPasswordErrorVBox.setManaged(false);
            newPasswordError.setManaged(false);
            newPasswordError.setVisible(false);
        }
    }

    //USER_MENU
    //===================================================================================================================
    @FXML
    private void availableSurveys() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/AvailableSurveys.fxml")));
            Parent availableSurveys = fxmlLoader.load();

            AvailableSurveysController availableSurveysController = fxmlLoader.getController();
            availableSurveysController.setUserLogin(userLogin);

            Scene scene = new Scene(availableSurveys, 1100, 700);
            Stage stage = (Stage) availableSurveysButtonMenu.getScene().getWindow();
            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void surveysHistory() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/HistorySurveys.fxml")));
            fxmlLoader.setControllerFactory(param -> new HistorySurveysController(this.userLogin));
            Parent surveysHistory = fxmlLoader.load();

            Scene scene = new Scene(surveysHistory, 1100, 700);
            Stage stage = (Stage) surveysHistoryButtonMenu.getScene().getWindow();
            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml")));
            Parent userDashboard = fxmlLoader.load();

            UserPulpitController userPulpitController = fxmlLoader.getController();
            userPulpitController.setUserLogin(userLogin);

            Scene scene = new Scene(userDashboard, 1100, 700);
            Stage stage = (Stage) backToDashboardButtonMenu.getScene().getWindow();
            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void logout() {
        try {
            Parent logout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/LoginView.fxml")));
            Scene scene = new Scene(logout, 400, 350);
            Stage stage = (Stage) logoutButtonMenu.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //===================================================================================================================
}
