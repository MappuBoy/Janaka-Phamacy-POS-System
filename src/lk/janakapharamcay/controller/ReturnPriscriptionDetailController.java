package lk.janakapharamcay.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.ReturnPrescriptionDetail;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnPriscriptionDetailController implements Initializable {
    public JFXTextField txtRetCustId;
    public JFXButton btnAddReturnOrder;
    public JFXButton btnUpdateReturnOrder;
    public JFXButton btnDeleteReturnOrder;
    public JFXButton btnSearchreturnOrder;
    public JFXTextField txtReturnDate;
    public JFXTextField txtReason;
    public JFXComboBox cmbPricId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateOrderIds();;
    }

    private void generateOrderIds() {
        ArrayList<String> OrderIdList = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  P_Number FROM prescription");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrderIdList.add(resultSet.getString(1));
            }
            cmbPricId.setItems(FXCollections.observableArrayList(OrderIdList));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void btnCustReturnAddOnAction(ActionEvent actionEvent) {
        ReturnPrescriptionDetail custReturnOrder = new ReturnPrescriptionDetail(txtRetCustId.getText(),cmbPricId.getSelectionModel().getSelectedItem().toString(),txtReturnDate.getText(), txtReason.getText());

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO  return_priscription_detail VALUES(?,?,?,?)");
            statement.setObject(1,custReturnOrder.getReturnPrescriptionId());
            statement.setObject(2,custReturnOrder.getPrescriptionNumber());
            statement.setObject(3,custReturnOrder.getReturnDate());
            statement.setObject(4,custReturnOrder.getReason());

            int add = statement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateCustReturnOnAction(ActionEvent actionEvent) {
        ReturnPrescriptionDetail custReturnOrder = new ReturnPrescriptionDetail(txtRetCustId.getText(),cmbPricId.getSelectionModel().getSelectedItem().toString(),txtReturnDate.getText(), txtReason.getText());
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE return_priscription_detail SET  P_Number=?, Return_Date=?, Reason=?WHERE Ret_Pris_id=?");
            statement.setObject(1,custReturnOrder.getReturnPrescriptionId());
            statement.setObject(2,custReturnOrder.getPrescriptionNumber());
            statement.setObject(3,custReturnOrder.getReturnDate());
            statement.setObject(4,custReturnOrder.getReason());
            int add = statement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!!!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void deleteCustReturnOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM return_priscription_detail WHERE Ret_Pris_id=?");
            preparedStatement.setObject(1, txtRetCustId.getText());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully.....!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void searchCustReturnOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM return_priscription_detail WHERE Ret_Pris_id=?");
            preparedStatement.setObject(1, txtRetCustId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cmbPricId.setAccessibleText(resultSet.getString(2));
                txtReturnDate.setText(resultSet.getString(3));
                txtReason.setText(resultSet.getString(4));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void returnCustIdOnAction(ActionEvent actionEvent) {cmbPricId.requestFocus();
    }

    public void returnCustDateOnAction(ActionEvent actionEvent) {txtReason.requestFocus();
    }


    public void cmdPricIdOnAction(ActionEvent actionEvent) {txtReturnDate.requestFocus();
    }
}
