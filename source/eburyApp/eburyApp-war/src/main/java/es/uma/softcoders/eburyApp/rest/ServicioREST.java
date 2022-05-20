package es.uma.softcoders.eburyApp.rest;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.ejb.GestionInformes;
import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Path("")
public class ServicioREST {
	@EJB
	private GestionInformes informes;
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
	@Produces({MediaType.TEXT_PLAIN})
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
	public Response clients(String request) {
			try {
				List<Cliente> clients = informes.customer(request);
				JsonArrayBuilder clientsArr = Json.createArrayBuilder();
				
				System.out.println("clients:\n=======\n" + clients + "\n=======\n");				
				
				for(Cliente c : clients) {
					
					System.out.println("c:\n=======\n" + c + "\n=======\n");
					
					JsonArrayBuilder products = Json.createArrayBuilder();
					for(CuentaFintech cf : c.getCuentas()) {
						
						System.out.println("cf of c:\n=======\n" + cf + "\n=======\n");
						
						products.add(Json.createObjectBuilder()
								.add("productNumber", cf.getIban())
								.add("status", cf.getEstado())
								.add("relationship", "propietaria"));	//  Los Clientes solo pueden ser dueños, no PersonasAutorizadas
						
						System.out.println("products:\n=======\n" + products.build() + "\n=======\n");
					}
					
					boolean isIndividual = c instanceof Individual;
					
					System.out.println("isIndividual:\n=======\n" + isIndividual + "\n=======\n");
					
					JsonObjectBuilder individual = Json.createObjectBuilder().add("products", products)
					.add("activeCustomer", (c.getEstado().equals("ACTIVO")) ? true : false)
					.add("identificationNumber", c.getIdentificacion())
					.add("dateOfBirth", isIndividual ? (((Individual) c).getFechaNacimiento() != null ? ((Individual) c).getFechaNacimiento().toGMTString() : "non-existent") : "non-existent");
					
					if(isIndividual)
						individual.add("name", Json.createObjectBuilder()
								.add("firstName", ((Individual) c).getNombre())
								.add("lastName", ((Individual) c).getApellido()));
					else
						individual.add("name", ((Empresa) c).getRazonSocial());
										
					individual.add("address", Json.createObjectBuilder()
							.add("streetNumber", c.getDireccion())
							.add("postalCode", c.getCodigoPostal())
							.add("city", c.getCiudad())
							.add("country", c.getPais()));
										
					clientsArr.add(individual);
				}
				
				return Response.ok(Json.createObjectBuilder().add("Individual", clientsArr).build()).build();
			}catch(InvalidJSONQueryException e) {
				// En caso de recibir alguna exceción esperada se devuelve el mensaje de la misma
				// Con una respuesta en status = BAD_REQUEST
				return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
			}
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
	@POST
	@Produces ({MediaType.APPLICATION_JSON})
	@Consumes ({MediaType.APPLICATION_JSON})
	public Response products(String request) {		
		try {
			List<Segregada> products = informes.product(request);
			JsonArrayBuilder productsArr =  Json.createArrayBuilder();
			
			for(Segregada s : products) {
				Cliente c = s.getCliente();	// Accedido frecuentemente
				
				boolean isEmpresa = c instanceof Empresa;
				
				JsonObjectBuilder accountHolder =  Json.createObjectBuilder()
					.add("activeCustomer", (c.getEstado().equals("ACTIVO")) ? true : false)
					.add("accountType", isEmpresa ? "Empresa" : "Fisica");
				
				if(isEmpresa)
					accountHolder.add("name", ((Empresa) c).getRazonSocial());
				else
					accountHolder.add("name", Json.createObjectBuilder()
							.add("firstName", ((Individual) c).getNombre())
							.add("lastName", ((Individual) c).getApellido()));
				
				accountHolder.add("address", Json.createObjectBuilder()
						.add("streetNumber", c.getDireccion())
						.add("postalCode", c.getCodigoPostal())
						.add("city", c.getCiudad())
						.add("country", c.getPais()));
				
				productsArr.add(Json.createObjectBuilder().add("accountHolder", accountHolder)
				.add("status", s.getEstado())
				.add("startDate", s.getFechaApertura().toGMTString())
				.add("endDate", (s.getFechaCierre() == null) ? "non-existent" : s.getFechaCierre().toGMTString()));
			}
			
			return Response.ok(Json.createObjectBuilder().add("products", productsArr).build()).build();
		}catch(InvalidJSONQueryException e) {
			// En caso de recibir alguna exceción esperada se devuelve el mensaje de la misma
			// Con una respuesta en status = BAD_REQUEST
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
