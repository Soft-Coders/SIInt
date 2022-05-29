package es.uma.softcoders.eburyApp.exceptions;

public class UsuarioYaExistenteException extends EburyAppException{
    private static final long serialVersionUID = 1L;

	public UsuarioYaExistenteException(String e){
        super(e);
    }
}
