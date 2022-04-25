package lk.janakapharamcay.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.ItemOrder;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemOrderSupplierForm implements Initializable {

    public JFXTextField txtBrandName;
    public JFXDatePicker txtOrderDate;
    public JFXDatePicker txtDeliveryDate;
    public JFXTextField txtQty;
    public JFXTextField txtDescription;
    public JFXComboBox cmbSuppId;
    public JFXComboBox cmbItemId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateDate();

        generateSupplierId();

        generateItemId();
    }

    private void generateDate() {
        txtOrderDate.setValue(LocalDate.now());
    }

    private void generateItemId() {
        ArrayList<String> itemIdList = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Item_Id FROM store");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                itemIdList.add(resultSet.getString(1));
            }
            cmbItemId.setItems(FXCollections.observableArrayList(itemIdList));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateSupplierId() {
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

    public void addItemOrderOnAction(ActionEvent actionEvent) {

        ItemOrder itemOrder = new ItemOrder(cmbSuppId.getSelectionModel().getSelectedItem().toString(),
                cmbSuppId.getSelectionModel().getSelectedItem().toString(), txtBrandName.getText(),
                txtDescription.getText(), txtOrderDate.getValue().toString(), txtDeliveryDate.getValue().toString(),
                Integer.parseInt(txtQty.getText()));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item_order VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,itemOrder.getSuppId());
            preparedStatement.setObject(2,itemOrder.getItemId());
            preparedStatement.setObject(3,itemOrder.getBrandName());
            preparedStatement.setObject(4,itemOrder.getDescription());
            preparedStatement.setObject(5,itemOrder.getOrderDate());
            preparedStatement.setObject(6,itemOrder.getDeliveryDate());
            preparedStatement.setObject(7,itemOrder.getQty());
            int add = preparedStatement.executeUpdate();
            if (add > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateItemOrderOnAction(ActionEvent actionEvent) {
        ItemOrder itemOrder = new ItemOrder(cmbSuppId.getSelectionModel().getSelectedItem().toString(),
                cmbSuppId.getSelectionModel().getSelectedItem().toString(), txtBrandName.getText(),
                txtDescription.getText(), txtOrderDate.getPromptText(), txtDeliveryDate.getPromptText(),
                Integer.parseInt(txtQty.getText()));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item_order SET " +
                    "Item_Id=?, Brand_name=?, description=?, Order_Date=?, Dilivary_Date=?, qty=? WHERE Supplier_id=?");
            preparedStatement.setObject(1,itemOrder.getItemId());
            preparedStatement.setObject(2,itemOrder.getBrandName());
            preparedStatement.setObject(3,itemOrder.getDescription());
            preparedStatement.setObject(4,itemOrder.getOrderDate());
            preparedStatement.setObject(5,itemOrder.getDeliveryDate());
            preparedStatement.setObject(6,itemOrder.getQty());
            preparedStatement.setObject(7,itemOrder.getSuppId());
            int add = preparedStatement.executeUpdate();
            if (add > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!!!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemOrderOnAction(ActionEvent actionEvent) {
        String suppId = cmbSuppId.getSelectionModel().getSelectedItem().toString();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM item_order WHERE Supplier_id=?");
            preparedStatement.setObject(1,suppId);
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully.....!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void searchItemOrderOnAction(ActionEvent actionEvent) {

        String suppId = cmbSuppId.getSelectionModel().getSelectedItem().toString();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item_order WHERE Supplier_id=?");
            preparedStatement.setObject(1,suppId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                cmbItemId.setValue(resultSet.getString(2));
                txtBrandName.setText(resultSet.getString(3));
                txtDescription.setText(resultSet.getString(4));
                txtOrderDate.setValue(LocalDate.parse(resultSet.getString(5)));
                txtDeliveryDate.setValue(LocalDate.parse(resultSet.getString(6)));
                txtQty.setText(resultSet.getString(7));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtBrandNameOnAction(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void txtDescriptionOnAction(ActionEvent actionEvent) {
        txtOrderDate.requestFocus();
    }

    public void clearUserOnAction(ActionEvent actionEvent) {
        txtBrandName.clear();
        txtDescription.clear();
        txtQty.clear();
    }

    public void itemIdOnAction(ActionEvent actionEvent) {
        String selectedItem = cmbItemId.getSelectionModel().getSelectedItem().toString();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM store WHERE Item_Id=?");
            preparedStatement.setObject(1,selectedItem);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                txtBrandName.setText(resultSet.getString(4));
                txtDescription.setText(resultSet.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
