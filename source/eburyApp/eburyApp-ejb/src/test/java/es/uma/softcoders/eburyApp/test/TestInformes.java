import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

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
           Date pDay = new Date(2017,06,23);
           Cliente pClient = new Cliente("ident","INDIVIDUAL","ACTIVO",pDay,);
            try{

            
            }catch(){

           }
        String q2="";
        String q3="";

    }

    @Test 
    public void testInformeAlemaniaInicio(){

    }


    @Test
    public void testInformeAlemaniaPeriodico(){

    }
    
}
