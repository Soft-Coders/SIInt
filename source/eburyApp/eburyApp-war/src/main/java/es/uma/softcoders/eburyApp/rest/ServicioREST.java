package es.uma.softcoders.eburyApp.rest;

import java.net.URI;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import es.uma.informatica.sii.agendaee.entidades.Contacto;
import es.uma.informatica.sii.agendaee.entidades.Usuario;
import es.uma.informatica.sii.agendaee.negocio.AgendaException;
import es.uma.informatica.sii.agendaee.negocio.ContactoInexistenteException;
import es.uma.informatica.sii.agendaee.negocio.Negocio;

@Path("/agenda")
public class ServicioREST {
	@EJB
	private Negocio negocio;
	@Context
	private UriInfo uriInfo;
	
	@HeaderParam("User-auth")
	private String autorizacion;
	
	@Path("/contactos")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getContactos() {
		Usuario usuario = getUsuario();
		if (usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {	
			usuario = negocio.refrescarUsuario(usuario);
			return Response.ok(usuario).build();
		} catch (AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	@Path("/contactos")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response aniadirContacto(Contacto contacto) {
		Usuario usuario = getUsuario();
		if (usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		if(contacto == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		try {	
			usuario = negocio.refrescarUsuario(usuario);
			
			contacto.setUsuario(usuario);
			negocio.insertar(contacto);
			
			URI uri = uriInfo.getBaseUriBuilder().path("agenda/contacto/"+contacto.getId()).build();
			return Response.created(uri).build();
		} catch (AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@Path("/contacto/{id}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getContacto(@PathParam("id") Long id) {
		Usuario usuario = getUsuario();
		if (usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {	
			usuario = negocio.refrescarUsuario(usuario);
			
			Contacto contacto = negocio.obtenerContacto(usuario, id);
			
			return Response.ok(contacto).build();
		} catch (AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@Path("/contacto/{id}")
	@PUT
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response modificarContacto(@PathParam("id") Long id, Contacto contacto) {
		Usuario usuario = getUsuario();
		if (usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		if(contacto == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		try {	
			usuario = negocio.refrescarUsuario(usuario);
			
			Contacto c = negocio.obtenerContacto(usuario, contacto.getId());
			c.setUsuario(usuario);
			c.setEmail(contacto.getEmail());
			c.setId(contacto.getId());
			c.setNombre(contacto.getNombre());
			c.setTelefono(contacto.getTelefono());
			
			negocio.modificar(c);
			URI uri = uriInfo.getBaseUriBuilder().path("agenda/contacto/"+contacto.getId()).build();
			
			return Response.ok(uri).build();
		} catch (AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@Path("/contacto/{id}")
	@DELETE
	public Response borrarContacto(@PathParam("id") Long id) {
		Usuario usuario = getUsuario();
		if (usuario == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {	
			usuario = negocio.refrescarUsuario(usuario);
			
			negocio.eliminarContacto(negocio.obtenerContacto(usuario, id));
			
			return Response.ok().build();
		} catch (AgendaException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	private Usuario getUsuario() {
		if (autorizacion == null) {
			return null;
		}
		
		String [] partesAutorizacion = autorizacion.split(":");
		if (partesAutorizacion.length != 2) {
			return null;
		}
		
		Usuario usuario = new Usuario();
		usuario.setCuenta(partesAutorizacion[0]);
		usuario.setContrasenia(partesAutorizacion[1]);
		
		return usuario;
	}

}
