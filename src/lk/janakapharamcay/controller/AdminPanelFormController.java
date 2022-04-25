package lk.janakapharamcay.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AdminPanelFormController {

    public AnchorPane context;
    public JFXButton btnBack;
    public TextField txtCurrentDate;
    public TextField txtAdminCurrentDate;

    public void initialize() throws IOException {
        loadDefault();

        genarateTime();

        generateCurrentDate();
    }

    private void generateCurrentDate() {
        txtAdminCurrentDate.setText(LocalDate.now().toString());
    }

    private void genarateTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            txtCurrentDate.setText(LocalTime.now().format(formatter));
        }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        txtCurrentDate.setText(LocalDate.now().toString());
    }

    private void loadDefault() throws IOException {
        setUi("AdminDashBoardForm");
    }

    public void itemOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StoreForm");
    }

    private void setUi(String location) throws IOException {
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(this.getClass().getResource("/lk/janakapharamcay/view/" + location + ".fxml")));
    }

    public void dashBoardOnAction(ActionEvent actionEvent) throws IOException {
        loadDefault();
    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AddSupplerForm");
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Janaka Pharmacy Medicine");
        Image image = new Image("/lk/janakapharamcay/assets/logo-pharmacy-png-favpng-wqN3RkFh6GsviFssfVsmdecmi.jpg");
        primaryStage.getIcons().add(image);

        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeForm");
    }

    public void doctorOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DoctorForm");
    }


}
