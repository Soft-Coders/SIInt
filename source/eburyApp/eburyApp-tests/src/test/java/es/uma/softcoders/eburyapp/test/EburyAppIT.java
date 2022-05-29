package es.uma.softcoders.eburyapp.test;
// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.uma.informatica.sii.anotaciones.Requisitos;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class EburyAppIT {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  /**
	*Este test se encarga de comprobar el correcto acceso al sistema de ADMINISTRATIVOS:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF1"})
  public void loginAdminRF1() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1512, 944));
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Menú Administrador"));
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("input:nth-child(2)"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("input:nth-child(3)"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("input:nth-child(4)"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("input:nth-child(5)"));
      assert(elements.size() > 0);
    }
  }
  /**
	*Este test se encarga de comprobar el correcto acceso al sistema de CLIENTES INDIVIDUALES y PERSONAS AUTORIZADAS:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF10"})
  public void loginUserRF10() {
    driver.get("http://localhost:8080/eburyApp-war/");
    driver.manage().window().setSize(new Dimension(1512, 944));
    driver.findElement(By.id("login:user")).sendKeys("ana");
    driver.findElement(By.id("login:pass")).sendKeys("ana");
    driver.findElement(By.name("login:j_idt10")).click();
    {
      List<WebElement> elements = driver.findElements(By.name("registro:j_idt4"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.name("registro:j_idt6"));
      assert(elements.size() > 0);
    }
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(1) > td")).getText(), is("Cliente"));
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td")).getText(), is("Persona Autorizada"));
    driver.findElement(By.linkText("Cancelar")).click();
  }
  /**
	*Este test se encarga de comprobar el funcionamiento de MODIFICAR CLIENTE:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	<li>Entra en BAJA CLIENTE para modificar un cliente</li>
	*	<li>Pulsa el boton MODIFICAR</li>
	*	<li>Rellena los campos obligatorios y pulsa MODIFICAR</li>
	*	<li>Comprueba que la identificacion del cliente seleccionado ha sido modificada</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF3"})
  public void modificarClienteRF3() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1920, 1055));
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    driver.findElement(By.cssSelector("input:nth-child(3)")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector("input:nth-child(4)"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.name("contactos:j_idt23"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.linkText("Log out"));
      assert(elements.size() > 0);
    }
    driver.findElement(By.name("contactos:list:3:j_idt16")).click();
    driver.findElement(By.id("modificacion:nombre")).sendKeys("individual");
    driver.findElement(By.id("modificacion:ape")).sendKeys("mod");
    driver.findElement(By.id("modificacion:ident")).sendKeys("individualMod");
    driver.findElement(By.id("modificacion:dir")).sendKeys("Direccion");
    driver.findElement(By.id("modificacion:ciudad")).sendKeys("Ciudad");
    driver.findElement(By.id("modificacion:cp")).sendKeys("CodigoPostal");
    driver.findElement(By.id("modificacion:pais")).sendKeys("Pais");
    driver.findElement(By.name("modificacion:j_idt29")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(1)")).getText(), is("individualMod"));
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(2)")).getText(), is("ACTIVO"));
  }
  /**
	*Este test se encarga de comprobar el funcionamiento de BAJA CLIENTE:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	<li>Entra en BAJA CLIENTE para dar de baja a un cliente</li>
	*	<li>Pulsa el boton BAJA</li>
	*	<li>Comprueba en la vista de ALTA que el cliente esta dado de baja</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF4"})
  public void bajaClienteRF4() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1200, 1011));
    driver.findElement(By.id("login:user")).click();
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    driver.findElement(By.id("Baja")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(1)")).getText(), is("empresaPrueba"));
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(2)")).getText(), is("ACTIVO"));
    driver.findElement(By.name("contactos:list:1:j_idt14")).click();
    driver.findElement(By.cssSelector("input:nth-child(4)")).click();
    driver.findElement(By.id("Alta")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(1)")).getText(), is("empresaPrueba"));
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(2)")).getText(), is("INACTIVO"));
    driver.findElement(By.name("altaClientes:list:0:j_idt12")).click();
  }
  /**
	*Este test se encarga de comprobar el funcionamiento de ALTA CLIENTE:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	<li>Accede a la vista de ALTA CLIENTE</li>
	*	<li>Comprueba que existe un cliente inactivo al que dar de alta</li>
	*	<li>Pulsa el boton de ALTA</li>
	*	<li>Sale de la vista, y comprueba si el estado del cliente ha cambiado a ACTIVO en la vista de BAJA CLIENTE</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF2"})
  public void altaClienteRF2() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1200, 1011));
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    driver.findElement(By.id("Alta")).click();
    assertThat(driver.findElement(By.cssSelector("td:nth-child(1)")).getText(), is("individualInactivo"));
    assertThat(driver.findElement(By.cssSelector("td:nth-child(2)")).getText(), is("INACTIVO"));
    driver.findElement(By.name("altaClientes:list:0:j_idt12")).click();
    driver.findElement(By.cssSelector("input:nth-child(5)")).click();
    driver.findElement(By.id("Baja")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(5) > td:nth-child(1)")).getText(), is("individualInactivo"));
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(5) > td:nth-child(2)")).getText(), is("ACTIVO"));
    driver.findElement(By.name("contactos:list:4:j_idt14")).click();
  }
  /**
	*Este test se encarga de comprobar el funcionamiento de AUTORIZAR PERSONA:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	<li>Entra en BAJA AUTORIZADO para AUTORIZAR un cliente</li>
	*	<li>Pulsa el boton AUTORIZAR</li>
	*	<li>Accede a una vista auxiliar y escribe la empresa en la que se autoriza y el tipo de autorizacion</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF6"})
  public void agregarAutorizadoRF6() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1920, 1055));
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    driver.findElement(By.xpath("(//input[@id=\'Baja\'])[2]")).click();
    driver.findElement(By.name("bajaAutorizados:list:0:j_idt19")).click();
    driver.findElement(By.id("autorizar:empresa")).sendKeys("empresaPrueba");
    driver.findElement(By.id("autorizar:tipo")).sendKeys("O");
    driver.findElement(By.name("autorizar:j_idt13")).click();
  }
  /**
	*Este test se encarga de comprobar el funcionamiento de ELIMINAR AUTORIZADO:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>ueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	<li>Entra en BAJA AUTORIZADO para inhabilitar un autorizado</li>
	*	<li>Pulsa el boton BAJA</li>
	*	<li>Comprueba en la vista de ALTA que el autorizado esta dado de baja</li>
	*	</ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF8"})
  public void eliminarAutorizadosRF8() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1200, 1011));
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    driver.findElement(By.cssSelector("html")).click();
    driver.findElement(By.xpath("(//input[@id=\'Baja\'])[2]")).click();
    assertThat(driver.findElement(By.cssSelector("td:nth-child(2)")).getText(), is("paPrueba"));
    assertThat(driver.findElement(By.cssSelector("td:nth-child(3)")).getText(), is("ACTIVO"));
    driver.findElement(By.name("bajaAutorizados:list:0:j_idt15")).click();
    driver.findElement(By.cssSelector("input:nth-child(4)")).click();
    driver.findElement(By.xpath("(//input[@id=\'Alta\'])[2]")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(1)")).getText(), is("paPrueba"));
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(2)")).getText(), is("INACTIVO"));
    driver.findElement(By.name("contactos:list:1:j_idt13")).click();
  }
  /**
	*Este test se encarga de comprobar el funcionamiento de MODIFICAR AUTORIZADO:
	*	<ul>
	*	<li>Ingresa los datos de acceso de un administrador</li>
	*	<li>Comprueba las credenciales y accede al sistema</li>
	*	<li>Comprueba que ha ingresado en la vista correcta</li>
	*	<li>Entra en BAJA AUTORIZADO para modificar un AUTORIZADO</li>
	*	<li>Pulsa el boton MODIFICARv
	*	<li>Rellena los campos obligatorios y pulsa MODIFICAR</li>
	*	<li>Comprueba que la identificacion, el nombre y el estado del autorizado seleccionado han sido modificados</li>
	*	<ul>
	* @author Ignacio Lopezosa & Jesus Cestino
	* */
	@Test
	@Requisitos({"RF7"})
  public void modificarAutorizadoRF7() {
    driver.get("http://localhost:8080/eburyApp-war/admin.xhtml");
    driver.manage().window().setSize(new Dimension(1200, 1011));
    driver.findElement(By.id("login:user")).sendKeys("ponciano");
    driver.findElement(By.id("login:pass")).sendKeys("ponciano");
    driver.findElement(By.name("login:j_idt11")).click();
    driver.findElement(By.xpath("(//input[@id=\'Baja\'])[2]")).click();
    driver.findElement(By.name("bajaAutorizados:list:0:j_idt17")).click();
    driver.findElement(By.id("modificacion:nombre")).sendKeys("mod");
    driver.findElement(By.id("modificacion:apellido")).sendKeys("pa");
    driver.findElement(By.id("modificacion:ident")).sendKeys("paMod");
    driver.findElement(By.id("modificacion:dir")).sendKeys("Direccion");
    driver.findElement(By.name("modificacion:j_idt23")).click();
    assertThat(driver.findElement(By.cssSelector("td:nth-child(1)")).getText(), is("mod"));
    assertThat(driver.findElement(By.cssSelector("td:nth-child(2)")).getText(), is("paMod"));
    assertThat(driver.findElement(By.cssSelector("td:nth-child(3)")).getText(), is("ACTIVO"));
    driver.findElement(By.name("bajaAutorizados:list:0:j_idt17")).click();
    driver.findElement(By.id("modificacion:ident")).sendKeys("paPrueba");
    driver.findElement(By.id("modificacion:nombre")).sendKeys("pa");
    driver.findElement(By.id("modificacion:apellido")).sendKeys("prueba");
    driver.findElement(By.id("modificacion:dir")).sendKeys("La calle de paPrueba");
    driver.findElement(By.name("modificacion:j_idt23")).click();
  }
}
