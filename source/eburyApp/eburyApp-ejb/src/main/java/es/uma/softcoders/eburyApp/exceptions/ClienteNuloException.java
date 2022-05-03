package es.uma.softcoders.eburyApp.exceptions;

public class ClienteNuloException extends RuntimeException{

    private static final long serialVersionUID = 1L;

	public ClienteNuloException(String e){
        super(e);
    }
}
