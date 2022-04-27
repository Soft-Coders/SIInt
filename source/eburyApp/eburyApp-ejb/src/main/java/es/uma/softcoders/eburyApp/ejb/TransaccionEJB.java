package es.uma.softcoders.eburyApp.ejb;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Transaccion;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DivisaInexistenteException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.SaldoInsuficienteException;

@Stateless
public class TransaccionEJB implements GestionTransaccion{
	
	@PersistenceContext(name="eburyAppEjb")
	private EntityManager em;
	
	@Override
	public void cambioDivisa(String cuentaPool, String divOrigen, String divDestino, Long cantidad) 
	throws EburyAppException { 
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
		
		// divisaO y divisaD sirven para guardar las divisas divOrigen y divDestino y evitar redundancias
		// si no existen se lanza una excepcion
		Divisa divisaO = em.find(Divisa.class, divOrigen);
		Divisa divisaD = em.find(Divisa.class, divDestino);
		if (divisaO == null) throw new DivisaInexistenteException("DIVISA ORIGEN INEXISTENTE");
		if (divisaD == null) throw new DivisaInexistenteException("DIVISA DESTINO INEXISTENTE");
		
		// Creación de la transacción: se calcula el cambio, se traspasa y se crea la entidad transacción que lo registre
		Long cambioADestino = calculoCambio(divisaO, divisaD, cantidad);
		try {
			transferenciaEntreCuentas(cp, corigen, cdestino, cantidad, cambioADestino);
			crearTransaccionCambioDivisas(cp, divisaO, divisaD, cantidad);
		} catch (SaldoInsuficienteException e) {
			e.printStackTrace();
		}
			
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
	private Long calculoCambio(Divisa divOrigen, Divisa divDestino, Long cantidad) {
		return cantidad * divOrigen.getCambioEuro() / divDestino.getCambioEuro();  
	}
	
	/**
	 * Este método resta la cantidad cant a la cuenta origen y le suma cantCambiada a la cuenta destino.
	 * Además actualiza las relaciones entre la Pooled y las CuentaReferencias para reflejar este cambio.
	 * */
	private void transferenciaEntreCuentas(Pooled pool, CuentaReferencia corigen, CuentaReferencia cdestino, Long cant, Long cantCambiada) throws SaldoInsuficienteException{
		Long a = corigen.getSaldo() - cant;  // a: saldo cambiado en la cuenta referencia origen
		
		// Si no hay saldo suficiente para realizar el cambio se lanza una excepción
		if (a < cant) throw new SaldoInsuficienteException("SALDO INSUFICIENTE PARA EL CAMBIO");
		else{   // Si hay saldo suficiente se realiza la transacción
			
			// Primero: se actualiza el saldo total de las cuentaReferencias
			corigen.setSaldo(a);
			Long b = cdestino.getSaldo() + cantCambiada;  // b: saldo cambiado en la cuenta referencia destino
			cdestino.setSaldo(b);
			
			// Segundo: se actualiza el saldo en las relaciones Pooled-CuentaReferencia
			//    Empezamos actualizando las relaciones de ambas cuentas referencias con la pooled
			Map<Pooled, Long> listaCuentasPooled = corigen.getDepositadaEn();
			listaCuentasPooled.put(pool, a);
			listaCuentasPooled = cdestino.getDepositadaEn();
			listaCuentasPooled.put(pool, b);
			//    Continuamos con la relación dentro de la cuenta pooled con las cuentas referencias
			Map<CuentaReferencia, Long> listaCuentaReferencias = pool.getDepositadaEn();
			a = listaCuentaReferencias.get(corigen);   // reutilizamos la variable auxiliar
			listaCuentaReferencias.put(corigen, (a - cant));
			b = listaCuentaReferencias.get(cdestino);  // reutilizamos la variable auxiliar
			listaCuentaReferencias.put(cdestino, (b + cantCambiada));

		}		
	}
	
	/**
	 * Este método crea una Transacción con los datos de el cambio de divisa */
	private void crearTransaccionCambioDivisas(Pooled pool, Divisa demisor, Divisa dreceptor, Long cantidad) {
		Date ahora = new Date();
		Transaccion trans = new Transaccion(ahora, "Cambio Divisas", demisor, dreceptor, pool, pool);
		trans.setCantidad(cantidad);
		em.persist(trans);
	}
	
	
}
