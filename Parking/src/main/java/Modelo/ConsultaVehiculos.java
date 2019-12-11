
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*Heredamos de la clase Conexion para poder usar sus metodos sin necesidad
  de instanciar la clase*/

public class ConsultaVehiculos extends Conexion{


    
/*Con esta funcion registraremos los vehiculos que seran ingresados a la base de datos local */
    
   public boolean ingresarVehiculo(Vehiculo vehiculo){
       
      Connection con=getConexion();
      
      String sqlRegistro="INSERT INTO vehiculos (placa,propietario,tipo_vehiculo,hora_entrada,estado) VALUES(?,?,?,?,?)";
      
       try {
           
            PreparedStatement st=con.prepareStatement(sqlRegistro);
        
            st.setString(1,vehiculo.getPlaca());
            st.setString(2,vehiculo.getPropietario());
            st.setString(3,vehiculo.getTipoVehiculo());
            st.setTimestamp(4,vehiculo.getHoraEntrada());
            st.setString(5,vehiculo.getEstado());
            st.executeUpdate();
            

            return true;
            
       } catch (SQLException e) {
           
           System.err.println(e); 
           return false;
           
       }finally{
           try {
               con.close();
               
           } catch (SQLException e) {
               
               System.err.println(e);
           }
           
       }
       
   }
   
      //Metodo para retirar los vehiculo
      public boolean retirarVehiculo(Vehiculo vehiculo){
       
      Connection con=getConexion();
      
      String sqlRegistro="UPDATE vehiculos SET hora_salida=?,valor_pagado=?,estado=? WHERE placa=? AND estado=?";
      
       try {
           
            PreparedStatement st=con.prepareStatement(sqlRegistro);
        
            st.setTimestamp(1,vehiculo.getHoraSalida());
            st.setDouble(2,vehiculo.getValorPagado());
            st.setString(3,vehiculo.getEstado());
            st.setString(4,vehiculo.getPlaca());
            st.setString(5,"Disponible");
            st.executeUpdate();
            
            return true;
            
       } catch (SQLException e) {
           
         System.err.print(e);
           return false;
       }finally{
           try {
               con.close();
               
           } catch (SQLException e) {
               
               System.err.println(e);
           }
           
           
           
       }
       
   }
      
      public boolean buscarVehiculo(Vehiculo vehiculo){
       
      Connection con=getConexion();
      ResultSet rs=null;
      String sqlRegistro="SELECT id,propietario,tipo_vehiculo,hora_entrada FROM vehiculos WHERE placa=?";
      
       try {
           
            PreparedStatement st=con.prepareStatement(sqlRegistro);
        
            st.setString(1,vehiculo.getPlaca());
            rs=st.executeQuery();
            if(rs.next()){
                
                vehiculo.setId(rs.getInt("id"));
                vehiculo.setPropietario(rs.getString("propietario"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                vehiculo.setHoraEntrada(rs.getTimestamp("hora_entrada"));
            }
            
            
            return true;
            
       } catch (SQLException e) {
           
           System.err.print(e);
           return false;
       }finally{
           try {
               con.close();
               
           } catch (SQLException e) {
               
               System.err.println(e);
           }
    
}
      }
}
