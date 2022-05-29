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
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "autorizado")
@ViewScoped
public class AutorizadoController implements Serializable{

        
		@EJB
        private GestionAutorizado gestionAutorizado;

        private List<PersonaAutorizada> listInactivos;

        private List<PersonaAutorizada> listActivos;

        

        private Long idAutorizado;


        public AutorizadoController(){
            
        }

        public void setListaInactivos(){
            try{listInactivos = gestionAutorizado.autorizadosInactivos();}
            catch(EburyAppException e){
                FacesMessage fm = new FacesMessage("No hay autorizados inactivos");
	            FacesContext.getCurrentInstance().addMessage("altaAutorizados:list", fm);
            }
        } 

        public List<PersonaAutorizada> getListaInactivos(){
            setListaInactivos();
            return listInactivos;
        }
        
        public void setListaActivos(){
            try{listActivos = gestionAutorizado.autorizadosActivos();}
            catch(EburyAppException e){
                FacesMessage fm = new FacesMessage("No hay autorizados activos");
	            FacesContext.getCurrentInstance().addMessage("bajaAutorizados:list", fm);
            }
        } 
        public String goRegister(){
            return "registro.xhtml";
        }
        public List<PersonaAutorizada> getListaActivos(){
            setListaActivos();
            return listActivos;
        }

        public String refrescarBaja(String user){
            return "bajaAutorizados.xhtml?faces-redirect=true&idsession="+user;
        }

        public String atras(String user){
            return "inicioAdmin.xhtml?faces-redirect=true&idsession="+user;
        }

        public String refrescarAlta(String user){
            return "altaAutorizados.xhtml?faces-redirect=true&idsession="+user;
        }

        public String darAlta(PersonaAutorizada c, String user){
            try{gestionAutorizado.altaAutorizado(c.getId());}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de alta");
            FacesContext.getCurrentInstance().addMessage("altaAutorizados:list", fm);}

            return refrescarAlta(user);
        }
        

        public String darBaja(PersonaAutorizada c, String user){
            try{gestionAutorizado.bajaAutorizado(c.getId());}
            catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al dar de baja");
            FacesContext.getCurrentInstance().addMessage("bajaAutorizados:list", fm);}

            return refrescarBaja(user);
        }

        public String goBaja(String user){
            return "bajaAutorizados.xhtml?faces-redirect=true&idsession="+user;
        }

        public String goAlta(String user){
            return "altaAutorizados.xhtml?faces-redirect=true&idsession="+user;
        }
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
        
        

