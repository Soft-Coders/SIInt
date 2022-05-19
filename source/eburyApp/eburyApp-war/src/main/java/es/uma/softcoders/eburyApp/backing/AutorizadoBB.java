package es.uma.softcoders.eburyApp.backing;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;


@Named(value="AutorizadoBB")
@RequestScoped
public class AutorizadoBB {
    
	@Inject 
	private GestionAutorizado interfazAutorizado;
	private GestionCliente interfazCliente;
	private PersonaAutorizada autorizado;
	private Empresa empresa;
	private List<Empresa> listaEmpresas;
	private List<PersonaAutorizada> listaAutorizados;
	
	/*
	public AutorizadoBB() {
		//this.listaAutorizados = interfazAutorizado.getAutorizados;
		//this.listaEmpresas = interfazEmpresas.getEmpresas;
	}
	
	public void setAutorizado(int id) {
		//this.autorizado = listaAutorizados.get(id);
	}
	
	public void setListaAutorizados(List<PersonaAutorizada> listaNueva) {
		this.listaAutorizados = listaNueva;
	}
	
	public List<PersonaAutorizada> getListaAutorizados() {
		return listaAutorizados;
	}
	
	public PersonaAutorizada getAutorizado(int id) {
		return listaAutorizados.get(id);
	}
	
	public void agregarAutorizado(PersonaAutorizada p, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException, UsuarioNoVinculadoException {
		interfazAutorizado.agregarAutorizado(p.getId(), p, empresa.getID(), cuenta);
	}
	
	public void modificarAutorizado(Long autorizacion) throws PersonaAutorizadaNoEncontradaException {
		interfazAutorizado.modificarAutorizado(autorizado, autorizacion);
	}
	
	public void eliminarAutorizado() {
		interfaz.eliminarAutorizado(autorizado, null);
	}
	*/
	
	
	
}
