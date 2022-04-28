import javax.naming.NamingException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import es.uma.softcoders.eburyApp.ejb.GestionInformes;
import es.uma.softcoders.eburyApp.ejb.InformesEJB;

public class TestInformes {

    private static final String INFORMES_EJB = "java:global/classes/InformesEJB";
    private static final String UNIDAD_PERSISTENCIA_PRUEBAS = "eburyAppTest";
    private GestionInformes gestionInformes;

    @Before
    public void setup() throws NamingException {
        gestionInformes = (GestionInformes) new InformesEJB();
        BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSISTENCIA_PRUEBAS);
    }

    @Test
    public void testInformeHolanda(){

        
            Date pDay = new Date(2017,06,23);
            Individual pInd = new Individual("Pep", "Doe");
            Divisa pDiv = new Divisa("GBP", "libras", '£', (long)0.94);
            CuentaReferencia pRef = new CuentaReferencia("CaixaBank", (long)40000, pDiv);
            Segregada pSeg = new Segregada("30",pRef);
            List<CuentaFintech> pCuentas = pInd.getCuentas();
            // Setters
            pInd.setIdentificacion("Ide");
            pInd.setTipo_cliente("INDIVIDUAL");
            pInd.setEstado("ACTIVO");
            pInd.setFecha_Alta(pDay);
            pInd.setDireccion("54");
            pInd.setCiudad("Amsterdam");
            pInd.setCodigoPostal("7207KE");
            pInd.setPais("NL");
            pCuentas.add(pSeg);
            // Persist
            em.persist(pClient);
            
            // PRIMER TEST
        String q1="{
            "searchParameters": {
            "questionType": "Customer",
            "startPeriod": "2015-04-25",
            "endPeriod": "2020-04-25",
            "name": {
            "firstName": "Pep",
            "lastName": "Doe"
            },
           "address": {
            "Number": "54",
            "postalCode": "7207KE",
            "country": "NL"
            }
            }
            ";
            try{
                List<Object> pRes = gestionInformes.informeHolanda(q1);
                if(Res.isEmpty())
                {
                    fail("No deberia ser vacia");
                }
            
            }catch(NullPointerException | InvalidJSONQueryException e){
                fail("No debería de lanzar esta excepcion");

            }

            // SEGUNDA PRUEBA
            String q2="{
                "searchParameters": {
                "questionTipe": "Customer",
                "startPeriodo": "2015-04-25",
                "endPeriodo": "2020-04-25",
                "namae": {
                "firstName": "Pep",
                "lastName": "Doe"
                },
            "address": {
                "Number": "54",
                "postalCode": "7207KE",
                "country": "NL"
                }
            }"; // ALGUNAS LINEAS DEL JSON INVALIDAS

            try{
                List<Object> pRes = gestionInformes.informeHolanda(q2);
                
            }catch(InvalidJSONQueryException e){
                // en caso de que sea success catch y ya?
            }

        String q3="";
        String q4="";


    }

    @Test 
    public void testInformeAlemaniaInicio(){

    }


    @Test
    public void testInformeAlemaniaPeriodico(){

    }
    
}
