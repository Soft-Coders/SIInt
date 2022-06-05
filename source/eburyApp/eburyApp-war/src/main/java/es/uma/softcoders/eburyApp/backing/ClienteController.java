package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;
import java.util.ArrayList;
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
import es.uma.softcoders.eburyApp.CuentaFintech;
import javax.inject.Inject;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Named(value = "cliente")
@ViewScoped
public class ClienteController implements Serializable{

        @EJB
        private GestionCliente gestionCliente;

        @EJB
	    private GestionCuenta cuentaEJB;

        @Inject
        private CuentaBB cuentaBB;

        private List<Cliente> listInactivos;

        private List<Cliente> listActivos;

        private List<CuentaFintech> listaCuentasPropias = new ArrayList<CuentaFintech>();

        

        private Long idCliente;


        public ClienteController(){
            
        }
        
        public String seleccionarEmpresa(Empresa empresa) {
            Long emp = empresa.getId();
            System.out.println("-----Antes de llamar al metodo----"+empresa.toString());
            cuentaBB.setListaCuentasPropias(cuentaEJB.cuentasEmpresa(emp));
            System.out.println("-----Despues de llamar al metodo----"+empresa.toString());
            
            return "vistaEmpresaSeleccionada.xhtml?faces-redirect=true&empresa="+emp.toString();
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
        
        

} 