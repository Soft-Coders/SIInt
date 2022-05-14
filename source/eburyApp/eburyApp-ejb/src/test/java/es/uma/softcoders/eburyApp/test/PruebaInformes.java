package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.ejb.GestionInformes;
import es.uma.softcoders.eburyApp.exceptions.FailedInitialCSVException;
import es.uma.softcoders.eburyApp.exceptions.FailedPeriodicCSVException;
import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

public class PruebaInformes {

    private static final String INFORMES_EJB = "java:global/classes/InformesEJB";
    private static final String UNIDAD_PERSISTENCIA_PRUEBAS = "eburyAppTest";
    private GestionInformes gestionInformes;

    @Before
    public void setup() throws NamingException {
        gestionInformes = (GestionInformes) SuiteTest.ctx.lookup(INFORMES_EJB);
        BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSISTENCIA_PRUEBAS);
    }

    /**
     * Este test comprueba el metodo testInformeHolanda() mediante la creación de un JSONObject -> String para los siguientes casos:
     *      <ul>
     *      <li>Query o petición de un cliente individual de forma correcta, devolverá:
     *      <ul>
     *      <li>- <b>NullPointerException</b> si la lista de cuentas está vacía (Fallo).</li>
     *      <li>- <b>InvalidJSONQueryException</b> si la query es incorrecta (Fallo). </li></ul></li>
     *      <li>Query o petición de un cliente individual de forma INCORRECTA, hace catch de:
     *      <ul>
     *      <li>- <b>InvalidJSONQueryException</b> (Éxito).</li></ul></li>
     *      
     *      <li>Query o petición de cuentas activas, devolverá:
     *      <ul>
     *      <li>- <b>NullPointerException</b> si la lista de cuentas está vacía (Fallo).
     *      <li>- <b>InvalidJSONQueryException</b> si la query es incorrecta (Fallo).</li></ul></li>
     *      <li>Query o petición de cuentas inactivas, devolverá:
     *      <ul>
     *      <li>- <b>NullPointerException</b> si la lista de cuentas está vacía (Fallo).
     *      <li>- <b>InvalidJSONQueryException</b> si la query es incorrecta (Fallo).</li></ul></li>
     *      <li>Query o petición de cuentas de forma INCORRECTA, devolverá:
     *      <ul>
     *      <li>- <b>InvalidJSONQueryException</b> (Éxito). </li></ul></li></ul>
     *          
     * @author Jesús Cestino
     */
    @Test
    @Requisitos({"RF11"})
    public void testInformeHolanda(){
            
            // Prueba de query para Cliente / query Customer
            JSONObject json = new JSONObject();
            JSONObject sP = new JSONObject();
            JSONObject addr = new JSONObject();
            JSONObject name = new JSONObject();
            name.put("firstName", "Cliente");
            name.put("lastName", "Prueba");

            
            addr.put("postalCode", "29010");
            

            sP.put("questionType", "Customer");
        
            sP.put("address", addr);
            sP.put("name",name);

            json.put("searchParameters", sP);
            
            String query = JSONValue.toJSONString(json);

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty())
                {
                    fail("No deberia ser vacia-0");
                }
            
            }catch(NullPointerException | InvalidJSONQueryException e){
            	System.out.println("\nTTTTT" + query + "\nTTTTT");
                fail("No debería de lanzar esta excepcion-1:" + e.getClass() + "-" + e.getMessage() + " ->\n" + e.getStackTrace());
            }

            // Prueba de invalidez de query para Cliente  / query Customer
            JSONObject json2 = new JSONObject();
            JSONObject sP2 = new JSONObject();
            JSONObject addr2 = new JSONObject();
            JSONObject name2 = new JSONObject();
            name2.put("firstName", "Cuenta");
            name2.put("lastName", "Prueba");

            
            addr2.put("postalCode", "29010");
            

            sP2.put("questionTipe", "Customer");
            sP2.put("startPeriodo", "2015-04-25");
            sP2.put("endPeriodo", "2020-04-25");
            sP2.put("adress", addr2);
            sP2.put("namae",name2);

            json2.put("searchParameters", sP2);
            
            query = JSONValue.toJSONString(json2);
            
            try{
                List<Object>pRes = gestionInformes.informeHolanda(query);
                
            }catch(InvalidJSONQueryException e){
                
            }

            //Cuenta activa query product
            JSONObject json4 = new JSONObject();
            JSONObject sP4 = new JSONObject();
            sP4.put("questionType","Product");
            sP4.put("status", "active");
            sP4.put("productNumber", "cpSegregada");

            json4.put("searchParameters", sP4);

            query=JSONObject.toJSONString(json4);

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty()){
                    fail("No debe ser vacía-4");
                }
            }catch(NullPointerException|InvalidJSONQueryException e){

                fail("No debe dar error");
            }

            //Cuenta inactiva query product
            JSONObject json5 = new JSONObject();
            JSONObject sP5 = new JSONObject();
            sP5.put("questionType","Product");
            sP5.put("status", "inactive");
            sP5.put("productNumber", "cpInactiva");

            json5.put("searchParameters", sP5);

            query = JSONObject.toJSONString(json5);

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty()){
                    fail("No debe ser vacía");
                }
            }catch(NullPointerException|InvalidJSONQueryException e){

                fail("No debe dar error-5");
            }

            // Product invalid JSON Query
            JSONObject json6 = new JSONObject();
            JSONObject sP6 = new JSONObject();
            sP6.put("questionaTyde","Product"); // Línea inválida
            sP6.put("status", "inactive");
            sP6.put("productNumber", "NL66XYZW1291965208");

            json6.put("searchParameters", sP6);

            try{
                List<Object>pRes = gestionInformes.informeHolanda(query);                
            }catch(InvalidJSONQueryException e){
                
            }
            

    }
    /**
     * Este test comprueba el funcionamiento del método informeAlemaniaInicio() en los siguientes casos:
     * <ul>
     *      <li>Set de cuentas segregadas correctas en un cliente, devolverá:
     *      <ul>
     *      <li>- <b>FailedInitialCSVException</b> si existe un fallo interno en el proceso de creación del CSV (Fallo).</li>
     *      <li>- <b>IllegalArgumentException</b> si no existe un valor que mapee el resultado de una línea (Fallo). </li></ul></li>
     * 
     *      <li>Set de cuentas segregadas correctas en un cliente con UNA cuenta incorrecta, devolverá:
     *      <ul>
     *      <li>- <b>FailedInitialCSVException</b> si existe un fallo interno en el iban de una cuenta (Éxito).</li>
     *      <li>- <b>IllegalArgumentException</b> si no existe un valor que mapee el resultado de una línea (Fallo). </li></ul></li>
     * 
     *      <li>Set de cuentas segregadas correctas en un cliente, devolverá:
     *      <ul>
     *      <li>- <b>FailedInitialCSVException</b> si existe un fallo interno en el proceso de creación del CSV (Fallo).</li>
     *      <li>- <b>IllegalArgumentException</b> si no existe un valor que mapee el resultado de una línea (Fallo). </li>
     *      <li>- <b>Fallo</b> si no contabiliza correctamente la fecha de nacimiento como "noexistente"</ul></li>
     * 
     * 
     * </ul>
     * @author Jesús Cestino
     */

    @Test 
    @Requisitos({"RF12"})
    public void testInformeAlemaniaInicio(){
        String path = "informe.csv";
        try {
            gestionInformes.informeAlemaniaInicio(path);
            try{
	            Reader csvData = new FileReader(path);
	            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvData);
	            int cont = 0;
				for (CSVRecord record : records) {
	                cont++;
				}
	            if(cont != 3){
	                fail("No hay las líneas que debería: " + cont);
	            }
            }catch(IllegalArgumentException|FileNotFoundException e){
                fail("No deberia dar error 1");
            }catch (IOException e) {
				throw new RuntimeException(e);
			}
            
        }catch(FailedInitialCSVException e){
            fail("No debería dar error 2" + e.getMessage() + e.getCause() +e.getClass() +e.getStackTrace());
        }catch(Exception e){
            fail("Error" + e.getMessage());
        }
    }
    
	/**
     * Este test comprueba el funcionamiento del método informeAlemaniaPeriodico() en los siguientes casos:
     * <ul>
     *      <li>Set de cuentas segregadas correctas en un cliente, devolverá:
     *      <ul>
     *      <li>- <b>FailedInitialCSVException</b> si existe un fallo interno en el proceso de creación del CSV (Fallo).</li>
     *      <li>- <b>IllegalArgumentException</b> si no existe un valor que mapee el resultado de una línea (Fallo). </li></ul></li>
     * 
     *      <li>Set de cuentas segregadas correctas en un cliente con UNA cuenta incorrecta, devolverá:
     *      <ul>
     *      <li>- <b>FailedInitialCSVException</b> si reconoce una cuenta inactiva y con un iban inválido (Éxito).</li>
     *      <li>- <b>IllegalArgumentException</b> si no existe un valor que mapee el resultado de una línea (Fallo). </li></ul></li>
     * 
     *      <li>Set de cuentas segregadas correctas en un cliente, devolverá:
     *      <ul>
     *      <li>- <b>FailedInitialCSVException</b> si existe un fallo interno en el proceso de creación del CSV (Fallo).</li>
     *      <li>- <b>IllegalArgumentException</b> si no existe un valor que mapee el resultado de una línea (Fallo). </li>
     *      <li>- <b>Fallo</b> si no contabiliza correctamente la fecha de nacimiento como "noexistente"</ul></li>
     * 
     * 
     * </ul>
     * @author Jesús Cestino
     */

    @Test
    @Requisitos({"RF12"})
    public void testInformeAlemaniaPeriodico(){
    	String path = "informes2.csv";
        try {
            gestionInformes.informeAlemaniaPeriodico(path);
            try{
	            Reader csvData = new FileReader(path);
	            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvData);
	            int cont = 0;
				for (CSVRecord record : records) {
	                cont++;
				}
	            if(cont != 4){
	                fail("No hay las líneas que debería: " + cont);
	            }
            }catch(IllegalArgumentException|FileNotFoundException e){
                fail("No deberia dar error - 1");
            }catch (IOException e) {
				throw new RuntimeException(e);
			}
            
        }catch(FailedPeriodicCSVException e){
            fail("No debería dar error - 2 " + e);
        }catch(Exception e){
            fail("Error" + e.getMessage());
        }

        try {
            String temp;
            BaseDatosInformes.setCuentas2();
            gestionInformes.informeAlemaniaPeriodico(path);
            try{
	            Reader csvData = new FileReader(path);
	            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvData);
	            for(CSVRecord csvRecord : records){
	                if(csvRecord.isMapped("IBAN")){
	                    temp = csvRecord.get("IBAN");
	                    if(temp == "45"){
	                        fail("No deberia reconocer esta cuenta porque esta inactiva");
	                    }
	                }
	            }
            }catch(IllegalArgumentException|FileNotFoundException e){
                fail("No deberia dar error 2.5");
            }catch (IOException e) {
				throw new RuntimeException(e);
			}
        }catch(FailedPeriodicCSVException e){
            //Success
        }catch(IllegalArgumentException e){
            fail("No debería dar este error - 3");
        }catch(Exception e){
            fail("Error" + e.getMessage());
        }

        try {
            int cont = 0;
            BaseDatosInformes.setCuentas3();
            gestionInformes.informeAlemaniaPeriodico(path);
            try{
	            Reader csvData = new FileReader(path);
	            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvData);
	            for(CSVRecord csvRecord : records){
	                if(csvRecord.isMapped("Date_Of_Birth")){
	                    String temp = csvRecord.get("Date_Of_Birth");
	                    if(temp=="noexistente"){
	                        cont++;
	                    }
	                    if(cont != 1){
	                        fail("Debería haber un \"noexistente\" en el CSV");
	                    }
	                }
	            }
			}catch(IllegalArgumentException|FileNotFoundException e){
                fail("No deberia dar error - 4");
            }catch (IOException e) {
				throw new RuntimeException(e);
			}	
        }catch(FailedPeriodicCSVException e){
            fail("No debería dar este error - 5");
        }catch(Exception e){
            fail("Error" + e.getMessage());
        }

    }
    
}
