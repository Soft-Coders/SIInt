package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVParser;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.omg.PortableInterceptor.SUCCESSFUL;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.GestionInformes;
import es.uma.softcoders.eburyApp.ejb.InformesEJB;
import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

public class TestInformes {

    private static final String INFORMES_EJB = "java:global/classes/InformesEJB";
    private static final String UNIDAD_PERSISTENCIA_PRUEBAS = "eburyAppTest";
    private GestionInformes gestionInformes;

    @Before
    public void setup() throws NamingException {
        gestionInformes = (GestionInformes) new InformesEJB();
        BaseDatosInformes.inicializaBaseDatos(UNIDAD_PERSISTENCIA_PRUEBAS);
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
     *      <li>Query o petición de un cliente empresa de forma correcta, devolverá:
     *      <ul>
     *      <li>- <b>NullPointerException</b> si la lista de cuentas está vacía (Fallo).</li>
     *      <li>- <b>InvalidJSONQueryException</b> si la query es incorrecta (Fallo).</li></ul></li>
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
            name.put("firstName", "Pep");
            name.put("lastName", "Doe");

            addr.put("Number", "54");
            addr.put("postalCode", "7207KE");
            addr.put("country", "NL");

            sP.put("questionType", "Customer");
            sP.put("startPeriod", "2015-04-25");
            sP.put("endPeriod", "2020-04-25");
            sP.put("adress", addr);
            sP.put("name",name);

            json.put("searchParameters", sP);
            
            String query = JSONValue.toJSONString(json);



            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty())
                {
                    fail("No deberia ser vacia");
                }
            
            }catch(NullPointerException | InvalidJSONQueryException e){
                fail("No debería de lanzar esta excepcion");

            }

            // Prueba de invalidez de query para Cliente  / query Customer
            JSONObject json2 = new JSONObject();
            JSONObject sP2 = new JSONObject();
            JSONObject addr2 = new JSONObject();
            JSONObject name2 = new JSONObject();
            name2.put("firstName", "Pep");
            name2.put("lastName", "Doe");

            addr2.put("Number", "54");
            addr2.put("postalCode", "7207KE");
            addr2.put("country", "NL");

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


            // TEST CON CLIENTE EMPRESA query Customer
            
            JSONObject json3 = new JSONObject();
            JSONObject sP3 = new JSONObject();
            JSONObject addr3 = new JSONObject();
            
            addr3.put("city", "Amsterdam");
            addr3.put("postalCode", "7207KE");
            addr3.put("country", "NL");

            sP3.put("questionType", "Customer");
            sP3.put("startPeriod", "2015-04-25");
            sP3.put("endPeriod", "2020-04-25");
            sP3.put("adress", addr3);

            json3.put("searchParameters", sP3);
            
            query = JSONValue.toJSONString(json3);
            

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty())
                {
                    fail("No deberia ser vacia");
                }
            }catch(NullPointerException | InvalidJSONQueryException e){
                    fail("No debe dar error");
            }

            //Cuenta activa query product
            JSONObject json4 = new JSONObject();
            JSONObject sP4 = new JSONObject();
            sP4.put("questionType","Product");
            sP4.put("status", "active");
            sP4.put("productNumber", "NL66XYZW1291965209");

            json4.put("searchParameters", sP4);

            query=JSONObject.toJSONString(json4);

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty()){
                    fail("No debe ser vacía");
                }
            }catch(NullPointerException|InvalidJSONQueryException e){

                fail("No debe dar error");
            }

            //Cuenta inactiva query product
            JSONObject json5 = new JSONObject();
            JSONObject sP5 = new JSONObject();
            sP5.put("questionType","Product");
            sP5.put("status", "inactive");
            sP5.put("productNumber", "NL66XYZW1291965208");

            json5.put("searchParameters", sP5);

            query = JSONObject.toJSONString(json5);

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty()){
                    fail("No debe ser vacía");
                }
            }catch(NullPointerException|InvalidJSONQueryException e){

                fail("No debe dar error");
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
        String path = "C:\\";
        try {
            BaseDatosInformes.setCuentas1();
            gestionInformes.testInformeAlemaniaInicio(path);
            File csvData = new File(path);
            CSVParser parser = CSVParser.parse(csvData,CSVFormat.DEFAULT);
            // ESTE FOR EACH ES PARA LA SEGUNDA PRUEBA
            int cont = 0;
            for(CSVRecord csvRecord : parser){
                cont++;
            }
            if(cont > 4){
                fail("Hay más lineas de las que debería");
            }
        }catch(IllegalArgumentException|FailedInitialCSVException e){
            fail("No debería dar error");
        }

        try {
            String temp;
            BaseDatosInformes.setCuentas2();
            gestionInformes.testInformeAlemaniaInicio(path);
            File csvData = new File(path);
            CSVParser parse = CSVParser.parse(csvData,CSVFormat.DEFAULT);
            for(CSVRecord csvRecord : parser){
                if(csvRecord.isMapped("IBAN")){
                    temp = csvRecord.get("IBAN");
                    if(temp == "45"){
                        fail("Debería haber saltado excepción");
                    }
                }
            }
        }catch(FailedInitialCSVException e){
            //Success
        }catch(IllegalArgumentException e){
            fail("No debería dar este error");
        }

        try {
            int cont = 0;
            BaseDatosInformes.setCuentas3();
            gestiomInformes.testInformeAlemaniaInicio(path);
            File csvData = new File(path);
            CSVParser parse = CSVParser.parse(csvData,CSVFormat.DEFAULT);
            for(CSVRecord csvRecord : parser){
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

        }catch(IllegalArgumentException e){
            fail("No debería dar este error");
        }catch(FailedInitialCSVException e){
            fail("No debería dar este error");
        }

    }


    @Test
    @Requisitos({"RF12"})
    public void testInformeAlemaniaPeriodico(){

    }
    
}
