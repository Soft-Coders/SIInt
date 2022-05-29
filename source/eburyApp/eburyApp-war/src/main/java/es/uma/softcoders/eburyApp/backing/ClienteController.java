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
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "cliente")
@ViewScoped
public class ClienteController implements Serializable{

        @EJB
        private GestionCliente gestionCliente;

        private List<Cliente> listInactivos;

        private List<Cliente> listActivos;

        

        private Long idCliente;


        public ClienteController(){
            
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
        public String goRegister(String user){
            return "registro.xhtml?faces-redirect=true&idsession="+user;
        }
        public List<Cliente> getListaActivos(){
            setListaActivos();
            return listActivos;
        }

        public String refrescarBaja(String user){
            return "bajaClientes.xhtml?faces-redirect=true&idsession="+user;
        }

        public String atras(String user){
            return "inicioAdmin.xhtml?faces-redirect=true&idsession="+user;
        }

        public String refrescarAlta(String user){
            return "altaClientes.xhtml?faces-redirect=true&idsession="+user;
        }

        public String darAlta(Cliente c, String user){
            try{gestionCliente.altaCliente(c.getId());}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de alta");
            FacesContext.getCurrentInstance().addMessage("altaClientes:list", fm);}

            return refrescarAlta(user);
        }

        public String darBaja(Cliente c, String user){
            try{gestionCliente.bajaCliente(c.getId());}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de baja");
            FacesContext.getCurrentInstance().addMessage("bajaClientes:list", fm);}

            return refrescarBaja(user);
        }

        public String goBaja(String user){
            return "bajaCliente.xhtml";
        }

        public String goAlta(String user){
            return "altaCliente.xhtml?faces-redirect=true&idsession="+user;
        }

        /* public String goModificarCliente(Cliente c){
            idCliente = c.getId();
            clienteBuffer = c;
            return "modificarClientes.xhtml";
        }

        public String modificarCliente(Cliente c){
            clienteAux.setFechaAlta(clienteBuffer.getFechaAlta());
            clienteAux.setFechaBaja(clienteBuffer.getFechaBaja());
            try{
                gestionCliente.modificarCliente(c, idCliente);  
                return  "bajaClientes.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClientes:modificacion", fm);}
            return "unexpected.xhtml";
		*/
        
        

} 