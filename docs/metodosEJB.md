## Métodos EJB:

- RF1  -> `public void login*(String cuenta, String clave)`
- RF2  -> `public void crearCliente(Cliente c)`
- RF3  -> `public void modificarCliente(Cliente c, String cliente)`
- RF4  -> `public void bajaCliente(String cliente)`
- RF5  -> `public void crearCuentaFintech(CuentaFintech cf)`
- RF6  -> Incluye crear un autorizado: `public void agregarAutorizado(PersonaAutorizada p, String empresa, Character cuenta)`
- RF7  -> `public void modificarAutorizado(PersonaAutorizada p, String autorizado)`
- RF8  -> `public void eliminarAutorizado(String autorizado, String empresa)` `public void bajaAutorizado(String autorizado)`
- RF9  -> `public void cerrarCuenta(String cuenta)`
- RF10 -> `public void comprobarCliente(String cliente)`
- RF11 -> `public void informeHolanda(String JSON)`
- RF12 -> `public void informeAlemaniaInicio()` `public void informeAlemaniaPeriodico()`
- RF15 -> Condición extra a controlar en: `cambioDivisa()`. Podría requerir un nuevo atributo o estructura de control para `personaAutorizada`.
- RF17 y 18 -> `public void cambioDivisa(String cuentaPool, String divOrigen, String divDestino, Double cantidad)`
- RF*  -> `public void verificarCuenta(Cuenta cuenta)`


>/* A la hora de crear una cuentafintech que no tiene asociada una cuenta referencia, se le crea una vacía automáticamente? (Aplicable también a cliente/personaautorizada con usuario). Otra idea sería tener una cuentareferencia por defecto para estos casos */
/* En los métodos en que se crea una entidad que se relaciona con otras, habría que pasar las otras entidades con que se relaciona por parámetro para actualizarlas también */

> Para RF17 y 18 se combinan las condiciones para realizar la operación en un solo método.
