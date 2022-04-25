package es.uma.softcoders.eburyApp.ejb;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.exceptions.ClienteExistenteException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNuloException;
import es.uma.softcoders.eburyApp.exceptions.ObligatorioNuloException;

public interface GestionCliente {
    
    /*
     * La aplicacion permitira crear un cliente y añadirlo a la base de datos,
     * si el cliente existe lanzara una excepcion 
	 */
    public void altaCliente(Cliente c) throws ObligatorioNuloException, ClienteExistenteException;
    
    public void modificarCliente(Cliente c, String cliente)throws ObligatorioNuloException, ClienteNuloException;

    public void comprobarCliente(String cliente);

    public void bajaCliente(String cliente) throws ClienteNoEncontradoException;
}
