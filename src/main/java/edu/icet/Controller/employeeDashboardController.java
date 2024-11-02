package edu.icet.Controller;

import db.DBConnection;
import edu.icet.Model.customerData;
import edu.icet.Model.getData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class employeeDashboardController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_brand;

    @FXML
    private TableColumn<customerData, String> purchase_col_brand;

    @FXML
    private TableColumn<customerData, BigDecimal> purchase_col_price;

    @FXML
    private TableColumn<customerData, String> purchase_col_productName;

    @FXML
    private TableColumn<customerData, String> purchase_col_quantity;

    @FXML
    private Label purchase_employeeId;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private ComboBox<String> purchase_productName;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Button purchase_receiptBtn;

    @FXML
    private TableView<customerData> purchase_tableView;

    @FXML
    private Label purchase_total;


    private double x = 0;
    private double y = 0;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private BigDecimal price = BigDecimal.valueOf(0);
    private SpinnerValueFactory<Integer> spinner;
    private ObservableList<customerData> purchaseList;
    private int customerId;
    private int qty;

    public void setPurchaseAdd() throws SQLException {
        purchaseCustomerId();
        purchaseSpinnerValue();
        purchaseGetPrice();

        String insertProd = "INSERT INTO customer (customer_id, brand, productName, quantity, price, date) VALUES (?, ?, ?, ?, ?, ?)";
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothingStore", "root", "12345");

        try {
            Alert alert;
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

            if (purchase_brand.getText().isEmpty() || purchase_productName.getSelectionModel().getSelectedItem() == null || qty == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose product/s first");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(insertProd);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, purchase_brand.getText());
                prepare.setString(3, purchase_productName.getSelectionModel().getSelectedItem());
                prepare.setString(4, String.valueOf(qty));
                prepare.setString(5, String.valueOf(price));
                prepare.setString(6, String.valueOf(sqlDate));

                prepare.executeUpdate();
                purchaseListData();
                purchaseShowListData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseGetPrice() throws SQLException {
        String gPrice = "SELECT price FROM product WHERE product_name = '" + purchase_productName.getSelectionModel().getSelectedItem() + "'";
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothingStore", "root", "12345");
        System.out.println("hi");
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(gPrice);

            if (result.next()) {
                price = result.getBigDecimal("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseSearchBrand() throws SQLException {
        String searchB = "SELECT * FROM product WHERE brand = ? AND status = 'Available'";
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothingStore", "root", "12345");

        try {
            prepare = connect.prepareStatement(searchB);
            prepare.setString(1, purchase_brand.getText());
            result = prepare.executeQuery();

            ObservableList<String> listProduct = FXCollections.observableArrayList();

            if (result.next()) {
                listProduct.add(result.getString("product_name"));
                while (result.next()) {
                    listProduct.add(result.getString("product_name"));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Brand: " + purchase_brand.getText() + " not found.");
                alert.showAndWait();
            }
            purchase_productName.setItems(listProduct);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (result != null) result.close();
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        }
    }

    public void purchaseSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    public void purchaseSpinnerValue() {
        qty = purchase_quantity.getValue();
    }

    public ObservableList<customerData> purchaseListData() throws SQLException {
        purchaseCustomerId();
        ObservableList<customerData> customerList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM  customer WHERE customer_id = '" + customerId + "'";
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothingStore", "root", "12345");

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerData custD = new customerData(
                        result.getInt("customer_id"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getInt("quantity"),
                        result.getBigDecimal("price"),
                        result.getDate("date")
                );

                customerList.add(custD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public void purchaseShowListData() throws SQLException {
        purchaseList = purchaseListData();

        purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseList);
    }

    public void purchaseCustomerId() throws SQLException {
        String cID = "SELECT customer_id FROM customer";
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothingStore", "root", "12345");
        try {
            prepare = connect.prepareStatement(cID);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }

            String checkCustomerId = "SELECT customer_id FROM customerreceipt";
            statement = connect.createStatement();
            result = statement.executeQuery(checkCustomerId);

            if (result.next()) {
                customerId += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayEmployeeId() {
        purchase_employeeId.setText(getData.employeeId);
    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/icet/View/Clothing-FXML.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(0.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> stage.setOpacity(1));

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayEmployeeId();
        try {
            purchaseShowListData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        purchaseSpinner();
        purchaseSpinnerValue();
        try {
            setPurchaseAdd();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
