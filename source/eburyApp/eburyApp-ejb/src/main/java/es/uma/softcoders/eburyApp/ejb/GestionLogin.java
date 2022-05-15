package es.uma.softcoders.eburyApp.ejb;

import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

public interface GestionLogin{

    /*
     * La aplicacion permitirá el acceso de la aplicación a aquellos usuarios administrativos
     * que introduzcan correctamente su usuario y contraseña
	 */
    public void loginAdmin(String cuenta, String clave)throws EburyAppException;
    
    /*
     * La aplicacion permitirá el acceso de la aplicación a aquellos usuarios convencionales
     * que introduzcan correctamente su usuario y contraseña
	 */
    public void loginUsuario(String cuenta, String clave)throws EburyAppException;
    


}