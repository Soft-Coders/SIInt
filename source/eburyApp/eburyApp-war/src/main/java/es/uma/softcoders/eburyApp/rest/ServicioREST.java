package es.uma.softcoders.eburyApp.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.uma.softcoders.eburyApp.ejb.InformesEJB;

@Path("/agenda")
public class ServicioREST {
	@EJB
	private InformesEJB informes;
	@Context
	private UriInfo uriInfo;
	
	/**
	 * Endpoint encargado de la comprobación de estado del servicio.
	 * 
	 * @return "OK"
	 * @author Ignacio Lopezosa
	 */
	@Path("/healthcheck")
	@GET
	public Response healthcheck() {
		return Response.ok("OK").build();
	}
	
	/**
	 * Endpoint encargado de devolver información de los clientes que cumplan con una determinada cadena de consulta.
	 * Esta consulta ha de ser en formato JSON.
	 * 
	 * @param request Consulta de <b>Clientes</b> en formato JSON
	 * @return <b>Lista de Clientes</b> que cumplen con las condiciones de la consulta.
	 * @author Ignacio Lopezosa
	 */
	@Path("/clients")
	@POST
	@Produces ({MediaType.APPLICATION_JSON})
	@Consumes ({MediaType.APPLICATION_JSON})
	public Response aniadirContacto(String request) {
			
			return Response.ok().build();
	}
	
	/**
	 * Endpoint encargado de devolver información de las cuentas que cumplan con una determinada cadena de consulta.
	 * Esta consulta ha de ser en formato JSON.
	 * 
	 * @param request Consulta de <b>Cuentas</b> en formato JSON
	 * @return <b>Lista de Clientes</b> que cumplen con las condiciones de la consulta.
	 * @author Ignacio Lopezosa
	 */
	@Path("/products")
	@GET
	@Produces ({MediaType.APPLICATION_JSON})
	@Consumes ({MediaType.APPLICATION_JSON})
	public Response getContacto(String request) {
			
			return Response.ok().build();
		
	}
}