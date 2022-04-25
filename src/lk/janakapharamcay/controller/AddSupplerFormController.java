package lk.janakapharamcay.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.Supplier;
import lk.janakapharamcay.view.tm.SupplierTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddSupplerFormController implements Initializable {

    public JFXTextField txtSuppId;
    public JFXTextField txtSuppName;
    public JFXTextField txtSuppAddress;
    public JFXTextField txtSuppEmail;
    public JFXButton btnDeleteSupplier;
    public TableView<SupplierTM> tblSupplier;
    public JFXTextField txtSuppRegNumber;
    public JFXTextField txtSuppTelNumber;
    public AnchorPane supplierFormContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize table
        tblSupplier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("suppID"));
        tblSupplier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblSupplier.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("telNumber"));
        tblSupplier.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("regNumber"));
        tblSupplier.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("email"));

        //set suppler details to table
        try {
            getSupplerDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //onClick to table
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtSuppId.setText(newValue.getSuppID());
            txtSuppName.setText(newValue.getName());
            txtSuppAddress.setText(newValue.getAddress());
            txtSuppTelNumber.setText(newValue.getTelNumber());
            txtSuppRegNumber.setText(newValue.getRegNumber());
            txtSuppEmail.setText(newValue.getEmail());

        });

        //autoGenaratesupplier id from the database
//        genarateSupplierID();
    }

    private void genarateSupplierID() {
    }

    private void getSupplerDetails() throws Exception {

        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        ObservableList<SupplierTM> tmObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM supplier");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                supplierArrayList.add(new Supplier(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),
                        resultSet.getString(6)));
            }
            for (Supplier supplier : supplierArrayList) {
                tmObservableList.add(new SupplierTM(supplier.getId(),supplier.getName(),
                        supplier.getAddress(),supplier.getTelNumber(),supplier.getRegNumber(),supplier.getEmail()));
            }
            tblSupplier.setItems(tmObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addSuppOnAction(ActionEvent actionEvent) throws Exception {

        Supplier supplier = new Supplier(txtSuppId.getText(), txtSuppName.getText(), txtSuppAddress.getText(), txtSuppTelNumber.getText(),
                txtSuppRegNumber.getText(), txtSuppEmail.getText());

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO supplier VALUES(?,?,?,?,?,?)");
        preparedStatement.setObject(1,supplier.getId());
        preparedStatement.setObject(2,supplier.getName());
        preparedStatement.setObject(3,supplier.getAddress());
        preparedStatement.setObject(4,supplier.getTelNumber());
        preparedStatement.setObject(5,supplier.getRegNumber());
        preparedStatement.setObject(6,supplier.getEmail());
        int add = preparedStatement.executeUpdate();

        if (add > 0){
            new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
        }

        //load new data with table
        getSupplerDetails();

        clearTextOnAction();
    }

    public void updateSuppOnAction(ActionEvent actionEvent) throws Exception {

        Supplier supplier = new Supplier(txtSuppId.getText(), txtSuppName.getText(), txtSuppAddress.getText(), txtSuppTelNumber.getText(),
                txtSuppRegNumber.getText(), txtSuppEmail.getText());

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE supplier SET Supplier_name=?, " +
                "Supplier_address=?, Supplier_teliphone_number=?, Supplier_Registration_number=?, Supplier_email=? " +
                "WHERE Supplier_id=?");
        preparedStatement.setObject(1,supplier.getName());
        preparedStatement.setObject(2,supplier.getAddress());
        preparedStatement.setObject(3,supplier.getTelNumber());
        preparedStatement.setObject(4,supplier.getRegNumber());
        preparedStatement.setObject(5,supplier.getEmail());
        preparedStatement.setObject(6,supplier.getId());
        int add = preparedStatement.executeUpdate();

        if (add > 0){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated!!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
        }

        //load new data with table
        getSupplerDetails();

        clearTextOnAction();
    }

    public void deleteSuppOnAction(ActionEvent actionEvent) throws Exception {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM supplier WHERE Supplier_id=?");
            preparedStatement.setObject(1,txtSuppId.getText());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Delete Successfully.....!", ButtonType.OK).show();
                txtSuppId.requestFocus();
            }else {
                new Alert(Alert.AlertType.WARNING, "Supplier Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //reload supplier table after deleting a supplier
        getSupplerDetails();
    }

    public void searchSuppOnAction(ActionEvent actionEvent) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM supplier WHERE Supplier_id=?");
            preparedStatement.setObject(1,txtSuppId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                txtSuppName.setText(resultSet.getString(2));
                txtSuppAddress.setText(resultSet.getString(3));
                txtSuppTelNumber.setText(resultSet.getString(4));
                txtSuppRegNumber.setText(resultSet.getString(5));
                txtSuppEmail.setText(resultSet.getString(6));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtSuppIdOnAction(ActionEvent actionEvent) {
        txtSuppName.requestFocus();
    }

    public void txtSuppAddressOnAction(ActionEvent actionEvent) {
        txtSuppTelNumber.requestFocus();
    }

    public void txtSuppNameOnAction(ActionEvent actionEvent) {
        txtSuppAddress.requestFocus();
    }

    public void txtSuppRegNumberOnAction(ActionEvent actionEvent) {
        txtSuppEmail.requestFocus();
    }

    public void txtSuppTelNumberOnAction(ActionEvent actionEvent) {
        txtSuppRegNumber.requestFocus();
    }

    public void txtSuppEmailOnAction(ActionEvent actionEvent) {
        txtSuppId.requestFocus();
    }

    public void clearTextOnAction() {
        txtSuppId.clear();
        txtSuppName.clear();
        txtSuppAddress.clear();
        txtSuppRegNumber.clear();
        txtSuppTelNumber.clear();
        txtSuppEmail.clear();
    }
    private void setUi(String location) throws IOException {
        supplierFormContext.getChildren().clear();
        supplierFormContext.getChildren().add(FXMLLoader.load(this.getClass().getResource("/lk/janakapharamcay/view/" + location + ".fxml")));
    }

    public void addReturnOrderSuppOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ItemOrderSupplierForm");
    }

    public void addOrderSuppOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SupplierReturnForm");
    }
}
