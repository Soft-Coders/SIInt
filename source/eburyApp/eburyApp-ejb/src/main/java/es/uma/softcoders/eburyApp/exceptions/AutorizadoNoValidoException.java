package es.uma.softcoders.eburyApp.exceptions;

public class AutorizadoNoValidoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public AutorizadoNoValidoException(String m){
        super(m);
    }

}
