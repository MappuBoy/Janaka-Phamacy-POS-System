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
import lk.janakapharamcay.model.Store;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreFormController implements Initializable {

    public JFXTextField txtMediId;
    public JFXTextField txtMediDescription;
    public JFXTextField txtMediUnitPrice;
    public JFXTextField txtMediQtyOnHand;
    public JFXButton btnAddMedicine;
    public JFXTextField txtBrandName;
    public JFXTextField txtItemType;
    public JFXComboBox cmdSuppId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genarateSuppIds();
    }

    private void genarateSuppIds() {

        ArrayList<String> supplierIdList = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Supplier_id FROM supplier");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierIdList.add(resultSet.getString(1));
            }
            cmdSuppId.setItems(FXCollections.observableArrayList(supplierIdList));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addMedicineOnAction(ActionEvent actionEvent) throws Exception {

        Store store = new Store(txtMediId.getText(), cmdSuppId.getSelectionModel().getSelectedItem().toString(), txtItemType.getText(),
                txtBrandName.getText(), txtMediDescription.getText(), Integer.parseInt(txtMediQtyOnHand.getText()),
                Double.parseDouble(txtMediUnitPrice.getText()));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO store VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setObject(1, store.getItemId());
            preparedStatement.setObject(2, store.getSuppId());
            preparedStatement.setObject(3, store.getItemType());
            preparedStatement.setObject(4, store.getBrandName());
            preparedStatement.setObject(5, store.getDescription());
            preparedStatement.setObject(6, store.getQty());
            preparedStatement.setObject(7, store.getUnitPrice());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                txtMediId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        cleaerMediOnAction(null);
    }

    public void updateMediOnAction(ActionEvent actionEvent) {

        Store store = new Store(txtMediId.getText(), cmdSuppId.getSelectionModel().getSelectedItem().toString(),
                txtItemType.getText(),
                txtBrandName.getText(),
                txtMediDescription.getText(), Integer.parseInt(txtMediQtyOnHand.getText()),
                Double.parseDouble(txtMediUnitPrice.getText()));

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE store SET Supplier_id=?," +
                    "Item_Type=?, Brand_name=?, description=?, qtyOnHand=?, unitPrice=? WHERE Item_Id=?");
            preparedStatement.setObject(1, store.getSuppId());
            preparedStatement.setObject(2, store.getItemType());
            preparedStatement.setObject(3, store.getBrandName());
            preparedStatement.setObject(4, store.getDescription());
            preparedStatement.setObject(5, store.getQty());
            preparedStatement.setObject(6, store.getUnitPrice());
            preparedStatement.setObject(7, store.getItemId());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!", ButtonType.OK).show();
                txtMediId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        cleaerMediOnAction(null);
    }

    public void deleteMediOnAction(ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM store WHERE Item_Id=?");
            preparedStatement.setObject(1, txtMediId.getText());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine Delete Successfully.....!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Medicine Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        cleaerMediOnAction(null);

    }

    public void cleaerMediOnAction(ActionEvent actionEvent) {
        txtMediId.clear();
        cmdSuppId.setValue("");
        txtItemType.clear();
        txtBrandName.clear();
        txtMediDescription.clear();
        txtMediUnitPrice.clear();
        txtMediQtyOnHand.clear();
    }

    public void searchMedicine() {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM store WHERE Item_Id=?");
            preparedStatement.setObject(1, txtMediId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cmdSuppId.setValue(resultSet.getString(2));
                txtItemType.setText(resultSet.getString(3));
                txtBrandName.setText(resultSet.getString(4));
                txtMediDescription.setText(resultSet.getString(5));
                txtMediQtyOnHand.setText(resultSet.getString(6));
                txtMediUnitPrice.setText(resultSet.getString(7));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void brandNameOnAction(ActionEvent actionEvent) {
        txtMediDescription.requestFocus();
    }

    public void mediIdOnAction(ActionEvent actionEvent) {
        searchMedicine();
        cmdSuppId.requestFocus();
    }

    public void mediDescOnAction(ActionEvent actionEvent) {
        txtMediUnitPrice.requestFocus();
    }

    public void mediUnitOnAction(ActionEvent actionEvent) {
        txtMediQtyOnHand.requestFocus();
    }

    public void mediQtyOnHandOnAction(ActionEvent actionEvent) {
        btnAddMedicine.requestFocus();
    }

    public void itemTypeOnAction(ActionEvent actionEvent) {
        txtBrandName.requestFocus();
    }

    public void searchMediOnAction(ActionEvent actionEvent) {
        searchMedicine();
    }

    public void clearTextOnAction() {
        txtMediId.clear();
        txtItemType.clear();
        txtBrandName.clear();
        txtMediUnitPrice.clear();
        txtMediDescription.clear();
        txtMediQtyOnHand.clear();
    }

    public List<String> getAllItemId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM store").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public Store getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  store WHERE  Item_Id='" + "'").executeQuery();
        if (resultSet.next()) {
            return new Store(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getDouble(7)
            );
        } else {
            return null;
        }

    }

}
