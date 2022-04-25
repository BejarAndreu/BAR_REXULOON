/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.ControladorOperacion.obten_idFactura;
import static controllers.ControladorPrincipal.mesa;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javafx.event.EventHandler;

import java.sql.SQLException;
import java.sql.Statement;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static sun.misc.Signal.handle;
import static controllers.ControladorPrincipal.Connexion;

/**
 *
 * @author alumneinf
 */
public class ControladorProductos implements Initializable {
    
    
    @FXML
    private ImageView coca;
    @FXML
    private ImageView bocata;
    @FXML
    private ImageView fanta;
    @FXML
    private ImageView agua;
    @FXML
    private ImageView pizza;
    @FXML
    private ImageView hamburguesa;
    
    
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //COMO CREAR UN IMAGEVIEW
        //https://www.tutorialspoint.com/how-to-display-an-image-in-javafx#:~:text=Create%20a%20FileInputStream%20representing%20the,to%20the%20setImage()%20method.
        
        
        coca.setOnMouseClicked(new EventHandler() {
            
            @Override
            public void handle(Event event) {
                try {
                    Connection conn = Connexion();
                    Statement stmt = conn.createStatement();
                    String sql = "select * from producto where nombre = 'Coca-cola'";
                    ResultSet rs = stmt.executeQuery(sql);
                    añadir_producto(rs);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        bocata.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    Connection conn = Connexion();
                    Statement stmt = conn.createStatement();
                    String sql = "select * from producto where nombre = 'Bocadillo lomo'";
                    ResultSet rs = stmt.executeQuery(sql);
                    añadir_producto(rs);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        fanta.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    Connection conn = Connexion();
                    Statement stmt = conn.createStatement();
                    String sql = "select * from producto where nombre = 'Fanta'";
                    ResultSet rs = stmt.executeQuery(sql);
                    añadir_producto(rs);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        agua.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    Connection conn = Connexion();
                    Statement stmt = conn.createStatement();
                    String sql = "select * from producto where nombre = 'Agua'";
                    ResultSet rs = stmt.executeQuery(sql);
                    añadir_producto(rs);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
    }    
    
    
    public void añadir_producto(ResultSet rs) {
        try{
            Connection conn = Connexion();
            Statement stmt = conn.createStatement();
            int producto_id = 0;
            float precio_venta = 0;
            if(rs.next()){
                producto_id = rs.getInt(1);
                precio_venta = rs.getFloat("precio_venta");
            }
            String sql = "INSERT INTO producto_factura (factura_id,producto_id,cantidad,importe_parcial) "
                        + "VALUES ("+obten_idFactura()+","+producto_id+",1,"+precio_venta+");";
            stmt.executeUpdate(sql);
            
            String query_suma = "SELECT SUM(importe_parcial) where factura_id = "+obten_idFactura()+";";
            ResultSet rs_suma = stmt.executeQuery(query_suma);
            int suma = 0;
            if(rs_suma.next()){
                suma = rs_suma.getInt(1);                
            }

            
            String query_actualiza = "SELECT * FROM factura;";
            ResultSet rs_actualiza = stmt.executeQuery(query_actualiza);
            rs_actualiza.absolute(1);
            rs_actualiza.updateInt("precio", suma);
            rs_actualiza.updateRow();
            
            
        }catch(SQLException e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
