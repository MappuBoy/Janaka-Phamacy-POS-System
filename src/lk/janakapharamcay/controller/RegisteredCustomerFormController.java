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
import javafx.scene.paint.Paint;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.RegisteredCustomer;
import lk.janakapharamcay.view.tm.RegisteredCustomerTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisteredCustomerFormController implements Initializable {

    public JFXTextField txtCustAddress;
    public JFXButton btnAddCustomer;
    public JFXButton btnUpdateCustomer;
    public JFXButton btnDeleteCustomer;
    public JFXButton btnSearchCustomer;
    public TableView<RegisteredCustomerTM> tblCustomer;
    public JFXTextField txtCustFirstName;
    public JFXTextField txtCustLastName;
    public JFXTextField txtCustTelPhone;
    public JFXTextField txtCustNic;
    public JFXTextField txtCustId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initzializ the table customer
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("regCustId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("telPhone"));
        tblCustomer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("nic"));

        //get selected row to textField
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtCustId.setText(newValue.getRegCustId());
            txtCustFirstName.setText(newValue.getFirstName());
            txtCustLastName.setText(newValue.getLastName());
            txtCustAddress.setText(newValue.getAddress());
            txtCustTelPhone.setText(newValue.getTelPhone());
            txtCustNic.setText(newValue.getNic());
        });

        //getALLCustomer details
        getAllCustomerDetails();

    }


    private void getAllCustomerDetails() {

        ArrayList<RegisteredCustomer> registeredCustomers = new ArrayList<>();
        ObservableList<RegisteredCustomerTM> tmObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM registered_customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                registeredCustomers.add(new RegisteredCustomer(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
            for (RegisteredCustomer regCust : registeredCustomers) {
                tmObservableList.add(new RegisteredCustomerTM(regCust.getRegCustId(), regCust.getFirstName(), regCust.getLastName(),
                        regCust.getAddress(), regCust.getTelPhone(), regCust.getNic()));
            }
            tblCustomer.setItems(tmObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCustAddOnAction(ActionEvent actionEvent) throws Exception {
        //add customer to database

        RegisteredCustomer regCustomer = new RegisteredCustomer(txtCustId.getText(), txtCustFirstName.getText(), txtCustLastName.getText(),
                txtCustAddress.getText(), txtCustTelPhone.getText(), txtCustNic.getText());

        /*if (Pattern.compile("^(C)[0-9]{5}$").matcher(txtCustId.getText()).matches()) {
            txtCustFirstName.requestFocus();
            if (Pattern.compile("^[A-z]{45}$").matcher(txtCustFirstName.getText()).matches()) {
                txtCustLastName.requestFocus();
                if (Pattern.compile("^[A-z]{45}$").matcher(txtCustLastName.getText()).matches()) {
                    txtCustTelPhone.requestFocus();
                    if (Pattern.compile("^[0-9]{10}$").matcher(txtCustTelPhone.getText()).matches()) {
                        txtCustTelPhone.requestFocus();
                        if (Pattern.compile("^[0-9]{9}*([a-z]{1})$").matcher(txtCustNic.getText()).matches()) {
                            txtCustNic.requestFocus();
                            if (Pattern.compile("^[A-Z|a-z]{1,}$").matcher(txtCustAddress.getText()).matches()) {

                                try {
                                    Connection connection = DBConnection.getInstance().getConnection();
                                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registered_customer " +
                                            "VALUES(?,?,?,?,?,?)");
                                    preparedStatement.setObject(1, regCustomer.getRegCustId());
                                    preparedStatement.setObject(2, regCustomer.getFirstName());
                                    preparedStatement.setObject(3, regCustomer.getLastName());
                                    preparedStatement.setObject(4, regCustomer.getAddress());
                                    preparedStatement.setObject(5, regCustomer.getTelPhone());
                                    preparedStatement.setObject(6, regCustomer.getNic());
                                    int add = preparedStatement.executeUpdate();
                                    if (add > 0) {
                                        new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                                        txtCustId.requestFocus();
                                    } else {
                                        new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
                                    }

                                } catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                txtCustAddress.setUnFocusColor(Paint.valueOf("red"));
                                //                            txtCustNic.requestFocus();
                            }

                        } else {
                            txtCustNic.setUnFocusColor(Paint.valueOf("red"));
                            //txtCustNic.requestFocus();
                        }
                    } else {
                        txtCustTelPhone.setUnFocusColor(Paint.valueOf("red"));
                        //txtCustTelPhone.requestFocus();
                    }
                } else {
                    txtCustLastName.setUnFocusColor(Paint.valueOf("red"));
//                    txtCustLastName.requestFocus();
                }
            } else {
                txtCustFirstName.setUnFocusColor(Paint.valueOf("red"));
//                txtCustLastName.requestFocus();
            }
        } else {
            txtCustId.setUnFocusColor(Paint.valueOf("red"));
            txtCustFirstName.requestFocus();
        }*/

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registered_customer " +
                    "VALUES(?,?,?,?,?,?)");
            preparedStatement.setObject(1, regCustomer.getRegCustId());
            preparedStatement.setObject(2, regCustomer.getFirstName());
            preparedStatement.setObject(3, regCustomer.getLastName());
            preparedStatement.setObject(4, regCustomer.getAddress());
            preparedStatement.setObject(5, regCustomer.getTelPhone());
            preparedStatement.setObject(6, regCustomer.getNic());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                txtCustId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        clearTextOnAction();
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) {
        //update customer

        RegisteredCustomer regCustomer = new RegisteredCustomer(txtCustId.getText(), txtCustFirstName.getText(), txtCustLastName.getText(),
                txtCustAddress.getText(), txtCustTelPhone.getText(), txtCustNic.getText());

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE registered_customer SET " +
                    "First_name=?, Last_name=?, Cus_address=?, Cus_teliphone_number=?, Cus_nic=? WHERE R_id=?");
            preparedStatement.setObject(1, regCustomer.getFirstName());
            preparedStatement.setObject(2, regCustomer.getLastName());
            preparedStatement.setObject(3, regCustomer.getAddress());
            preparedStatement.setObject(4, regCustomer.getTelPhone());
            preparedStatement.setObject(5, regCustomer.getNic());
            preparedStatement.setObject(6, regCustomer.getRegCustId());
            int add = preparedStatement.executeUpdate();
            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!", ButtonType.OK).show();
                txtCustId.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        clearTextOnAction();
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {
        //delete customer
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM registered_customer WHERE R_id=?");
            preparedStatement.setObject(1, txtCustNic.getText());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Doctor Delete Successfully.....!", ButtonType.OK).show();
                txtCustFirstName.requestFocus();
            } else {
                new Alert(Alert.AlertType.WARNING, "Doctor Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        //search customer in database

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM registered_customer WHERE R_id=?");
            preparedStatement.setObject(1, txtCustNic.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                txtCustFirstName.setText(resultSet.getString(2));
                txtCustLastName.setText(resultSet.getString(3));
                txtCustAddress.setText(resultSet.getString(4));
                txtCustTelPhone.setText(resultSet.getString(5));
                txtCustNic.setText(resultSet.getString(6));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addFirstNameOnAction(ActionEvent actionEvent) {
        txtCustLastName.requestFocus();
    }

    public void addlastOnAction(ActionEvent actionEvent) {
        txtCustAddress.requestFocus();
    }

    public void telPhoneOnAction(ActionEvent actionEvent) {
        txtCustNic.requestFocus();
    }

    public void nicOnAction(ActionEvent actionEvent) {
        txtCustId.requestFocus();
    }

    public void addIdOnAction(ActionEvent actionEvent) {
        txtCustFirstName.requestFocus();
    }

    public void addressOnAction(ActionEvent actionEvent) {
        txtCustTelPhone.requestFocus();
    }

    public void clearTextOnAction() {
        txtCustId.clear();
        txtCustFirstName.clear();
        txtCustLastName.clear();
        txtCustAddress.clear();
        txtCustTelPhone.clear();
        txtCustNic.clear();
    }
}
