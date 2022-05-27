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
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "modificar")
@RequestScoped
public class Modificar implements Serializable{

        @EJB
        private GestionCliente gestionCliente;

        @Inject
        private ClienteController clienteCont;

        private Individual individualAux = new Individual();

        private Individual individualBuffer = new Individual();

        private Empresa empresaAux = new Empresa();

        private Empresa empresaBuffer = new Empresa();

        private Long idCliente;

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


        public String goModificarCliente(Cliente c){
            if(c.getID() == null){
                return "unexpected.xhtml";
            } else{
                idCliente = c.getID();
            }

            if(c instanceof Individual){
                individualBuffer = (Individual) c;
                return "modificarClienteInd.xhtml";
            }else if(c instanceof Empresa){
                empresaBuffer = (Empresa) c;
                return "modificarClienteEmp.xhtml";
            } else {
                return "unexpected.xhml";
            }
        }

        public String modificarClienteInd(Individual i){
            i.setID(individualBuffer.getID());
            i.setFecha_Alta(individualBuffer.getFecha_Alta());
            i.setFecha_Baja(individualBuffer.getFecha_Baja());
            try{
                if(getIdCliente() == null){
                    return "inicioAdmin.xhtml";
                }
                gestionCliente.modificarCliente(i, getIdCliente());  
                return  "bajaClientes.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClienteInd:modificacion", fm);}
            return "unexpected.xhtml";
        }

        public String modificarClienteEmp(Empresa emp){
            emp.setID(empresaBuffer.getID());
            emp.setFecha_Alta(empresaBuffer.getFecha_Alta());
            emp.setFecha_Baja(empresaBuffer.getFecha_Baja());
            try{
                if(getIdCliente() == null){
                    return "inicioAdmin.xhtml";
                }
                gestionCliente.modificarCliente(emp, getIdCliente());  
                return  "bajaClientes.xhtml";    
            }catch(EburyAppException e){
            FacesMessage fm = new FacesMessage("Error al modificar");
            FacesContext.getCurrentInstance().addMessage("modificarClienteEmp:modificacion", fm);}
            return "unexpected.xhtml";

        }

} 