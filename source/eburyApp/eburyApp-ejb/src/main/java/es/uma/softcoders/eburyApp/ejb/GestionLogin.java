import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoAdministrativoException;

public interface GestionLogin{

    /*
     * La aplicacion permitirá el acceso de la aplicación a aquellos usuarios administrativos
     * que introduzcan correctamente su usuario y contraseña
	 */
    public void loginAdmin(String cuenta, String clave)throws UsuarioNoAdministrativoException,ClienteNoEncontradoException,CuentaNoCoincidenteException;
    
    /*
     * La aplicacion permitirá el acceso de la aplicación a aquellos usuarios convencionales
     * que introduzcan correctamente su usuario y contraseña
	 */
    public void loginUsuario(String cuenta, String clave)throws ClienteNoEncontradoException,CuentaNoCoincidenteException;
    


}