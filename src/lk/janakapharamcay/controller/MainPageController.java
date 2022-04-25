package lk.janakapharamcay.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.janakapharamcay.db.DBConnection;
import lk.janakapharamcay.model.Prescription;
import lk.janakapharamcay.model.PrescriptionDetail;
import lk.janakapharamcay.view.tm.CartTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class MainPageController {

    public JFXTextField txtCustmerTp;
    public JFXTextField txtCustmerNic;
    public JFXTextField txtCustmerName;
    public TableView<CartTm> tblOrderDetail;
    public TableColumn tbItemid;
    public TableColumn ibBrand;
    public TableColumn tbDiscription;
    public TableColumn tbunitprice;
    public TableColumn tbQty;
    public TableColumn tbPrice;
    public JFXTextField txtAddQty;
    public JFXTextField txtBrand;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtDiscription;
    public JFXTextField txtUnitPrice;
    public TextField txtOrderTotal;
    public TextField txtOrderCash;
    public TextField txtOrderBalance;
    public Label lbtime;
    public Label lbOrder;
    public Label lbDate;
    public AnchorPane dashBoardContext;
    public JFXTextField txtCustmerSirname;
    public JFXTextField txtId;
    public JFXTextField txtDocId;
    public Label lblEmpId;
    public JFXTextField txtCustmerId;
    ObservableList<CartTm> obList = FXCollections.observableArrayList();
    int cartSelectedRowForRemove = -1;


    public void initialize() throws SQLException, ClassNotFoundException {

        tbItemid.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        ibBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tbDiscription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbunitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tbQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tbPrice.setCellValueFactory(new PropertyValueFactory<>("orderItemPrice"));

        loadDateAndTime();

        tblOrderDetail.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

        generatePrecriptionId();

    }

    public void setLblEmpId(String empId) {
        lblEmpId.setText(empId);
    }

    private void generatePrecriptionId() {

        String lastOrderId = null;

        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().
                    prepareStatement("SELECT P_Number FROM prescription_detail ORDER BY P_Number DESC LIMIT 1");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lastOrderId = resultSet.getString(1);
            }

            if (lastOrderId != null) {
                lastOrderId = lastOrderId.split("[A-Z]")[1];
                if (Integer.parseInt(lastOrderId) < 9) {
                    lastOrderId = "O00" + (Integer.parseInt(lastOrderId) + 1);
                    lbOrder.setText(lastOrderId);
                } else if (Integer.parseInt(lastOrderId) < 100) {
                    lastOrderId = "O0" + (Integer.parseInt(lastOrderId) + 1);
                    lbOrder.setText(lastOrderId);
                }
            } else {
                lbOrder.setText("O001");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void RemoveOrderDetailOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "please select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            tblOrderDetail.refresh();
        }
    }

    public void calculateBalance(ActionEvent actionEvent) {

        if (!txtOrderCash.getText().isEmpty()){
            double orderTotal = Double.parseDouble(txtOrderTotal.getText());
            double custCash = Double.parseDouble(txtOrderCash.getText());
            double cashBalance = custCash - orderTotal;

            cashBalance = cashBalance*100;
            cashBalance = (double) ((int)cashBalance);
            cashBalance = cashBalance/100;

            txtOrderBalance.setText(Double.toString(cashBalance));
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Please Enter Amount to calculate Balance").show();
        }
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        PrescriptionDetail prescriptionDetail = new PrescriptionDetail(lbOrder.getText(), txtCustmerId.getText(),
                txtDocId.getText(), lblEmpId.getText(),
                lbDate.getText(), lbtime.getText(), Double.parseDouble(txtOrderTotal.getText()));

        ArrayList<Prescription> prescriptionArrayList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            prescriptionArrayList.add(new Prescription(lbOrder.getText(), cartTm.getItemId(), cartTm.getBrand(),
                    cartTm.getDescription(), cartTm.getQty(), cartTm.getUnitPrice(), cartTm.getOrderItemPrice()));
        }

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            if (cashPayments()) {
                boolean addOrder = addOrder(prescriptionDetail);
                if (addOrder) {
                    boolean addOrderDetails = addOrderDetails(prescriptionArrayList);
                    if (addOrderDetails) {
                        boolean updateItemQty = updateItemQty(prescriptionArrayList);
                        if (updateItemQty) {
                            connection.commit();
                            new Alert(Alert.AlertType.CONFIRMATION, "Order placed successfully!!!!", ButtonType.OK).show();
                        }
                    }
                }else {
                    connection.rollback();
                    new Alert(Alert.AlertType.WARNING, "Order placed UnSuccessfully!!!!", ButtonType.OK).show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Cash should be greater than total!!!!", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

        clearAllTextFields();

        generatePrecriptionId();
    }

    public boolean cashPayments() {
        double total = Double.parseDouble(txtOrderTotal.getText());
        double cash = Double.parseDouble(txtOrderCash.getText());
        return cash > total;
    }

    public void printOnAction(ActionEvent actionEvent) {
    }

    public void PrintreportOnAction(ActionEvent actionEvent) {
    }

    public void addOrderDetailOnAction(ActionEvent actionEvent) {
        String itemId = txtId.getText();
        String brand = txtBrand.getText();
        String discription = txtDiscription.getText();
        double unitprice = Double.parseDouble(txtUnitPrice.getText());
        int qtyonHand = Integer.parseInt(txtQtyOnHand.getText());
        int customerQty = Integer.parseInt(txtAddQty.getText());
        double total = customerQty * unitprice;

        if (qtyonHand < customerQty) {
            new Alert(Alert.AlertType.WARNING, "Invalid amount").show();
            return;
        }

        CartTm tm = new CartTm(itemId, brand, discription, unitprice, customerQty, total);
        int rowNumber = isExists(tm);
        if (isExists(tm) == -1) {//adding new value
            obList.add(tm);
        } else {//update
            CartTm temptm = obList.get(rowNumber);
            CartTm newTm = new CartTm(temptm.getItemId(), temptm.getBrand(), temptm.getDescription(), unitprice, temptm.getQty() + customerQty, total + temptm.getOrderItemPrice());
            if (qtyonHand < temptm.getQty()) {
                new Alert(Alert.AlertType.WARNING, "Invalid amount").show();
                return;
            }

            obList.remove(rowNumber);
            obList.add(newTm);

        }

        tblOrderDetail.setItems(obList);
        calCulateCost();
    }

    //----------------------------------------------------------------------------------------------
    //Date and time.

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemId().equals(obList.get(i).getItemId())) {
                return i;
            }
        }
        return -1;
    }
    //-----------------------------------------------------------------------------------------------------

    void calCulateCost() {
        double ttl = 0;
        for (CartTm tm : obList) {
            ttl += tm.getOrderItemPrice();
        }
        txtOrderTotal.setText(String.valueOf(ttl));
    }

    public void SearchIdOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM store WHERE  Item_Id=?");
            preparedStatement.setObject(1, txtId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                txtBrand.setText(resultSet.getString(4));
                txtDiscription.setText(resultSet.getString(5));
                txtQtyOnHand.setText(resultSet.getString(6));
                txtUnitPrice.setText(resultSet.getString(7));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        txtAddQty.clear();
        txtAddQty.requestFocus();

    }

    public void SearchBrandOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM store WHERE Brand_name=?");
            preparedStatement.setObject(1, txtBrand.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                txtId.setText(resultSet.getString(1));
                txtDiscription.setText(resultSet.getString(5));
                txtQtyOnHand.setText(resultSet.getString(6));
                txtUnitPrice.setText(resultSet.getString(7));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtAddQty.requestFocus();

    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM registered_customer WHERE Cus_teliphone_number=?");
            preparedStatement.setObject(1, txtCustmerTp.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                txtCustmerId.setText(resultSet.getString(1));
                txtCustmerName.setText(resultSet.getString(2));
                txtCustmerSirname.setText(resultSet.getString(3));
                txtCustmerNic.setText(resultSet.getString(6));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtId.requestFocus();

    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lbDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lbtime.setText(currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void CashierDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    private void setUi(String location) throws IOException {
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(FXMLLoader.load(this.getClass().getResource("/lk/janakapharamcay/view/" + location + ".fxml")));
    }

    //=====================transaction part====================================
    public static boolean addOrder(PrescriptionDetail presDetail) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO prescription_detail VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setObject(1, presDetail.getPrescriptionNumber());
            preparedStatement.setObject(2, presDetail.getRegCustId());
            preparedStatement.setObject(3, presDetail.getDocId());
            preparedStatement.setObject(4, presDetail.getEmpId());
            preparedStatement.setObject(5, presDetail.getPrescriptionDate());
            preparedStatement.setObject(6, presDetail.getPrescriptionTime());
            preparedStatement.setObject(7, presDetail.getPrescriptionCost());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addOrderDetails(ArrayList<Prescription> arrayList) throws SQLException, ClassNotFoundException {
        for (Prescription prescription : arrayList) {
            boolean addOrderDetails = addOrderDetails(prescription);
            if (!addOrderDetails) {
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderDetails(Prescription prescription) throws SQLException, ClassNotFoundException {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Prescription VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setObject(1, prescription.getPrescriptionNumber());
            preparedStatement.setObject(2, prescription.getItemId());
            preparedStatement.setObject(3, prescription.getBrandName());
            preparedStatement.setObject(4, prescription.getDescription());
            preparedStatement.setObject(5, prescription.getQty());
            preparedStatement.setObject(6, prescription.getUnitPrice());
            preparedStatement.setObject(7, prescription.getOrderItemPrice());
            return preparedStatement.executeUpdate() > 0 ;
    }

    public static boolean updateItemQty(ArrayList<Prescription> arrayList) throws SQLException, ClassNotFoundException {
        for (Prescription prescription : arrayList) {
            boolean updateQty = updateItemQty(prescription);
            if (!updateQty) {
                return false;
            }
        }
        return true;
    }

    public static boolean updateItemQty(Prescription prescription) throws SQLException, ClassNotFoundException {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE store SET qtyOnHand=qtyOnHand-? WHERE Item_Id=?");
            statement.setObject(1, prescription.getQty());
            statement.setObject(2, prescription.getItemId());
            return statement.executeUpdate() > 0;
    }

    private void clearAllTextFields(){
        txtCustmerTp.clear();
        txtCustmerId.clear();
        txtCustmerName.clear();
        txtCustmerSirname.clear();
        txtCustmerNic.clear();
        txtId.clear();
        txtBrand.clear();
        txtUnitPrice.clear();
        txtAddQty.clear();
        txtDiscription.clear();
        txtQtyOnHand.clear();
        txtDocId.clear();
        txtOrderTotal.clear();
        txtOrderCash.clear();
        txtOrderBalance.clear();
        for ( int i = 0; i<tblOrderDetail.getItems().size(); i++) {
            tblOrderDetail.getItems().clear();
        }
    }

}

