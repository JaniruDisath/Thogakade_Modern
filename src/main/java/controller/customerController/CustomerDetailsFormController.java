package controller.customerController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDetailsFormController implements Initializable {


    //Buttons
//    @FXML
//    private Button addCustomerButton;
//
//    @FXML
//    private Button clearIFormButton;
//
//    @FXML
//    private Button deleteCustomerButton;
//
//    @FXML
//    private Button updateCustomerDetailsButton;

    //Table and Columns

    @FXML
    private TableView<Customer> detailsTable;

    @FXML
    private TableColumn<?, ?> customerIDColumn;

    @FXML
    private TableColumn<?, ?> dobColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> postalCodeColumn;

    @FXML
    private TableColumn<?, ?> provinceColumn;

    @FXML
    private TableColumn<?, ?> salaryColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> cityColumn;


    //Error Messages
    @FXML
    private Label customerIDErrorMessage;

    @FXML
    private Label titleErrorMessage;

    @FXML
    private Label nameErrorMessage;

    @FXML
    private Label dobErrorMessage;

    @FXML
    private Label salaryErrorMessage;

    @FXML
    private Label addressErrorMessage;

    @FXML
    private Label cityErrorMessage;

    @FXML
    private Label provinceErrorMessage;

    @FXML
    private Label postalCodeErrorMessage;

    //Input Fields

    @FXML
    private TextField customerIDTextField;

    @FXML
    private ComboBox<String> titleComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private ComboBox<String> provinceComboBox;

    @FXML
    private TextField postalCodeTextField;

    private final CustomerDetailsService service  = new CustomerDetailsController();


    @FXML
    void onAddCustomer() {
        addCustomerDetails(new Customer(
                customerIDTextField.getText(),
                titleComboBox.getValue(),
                nameTextField.getText(),
                dobDatePicker.getValue(),
                Double.parseDouble(salaryTextField.getText()),
                addressTextField.getText(),
                cityTextField.getText(),
                provinceComboBox.getValue(),
                postalCodeTextField.getText()
        ));
    }

    @FXML
    void onClearForm() {
        clearErrorMessages();
        clearTextFields();
    }

    @FXML
    void onDeleteCustomer() {
        deleteCustomerDetails(customerIDTextField.getText());
    }

    @FXML
    void onUpdateCustomer() {
        updateCustomerDetails(new Customer(
                customerIDTextField.getText(),
                titleComboBox.getValue(),
                nameTextField.getText(),
                dobDatePicker.getValue(),
                Double.parseDouble(salaryTextField.getText()),
                addressTextField.getText(),
                cityTextField.getText(),
                provinceComboBox.getValue(),
                postalCodeTextField.getText()
        ));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearErrorMessages();
        setTitleComboBoxElements();
        setProvinceComboBoxElements();
        setCellValueFactory();
        setDetailsTable();
        getValuesOnTableRowClick();
    }

    //Clean UI
    private void clearErrorMessages() {
        customerIDErrorMessage.setText("");
        titleErrorMessage.setText("");
        nameErrorMessage.setText("");
        dobErrorMessage.setText("");
        salaryErrorMessage.setText("");
        addressErrorMessage.setText("");
        cityErrorMessage.setText("");
        provinceErrorMessage.setText("");
        postalCodeErrorMessage.setText("");
    }

    private void clearTextFields() {
        customerIDTextField.setText("");
        titleComboBox.getSelectionModel().clearSelection();
        nameTextField.setText("");
        dobDatePicker.setValue(null);
        salaryTextField.setText("");
        addressTextField.setText("");
        cityTextField.setText("");
        provinceComboBox.getSelectionModel().clearSelection();
        postalCodeTextField.setText("");
        detailsTable.getSelectionModel().clearSelection();
    }

    //Combo Box Values

    private void setTitleComboBoxElements() {
        ObservableList<String> titles = FXCollections.observableArrayList(
                "Mr",
                "Mrs",
                "Ms");
        titleComboBox.setItems(titles);
    }

    private void setProvinceComboBoxElements() {
        ObservableList<String> provinces = FXCollections.observableArrayList(
                "Southern",
                "Western",
                "Sabaragamuwa",
                "Uva",
                "Central",
                "Eastern",
                "North Western",
                "North Central",
                "Northern");
        provinceComboBox.setItems(provinces);
    }

    //Setting ID to identify to set values on each column
    private void setCellValueFactory() {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
    }


    //Database Functions

    private void setDetailsTable() {
        detailsTable.setItems(getAllCustomerDetails());
    }

    public ObservableList<Customer> getAllCustomerDetails() {
        return service.getAllCustomerDetails();
    }

    public void addCustomerDetails(Customer customer) {
        service.addCustomerDetails(customer);
        setDetailsTable();
        clearErrorMessages();
        clearTextFields();
    }

    public void deleteCustomerDetails(String customerId) {
        service.deleteCustomerDetails(customerId);
        setDetailsTable();
        clearErrorMessages();
        clearTextFields();
    }

    public void updateCustomerDetails(Customer customer) {
        service.updateCustomerDetails(customer);
        setDetailsTable();
        clearErrorMessages();
        clearTextFields();
    }

    //Get Values From Table When the table row is selected
    private void getValuesOnTableRowClick() {
        detailsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        customerIDTextField.setText(String.valueOf(newValue.getId()));
                        titleComboBox.setValue(newValue.getTitle());
                        nameTextField.setText(newValue.getName());
                        dobDatePicker.setValue(newValue.getDob());
                        salaryTextField.setText(String.valueOf(newValue.getSalary()));
                        addressTextField.setText(newValue.getAddress());
                        cityTextField.setText(newValue.getCity());
                        provinceComboBox.setValue(newValue.getProvince());
                        postalCodeTextField.setText(newValue.getPostalCode());
                    }
                }
        );
    }

}
