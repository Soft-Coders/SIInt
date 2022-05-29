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
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "modificarAut")
@RequestScoped
public class ModificarAut implements Serializable{

        @EJB
        private GestionAutorizado gestionAutorizado;

        
        private PersonaAutorizada autorizadoBuffer = new PersonaAutorizada();
        
        private PersonaAutorizada autorizadoAux = new PersonaAutorizada();

        private Long idCliente;
        
        private Long idAutorizado;

        public ModificarAut(){
            
        }

        public PersonaAutorizada getAutorizadoAux(){
            return this.autorizadoAux;
        }

        public void setAutorizadoAux(PersonaAutorizada aut){
            this.autorizadoAux = aut;
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
        


        public String goModificarAut(PersonaAutorizada aut){
            autorizadoBuffer = aut;
            return "modificarAutorizado.xhtml"  + "?faces-redirect=true" + "&id=" + aut.getId().toString();
            
        }


        

        public String modificarAutorizado(PersonaAutorizada a, String id){
            Long idL = Long.valueOf(id);
            a.setId(idL);
            a.setFechaInicio(autorizadoBuffer.getFechaInicio());
            a.setFechaFin(autorizadoBuffer.getFechaFin());
            a.setFechaNacimiento(autorizadoBuffer.getFechaNacimiento());
            

            try{
                gestionAutorizado.modificarAutorizado(a, idL);  
                return  "bajaAutorizados.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClienteEmp:modificacion", fm);}
            return "unexpected.xhtml";

        }

} 