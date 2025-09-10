package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerDetailsController implements Initializable {


    @FXML
    private Button addCustomerButton;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> cityColumn;


    @FXML
    private Button clearIFormButton;


    @FXML
    private Button deleteCustomerButtom;

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
    private Button updateCustomerDetailsButton;

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


    @FXML
    void onAddCustomer(ActionEvent event) {
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
    void onClearForm(ActionEvent event) {
        clearErrorMessages();
        clearTextFields();
    }

    @FXML
    void onDeleteCustomer(ActionEvent event) {
        deleteCustomerDetails(customerIDTextField.getText());

    }

    @FXML
    void onUpdateCustomer(ActionEvent event) {
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

    private void setDetailsTable() {
        detailsTable.setItems(getAllCustomerDetails());
    }

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
    }

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

    public ObservableList<Customer> getAllCustomerDetails() {
        ObservableList<Customer> itemDetails = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement("Select * FROM customer;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemDetails.add(new Customer(
                                resultSet.getString("id"),
                                resultSet.getString("title"),
                                resultSet.getString("name"),
                                resultSet.getDate("dob").toLocalDate(),
                                resultSet.getDouble("salary"),
                                resultSet.getString("address"),
                                resultSet.getString("city"),
                                resultSet.getString("province"),
                                resultSet.getString("postalCode")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemDetails;
    }

    public void addCustomerDetails(Customer customer) {

        String SQL = "INSERT INTO customer(id, title, name, dob, salary, address, city, province, postalCode) VALUES(?,?,?,?,?,?,?,?,?);";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getTitle());
            preparedStatement.setObject(3, customer.getName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setObject(5, customer.getSalary());
            preparedStatement.setObject(6, customer.getAddress());
            preparedStatement.setObject(7, customer.getCity());
            preparedStatement.setObject(8, customer.getProvince());
            preparedStatement.setObject(9, customer.getPostalCode());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDetailsTable();
    }

    public void deleteCustomerDetails(String customerId) {
        String SQL = "delete from customer where id='" + customerId + "'";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setDetailsTable();
    }

    public void updateCustomerDetails(Customer customer) {
        String SQL = """
                UPDATE customer
                SET title = ?, name = ?, dob = ?, salary = ?, address = ?, city = ?, province = ?, postalCode = ?
                WHERE id = ?;
                """;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/thogakade_modern", "root", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setObject(1, customer.getTitle());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getDob());   // LocalDate will auto-map if using MySQL 8+ driver
            preparedStatement.setObject(4, customer.getSalary());
            preparedStatement.setObject(5, customer.getAddress());
            preparedStatement.setObject(6, customer.getCity());
            preparedStatement.setObject(7, customer.getProvince());
            preparedStatement.setObject(8, customer.getPostalCode());
            preparedStatement.setObject(9, customer.getId());    // WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("No customer found with id: " + customer.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setDetailsTable(); // refresh your TableView
    }



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
