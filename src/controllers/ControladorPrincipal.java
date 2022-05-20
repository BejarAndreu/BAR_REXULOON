/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alumneinf
 */
public class ControladorPrincipal implements Initializable {

    @FXML
    private ImageView mesa1;
    @FXML
    private ImageView mesa2;
    @FXML
    private ImageView mesa3;
    @FXML
    private ImageView mesa4;
    @FXML
    private ImageView mesa5;
    @FXML
    private ImageView mesa6;
    
    public static int mesa = 0;
    
    
    
    public static Connection Connexion() throws ClassNotFoundException, SQLException{
        String myDriver = "org.postgresql.Driver";
        Class.forName(myDriver);
        Connection conn = DriverManager.getConnection("jdbc:postgresql://10.2.205.28:5432/bar2","bar", "bar");
        return conn;
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mesa1.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Parent root2 = (Parent)fxmlLoader.load();
                    Stage stage3 = new Stage();
                    stage3.setScene(new Scene(root2));
                    stage3.show();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                mesa = 1;
                System.out.println(mesa);
            }
        });
        
        mesa2.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Parent root2 = (Parent)fxmlLoader.load();
                    Stage stage3 = new Stage();
                    stage3.setScene(new Scene(root2));
                    stage3.show();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                mesa = 2;
                System.out.println(mesa);
            }
        });
        
        mesa3.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Parent root2 = (Parent)fxmlLoader.load();
                    Stage stage3 = new Stage();
                    stage3.setScene(new Scene(root2));
                    stage3.show();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                mesa = 3;
                System.out.println(mesa);
            }
        });
        
        mesa4.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Parent root2 = (Parent)fxmlLoader.load();
                    Stage stage3 = new Stage();
                    stage3.setScene(new Scene(root2));
                    stage3.show();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                mesa = 4;
                System.out.println(mesa);
            }
        });
        
        mesa5.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Parent root2 = (Parent)fxmlLoader.load();
                    Stage stage3 = new Stage();
                    stage3.setScene(new Scene(root2));
                    stage3.show();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                mesa = 5;
                System.out.println(mesa);
            }
        });
        
        mesa6.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    Parent root2 = (Parent)fxmlLoader.load();
                    Stage stage3 = new Stage();
                    stage3.setScene(new Scene(root2));
                    stage3.show();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                mesa = 6;
                System.out.println(mesa);
            }
        });
        
    }    
    
    
    
}
