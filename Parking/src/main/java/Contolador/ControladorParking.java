
package Contolador;

import Modelo.ConsultaVehiculos;
import Modelo.Vehiculo;
import Vista.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


public class ControladorParking implements ActionListener {
    
    //agregamos la vista y los modelos a ser empleados por el controlador;
    
    Principal view;
    Vehiculo modelo1;
    ConsultaVehiculos modelo2;
    
    public ControladorParking (Principal view,Vehiculo modelo1,ConsultaVehiculos modelo2){
        
        this.view=view;
        this.modelo1=modelo1;
        this.modelo2=modelo2;
        this.view.getPanelIngresarVehiculo().btnRegistrar.addActionListener(this);
        this.view.getPanelRetirarVehiculo().btnBuscar.addActionListener(this);
        this.view.getPanelRetirarVehiculo().btnRetirar.addActionListener(this);
    }
    
    public void iniciar(){
        
        this.view.setTitle("ParkingSoft");
        this.view.setVisible(true);
    }
    
    
    /*este metodo es el encargado de "escuchar los botones de la vista", se declararon condicionales "if" para determinar cual boton a sido presionado
      lo que se realiza dentro de este metodo es que los valores que se encuentran dentro de la caja de texto son asignadas al modelo y estas a su vez
      realiza la consulta correspondiente a la base de datos.  
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        String tipoAuto="";
        
        // Al cumplirse esta condicional se realizara el registro de un nuevo auto
        
        if(e.getSource()==view.getPanelIngresarVehiculo().btnRegistrar){
            
            //Usamos la clase Date y Timestamp para generar la hora de entrar del vehiculo.
            
            Date date=new Date();
            Timestamp horaEntrada=new Timestamp(date.getTime());
            
            if(view.getPanelIngresarVehiculo().rdAutoIngreso.isSelected()){
                tipoAuto="Automovil";
            }else{
                tipoAuto="Motocicleta";
            }
            //Asigno los valores de las cajas de texto a las variables correspondientes
            
            String placa=view.getPanelIngresarVehiculo().txtPlacaIngreso.getText();
            String propietario=view.getPanelIngresarVehiculo().txtNombreIngreso.getText();
            
            //le pasamos las variables generadas posteriormente para luego pasarlas al modelo
            
            modelo1.setPlaca(placa);
            modelo1.setPropietario(propietario);
            modelo1.setTipoVehiculo(tipoAuto);
            modelo1.setHoraEntrada(horaEntrada);
            modelo1.setEstado("Disponible");
     
            
            if(modelo2.ingresarVehiculo(modelo1)){
                
                JOptionPane.showMessageDialog(null,"Vehiculo registrado con exito");
                
            }else{
                
                JOptionPane.showMessageDialog(null,"Error al registrar vehiculo");
                
            }
            
            
        }
        //Al cumplirse esta condicional se realizara la busquedad del vehiculo.
       
        if(e.getSource()==view.getPanelRetirarVehiculo().btnBuscar){
         
            //Obtenemos la placa de la caja de texto la cual sera usada por el modelo para realizar el update.
            
            String placa=view.getPanelRetirarVehiculo().txtPlacaRetirar.getText();
            
            modelo1.setPlaca(placa);
            
            
            if(modelo2.buscarVehiculo(modelo1)){
              
              view.getPanelRetirarVehiculo().txtIdRetira.setText(String.valueOf(modelo1.getId()));
              view.getPanelRetirarVehiculo().txtPropietario.setText(modelo1.getPropietario());
              view.getPanelRetirarVehiculo().txtTipoVehiculo.setText(modelo1.getTipoVehiculo());
              view.getPanelRetirarVehiculo().txtHoraEntrada.setText(String.valueOf(modelo1.getHoraEntrada()));
                
            }else{
                JOptionPane.showMessageDialog(null,"No se encontraron resultados");
            }
            
        }
        
             //Al cumplirse esta condicional se realizara la busquedad del vehiculo.
       
       if(e.getSource()==view.getPanelRetirarVehiculo().btnRetirar){
           
          //Asignamos el valor por minuto de estadia tanto del automovil como motocicleta.
          
           double valorPorMinutoAuto=5;
           double valorPorMinutoMoto=3;
           
          /*Para evitar una consulta a la base de datos, se tomo el valor de la hora de entrada
           y se realiza un convert para usar el formato Timestamp y asi calcular los minutos de estadia*/
          try{
           
            Date date=new Date();
            Timestamp horaSalida=new Timestamp(date.getTime());
            modelo1.setHoraSalida(horaSalida);
            String formato=view.getPanelRetirarVehiculo().txtHoraEntrada.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(formato);
            Timestamp horaEntrada=new java.sql.Timestamp(parsedDate.getTime());
            
            /*restamos los milisegundos de la hora de salida menos los de la hora de entrada y
              los dividimos entre 60000 para obtener los minutos totales de estadia.  
            */
            int minutos= ((int)(horaSalida.getTime()-(int)horaEntrada.getTime()))/60000;
            
            String tipoVehiculo=view.getPanelRetirarVehiculo().txtTipoVehiculo.getText();
            
            //se verifica que tipo de vehiculo es para realizar el calculo del valor a pagar.
            if(tipoVehiculo.equals("Automovil")){
                
                modelo1.setValorPagado(valorPorMinutoAuto*=minutos);
                
            }
            if(tipoVehiculo.equals("Motocicleta")){
                 modelo1.setValorPagado(valorPorMinutoMoto*=minutos);
            }
            
          }catch(Exception a){
              System.err.print(a);
          }
      
         
          
            //Obtenemos la placa de la caja de texto la cual sera usada por el modelo para realizar el update.
            
            String placa=view.getPanelRetirarVehiculo().txtPlacaRetirar.getText();
            
            modelo1.setPlaca(placa);
            modelo1.setEstado("No Disponible");
            
            
            if(modelo2.retirarVehiculo(modelo1)){
              
              view.getPanelRetirarVehiculo().txtTotalPagar.setText(String.valueOf(modelo1.getValorPagado()));
              
              JOptionPane.showMessageDialog(null,"Vehiculo retirado");
                
            }else{
                JOptionPane.showMessageDialog(null,"Error Inesperado");
            }
            
        }
        
        
    }
    
    
}
