package es.uma.softcoders.eburyApp.exceptions;

public class UsuarioNoAdministrativoException extends RuntimeException{

    public UsuarioNoAdministrativoException(String message) {
		super(message);
	}
}
