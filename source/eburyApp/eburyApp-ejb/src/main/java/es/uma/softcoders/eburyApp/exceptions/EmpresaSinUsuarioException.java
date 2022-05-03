package es.uma.softcoders.eburyApp.exceptions;

public class EmpresaSinUsuarioException extends RuntimeException{

    private static final long serialVersionUID = 1L;

	public EmpresaSinUsuarioException(String m){
        super(m);
    }
}
