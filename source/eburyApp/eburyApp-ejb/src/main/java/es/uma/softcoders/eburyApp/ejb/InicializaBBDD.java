package es.uma.softcoders.eburyApp.ejb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Transaccion;
import es.uma.softcoders.eburyApp.Usuario;

@Singleton
@Startup
public class InicializaBBDD {
	
	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
		
		if(em.find(null, em) != null)
			return;
		
		Empresa e = new Empresa();
		Individual i = new Individual();
		PersonaAutorizada pa = new PersonaAutorizada();
		Segregada nl = new Segregada();
		Segregada fr = new Segregada();
		Segregada de = new Segregada();
		Pooled es = new Pooled();
		Transaccion nl_es = new Transaccion();
		Divisa eur = new Divisa();
		Divisa dol = new Divisa();
		Divisa pnd = new Divisa();
		Usuario juan = new Usuario();
		Usuario ana = new Usuario();
		Usuario pon = new Usuario();
		CuentaReferencia hnC = new CuentaReferencia();
		CuentaReferencia esC = new CuentaReferencia();
		CuentaReferencia vgC = new CuentaReferencia();
		CuentaReferencia gbC = new CuentaReferencia();
		
		e.setIdentificacion("P3310693A");
		e.setTipo_cliente("EMPRESA");
		e.setFecha_Alta(new Date());
		e.setCiudad("Málaga");
		e.setCodigoPostal("29010");
		e.setPais("España");
		e.setRazonSocial("SA");
		
		i.setIdentificacion("63937528N");
		i.setTipo_cliente("INDIVIDUAL");
		i.setFecha_Alta(new Date());
		i.setCiudad("Madrid");
		i.setCodigoPostal("28770");
		i.setPais("España");
		i.setNombre("Oscar");
		i.setApellido("Lopezosa");
		
		Map<Empresa, Character> aut = new HashMap<>();
		aut.put(e, 'W');
		pa.setIdentificacion("Y4001267V");
		pa.setAutorizacion(aut);
		pa.setNombre("Paco");
		pa.setApellidos("Gutierrez");
		pa.setDireccion("La calle de Paco");
		
		eur.setAbreviatura("eur");
		eur.setCambioEuro(1.00);
		
		vgC.setDivisa(dol);
		
		nl.setIban("NL63ABNA6548268733");
		nl.setCliente(e);
		nl.setEstado("ACTIVO");
		nl.setCuentaRef(vgC);
		
	}
	
}
