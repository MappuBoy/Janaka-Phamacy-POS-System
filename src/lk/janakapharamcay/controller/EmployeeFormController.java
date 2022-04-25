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
import lk.janakapharamcay.model.Employee;
import lk.janakapharamcay.view.tm.EmployeeTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {


    public TableView<EmployeeTM> tblEmployee;
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpFirstName;
    public JFXTextField txtEmpLastName;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtTelPhone;
    public JFXTextField txtNic;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXTextField txtEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            //initzializ the table customer
            tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("EmpId"));
            tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("EmpAddress"));
            tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("EmpTelNumber"));
            tblEmployee.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("EmpNIC"));
            tblEmployee.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("EmpEmail"));

            //get selected row to textField
            tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                txtEmpId.setText(newValue.getEmpId());
                txtEmpFirstName.setText(newValue.getFirstName());
                txtEmpLastName.setText(newValue.getLastName());
                txtEmpAddress.setText(newValue.getEmpAddress());
                txtTelPhone.setText(newValue.getEmpTelNumber());
                txtNic.setText(newValue.getEmpNIC());
                txtEmail.setText(newValue.getEmpEmail());
            });

            //getALLCustomer details
            getAllCustomerDetails();


    }


    private void getAllCustomerDetails() {

        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        ObservableList<EmployeeTM> tmObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                employeeArrayList.add(new Employee(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)));
            }
            for (Employee employee : employeeArrayList) {
                tmObservableList.add(new EmployeeTM(employee.getEmpId(),employee.getFirstName(),
                        employee.getLastName(),employee.getEmpAddress(),employee.getEmpTelNumber(),
                        employee.getEmpNIC(),employee.getEmpEmail()));
            }
            tblEmployee.setItems(tmObservableList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCustAddOnAction(ActionEvent actionEvent) {

        Employee employee = new Employee(txtEmpId.getText(), txtEmpFirstName.getText(), txtEmpLastName.getText(), txtEmpAddress.getText(),
                txtTelPhone.getText(), txtNic.getText(), txtUserName.getText(), txtPassword.getText(), txtEmail.getText());

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,employee.getEmpId());
            preparedStatement.setObject(2,employee.getFirstName());
            preparedStatement.setObject(3,employee.getLastName());
            preparedStatement.setObject(4,employee.getEmpAddress());
            preparedStatement.setObject(5,employee.getEmpTelNumber());
            preparedStatement.setObject(6,employee.getEmpNIC());
            preparedStatement.setObject(7,employee.getEmpUserName());
            preparedStatement.setObject(8,employee.getEmpPassword());
            preparedStatement.setObject(9,employee.getEmpEmail());

            int add = preparedStatement.executeUpdate();

            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                txtEmpId.requestFocus();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        getAllCustomerDetails();

        clearTextOnAction();
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) {

        //update employee
        Employee employee = new Employee(txtEmpId.getText(), txtEmpFirstName.getText(), txtEmpLastName.getText(), txtEmpAddress.getText(),
                txtTelPhone.getText(), txtNic.getText(), txtUserName.getText(), txtPassword.getText(), txtEmail.getText());

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee SET First_name=?, Last_name=?, " +
                    "Emp_address=?, Emp_teliphone_number=?, Emp_nic=?, Emp_username=?, Emp_password=?, Emp_email=? WHERE Emp_id=?");
            preparedStatement.setObject(1,employee.getFirstName());
            preparedStatement.setObject(2,employee.getLastName());
            preparedStatement.setObject(3,employee.getEmpAddress());
            preparedStatement.setObject(4,employee.getEmpTelNumber());
            preparedStatement.setObject(5,employee.getEmpNIC());
            preparedStatement.setObject(6,employee.getEmpUserName());
            preparedStatement.setObject(7,employee.getEmpPassword());
            preparedStatement.setObject(8,employee.getEmpEmail());
            preparedStatement.setObject(9,employee.getEmpId());

            int add = preparedStatement.executeUpdate();

            if (add > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!", ButtonType.OK).show();
                txtEmpId.requestFocus();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        getAllCustomerDetails();

        clearTextOnAction();
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {
        //delete employee
        String empIdText = txtEmpId.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE Emp_id=?");
            preparedStatement.setObject(1,empIdText);
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete Successfully.....!", ButtonType.OK).show();
                txtEmpId.requestFocus();
            }else {
                new Alert(Alert.AlertType.WARNING, "Employee Delete Unsuccessfully.....", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        //search employee in database
        String empIdText = txtEmpId.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE Emp_id=?");
            preparedStatement.setObject(1,empIdText);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                txtEmpFirstName.setText(resultSet.getString(1));
                txtEmpLastName.setText(resultSet.getString(2));
                txtEmpAddress.setText(resultSet.getString(3));
                txtTelPhone.setText(resultSet.getString(4));
                txtNic.setText(resultSet.getString(5));
                txtUserName.setText(resultSet.getString(6));
                txtPassword.setText(resultSet.getString(7));
                txtEmail.setText(resultSet.getString(8));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addIdOnAction(ActionEvent actionEvent) {
        txtEmpFirstName.requestFocus();
    }

    public void addressOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void phoneOnAction(ActionEvent actionEvent) {
        txtNic.requestFocus();
    }

    public void emailOnAction(ActionEvent actionEvent) {
        txtTelPhone.requestFocus();
    }

    public void passwordOnAction(ActionEvent actionEvent) {
        txtEmpId.requestFocus();
    }

    public void userNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void nicOnAction(ActionEvent actionEvent) {
        txtUserName.requestFocus();
    }

    public void addLastNameOnAction(ActionEvent actionEvent) {
        txtEmpAddress.requestFocus();
    }

    public void addFirstNameOnAction(ActionEvent actionEvent) {
        txtEmpLastName.requestFocus();
    }

    public void clearTextOnAction() {
        txtEmpId.clear();
        txtEmpFirstName.clear();
        txtEmpLastName.clear();
        txtEmpAddress.clear();
        txtTelPhone.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtEmail.clear();
        txtNic.clear();
    }
}
