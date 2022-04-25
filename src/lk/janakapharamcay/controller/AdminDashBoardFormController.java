package lk.janakapharamcay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.PrescriptionDetail;
import lk.janakapharamcay.model.ReturnPrescriptionDetail;
import lk.janakapharamcay.view.tm.PrescriptionDetailTM;
import lk.janakapharamcay.view.tm.ReturnPrescriptionDetailTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminDashBoardFormController implements Initializable {

    public Label lblMedicineCount;
    public Label lblCustomerCount;
    public Label lblOrderCount;
    public TableView<PrescriptionDetailTM> tblOrder;
    public TableView<ReturnPrescriptionDetailTM> tblReturnPres;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //initialize table
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("prescriptionNumber"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("regCustId"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("docId"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("prescriptionDate"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("prescriptionTime"));
        tblOrder.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("prescriptionCost"));

        //initialize table
        tblReturnPres.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("returnPrescriptionId"));
        tblReturnPres.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("prescriptionNumber"));
        tblReturnPres.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblReturnPres.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("reason"));

        //get all count from the item table
        getItemCount();

        //get all count from the customer table
        getCoustomerCount();

        //get all count from the order table
        getOrderCount();

        //load All the Prescription details to the table
        getAllPrescriptionDetails();

        //load All the Prescription details to the table
       getAllOrderReturns();
    }

    private void getAllOrderReturns() {
        ArrayList<ReturnPrescriptionDetail> returnPresDetailArrayList = new ArrayList<>();
        ObservableList<ReturnPrescriptionDetailTM> tmObservableList = FXCollections.observableArrayList();

        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM return_priscription_detail");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                returnPresDetailArrayList.add(new ReturnPrescriptionDetail(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4)));
            }
            for (ReturnPrescriptionDetail detail : returnPresDetailArrayList) {
                tmObservableList.add(new ReturnPrescriptionDetailTM(detail.getReturnPrescriptionId(),
                        detail.getPrescriptionNumber(),detail.getReturnDate(),detail.getReason()));
            }
            tblReturnPres.setItems(tmObservableList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getAllPrescriptionDetails() {

        ArrayList<PrescriptionDetail> prescriptionDetailArrayList = new ArrayList<>();
        ObservableList<PrescriptionDetailTM> tmObservableList = FXCollections.observableArrayList();

        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM prescription_detail");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                prescriptionDetailArrayList.add(new PrescriptionDetail(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getDouble(7)));
            }
            for (PrescriptionDetail detail : prescriptionDetailArrayList) {
                tmObservableList.add(new PrescriptionDetailTM(detail.getPrescriptionNumber(),
                        detail.getRegCustId(), detail.getDocId(), detail.getEmpId(), detail.getPrescriptionDate(),
                        detail.getPrescriptionTime(), detail.getPrescriptionCost()));
            }
            tblOrder.setItems(tmObservableList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void getOrderCount() {

        String lblOrderCountTex = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(P_Number) FROM Prescription_Detail");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int orderCount = resultSet.getInt(1);
                lblOrderCountTex = String.valueOf(orderCount);
            }
            if (lblOrderCountTex != null) {
                lblOrderCount.setText(lblOrderCountTex);
            } else {
                lblOrderCount.setText("0");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getCoustomerCount() {

        String lblRegCustCountText = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(R_id) FROM registered_customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int regCustCount = resultSet.getInt(1);
                lblRegCustCountText = String.valueOf(regCustCount);
            }
            if (lblRegCustCountText != null) {
                lblCustomerCount.setText(lblRegCustCountText);
            } else {
                lblCustomerCount.setText("0");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getItemCount() {

        String lblMediCountText = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(Item_Id) FROM store");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int regCustCount = resultSet.getInt(1);
                lblMediCountText = String.valueOf(regCustCount);
            }
            if (lblMediCountText != null) {
                lblMedicineCount.setText(lblMediCountText);
            } else {
                lblMedicineCount.setText("0");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
