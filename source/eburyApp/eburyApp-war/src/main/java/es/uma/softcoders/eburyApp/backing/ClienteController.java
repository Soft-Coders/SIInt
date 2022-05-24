package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "cliente")
@ViewScoped
public class ClienteController implements Serializable{

        @EJB
        private GestionCliente gestionCliente;

        private List<Cliente> listInactivos;

        private List<Cliente> listActivos;

        private Cliente clienteAux;

        private Cliente clienteBuffer;

        private Long idCliente;

        public ClienteController(){
            Cliente clienteAux = new Cliente();
            Cliente clienteBuffer = new Cliente();
        }

        public Cliente getClienteAux(){
            return clienteAux; 
        }
        public Cliente getClienteBuffer(){
            return clienteBuffer; 
        }

        public void setListaInactivos(){
            try{listInactivos = gestionCliente.clientesInactivos();}
            catch(EburyAppException e){
                FacesMessage fm = new FacesMessage("No hay clientes inactivos");
	            FacesContext.getCurrentInstance().addMessage("altaClientes:list", fm);
            }
        } 

        public List<Cliente> getListaInactivos(){
            setListaInactivos();
            return listInactivos;
        }
        
        public void setListaActivos(){
            try{listActivos = gestionCliente.clientesActivos();}
            catch(EburyAppException e){
                FacesMessage fm = new FacesMessage("No hay clientes activos");
	            FacesContext.getCurrentInstance().addMessage("bajaClientes:list", fm);
            }
        } 
        public String goRegister(){
            return "registro.xhtml";
        }
        public List<Cliente> getListaActivos(){
            setListaActivos();
            return listActivos;
        }

        public String refrescarBaja(){
            return "bajaClientes.xhtml";
        }

        public String atras(){
            return "inicioAdmin.xhtml";
        }

        public String refrescarAlta(){
            return "altaClientes.xhtml";
        }

        public String darAlta(Cliente c){
            try{gestionCliente.altaCliente(c);}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de alta");
            FacesContext.getCurrentInstance().addMessage("altaClientes:list", fm);}

            return refrescarAlta();
        }

        public String darBaja(Cliente c){
            try{gestionCliente.bajaCliente(c.getID());}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de baja");
            FacesContext.getCurrentInstance().addMessage("bajaClientes:list", fm);}

            return refrescarBaja();
        }

        public String goBaja(){
            return "bajaCliente.xhtml";
        }

        public String goAlta(){
            return "altaCliente.xhtml";
        }

        public String goModificarCliente(Cliente c){
            idCliente = c.getID();
            clienteBuffer = c;
            return "modificarClientes.xhtml";
        }

        public String modificarCliente(Cliente c){
            clienteAux.setFecha_Alta(clienteBuffer.getFecha_Alta());
            clienteAux.setFecha_Baja(clienteBuffer.getFecha_Baja());
            try{
                gestionCliente.modificarCliente(c, idCliente);  
                return  "bajaClientes.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClientes:modificacion", fm);}
            return "unexpected.xhtml";

        }

} 