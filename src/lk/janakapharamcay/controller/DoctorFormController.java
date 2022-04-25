package lk.janakapharamcay.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.Doctor;
import lk.janakapharamcay.view.tm.DoctorTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DoctorFormController implements Initializable {

    public TableView<DoctorTM> tblDoc;
    public JFXTextField txtDocId;
    public JFXTextField txtDocName;
    public JFXTextField txtBillAmount;
    public JFXButton btnUpdateDoctor;
    public JFXButton btnDeleteDoctor;
    public JFXButton btnSearchDoctor;
    public JFXButton btnAddDoctor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize the table customer
        tblDoc.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("docId"));
        tblDoc.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("docName"));
        tblDoc.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("bill"));

        //get selected row to textField
        tblDoc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtDocId.setText(newValue.getDocId());
            txtDocName.setText(newValue.getDocName());
            txtBillAmount.setText(String.valueOf(newValue.getBill()));
        });

        //getALLCustomer details
        getAllDoctorDetails();



    }

    private void getAllDoctorDetails() {
        ArrayList<Doctor> employeeArrayList = new ArrayList<>();
        ObservableList<DoctorTM> tmObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeArrayList.add(new Doctor(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3)));
            }
            for (Doctor doc : employeeArrayList) {
                tmObservableList.add(new DoctorTM(doc.getDocId(), doc.getDocName(), doc.getBill()));
            }
            tblDoc.setItems(tmObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void docIdOnAction(ActionEvent actionEvent) {
        if (txtDocId.getText().length() < 0) {
            txtDocId.requestFocus();
        } else {
            txtDocName.requestFocus();
        }
    }

    public void docNameOnAction(ActionEvent actionEvent) {
        txtBillAmount.requestFocus();
    }

    public void billAmountOnAction(ActionEvent actionEvent) {
        txtDocId.requestFocus();
    }

    public void btnDocAddOnAction(ActionEvent actionEvent) {

        Doctor doctor = new Doctor(txtDocId.getText(), txtDocName.getText(),
                Double.parseDouble(txtBillAmount.getText()));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO doctor VALUES(?,?,?)");
            preparedStatement.setObject(1, doctor.getDocId());
            preparedStatement.setObject(2, doctor.getDocName());
            preparedStatement.setObject(3, doctor.getBill());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                txtDocId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        getAllDoctorDetails();

        clearTextOnAction();
    }

    public void updateDocOnAction(ActionEvent actionEvent) {

        Doctor doctor = new Doctor(txtDocId.getText(), txtDocName.getText(),
                Double.parseDouble(txtBillAmount.getText()));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE doctor SET Doctor_name=?, Bill=?" +
                    "WHERE D_id=?");
            preparedStatement.setObject(1, doctor.getDocName());
            preparedStatement.setObject(2, doctor.getBill());
            preparedStatement.setObject(3, doctor.getDocId());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Update Success!!", ButtonType.OK).show();
                txtDocId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Update Failed! Try Again!", ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        getAllDoctorDetails();

        clearTextOnAction();
    }

    public void deleteDocOnAction(ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM doctor WHERE D_id=?");
            preparedStatement.setObject(1, txtDocId.getText());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Doctor Delete Successfully.....!", ButtonType.OK).show();
                txtDocId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Doctor Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        getAllDoctorDetails();
    }

    public void searchDocOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE D_id=?");
            preparedStatement.setObject(1, txtDocId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                txtDocName.setText(resultSet.getString(2));
                txtBillAmount.setText(resultSet.getString(3));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearTextOnAction() {
        txtDocId.clear();
        txtDocName.clear();
        txtBillAmount.clear();
    }

}
