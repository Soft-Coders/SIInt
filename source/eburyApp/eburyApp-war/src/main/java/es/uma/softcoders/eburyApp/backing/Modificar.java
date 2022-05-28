package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "modificar")
@RequestScoped
public class Modificar implements Serializable{

        @EJB
        private GestionCliente gestionCliente;

        @Inject
        private ClienteController clienteCont;
        
        private PersonaAutorizada autorizadoBuffer = new PersonaAutorizada();
        
        private PersonaAutorizada autorizadoAux = new PersonaAutorizada();

        private Individual individualAux = new Individual();

        private Individual individualBuffer = new Individual();

        private Empresa empresaAux = new Empresa();

        private Empresa empresaBuffer = new Empresa();

        private Long idCliente;
        
        private Long idAutorizado;

        public Modificar(){
            
        }

        
        public void setEmpresaAux(Empresa e){
            this.empresaAux = e;
        } 
        public void setEmpresaBuffer(Empresa e){
            this.empresaBuffer = e;
        } 
        public void setIndividualAux(Individual i){
            this.individualAux = i;
        } 
        public void setIndividualBuffer(Individual i){
            this.individualBuffer = i;
        } 

        public Individual getIndividualBuffer(){
            return individualBuffer;
        }
        public Individual getIndividualAux(){
            return individualAux;
        }
        public Empresa getEmpresaBuffer(){
            return empresaBuffer;
        }
        public Empresa getEmpresaAux(){
            return empresaAux;
        }
        
        
        public String goRegister(){
            return "registro.xhtml";
        }
       

       

        public String atras(){
            return "inicioAdmin.xhtml";
        }


        

        public String goBaja(){
            return "bajaCliente.xhtml";
        }

        public String goAlta(){
            return "altaCliente.xhtml";
        }
        public Long getIdCliente(){
            return idCliente;
        }
        
        public String goModificarAutorizado(PersonaAutorizada p) {
        	if(p.getId() == null) {
        		return "unexpected.xhtml";
        	}else {
        		idAutorizado = p.getId();
        	}
        	
        	autorizadoBuffer = p;
        	return "modificarAutorizado.xhtml";
        	
        }


        public String goModificarCliente(Cliente c){
            if(c instanceof Individual){
                individualBuffer = (Individual) c;
                return "modificarClienteInd.xhtml"  + "?faces-redirect=true" + "&id=" + idCliente.toString();
            }else if(c instanceof Empresa){
                empresaBuffer = (Empresa) c;
                return "modificarClienteEmp.xhtml"  + "?faces-redirect=true" + "&id=" + idCliente.toString();
            } else {
                return "unexpected.xhml";
            }
        }


        public String modificarClienteInd(Individual i, String id){
            Long idL = Long.valueOf(id);
            i.setId(individualBuffer.getId());
            i.setFechaAlta(individualBuffer.getFechaAlta());
            i.setFechaAlta(individualBuffer.getFechaAlta());

            try{
                gestionCliente.modificarCliente(i, idL);  
                return  "bajaClientes.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClienteInd:modificacion", fm);}
            return "unexpected.xhtml";
        }

        public String modificarClienteEmp(Empresa emp, String id){
            Long idL = Long.valueOf(id);
            emp.setId(idL);
            emp.setFechaAlta(empresaBuffer.getFechaAlta());
            emp.setFechaAlta(empresaBuffer.getFechaAlta());

            try{
                gestionCliente.modificarCliente(emp, idL);  
                return  "bajaClientes.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClienteEmp:modificacion", fm);}
            return "unexpected.xhtml";

        }

} 