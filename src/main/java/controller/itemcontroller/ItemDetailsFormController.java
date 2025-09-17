package controller.itemcontroller;


import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

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

    private final ItemDetailsService service = new ItemDetailsController();

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
        return service.getAllCustomerDetails();
    }

    public void addItemDetails(Item item) {
        service.addItemDetails(item);
        setDetailsTable();
    }


    public void deleteItemDetails(String itemId) {
        service.deleteItemDetails(itemId);
        setDetailsTable();
    }

    public void updateItemDetails(Item item) {
        service.updateItemDetails(item);
        setDetailsTable();
    }


}
