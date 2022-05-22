package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

public interface GestionCliente {

    public List<Cliente> clientesInactivos() throws EburyAppException;

    public List<Cliente> clientesActivos() throws EburyAppException;
    
    /**
     * La aplicacion permitira crear un cliente y añadirlo a la base de datos,
     * dependiendo del tipo de cliente que se de de alta
     * @param c     Cliente a dar de alta en el sistema
     * @throws EburyAppException 
	 */
    public void altaCliente(Cliente c) throws  EburyAppException;
    /**
     * La aplicacion permitira modificar los datos de los clientes tras rellenar un formulario,
     * se comprobará que los datos obligatorios no sean nulos y en caso de ser clientes empresa o individual
     * se cambiarán los datos pertinentes.
     * @param c Cliente con los datos modificados
     * @param cliente   Primary key del cliente al que cambiar los datos
	 */
    public void modificarCliente(Cliente c, Long cliente)throws EburyAppException;

    /**
     * La aplicacion comprobará que el cliente no es una empresa y, en caso de que no lo sea
     * se comprobará que el cliente posee un usuario y su estado es ACTIVO. RF10
     * @param cliente   PK del individual o la empresa a comprobar
     * @throws EburyAppException 
	 */
    public void comprobarCliente(Long cliente) throws EburyAppException;

    /**
     * La aplicacion comprobará que la persona autorizada está en estado ACTIVO, posee una autorización y, además, 
     * tiene asignado un Usuario 
     * @param aut   PK de la persona autorizada a comprobar
     * @throws EburyAppException 
	 */
    
    public void comprobarAutorizado(Long aut) throws EburyAppException;

    /**
     * La aplicacion permitirá dar de baja a un cliente cambiando su estado a INACTIVO
     * @param cliente   PK del cliente a dar de baja en el sistema
     * @throws EburyAppException 
	 */
    public void bajaCliente(Long cliente) throws EburyAppException;
}
