package datos;

import dominio.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ClienteDaoJDBC {
    
 private static final String SQL_SELECT= "SELECT id_cliente, nombre,apellido,email,telefono,saldo FROM clientes";
 private static final String SQL_SELECT_BY_ID="SELECT id_cliente, nombre,apellido,email,telefono,saldo FROM clientes WHERE id_cliente=?";
 private static final String SQL_INSERT="INSERT INTO clientes(nombre,apellido,email,telefono,saldo) VALUES(?,?,?,?,?)";
 private static final String SQL_UPDATE="UPDATE clientes SET nombre=?, apellido=?, email=?, telefono=?, saldo=? WHERE id_cliente=?";    
 private static final String SQL_DELETE= "DELETE FROM clientes WHERE id_cliente=?";
    
 public List<Cliente> listar(){
     Connection conn=null;
     PreparedStatement stmt=null;
     ResultSet rs=null;
     List<Cliente> clientes= new ArrayList<Cliente>();
     
     try {
         conn=Conexion.getConnection();
         stmt=conn.prepareStatement(SQL_SELECT);
         rs=stmt.executeQuery();
         while (rs.next()) {             
             int idCliente=rs.getInt("id_cliente");
             String nombre=rs.getString("nombre");
             String apellido=rs.getString("apellido");
             String email=rs.getString("email");
             String telefono=rs.getString("telefono");
             double saldo=rs.getDouble("saldo");
             System.out.println(nombre+" "+apellido+" "+email);
             
           clientes.add(new Cliente(idCliente,nombre,apellido,email,telefono,saldo));
         }
         
     } catch (Exception e) {
         e.printStackTrace(System.out);
     }finally{
         Conexion.Close(rs);
         Conexion.Close(stmt);
         Conexion.Close(conn);
                 
     }
     
     return clientes;
 }
 
 public Cliente buscar(Cliente cliente){
     Connection conn=null;
     PreparedStatement stmt=null;
     ResultSet rs=null;
    
     try {
         conn=Conexion.getConnection();
         stmt=conn.prepareStatement(SQL_SELECT_BY_ID);
         stmt.setInt(1, cliente.getIdCliente());
         rs=stmt.executeQuery();
             rs.next();
             String nombre=rs.getString("nombre");
             String apellido=rs.getString("apellido");
             String email=rs.getString("email");
             String telefono=rs.getString("telefono");
             double saldo=rs.getDouble("saldo");
           
             cliente.setNombre(nombre);
             cliente.setApellido(apellido);
             cliente.setEmail(email);
             cliente.setTelefono(telefono);
             cliente.setSaldo(saldo);
         
     } catch (Exception e) {
         e.printStackTrace(System.out);
     }finally{
         Conexion.Close(rs);
         Conexion.Close(stmt);
         Conexion.Close(conn);
                 
     }
     
     return cliente;
 
 }
 
 public int insertar(Cliente cliente){
   Connection conn=null;
     PreparedStatement stmt=null;
     int rows=0;
    
     try {
         conn=Conexion.getConnection();
         stmt=conn.prepareStatement(SQL_INSERT);
         stmt.setString(1, cliente.getNombre());
         stmt.setString(2, cliente.getApellido());
         stmt.setString(3, cliente.getEmail());
         stmt.setString(4, cliente.getTelefono());
         stmt.setDouble(5, cliente.getSaldo());
         
         rows=stmt.executeUpdate();

     } catch (Exception e) {
         e.printStackTrace(System.out);
     }finally{
         Conexion.Close(stmt);
         Conexion.Close(conn);
                 
     }  
     return rows;
 }
 public int actualizar(Cliente cliente){
     Connection conn=null;
     PreparedStatement stmt=null;
     int rows=0;
    
     try {
         conn=Conexion.getConnection();
         stmt=conn.prepareStatement(SQL_UPDATE);
         stmt.setString(1, cliente.getNombre());
         stmt.setString(2, cliente.getApellido());
         stmt.setString(3, cliente.getEmail());
         stmt.setString(4, cliente.getTelefono());
         stmt.setDouble(5, cliente.getSaldo());
         stmt.setInt(6, cliente.getIdCliente());
         
         rows=stmt.executeUpdate();

     } catch (Exception e) {
         e.printStackTrace(System.out);
     }finally{
         Conexion.Close(stmt);
         Conexion.Close(conn);
                 
     }  
     return rows;
 }
 public int eliminar(Cliente cliente){
  Connection conn=null;
     PreparedStatement stmt=null;
     int rows=0;
    
     try {
         conn=Conexion.getConnection();
         stmt=conn.prepareStatement(SQL_DELETE);
         stmt.setInt(1, cliente.getIdCliente());
         
         rows=stmt.executeUpdate();

     } catch (Exception e) {
         e.printStackTrace(System.out);
     }finally{
         Conexion.Close(stmt);
         Conexion.Close(conn);
                 
     }  
     return rows;
 }
    public static void main(String[] args) {
        ClienteDaoJDBC cliente= new ClienteDaoJDBC();
        cliente.listar();
    }
}
