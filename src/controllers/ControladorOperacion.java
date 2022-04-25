/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import static controllers.ControladorPrincipal.Connexion;
import static controllers.ControladorPrincipal.mesa;
import java.awt.event.MouseEvent;
import java.io.File;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author alumneinf
 */
public class ControladorOperacion implements Initializable {
    
/*
    @FXML
    private Button cobrar;
    @FXML
    private Button anadir;
    @FXML
    private Button abrir_algo;
*/

    @FXML
    private Button factura;
    
   
    
    @FXML
    private void Cobrar(ActionEvent event) {
        System.out.println("Clicaste cobrar!");
    }
    
    @FXML
    private void AñadirProducto(ActionEvent event) throws IOException {
        System.out.println("Clicaste Añadir producto!");
        
        URL url = new File("src/diseños/Productos.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root1 = (Parent)fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        stage2.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       factura.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    
                    Connection conn = Connexion();
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
                    LocalDateTime now = LocalDateTime.now();
                    String date = formatter.format(now);
                    
                    String sql = "insert into factura values ("+obten_nuevo_idFactura()+","+mesa+",0,'"+date+"');";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    System.out.println("Has creado una factura para la mesa "+mesa);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorOperacion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorOperacion.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }    
    
   static int obten_nuevo_idFactura() throws ClassNotFoundException, SQLException{
       Connection conn = Connexion();
       String sql = "SELECT count(*) from factura";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       int id = 0;
       if(rs.next()){
        id = rs.getInt(1);
       }
       System.out.println(id);
       return id+1;
   }
   
   static int obten_idFactura() throws ClassNotFoundException, SQLException{
       Connection conn = Connexion();
       String sql = "SELECT max(factura_id) from factura where mesa_id = '"+mesa+"'";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       int id = 0;
       if(rs.next()){
        id = rs.getInt(1);
       }
       System.out.println(id);
       return id;
   }
    
}
