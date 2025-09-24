package controller.pages.itemcontroller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import repository.ItemTable.ItemTable;
import repository.Thogakade_Modern;

import java.net.URL;

import java.util.ResourceBundle;

public class ItemDetailsFormController implements Initializable {

    //Buttons
    @FXML
    private Button addItemButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private Button clearIFormButton;

    @FXML
    private Button updateItemDetailsButton;


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
    private TextField qtyOnHandTextField;

    @FXML
    private TextField descriptionTextField;

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

    private Thogakade_Modern<Item> thogakade_modern = new ItemTable();

    @FXML
    void onAddItem() {
        addItemDetails(
                new Item(
                        itemCodeTextField.getText(),
                        descriptionTextField.getText(),
                        packSizeTextField.getText(),
                        Double.parseDouble(unitPriceTextField.getText()),
                        Integer.parseInt(qtyOnHandTextField.getText()),
                        "itemImage"
                )
        );
        clearForm();

    }

    @FXML
    void onClearForm() {
        clearForm();
        clearErrorMessage();
    }

    @FXML
    void onDeleteItem() {
        deleteItemDetails(itemCodeTextField.getText());
    }

    @FXML
    void onUpdateItem() {
        updateItemDetails(
                new Item(
                        itemCodeTextField.getText(),
                        descriptionTextField.getText(),
                        packSizeTextField.getText(),
                        Double.parseDouble(unitPriceTextField.getText()),
                        Integer.parseInt(qtyOnHandTextField.getText()),
                        "itemImage"

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

    //Clean UI
    private void clearErrorMessage() {
        itemCodeErrorMessage.setText("");
        descriptionErrorMessage.setText("");
        packSizeErrorMessage.setText("");
        unitPriceErrorMessage.setText("");
        qtyOnHandErrorMessage.setText("");
    }

    private void clearForm() {
        itemCodeTextField.setText("");
        descriptionTextField.setText("");
        packSizeTextField.setText("");
        unitPriceTextField.setText("");
        unitPriceTextField.setText("");
        qtyOnHandTextField.setText("");
        detailsTable.getSelectionModel().clearSelection();
    }

    //Setting ID to identify to set values on each column
    private void setCellValueFactory() {
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        packSizeColumn.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        qtyOnHandColumn.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    //Get Values From Table When the table row is selected
    private void getValuesOnTableRowClick() {
        detailsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        itemCodeTextField.setText(String.valueOf(newValue.getItemCode()));
                        descriptionTextField.setText(String.valueOf(newValue.getDescription()));
                        packSizeTextField.setText(String.valueOf(newValue.getPackSize()));
                        unitPriceTextField.setText(String.valueOf(newValue.getUnitPrice()));
                        qtyOnHandTextField.setText(String.valueOf(newValue.getQtyOnHand()));
                    }
                }
        );
    }

    //Database
    private void setDetailsTable() {
        detailsTable.setItems(getAllCustomerDetails());
    }

    public ObservableList<Item> getAllCustomerDetails() {
        return FXCollections.observableArrayList(thogakade_modern.getAllData());
    }

    public void addItemDetails(Item item) {
        thogakade_modern.insertAnItem(item);
        setDetailsTable();
    }


    public void deleteItemDetails(String itemId) {
        thogakade_modern.deleteAnItem(itemId);
        setDetailsTable();
    }

    public void updateItemDetails(Item item) {
        thogakade_modern.updateAnItem(item);
        setDetailsTable();
    }


}
