package edu.icet.Controller;

import db.DBConnection;
import edu.icet.Model.getData;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.EventObject;
import java.util.Objects;

import static java.util.Objects.*;
import static javafx.fxml.FXMLLoader.load;

public class HelloController {


    @FXML
    private AnchorPane admin_form;

    @FXML
    private Hyperlink admin_hyperLink;

    @FXML
    private Button admin_loginBtn;

    @FXML
    private PasswordField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private AnchorPane employee_form;

    @FXML
    private Hyperlink employee_hyperLink;

    @FXML
    private TextField employee_id;

    @FXML
    private Button employee_loginBtn;

    @FXML
    private PasswordField employee_password;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button side_GetBtn;

    @FXML
    private Button side_JustBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private AnchorPane supplier_form;

    @FXML
    private Hyperlink supplier_hyperLink;

    @FXML
    private TextField supplier_id;

    @FXML
    private Button supplier_loginBtn;

    @FXML
    private PasswordField supplier_password;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private double x = 0;
    private double y = 0;

    public void employeeLogin() {
        String employeeData = "SELECT * FROM employee WHERE employeeId = ? and password = ?";
        connect = DBConnection.getInstance().getConnection();
        try {
            Alert alert;

            prepare = connect.prepareStatement(employeeData);

            if (employee_id.getText().isEmpty() || employee_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepare.setString(1, employee_id.getText());
                prepare.setString(2, employee_password.getText());
                result = prepare.executeQuery();

                if (result.next()) {

                    getData.employeeId = employee_id.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    employee_loginBtn.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/icet/view/EmployeeDashboard.fxml"));
                    Parent root = loader.load();


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
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Employee ID/password");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void supplierLogin(){
        String employeeData ="SELECT * FROM  supplier WHERE supplier_id = ? and password = ?";
        connect = DBConnection.getInstance().getConnection();
        try{

            Alert alert;

            prepare = connect.prepareStatement(employeeData);
            if (supplier_id.getText().isEmpty()
                    || supplier_password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                prepare.setString(1, supplier_id.getText());
                prepare.setString(2, supplier_password.getText());
                result = prepare.executeQuery();

                if (result.next()){

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    supplier_loginBtn.getScene().getWindow().hide();

                    Parent root = load(requireNonNull(getClass().getResource("supplierDashboard.fxml")));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();

                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Supplier ID/password");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){e.printStackTrace();}

    }

    public void adminLogin(){
        String adminData = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = DBConnection.getInstance().getConnection();
        try{

            Alert alert;
            if (admin_username.getText().isEmpty()
                    || admin_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
                prepare = connect.prepareStatement(adminData);
                prepare.setString(1, admin_username.getText());
                prepare.setString(2, admin_password.getText());
                result = prepare.executeQuery();

                if (result.next()){

                    getData.username = admin_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    admin_loginBtn.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/icet/View/adminDashboard.fxml"));
                    Parent root = loader.load();


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
                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/password");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){e.printStackTrace();}

    }

    public void switchForm(ActionEvent actionEvent) {

        if (actionEvent.getSource() == admin_hyperLink) {
            admin_form.setVisible(false);
            employee_form.setVisible(true);
        } else if (actionEvent.getSource() == employee_hyperLink) {
            admin_form.setVisible(true);
            employee_form.setVisible(false);
        }


        TranslateTransition slider = new TranslateTransition();
        slider.setDuration(Duration.seconds(0.5));
        slider.setNode(side_form);

        if (actionEvent.getSource() == admin_hyperLink) {

            slider.setToX(300);
            slider.setOnFinished(e -> {
                side_GetBtn.setVisible(true);
                side_JustBtn.setVisible(false);
                admin_form.setVisible(true);
                employee_form.setVisible(false);
            });
            slider.play();

        } else if (actionEvent.getSource() == supplier_hyperLink) {

            slider.setToX(0);
            slider.setOnFinished(e -> {
                side_GetBtn.setVisible(false);
                side_JustBtn.setVisible(true);
                admin_form.setVisible(false);
                employee_form.setVisible(true);
            });
            slider.play();
        }

        if (actionEvent.getSource() == side_JustBtn) {
            slider.setToX(300);
            slider.setOnFinished(e -> {
                side_GetBtn.setVisible(true);
                side_JustBtn.setVisible(false);

            });
            slider.play();

        } else if (actionEvent.getSource() == side_GetBtn) {
            slider.setToX(0);
            slider.setOnFinished(e -> {
                side_GetBtn.setVisible(false);
                side_JustBtn.setVisible(true);
            });
            slider.play();
        }
    }

    public void close() {
        System.exit(0);
    }
}