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
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 *
 * @author alumneinf
 */
public class ControladorProductos implements Initializable {
    
    @FXML
    private ComboBox productos;
    
    @FXML
    private ComboBox tipos;
    
    @FXML
    private Text texto;
    
    @FXML
    private Button añadir;
    
    @FXML
    private void volver_producto(ActionEvent event) throws IOException {
        URL url = new File("src/diseños/Operacion.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root2 = (Parent)fxmlLoader.load();
        Stage stage3 = new Stage();
        stage3.setScene(new Scene(root2));
        stage3.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = Connexion();
            Statement stmt = conn.createStatement();
            String query = "select distinct tipo from producto";
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<String> tipos_des = FXCollections.observableArrayList();
            
            while(rs.next()){
                tipos_des.add(rs.getString("tipo"));
            }
            
            tipos.setItems(tipos_des);

            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tipos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Connection conn = Connexion();
                    Statement stmt = conn.createStatement();
                    texto.setText(tipos.getValue().toString());
                    String query = "select nombre from producto where tipo = '"+tipos.getValue().toString()+"';";
                    
                    ResultSet rs = stmt.executeQuery(query);
                    
                    ObservableList<String> productos_des = FXCollections.observableArrayList();
                    
                    while(rs.next()){
                        productos_des.add(rs.getString("nombre"));
                    }
                    
                    productos.setItems(productos_des);
                    
                    conn.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        añadir.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    Connection conn = Connexion();
                    Statement stmt = conn.createStatement();
                    
                    String query = "select * from producto where nombre = '"+productos.getValue().toString()+"';";
                    
                    ResultSet rs = stmt.executeQuery(query);
                    int id = 0;
                    while(rs.next()){
                        id = rs.getInt("producto_id");
                    }
                    
                    anyadirProducto(id,mesa,"1");
                    conn.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
    
    
    public static boolean anyadirProducto(int id_producto, int id_mesa, String cantidad) throws ClassNotFoundException, SQLException{
    boolean res = false;
    Connection conn = Connexion();
    try {
        //Primero busca la disponibilidad y que factura tiene asignada actualmente
        String query = "select disponible,id_factura_actual from mesa where mesa_id="+id_mesa;
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        if(!rs.isClosed() && rs.next()){
            if(rs.getBoolean("disponible")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                //En caso de estar disponible quiere decir que hay que crear una factura nueva
                query = "insert into factura(mesa_id,precio,fecha) values(" + id_mesa + ",0,'" + date + "')";
                st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.executeUpdate();
                //Con este comando podemos conseguir la ultima id generada por la ultima query ejecutada por nuestro codigo
                rs = st.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    long id_factura = rs.getLong(1);
                    //Actualizamos el estado de la mesa a no disponible y le asignamos la id de la factura asignada a esa mesa
                    query = "update mesa set disponible=false, id_factura_actual=" + id_factura + " where mesa_id=" + id_mesa;
                    st = conn.prepareStatement(query);
                    st.executeUpdate();
                    //Cogemos el precio del producto que escogemos en el ComboBox (desplegable)
                    query = "select precio_venta from producto where producto_id=" + id_producto;
                    st = conn.prepareStatement(query);
                    rs = st.executeQuery();
                    float precio = 0;
                    if (rs.next()) precio = rs.getFloat("precio_venta");
                    //Creamos el registro de la tabla intermedia entre factura y producto y ponemos cantidad pasada por parametro, el precio del producto
                    //y la id de la factura y del producto
                    query = "insert into producto_factura(factura_id,producto_id,cantidad,importe_parcial) values(" + id_factura + "," + id_producto + "," + cantidad + "," + precio + ")";
                    st = conn.prepareStatement(query);
                    st.executeUpdate();
                    res=true;
                }
            }
            else if(!rs.getBoolean("disponible")){
                //En caso de la mesa no estar disponible cogemos la id de la factura actual
                int id_factura = rs.getInt("id_factura_actual");
                //Con esa id buscamos si existe ya un registro en la tabla intermedia entre producto y factura con los ids proporcionados
                query = "select * from producto_factura where factura_id="+id_factura+" and producto_id="+id_producto;
                st = conn.prepareStatement(query);
                rs = st.executeQuery();
                if(rs.next()){
                    //En caso de existir sumamos la cantidad y un trigger se encargará de actualizar el precio parcial
                    query = "update producto_factura set cantidad=cantidad+"+cantidad+" where factura_id="+id_factura+" and producto_id="+id_producto;
                    st = conn.prepareStatement(query);
                    st.executeUpdate();
                    res=true;
                }
                else{
                    //En caso de no existir creamos un nuevo registro con id de factura, id de producto, cantidad y el precio que recogemos
                    query = "select precio_venta from producto where producto_id="+id_producto;
                    st = conn.prepareStatement(query);
                    rs = st.executeQuery();
                    float precio = 0;
                    if(rs.next())precio=rs.getFloat("precio_venta");
                    query = "insert into producto_factura(factura_id,producto_id,cantidad,importe_parcial) values("+id_factura+","+id_producto+","+cantidad+","+precio+")";
                    st = conn.prepareStatement(query);
                    st.executeUpdate();
                    res=true;
                }
            }
        }

    } catch (SQLException throwables) {
        throwables.printStackTrace();
        res=false;
    }
    try {
        conn.close();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    return res;
}
    
}
