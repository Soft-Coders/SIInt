package es.uma.softcoders.eburyApp.exceptions;

public class ClienteExistenteException extends EburyAppException{
    private static final long serialVersionUID = 1L;

	public ClienteExistenteException(String e){
        super(e);
    }
}
