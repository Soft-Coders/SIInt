## Métodos EJB:
<<<<<<< Updated upstream
- RF1   ->  `public void login*(String cuenta, String clave)`
- RF2   ->  `public void crearCliente(Cliente c)`
- RF3   ->  `public void modificarCliente(Cliente c, String cliente)`
- RF 5 -> `public void crearCuentaFintech(Cuenta c, Cuenta[] referencias)` 
- RF 7 -> `public void modificarAutorizado(PersonaAutorizada p, String autorizado)`
- RF9   ->  `public void cerrarCuenta(String cuenta)`
- RF10  ->  `public void loginCliente(String cuenta, String clave)`
            `public void loginPersonaAutorizada(String cuenta, String clave)`
- RF 11 ->  `public void informeHolanda()`
- RF*   ->  `public void verificarCuenta(Cuenta cuenta)`
- RF 18 -> `public void cambioDivisa(Cuenta c, double cantidad)`



/* A la hora de crear una cuentafintech que no tiene asociada una cuenta referencia, se le crea una vacía automáticamente? (Aplicable también a cliente/personaautorizada con usuario). Otra idea sería tener una cuentareferencia por defecto para estos casos */
/* En los métodos en que se crea una entidad que se relaciona con otras, habría que pasar las otras entidades con que se relaciona por parámetro para actualizarlas también */

