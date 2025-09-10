package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ItemDetailsController implements Initializable {

    @FXML
    private Button addItemButton;

    @FXML
    private TextField qtyOnHandTextField;

    @FXML
    private Button clearIFormButton;

    @FXML
    private Button deleteItemButtom;




    @FXML
    private TextField descriptionTextField;

    //Table and Columns
    @FXML
    private TableView<Item> detailsTable;

    @FXML
    private TableColumn<?, ?> itemCodeColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> packSizeColumn;

    @FXML
    private TableColumn<?, ?> unitPriceColumn;

    @FXML
    private TableColumn<?, ?> qtyOnHandColumn;

    //Text Field
    @FXML
    private TextField itemCodeTextField;

    @FXML
    private TextField packSizeTextField;

    @FXML
    private TextField unitPriceTextField;

    @FXML
    private Button updateItemDetailsButton;

    //Error Message
    @FXML
    private Label itemCodeErrorMessage;

    @FXML
    private Label descriptionErrorMessage;

    @FXML
    private Label packSizeErrorMessage;

    @FXML
    private Label unitPriceErrorMessage;

    @FXML
    private Label qtyOnHandErrorMessage;


    @FXML
    void onAddItem(ActionEvent event) {
        addItemDetails(
                new Item(
                        itemCodeTextField.getText(),
                        descriptionTextField.getText(),
                        packSizeTextField.getText(),
                        Double.parseDouble(unitPriceTextField.getText()),
                        Integer.parseInt(qtyOnHandTextField.getText())

                )
        );
        clearForm();

    }

    @FXML
    void onClearForm(ActionEvent event) {
        clearForm();
        clearErrorMessage();
    }

    @FXML
    void onDeleteItem(ActionEvent event) {
        deleteItemDetails(itemCodeTextField.getText());
        clearForm();
    }

    @FXML
    void onUpdateItem(ActionEvent event) {
        updateItemDetails(
                new Item(
                        itemCodeTextField.getText(),
                        descriptionTextField.getText(),
                        packSizeTextField.getText(),
                        Double.parseDouble(unitPriceTextField.getText()),
                        Integer.parseInt(qtyOnHandTextField.getText())

                )
        );
        clearForm();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearErrorMessage();
        setCellValueFactory();
        setDetailsTable();
        getValuesOnTableRowClick();


    }

    private void clearErrorMessage(){
        itemCodeErrorMessage.setText("");
        descriptionErrorMessage.setText("");
        packSizeErrorMessage.setText("");
        unitPriceErrorMessage.setText("");
        qtyOnHandErrorMessage.setText("");
    }

    private void setCellValueFactory() {
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        packSizeColumn.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        qtyOnHandColumn.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private void setDetailsTable(){
        detailsTable.setItems(getAllCustomerDetails());
    }

    public ObservableList<Item> getAllCustomerDetails() {
        ObservableList<Item> itemDetails = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement("Select * FROM item;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Item(
                        resultSet.getString("itemCode"),
                        resultSet.getString("description"),
                        resultSet.getString("packSize"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("qtyOnHand")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

    private void getValuesOnTableRowClick() {
        detailsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        itemCodeTextField.setText(String.valueOf(newValue.getItemCode()));
                        descriptionTextField.setText(String.valueOf(newValue.getDescription()));
                        packSizeTextField.setText(String.valueOf(newValue.getPackSize()));
                        unitPriceTextField.setText(String.valueOf(newValue.getUnitPrice()));
                        qtyOnHandTextField.setText(String.valueOf(newValue.getQtyOnHand()));;
                    }
                }
        );
    }

    //Database

    public void addItemDetails(Item item) {

        String SQL = "INSERT INTO item(itemCode, description, packSize, unitPrice, qtyOnHand) VALUES(?,?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1, item.getItemCode());
            preparedStatement.setObject(2, item.getDescription());
            preparedStatement.setObject(3, item.getPackSize());
            preparedStatement.setObject(4, item.getUnitPrice());
            preparedStatement.setObject(5, item.getQtyOnHand());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDetailsTable();
    }

    private void clearForm(){
        itemCodeTextField.setText("");
        descriptionTextField.setText("");
        packSizeTextField.setText("");
        unitPriceTextField.setText("");
        unitPriceTextField.setText("");
        qtyOnHandTextField.setText("");
    }

    public void deleteItemDetails(String itemId) {
        String SQL = "delete from item where itemCode='" + itemId + "'";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDetailsTable();
    }

    public void updateItemDetails(Item item) {
        String SQL = """
                UPDATE item
                SET  description = ?, packSize = ?, unitPrice = ?, qtyOnHand = ?
                WHERE itemCode = ?;
                """;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setObject(1, item.getDescription());
            preparedStatement.setObject(2, item.getPackSize());
            preparedStatement.setObject(3, item.getUnitPrice());   // LocalDate will auto-map if using MySQL 8+ driver
            preparedStatement.setObject(4, item.getQtyOnHand());
            preparedStatement.setObject(5, item.getItemCode()); // WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("No customer found with id: " + item.getItemCode());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setDetailsTable(); // refresh your TableView
    }



}
