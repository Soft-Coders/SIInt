package es.uma.softcoders.eburyApp.backing;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "cliente")
@RequestScoped
public class ClienteController {

        @EJB
        private GestionCliente gestionCliente;

        private List<Cliente> listInactivos;

        private List<Cliente> listActivos;

        private Cliente clienteAux;

        private Cliente clienteBuffer;

        private Long idCliente;

        public void setListaInactivos(){
            try{listInactivos = gestionCliente.clientesInactivos();}
            catch(EburyAppException e){
                FacesMessage fm = new FacesMessage("No hay clientes inactivos");
	            FacesContext.getCurrentInstance().addMessage("altaClientes:list", fm);
            }
        } 

        public List<Cliente> getListaInactivos(){
            return listInactivos;
        }
        
        public void setListaActivos(){
            try{listActivos = gestionCliente.clientesActivos();}
            catch(EburyAppException e){
                FacesMessage fm = new FacesMessage("No hay clientes activos");
	            FacesContext.getCurrentInstance().addMessage("bajaClientes:list", fm);
            }
        } 

        public List<Cliente> getListaActivos(){
            return listActivos;
        }

        public String refrescarBaja(){
            setListaActivos();
            return "bajaClientes.xhtml";
        }

        public String atras(){
            return "inicioAdmin.xhtml";
        }

        public String refrescarAlta(){
            setListaInactivos();
            return "altaClientes.xhtml";
        }

        public String darAlta(Cliente c){
            try{gestionCliente.altaCliente(c.getId());}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de alta");
            FacesContext.getCurrentInstance().addMessage("altaClientes:list", fm);}

            return refrescarAlta();
        }

        public String darBaja(Cliente c){
            try{gestionCliente.bajaCliente(c.getId());}
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

        }

} 