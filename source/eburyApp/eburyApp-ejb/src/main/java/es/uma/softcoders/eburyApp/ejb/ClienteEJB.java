package es.uma.softcoders.eburyApp.ejb;

import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.exceptions.ClienteExistenteException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontrado;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNuloException;
import es.uma.softcoders.eburyApp.exceptions.ObligatorioNuloException;
@Stateless
public class ClienteEJB implements GestionCliente {

    @PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;

    @Override
    public void altaCliente(Cliente c){
        Cliente clienteEntity = em.find(Cliente.class, c.getID());
        if(clienteEntity != null)
            throw new ClienteExistenteException("El cliente ya existe");

        if(c.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
        if(c.getTipo_cliente()==null)
            throw new ObligatorioNuloException("Tipo del cliente nulo");
        
        if(c.getEstado() == null)
            throw new ObligatorioNuloException("Estado del cliente nulo");
        
        if(c.getFecha_Alta() == null)
            throw new ObligatorioNuloException("Fecha de alta nula");
        
        if(c.getDireccion() == null)
            throw new ObligatorioNuloException("Direccion nula");
        
        if(c.getCiudad() == null)
            throw new ObligatorioNuloException("Ciduad nula");
        
        if(c.getCodigoPostal()==null)
            throw new ObligatorioNuloException("Codigo postal nulo");
        
        if(c.getPais()==null)
            throw new ObligatorioNuloException("Pais nulo");
        

        if(c instanceof Empresa){
            Empresa e = (Empresa) c;
            e.setEstado("ACTIVO");
            Map<PersonaAutorizada, Character> m = e.getAutorizacion();
            Set<PersonaAutorizada> pAs= m.keySet();
             if (pAs == null){

             }

            em.persist(e);
        }else if(c instanceof Individual){
            Individual i = (Individual) c;
            i.setEstado("ACTIVO");
            em.persist(i);
        }
    }

    @Override
    public void modificarCliente(Cliente c, String cliente) {
        Cliente clienteEntity = em.find(Cliente.class, cliente);
        if(clienteEntity == null){
            throw new ClienteNoEncontradoException("Cliente no encotrado");
        }
        if(c == null){
            throw new ClienteNuloException("Cliente nulo");
        }

        if(c.getIdentificacion() == null || c.getTipo_cliente() == null || c.getEstado() == null 
            || c.getFecha_Alta() == null || c.getDireccion() == null || c.getCiudad() == null
            || c.getCodigoPostal() == null || c.getPais() == null){
            throw new ObligatorioNuloException("Algun parametro obligatorio es nulo");
        }

        clienteEntity.setIdentificacion(c.getIdentificacion());
        clienteEntity.setTipo_cliente(c.getTipo_cliente());
        clienteEntity.setEstado(c.getEstado());
        clienteEntity.setFecha_Alta(c.getFecha_Alta());
        clienteEntity.setDireccion(c.getDireccion());
        clienteEntity.setCiudad(c.getCiudad());
        clienteEntity.setCodigoPostal(c.getCodigoPostal());
        clienteEntity.setPais(c.getPais());

        if(clienteEntity instanceof Empresa){
            Empresa e = (Empresa) clienteEntity;
            em.persist(e);
        }else if(clienteEntity instanceof Individual){
            Individual i = (Individual) clienteEntity;
            em.persist(i);
        }else{
            em.persist(clienteEntity);
        }
                
    }

    @Override
    public void comprobarCliente(String cliente) {

    }

    @Override
    public void bajaCliente(String cliente) {
        Cliente clienteEntity = em.find(Cliente.class, cliente);

        if(clienteEntity == null){
            throw new ClienteNuloException("Cliente nulo");
        }

        clienteEntity.setEstado("INACTIVO");
        em.persist(clienteEntity);
    }
    
}
