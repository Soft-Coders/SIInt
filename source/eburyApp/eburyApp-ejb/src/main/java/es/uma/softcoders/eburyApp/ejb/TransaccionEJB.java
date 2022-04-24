package es.uma.softcoders.eburyApp.ejb;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import es.uma.softcoders.eburyApp.Cuenta;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Transaccion;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DivisaInexistenteException;
import es.uma.softcoders.eburyApp.exceptions.SaldoInsuficienteException;

public class TransaccionEJB implements GestionTransaccion{
	
	/* 1*** DEBERÍA COMPROBAR QUE EXISTEN LAS DIVISAS/QUE TIENEN DIVISAS ASOCIADAS LAS CUENTAREFERENCIAS????? */
	/* LINEA 127 - en la entidad Transacción, cantidad debería ser Double */
	/* No sé para qué sirve el LOG, lo tiene el profe pero no se usa */
	
	private static final Logger LOG = Logger.getLogger(CuentaEJB.class.getCanonicalName());
	
	@PersistenceContext(name="eburyApp")
	private EntityManager em;
	
	@Override
	public void cambioDivisa(String cuentaPool, String divOrigen, String divDestino, Double cantidad) 
	throws CuentaNoExistenteException, DivisaInexistenteException {
		// TODO 
		Pooled cp = em.find(Pooled.class, cuentaPool);  //cp: Objeto de la cuenta Pooled
		
		// Si no existe la cuenta se lanza una excepción
		if (cp == null) {
			throw new CuentaNoExistenteException("CUENTA POOLED INEXISTENTE");
		}
		
		// Si la cuenta existe se itera sobre ella para encontrar cuentaReferencias asociadas a las divisas indicadas
		CuentaReferencia corigen = buscarReferencia(cp, divOrigen);
		CuentaReferencia cdestino = buscarReferencia(cp, divDestino);
		if (corigen == null) throw new CuentaNoExistenteException("CUENTA ORIGEN INEXISTENTE");
		if (cdestino == null) throw new CuentaNoExistenteException("CUENTA DESTINO INEXISTENTE");
		
		/* 1*** */
		
		// divisaO y divisaD sirven para guardar las divisas divOrigen y divDestino y evitar redundancias
		// si no existen se lanza una excepcion
		Divisa divisaO = em.find(Divisa.class, divOrigen);
		Divisa divisaD = em.find(Divisa.class, divDestino);
		if (divisaO == null) throw new DivisaInexistenteException("DIVISA ORIGEN INEXISTENTE");
		if (divisaD == null) throw new DivisaInexistenteException("DIVISA DESTINO INEXISTENTE");
		
		Double cambioADestino = calculoCambio(divisaO, divisaD, cantidad);
		try {
			transferenciaEntreCuentas(corigen, cdestino, cantidad, cambioADestino);
		} catch (SaldoInsuficienteException e) {
			e.printStackTrace();
		}
		crearTransaccionCambioDivisas(cp, divisaO, divisaD, cantidad);
		
	}
	
	/**
	 * Este método itera sobre la relación que presenta la cuenta Pooled con CuentaReferencia
	 * para encontrar la que se relaciona con la divisa indicada
	 * */
	private CuentaReferencia buscarReferencia(Pooled pool, String divisa) {
		CuentaReferencia laEncontrada = null; // laEncontrada: cuenta buscada, es null si no se ha encontrado
		// poolRelacion: el iterador
		Iterator<Map.Entry<CuentaReferencia, Long>> poolRelacion = pool.getDepositadaEn().entrySet().iterator();
		CuentaReferencia craux = new CuentaReferencia();  // craux: variable auxiliar para las CuentaReferencias
		
		// Se itera mientras haya valores en el map y la Cuenta no se ha encontrado
		while (poolRelacion.hasNext() && Objects.isNull(laEncontrada)) {
			// entry: los valores que toma el map en cada una de las iteraciones
			// 	entry.getKey(): devuelve la CuentaReferencia
			// 	entry.getValue(): devuelve el saldo de la Pooled en esa CuentaReferencia, en su divisa asociada
			Map.Entry<CuentaReferencia, Long> entry = poolRelacion.next(); 
			craux = entry.getKey();
			if (cuentaReferenciaDeseada(craux, craux.getDivisa().getAbreviatura()) == true) {
				laEncontrada = craux;
			}
		}
		return laEncontrada;
	}
	
	/**
	 * Este método comprueba si la CuentaReferencia se relaciona con la divisa indicada.
	 * Devuelve true si están relacionadas y false si no lo están
	 * */
	private boolean cuentaReferenciaDeseada(CuentaReferencia ref, String divisa) {
		if (ref.getDivisa().getAbreviatura() == divisa) return true;
		else return false;
	}
	
	/**
	 * Este método devuelve el valor de la cantidad introducida en la divisa origen
	 * una vez pasada a la divisa destino
	 * */
	private Double calculoCambio(Divisa divOrigen, Divisa divDestino, Double cantidad) {
		return cantidad * divOrigen.getCambioEuro() / divDestino.getCambioEuro();  
	}
	
	/**
	 * Este método resta la cantidad cant a la cuenta origen y le suma cantCambiada a la cuenta destino
	 * */
	private void transferenciaEntreCuentas(CuentaReferencia corigen, CuentaReferencia cdestino, Double cant, Double cantCambiada) 
	throws SaldoInsuficienteException{
		Double a = corigen.getSaldo();  // a: saldo original en origen
		
		// Si no hay saldo suficiente para realizar el cambio se lanza una excepción
		if (a < cant) throw new SaldoInsuficienteException("SALDO INSUFICIENTE PARA EL CAMBIO");
		else{
			corigen.setSaldo(a - cant);
			Double b = cdestino.getSaldo();
			cdestino.setSaldo(b + cantCambiada);
		}
	}
	
	/**
	 * Este método crea una Transacción con los datos de el cambio de divisa */
	private void crearTransaccionCambioDivisas(Pooled pool, Divisa demisor, Divisa dreceptor, Double cantidad) {
		Date ahora = new Date();
		Transaccion trans = new Transaccion(ahora, "Cambio Divisas", demisor, dreceptor, pool, pool);
		// trans.setCantidad(cantidad);
		em.persist(trans);
	}
	
}
