package es.uma.softcoders.eburyApp.exceptions;

public class ClienteNoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public ClienteNoEncontradoException(String e){
        super(e);
    }
}
