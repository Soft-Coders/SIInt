package es.uma.softcoders.eburyApp.exceptions;

public class ClienteNoValidoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public ClienteNoValidoException(String m){
        super(m);
    }

}
