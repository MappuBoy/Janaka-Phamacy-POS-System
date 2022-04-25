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
import lk.janakapharamcay.model.ReturnOrder;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierReturnFormController implements Initializable {

    public JFXTextField txtRetId;
    public JFXTextField txtItemId;
    public JFXTextField txtBrandName;
    public JFXButton btnAddDoctor;
    public JFXButton btnUpdateDoctor;
    public JFXButton btnDeleteDoctor;
    public JFXButton btnSearchDoctor;
    public JFXTextField txtDesc;
    public JFXTextField txtReturnDate;
    public JFXTextField txtReason;
    public JFXTextField txtQty;
    public JFXComboBox cmbSuppId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateSuppIds();
    }

    private void generateSuppIds() {
        ArrayList<String> suppIdList = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Supplier_id FROM supplier");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                suppIdList.add(resultSet.getString(1));
            }
            cmbSuppId.setItems(FXCollections.observableArrayList(suppIdList));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSuppReturnAddOnAction(ActionEvent actionEvent) {

        ReturnOrder suppReturnOrder = new ReturnOrder(txtRetId.getText(), txtItemId.getText(), txtBrandName.getText(),
                txtDesc.getText(), txtReturnDate.getText(), txtReason.getText(), Integer.parseInt(txtQty.getText()),
                cmbSuppId.getSelectionModel().getSelectedItem().toString());

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO return_order VALUES(?,?,?,?,?,?,?,?,?)");
            statement.setObject(1,suppReturnOrder.getReturnItemId());
            statement.setObject(2,suppReturnOrder.getReturnItemId());
            statement.setObject(3,suppReturnOrder.getBrandName());
            statement.setObject(4,suppReturnOrder.getDescription());
            statement.setObject(5,suppReturnOrder.getReturnDate());
            statement.setObject(6,suppReturnOrder.getReason());
            statement.setObject(7,suppReturnOrder.getQty());
            statement.setObject(8,suppReturnOrder.getSupplierId());
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

    public void updateSuppReturnOnAction(ActionEvent actionEvent) {
        ReturnOrder suppReturnOrder = new ReturnOrder(txtRetId.getText(),txtItemId.getText(), txtBrandName.getText(),
                txtDesc.getText(), txtReturnDate.getText(), txtReason.getText(), Integer.parseInt(txtQty.getText()),
                cmbSuppId.getSelectionModel().getSelectedItem().toString());

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE return_order SET Item_Id=?, Brand_name=?, " +
                    "description=?, Return_Date=?, Reason=?, qty=?, Supplier_id=? WHERE Ret_Item_id=?");
            statement.setObject(1,suppReturnOrder.getReturnItemId());
            statement.setObject(2,suppReturnOrder.getBrandName());
            statement.setObject(3,suppReturnOrder.getDescription());
            statement.setObject(4,suppReturnOrder.getReturnDate());
            statement.setObject(5,suppReturnOrder.getReason());
            statement.setObject(6,suppReturnOrder.getQty());
            statement.setObject(7,suppReturnOrder.getSupplierId());
            statement.setObject(8,suppReturnOrder.getReturnItemId());
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

    public void deleteSuppReturnOnAction(ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM return_order WHERE Ret_Item_id=?");
            preparedStatement.setObject(1, txtRetId.getText());
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

    public void searchSuppReturnOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM return_order WHERE Ret_Item_id=?");
            preparedStatement.setObject(1, txtRetId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                txtItemId.setText(resultSet.getString(2));
                txtBrandName.setText(resultSet.getString(3));
                txtDesc.setText(resultSet.getString(4));
                txtReturnDate.setText(resultSet.getString(5));
                txtReason.setText(resultSet.getString(6));
                txtQty.setText(String.valueOf(resultSet.getInt(7)));

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void returnItemIdOnAction(ActionEvent actionEvent) {
        txtItemId.requestFocus();
    }

    public void itemIdOnAction(ActionEvent actionEvent) {
        txtBrandName.requestFocus();
    }

    public void brandNameOnAction(ActionEvent actionEvent) {
        txtDesc.requestFocus();
    }

    public void descriptionOnAction(ActionEvent actionEvent) {
        txtDesc.requestFocus();
    }

    public void returnDateOnAction(ActionEvent actionEvent) {
        txtReason.requestFocus();
    }

    public void reasonOnAction(ActionEvent actionEvent) {
        txtQty.requestFocus();
    }

    public void qtyOnAction(ActionEvent actionEvent) {
        cmbSuppId.requestFocus();
    }


}
