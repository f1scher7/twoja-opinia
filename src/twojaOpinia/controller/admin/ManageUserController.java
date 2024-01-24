package twojaOpinia.controller.admin;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.util.Duration;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;

import static twojaOpinia.util.SaltUtil.generateSalt;
import static twojaOpinia.util.JavaFXMethods.centerStage;

public class ManageUserController {
    private UserDao userDao = new UserDao();

    private String adminLogin;

    @FXML
    private TextField newUserNameField;
    @FXML
    private TextField newUserSurnameField;
    @FXML
    private ChoiceBox<String> sexChoiceBox;
    @FXML
    private DatePicker newUserBirthdayDate;
    @FXML
    private TextField newUserCountryField;
    @FXML
    private TextField newUserCityField;
    @FXML
    private TextField newUserEmailField;
    @FXML
    private TextField newUserLoginField;
    @FXML
    private TextField newUserPasswordField;
    @FXML
    private TextField newUserFinalPasswordField;
    @FXML
    private Label passwordFieldErrorLabel;
    @FXML
    private CheckBox newUserIsAdminCheck;
    @FXML
    private Button createNewUserButton;

    @FXML
    private TextField findUserByLoginField;
    @FXML
    private Button findUserDataButton;
    @FXML
    private TextField deleteUserByLoginField;
    @FXML
    private Button deleteUserButton;

    @FXML
    private Label adminLoginLabel;
    @FXML
    private Button manageUserButtonMenu;
    @FXML
    private Button manageSurveyButtonMenu;
    @FXML
    private Button analyzeResultsButtonMenu;
    @FXML
    private Button historyOfAddedSurveysButtonMenu;
    @FXML
    private Button backToDashboardButtonMenu;
    @FXML
    private Button logoutButtonMenu;


    @FXML
    public void initialize() {

        manageSurveyButtonMenu.setOnMouseEntered(e -> manageSurveyButtonMenu.setCursor(Cursor.HAND));
        manageSurveyButtonMenu.setOnMouseExited(e -> manageSurveyButtonMenu.setCursor(Cursor.DEFAULT));

        analyzeResultsButtonMenu.setOnMouseEntered(e -> analyzeResultsButtonMenu.setCursor(Cursor.HAND));
        analyzeResultsButtonMenu.setOnMouseExited(e -> analyzeResultsButtonMenu.setCursor(Cursor.DEFAULT));

        historyOfAddedSurveysButtonMenu.setOnMouseEntered(e -> historyOfAddedSurveysButtonMenu.setCursor(Cursor.HAND));
        historyOfAddedSurveysButtonMenu.setOnMouseExited(e -> historyOfAddedSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        backToDashboardButtonMenu.setOnMouseEntered(e -> backToDashboardButtonMenu.setCursor(Cursor.HAND));
        backToDashboardButtonMenu.setOnMouseExited(e -> backToDashboardButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));

        deleteUserButton.setOnMouseEntered(e -> deleteUserButton.setCursor(Cursor.HAND));
        deleteUserButton.setOnMouseExited(e -> deleteUserButton.setCursor(Cursor.DEFAULT));

        findUserDataButton.setOnMouseEntered(e -> findUserDataButton.setCursor(Cursor.HAND));
        findUserDataButton.setOnMouseExited(e -> findUserDataButton.setCursor(Cursor.DEFAULT));

        createNewUserButton.setOnMouseEntered(e -> createNewUserButton.setCursor(Cursor.HAND));
        createNewUserButton.setOnMouseExited(e -> createNewUserButton.setCursor(Cursor.DEFAULT));

        createNewUserButton.setDisable(true);
        passwordFieldErrorLabel.setManaged(false);
        passwordFieldErrorLabel.setVisible(false);

        newUserNameField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserSurnameField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserBirthdayDate.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserCountryField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserCityField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserEmailField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserLoginField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserPasswordField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserFinalPasswordField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        newUserIsAdminCheck.selectedProperty().addListener((observable, oldValue, newValue) -> checkFields());

        sexChoiceBox.getItems().addAll("M", "K");
        sexChoiceBox.setValue("Płeć");
    }

    public void setAdminLogin(String login) {
        this.adminLogin = login;
        adminLoginLabel.setText(login);
    }
    private void checkFields() {
        if (newUserNameField.getText().isEmpty() ||
                newUserSurnameField.getText().isEmpty() ||
                newUserBirthdayDate.getValue() == null ||
                newUserCountryField.getText().isEmpty() ||
                newUserCityField.getText().isEmpty() ||
                newUserEmailField.getText().isEmpty() ||
                newUserLoginField.getText().isEmpty() ||
                newUserPasswordField.getText().isEmpty() ||
                newUserFinalPasswordField.getText().isEmpty()) {
            createNewUserButton.setDisable(true);
        } else if (!newUserPasswordField.getText().equals(newUserFinalPasswordField.getText())) {
            createNewUserButton.setDisable(true);
            newUserPasswordField.setStyle("-fx-border-color: red;");
            newUserFinalPasswordField.setStyle("-fx-border-color: red;");
            passwordFieldErrorLabel.setText("  Hasła nie pasują!");
            passwordFieldErrorLabel.setStyle("-fx-text-fill: #ff1d1d; -fx-font-size: 13px; -fx-font-weight: bold; -fx-pref-width: 200; -fx-pref-height: 30; -fx-alignment: center;");
            passwordFieldErrorLabel.setVisible(true);
            passwordFieldErrorLabel.setManaged(true);
        } else {
            passwordFieldErrorLabel.setManaged(false);
            passwordFieldErrorLabel.setVisible(false);
            createNewUserButton.setDisable(false);
            newUserPasswordField.setStyle(null);
            newUserFinalPasswordField.setStyle(null);
            passwordFieldErrorLabel.setText("");
        }
    }
    @FXML
    private void addNewUser() {
        String name = newUserNameField.getText();
        String surname = newUserSurnameField.getText();
        String sex = sexChoiceBox.getValue();
        LocalDate date = newUserBirthdayDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String birthday = date.format(formatter);
        String country = newUserCountryField.getText();
        String city = newUserCityField.getText();
        String email = newUserEmailField.getText();
        String login = newUserLoginField.getText();
        String finalPassword = newUserFinalPasswordField.getText();
        boolean isAdmin = newUserIsAdminCheck.isSelected();

        String salt = generateSalt();
        User user = new User(name, surname, sex, email, birthday, country, city, login, finalPassword, salt, isAdmin);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("TwojaOpinia");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Czy na pewno chcesz dodać użytkownika o loginie: " + "'" + login + "' " + "?");

        Stage confirmationStage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
        confirmationStage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
        confirmationStage.centerOnScreen();

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                userDao.insert(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TwojaOpinia");
                alert.setHeaderText(null);
                alert.setContentText("Użytkownik o loginie: " + "'" + login + "' " + "został pomyślnie stworzony!");

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                stage.centerOnScreen();

                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("TwojaOpinia");
                alert.setHeaderText(null);
                alert.setContentText("Użytkownika o loginie: " + "'" + login + "' " + "już istnieje!");

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                stage.centerOnScreen();

                alert.showAndWait();
            }
            newUserNameField.setText("");
            newUserSurnameField.setText("");
            newUserBirthdayDate.setValue(null);
            newUserCountryField.setText("");
            newUserCityField.setText("");
            newUserEmailField.setText("");
            newUserLoginField.setText("");
            newUserPasswordField.setText("");
            newUserFinalPasswordField.setText("");
            newUserIsAdminCheck.setSelected(false);
            sexChoiceBox.setValue("Płeć");
        }
    }

    @FXML
    private void findUserDataByLogin() {
        String login = findUserByLoginField.getText();
        User user = userDao.getUserDataByLogin(login);
        if (user != null) {
            String userDetails = "Imię: " + user.getName() + "\n" +
                    "Nazwisko: " + user.getSurname() + "\n" +
                    "Płeć: " + user.getSex() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Data urodzenia: " + user.getBirthday() + "\n" +
                    "Kraj: " + user.getCountry() + "\n" +
                    "Miasto: " + user.getCity() + "\n" +
                    "Login: " + user.getLogin() + "\n" +
                    "Admin: " + (user.isAdmin() ? "Tak" : "Nie");

            TextArea textArea = new TextArea(userDetails);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dane użytkownika o loginie " + login);
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(textArea);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("Użytkownika o loginie: " + "'" + login + "' " + "nie istnieje!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        }
        findUserByLoginField.setText("");
    }

    @FXML
    private void deleteUser() {
        String login = deleteUserByLoginField.getText().replace(" ", "");
        if (!login.isEmpty()) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("TwojaOpinia");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Czy na pewno chcesz usunąć użytkownika o loginie: " + "'" + login + "' " + "?");

            Stage confirmationStage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
            confirmationStage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            confirmationStage.centerOnScreen();

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    int affectedRows = userDao.deleteUserByLogin(login);
                    if (affectedRows == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("TwojaOpinia");
                        alert.setHeaderText(null);
                        alert.setContentText("Użytkownika o loginie: " + "'" + login + "' " + "nie istnieje!");

                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                        stage.centerOnScreen();

                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("TwojaOpinia");
                        alert.setHeaderText(null);
                        alert.setContentText("Użytkownik o loginie: " + "'" + login + "' " + "został pomyślnie usunięty!");

                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                        stage.centerOnScreen();

                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("TwojaOpinia");
                    alert.setHeaderText(null);
                    alert.setContentText("Użytkownika o loginie: " + "'" + login + "' " + "nie istnieje!");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                    stage.centerOnScreen();

                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("Pole 'login użytkownika' nie może być puste!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        }
        deleteUserByLoginField.setText("");
    }

    //ADMIN_MENU
    // ===================================================================================================================
    @FXML
    private void manageSurvey() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/ManageSurvey.fxml")));
            Parent manageSurvey = loader.load();

            ManageSurveyController manageSurveyController = loader.getController();
            manageSurveyController.setAdminLogin(adminLogin);

            Scene scene = new Scene(manageSurvey, 1100, 700);
            Stage stage = (Stage) manageSurveyButtonMenu.getScene().getWindow();

            TranslateTransition tt = new TranslateTransition(Duration.millis(550), scene.getRoot());
            tt.setFromX(-200f);
            tt.setToX(0f);
            tt.play();

            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void analyzeSurveys() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/SurveysAnalysis.fxml")));
            Parent analyzeSurveys = fxmlLoader.load();

            SurveysAnalysisController analysisController = fxmlLoader.getController();
            analysisController.setAdminLogin(adminLogin);

            Scene scene = new Scene(analyzeSurveys, 1100, 700);
            Stage stage = (Stage) analyzeResultsButtonMenu.getScene().getWindow();

            TranslateTransition tt = new TranslateTransition(Duration.millis(550), scene.getRoot());
            tt.setFromX(-200f);
            tt.setToX(0f);
            tt.play();

            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void historyOfAddedSurveys() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/HistorySurveys.fxml")));
            Parent historySurveys = fxmlLoader.load();

            HistorySurveysController historySurveysController = fxmlLoader.getController();
            historySurveysController.setAdminLogin(adminLogin);

            Scene scene = new Scene(historySurveys, 1100, 700);
            Stage stage = (Stage) historyOfAddedSurveysButtonMenu.getScene().getWindow();

            TranslateTransition tt = new TranslateTransition(Duration.millis(550), scene.getRoot());
            tt.setFromX(-200f);
            tt.setToX(0f);
            tt.play();

            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/AdminDashboard.fxml")));
            Parent adminDashboard = fxmlLoader.load();

            AdminPulpitController adminPulpitController = fxmlLoader.getController();
            adminPulpitController.setAdminLogin(adminLogin);

            Scene scene = new Scene(adminDashboard, 1100, 700);
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
            Scene scene = new Scene(logout, 400, 420);
            Stage stage = (Stage) logoutButtonMenu.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());

        }
    }
    //===================================================================================================================
}
