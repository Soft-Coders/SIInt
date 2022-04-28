package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

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

    @Test
    public void testInformeHolanda(){
            
            // PRIMER TEST
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

            // SEGUNDA PRUEBA
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
            
            /*
            query="{
                \"searchParameters\": {
                \"questionTipe\": \"Customer\",
                \"startPeriodo\": \"2015-04-25\",
                \"endPeriodo\": \"2020-04-25\",
                \"namae\": {
                \"firstName\": \"Pep\",
                \"lastName\": \"Doe\"
                },
            \"address\": {
                \"Number\": \"54\",
                \"postalCode\": \"7207KE\",
                \"country\": \"NL\"
                }
            }";
            */ // ALGUNAS LINEAS DEL JSON INVALIDAS

            try{
                List<Object>pRes = gestionInformes.informeHolanda(query);
                
            }catch(InvalidJSONQueryException e){
                
            }


            // TEST CON CLIENTE EMPRESA
            
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
            
            // COMO SERÍA LA QUERY PARA EMPRESAS?

            try{
                List<Object> pRes = gestionInformes.informeHolanda(query);
                if(pRes.isEmpty())
                {
                    fail("No deberia ser vacia");
                }
            }catch(NullPointerException | InvalidJSONQueryException e){
                    fail("No debe dar error");
            }
            
            JSONObject json4 = new JSONObject();
            JSONObject sP4 = new JSONObject();
            sP4.put("questionType","Product");
            sP4.put("status", "active");
            sP4.put("productNumber", "NL66XYZW1291965209");

            json4.put("searchParameters", sP4);

            query=JSONObject.toJSONString(json4);
            

    }

    @Test 
    public void testInformeAlemaniaInicio(){

    }


    @Test
    public void testInformeAlemaniaPeriodico(){

    }
    
}
