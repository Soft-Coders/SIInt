package es.uma.softcoders.eburyApp.backing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "registro")
@RequestScoped
public class Registro {

       public String registroIndividual() {return "registroIndividual.xhtml";}

       public String registroEmpresa(){return "registroEmpresa.xhtml";}

       
} 