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
import java.text.DecimalFormat;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
    private Button cobrar;
    
    @FXML
    private Label texto;
    
    @FXML
    private Text total;
    
    @FXML
    private TextField pagado;
    
    @FXML
    private Text cambio;
    
   
    
    @FXML
    private void Cobrar(ActionEvent event) {
        System.out.println("Clicaste cobrar!");
    }
    
    @FXML
    private void abrir_producto(ActionEvent event) throws IOException {
        URL url = new File("src/diseños/Productos.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root1 = (Parent)fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        stage2.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void volver_inicio(ActionEvent event) throws IOException {
        URL url = new File("src/diseños/Principal.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root1 = (Parent)fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        stage2.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            System.out.println("Actualmente estas operando sobre la factura "+obten_idFactura());
            Connection conn = Connexion();
            
            String sql = "select p.nombre as nombre, pf.importe_parcial as importe from producto_factura as pf join producto as p on pf.producto_id = p.producto_id where pf.factura_id = "+obten_idFactura()+";";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            String resultado = "";
            while (rs.next())
            {
                resultado += rs.getString("nombre");
                resultado += "\t\t";
                resultado += rs.getFloat("importe")+"€";
                resultado += "\n";
                
            }
            texto.setText(resultado);
            
            String query = "select precio from factura where factura_id = "+obten_idFactura()+";";
            rs = stmt.executeQuery(query);
            
            String resultado2 = "";
            while (rs.next())
            {
                resultado2 = rs.getString("precio");
            }
            total.setText(resultado2);
            conn.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorOperacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorOperacion.class.getName()).log(Level.SEVERE, null, ex);
            }

            factura.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        
                        Connection conn = Connexion();
                        
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDateTime now = LocalDateTime.now();
                        String date = formatter.format(now);
                        int id = obten_nuevo_idFactura();
                        String sql = "insert into factura values ("+id+","+mesa+",0,'"+date+"');"
                                + "update mesa set disponible = false, id_factura_actual = "+id+" where mesa_id = "+mesa+";";
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        System.out.println("Has creado una factura para la mesa "+mesa);
                        texto.setText("");
                        conn.close();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorOperacion.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorOperacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            cobrar.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        Connection conn = Connexion();
                        Statement stmt = conn.createStatement();
                        
                        String query = "select precio from factura where factura_id = "+obten_idFactura()+";";
                        ResultSet rs = stmt.executeQuery(query);

                        float precio_total = 0;
                        while (rs.next())
                        {
                            precio_total = rs.getFloat("precio");
                        }
                        DecimalFormat nf = new DecimalFormat("#.00");
                        float paga = Float.parseFloat(pagado.getText());
                        float resto = precio_total - paga;
                        if(resto == 0){
                            cambio.setText("0");
                            total.setText("0");
                            pagado.setText("");
                            String sql = "update mesa set disponible = true, id_factura_actual = null where mesa_id = "+mesa+";";
                            stmt.executeUpdate(sql);
                        }else{
                            if(resto > 0){
                                total.setText(String.valueOf(nf.format(resto)));
                                pagado.setText("");
                                cambio.setText("0");
                                query = "update factura set precio = "+resto+";";
                                stmt.executeUpdate(query);
                            }else{
                                if(resto < 0){
                                    total.setText("0");
                                    pagado.setText("");
                                    cambio.setText(String.valueOf(nf.format(resto)));
                                    String sql = "update mesa set disponible = true, id_factura_actual = null where mesa_id = "+mesa+";";
                                    stmt.executeUpdate(sql);
                                }
                            }
                        }
                        
                        
                        
                        conn.close();
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
       String sql = "SELECT max(factura_id) from factura";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       int id = 0;
       if(rs.next()){
        id = rs.getInt(1);
       }
       id += 1;
       System.out.println("Has creado el id "+id);
       conn.close();
       return id+1;
   }
   
   static int obten_idFactura() throws ClassNotFoundException, SQLException{
       Connection conn = Connexion();
       String sql = "SELECT id_factura_actual from mesa where mesa_id = "+mesa+";";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       int id = 0;
       if(rs.next()){
        id = rs.getInt(1);
       }
       System.out.println(id);
       conn.close();
       return id;
   }
    
}
