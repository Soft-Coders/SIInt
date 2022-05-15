package es.uma.softcoders.eburyApp.exceptions;

public class UsuarioNoAdministrativoException extends EburyAppException{

    private static final long serialVersionUID = 1L;

	public UsuarioNoAdministrativoException(String message) {
		super(message);
	}
}
