package es.uma.softcoders.eburyApp.backing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ClienteController {

       public String getSaludo() {
               return "Hola mundo";
       }

} 