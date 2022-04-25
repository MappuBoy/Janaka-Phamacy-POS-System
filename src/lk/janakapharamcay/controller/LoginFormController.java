package lk.janakapharamcay.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.janakapharamcay.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    public JFXTextField txtUserName;
    public AnchorPane root;
    public JFXButton btnCancel;
    public JFXPasswordField txtPass;
    public JFXButton btnLogin;

    String logedEmpId;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        String userName = txtUserName.getText();
        String userPass = txtPass.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE Emp_username=? AND Emp_password=?");
            preparedStatement.setObject(1, userName);
            preparedStatement.setObject(2, userPass);
            ResultSet rst = preparedStatement.executeQuery();
            while (rst.next()) {

                if (userName.equalsIgnoreCase("admin") && userPass.equalsIgnoreCase("1234")) {

                    logedEmpId = rst.getString(1);

                    System.out.println(logedEmpId);

                    new Alert(Alert.AlertType.CONFIRMATION, "Welcome to Manager DashBoard ").show();
                    Stage window = (Stage) this.root.getScene().getWindow();
                    window.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/janakapharamcay/view/AdminPanelForm.fxml"))));
                    window.centerOnScreen();

                } else if (userName.equalsIgnoreCase("employee") && userPass.equalsIgnoreCase("1235")) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/janakapharamcay/view/Mainpage.fxml"));
                    Parent root = loader.load();

                    //Get controller of scene2
                    MainPageController controller = loader.getController();
                    //Pass whatever data you want. You can have multiple method calls here
                    controller.setLblEmpId(rst.getString(1));

                    //Show scene 2 in new window
                    Stage stage = (Stage) this.root.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Please check your Username and Password").show();
                    clearloginOnAction(null);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getEmpId() {
        return logedEmpId;
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void userTxtOnAction(ActionEvent actionEvent) {
        txtPass.requestFocus();
    }

    public void passTxtOnAction(ActionEvent actionEvent) {
        btnLogin.requestFocus();
    }

    public void clearloginOnAction(ActionEvent actionEvent) {
        txtUserName.clear();
        txtPass.clear();

    }

}
