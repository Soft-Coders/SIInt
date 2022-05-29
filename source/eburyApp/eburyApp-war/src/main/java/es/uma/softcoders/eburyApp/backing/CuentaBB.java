package es.uma.softcoders.eburyApp.backing;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Usuario;
import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;



@Named(value="cuentaBB")
@RequestScoped
public class CuentaBB {
	
	@EJB
	private GestionCuenta cuentaEJB;
	
	@Inject
	private TransaccionBB trBB;
	
	private Long usuario;
	private boolean individual;
	private boolean autorizado;
	private List<CuentaFintech> listaCuentasPropias;
	private List<CuentaFintech> listaFintech;
	private List<Empresa> listaEmpresasAutorizadas;
	private String emp;   //emp guarda la empresa que ha sido seleccionada
	private List<CuentaFintech> listaCuentasAutorizadas;
	private CuentaFintech cf = new CuentaFintech();
    private String iban;
    private String tipo;
    
    
    public List<CuentaFintech> getListaCuentasPropias() {
		return listaCuentasPropias;
	}
	public void setListaCuentasPropias(List<CuentaFintech> listaCuentasPropias) {
		this.listaCuentasPropias = listaCuentasPropias;
	}
	public List<Empresa> getListaEmpresasAutorizadas() {
		return listaEmpresasAutorizadas;
	}
	public void setListaEmpresasAutorizadas(List<Empresa> listaEmpresasAutorizadas) {
		this.listaEmpresasAutorizadas = listaEmpresasAutorizadas;
	}
	public List<CuentaFintech> getListaCuentasAutorizadas() {
		return listaCuentasAutorizadas;
	}
	public void setListaCuentasAutorizadas(List<CuentaFintech> listaCuentasAutorizadas) {
		this.listaCuentasAutorizadas = listaCuentasAutorizadas;
	}
	
	public List<CuentaFintech> getListaFintech(){
		return listaFintech;
	}
	public void setListaFintech(List<CuentaFintech> a) {
		listaFintech = a;
	}
	
    public CuentaFintech getCf () {
    	return cf;
    }
    public void setCuentaFintech(CuentaFintech cuenta) {
    	this.cf = cuenta;
    }
    
    public String getIban() {
    	return iban;
    }
    public void setIban(String i) {
    	iban = i;
    }
   
    public Long getUsuario() {
    	return usuario;
    }
    public void setUsuario(Long u) {
    	usuario = u;
    	//individual = cuentaEJB.esIndividual(u);
    	//autorizado = cuentaEJB.esAutorizado(u);
    	//if (individual == true) {
    	//	getCuentasPropias();
    	//} else if (autorizado == true) {
    	//	getEmpresasAutorizadas();
    	//}
    }
    
    public String getTipo() {
    	return tipo;
    }
    public void setTipo(String t) {
    	tipo=t;
    }
    
    
    public String seleccionarEmpresa(String empresa) {
    	this.emp = empresa;
    	//getCuentasAutorizadas();
    	return "vistaEmpresaSeleccionada.xhtml";
    }
    public String seleccionarCuenta(String cuenta) {
    	this.cf = cuentaEJB.getCuentaFintech(cuenta);  
    	iban = cuentaEJB.getIbanCuenta(cuenta);
    	return "vistaCuentaSaldo.xhtml";
    }
    
    private void getCuentasPropias() {
    	listaCuentasPropias = cuentaEJB.getCuentasFintechPropias(usuario);
    }
    private void getEmpresasAutorizadas() {
    	listaEmpresasAutorizadas = cuentaEJB.getEmpresasAutorizadas(usuario);
    }
    //private void getCuentasAutorizadas() {
    //	listaCuentasAutorizadas = cuentaEJB.getCuentasAutorizadas(emp);
    //}
    
    public void crearCuentaF() throws EburyAppException {
    	if (autorizado == true) {
    		cuentaEJB.crearCuentaFintech(cf, tipo, usuario, emp);
    	} else {
    		cuentaEJB.crearCuentaFintech(cf, tipo, usuario);
    	}
    }

    public void cerrarCuentaF() throws EburyAppException {
		cuentaEJB.cerrarCuentaFintech(iban);
		
    }
    
    public String vistaCrearCuenta() {
		return "vistaCrearCuentaFintech.xhtml";
	}
    public String vistaEliminarCuenta() {
		
		return "vistaEliminarCuentaFintech.xhtml";
	}
    public String vistaPrincipalCliente() {
    	return "vistaPrincipalCliente.xhtml";
    }
    public String vistaConfirmarEliminar(){
		try {
			cerrarCuentaF();
		} catch (EburyAppException e) {
			e.printStackTrace();
		}
    	return "vistaPrincipalCliente.xhtml";
    }
    
    public String irCambioDivisa() {
    	trBB.setIban(iban);
    	return "vistaCambioDivisa.xhtml";
    }
    
    //public void guardarTodasFintech() {
    //	listaFintech = cuentaEJB.getAllFintech();
    //}
    
    public boolean compruebaSaldoCero(CuentaFintech cue) {
    	Double saldo = 0;
    	if (cue instanceof Segregada) {
    		Segregada s = (Segregada)cue;
    		saldo = s.getCuentaRef().getSaldo();
    	} else {
        		Pooled s = (Pooled)cue;
        		Collection<Double> aux = s.getDepositadaEn().values();
        		if (aux.isEmpty()) {
        			return true;
        		} else {
        			for (Double d : aux) {
        				saldo += d;
        			}
        		}
    	}
    	return saldo == 0;
    }
    
    public Double devuelveSaldo(CuentaFintech cue) {
    	Double saldo = 0.0;
    	if (cue instanceof Segregada) {
    		Segregada s = (Segregada)cue;
    		saldo = s.getCuentaRef().getSaldo();
    	} else {
        		Pooled s = (Pooled)cue;
        		Collection<Double> aux = s.getDepositadaEn().values();
        			for (Double d : aux) {
        				saldo += d;
        			}
        		
    	}
    	return saldo;
    }
} 
