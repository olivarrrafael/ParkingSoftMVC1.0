/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.*;
import javax.swing.JOptionPane;
import java.util.Date;
import java.sql.Timestamp;

public class Conexion {
    
   private Connection conexion=null;
   private final String url="jdbc:mysql://localhost:3306/bd_parking";
   private final String user="root";
   private final String pass="rafaelrey2";
   private final String registro=" INSERT INTO vehiculos (placa,propietario,tipo_vehiculo,hora_entrada,estado) VALUES (?,?,?,?,?)";
   
   
   
   public Connection getConexion(){
       
       try {
           
          conexion=DriverManager.getConnection(url,user,pass);
          
                  
       } catch (SQLException e) {
           
           System.err.println(e);
           
       }
       return conexion;
       
       
   }
   
   
   public void RetirarVehiculo(){
       
       
   }
   
   public double retirarVehiculo(String matricula){
     String TiempoEstadia="SELET hora_entrada FROM vehiculos WHERE placa LIKE ";
       return 0;
     
       
   }
   
       
   } 

