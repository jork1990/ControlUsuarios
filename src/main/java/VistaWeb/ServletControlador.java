
package VistaWeb;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;


@WebServlet(name = "ServletControlador", urlPatterns = {"/ServletControlador"})
public class ServletControlador extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String accion=request.getParameter("accion");
        if (accion !=null) {
            switch(accion){
                case "editar":
                    this.editarCliente(request, response); break;
                case "eliminar":
                    this.eliminarCliente(request, response); break;
                    
                default:
                  this.accionDefault(request, response);  
            }
            
        } else {
            this.accionDefault(request, response);
        }
    }
    
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String accion=request.getParameter("accion");
        if (accion !=null) {
            switch(accion){
                case "insertar":
                    this.insertarCliente(request, response); break;
                case "modificar":
                    this.modificarCliente(request, response); break;
                case "eliminar":
                    this.eliminarCliente(request, response); break;
                        
                default:
                  this.accionDefault(request, response);  
            }
            
        } else {
            this.accionDefault(request, response);
        }
    }

    protected void accionDefault(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = "+clientes);
        HttpSession sesion=request.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal",this.calcularSaldoTotal(clientes));
        
        response.sendRedirect("clientes.jsp");
    }

    private double calcularSaldoTotal(List<Cliente> clientes){
    double saldoTotal=0;
        for (Cliente cliente : clientes) {
            saldoTotal+=cliente.getSaldo();
        }
    
    return saldoTotal;
    }
    
        protected void editarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      int idCliente=Integer.parseInt(request.getParameter("idCliente"));
      Cliente cliente = new ClienteDaoJDBC().buscar(new Cliente(idCliente));
      request.setAttribute("cliente", cliente);
      String jspEditar="/WEB-INF/paginas/cliente/editarCliente.jsp";
      request.getRequestDispatcher(jspEditar).forward(request, response);
    }
    protected void modificarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      int idCliente=Integer.parseInt(request.getParameter("idCliente"));
      String nombre= request.getParameter("nombre");
      String apellido= request.getParameter("apellido");
      String email= request.getParameter("email");
      String telefno= request.getParameter("telefono");
      double saldo=0;
      
      String saldoString= request.getParameter("saldo");
      if(saldoString!=null && !"".equals(saldoString))
            saldo = Double.parseDouble(saldoString);
      Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefno, saldo);
      int resgistrosModificados= new ClienteDaoJDBC().actualizar(cliente);
      this.accionDefault(request, response);
      
    }

     protected void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      int idCliente=Integer.parseInt(request.getParameter("idCliente"));
      Cliente cliente = new Cliente(idCliente);
      int registrosModificados= new ClienteDaoJDBC().eliminar(cliente);
      this.accionDefault(request, response);
    }
        protected void insertarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   //   int idCliente=Integer.parseInt(request.getParameter("idCliente"));
      String nombre= request.getParameter("nombre");
      String apellido= request.getParameter("apellido");
      String email= request.getParameter("email");
      String telefno= request.getParameter("telefono");
      double saldo=0;
      
      String saldoString= request.getParameter("saldo");
      if(saldoString!=null && !"".equals(saldoString))
            saldo = Double.parseDouble(saldoString);
      Cliente cliente = new Cliente(nombre, apellido, email, telefno, saldo);
      int resgistrosModificados= new ClienteDaoJDBC().insertar(cliente);
      this.accionDefault(request, response);
      
    }
}
