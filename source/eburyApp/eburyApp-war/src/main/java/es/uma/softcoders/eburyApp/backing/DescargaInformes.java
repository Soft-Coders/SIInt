package es.uma.softcoders.eburyApp.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import es.uma.softcoders.eburyApp.ejb.GestionInformes;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.FailedInitialCSVException;
import es.uma.softcoders.eburyApp.exceptions.FailedPeriodicCSVException;

@Named(value = "descarga")
@RequestScoped
public class DescargaInformes {
	
	@EJB
	GestionInformes gestionInformes;
	
	@Inject
	InfoSesion sesion;
	
	private static int instance = 0;
	
    public DescargaInformes() {
    	System.out.println("> descarga.DescargaInformes() : CREATED " + ++instance);
    }
    
    public void descargaInicial() {
    	
    	System.out.println("> descarga.descargaInicial() : PRE : request");
    	
    	Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String path;
    	
    	System.out.println("> descarga.descargaInicial() : POST : request : " + request);
    	
    	if(request instanceof HttpServletRequest) {
    		System.out.println("> descarga.descargaInicial() : PRE : path");
    		
    		path = ((HttpServletRequest) request).getRequestURL().toString();
    		path = "informe.csv";
    		
    		System.out.println("> descarga.descargaInicial() : POST : path : " + path);
    	}
    	else {
    		System.out.println("> descarga.descargaInicial() : PRE : RuntTimeException : [! request instanceof HttpServletRequest] ");
    		
    		throw new RuntimeException("WHY DOWNLOAD IS NOT SERVLET REQUEST?");
    	}
    	
    	try {
    		System.out.println("> descarga.descargaInicial() : PRE : informeAlemaniaInicio(path)");
    		
			gestionInformes.informeAlemaniaInicio(path);
			
			System.out.println("> descarga.descargaInicial() : POST : informeAlemaniaInicio(path) : path : " + path);
			
			System.out.println("> descarga.descargaInicial() : PRE : download(path)");
			
			download(path);
		} catch (IOException e) {
			System.out.println("> descarga.descargaInicial() : IOException : Error " + e);
			FacesMessage fm = new FacesMessage("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
			e.printStackTrace();
		} catch (FailedInitialCSVException e) {
			System.out.println("> descarga.descargaInicial() : FailedPeriodicCSVException : Error " + e);
			FacesMessage fm = new FacesMessage("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
			e.printStackTrace();
		} catch (EburyAppException e) {
			System.out.println("> descarga.descargaInicial() : EburyAppException : Error Debe de ser administrativo\n" + e);
			FacesMessage fm = new FacesMessage("Error 401: Debe de ser administrativo\n" + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
			e.printStackTrace();
		}
    }
    
    public void descargaPeriodica() {
    	
    	System.out.println("> descarga.descargaPeriodica() : PRE : request");

    	Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String path;
    	
    	System.out.println("> descarga.descargaPeriodica() : POST : request : " + request);
    	
    	if(request instanceof HttpServletRequest) {
    		System.out.println("> descarga.descargaPeriodica() : PRE : path");
    		
    		path = ((HttpServletRequest) request).getRequestURL().toString();
    		path = "informe.csv";

    		System.out.println("> descarga.descargaPeriodica() : POST : path : " + path);
    	}
    	else {
    		System.out.println("> descarga.descargaPeriodica() : PRE : RuntTimeException : [! request instanceof HttpServletRequest] ");
    		
    		throw new RuntimeException("WHY DOWNLOAD IS NOT SERVLET REQUEST?");
    	}
    	
    	try {
    		System.out.println("> descarga.descargaPeriodica() : PRE : informeAlemaniaInicio(path)");
    		
			gestionInformes.informeAlemaniaPeriodico(path);
			
			System.out.println("> descarga.descargaPeriodica() : POST : informeAlemaniaInicio(path) : path : " + path);
			
			System.out.println("> descarga.descargaPeriodica() : PRE : download(path)");
			
			download(path);
		} catch (IOException e) {
			System.out.println("> descarga.descargaPeriodica() : IOException : Error " + e);
			FacesMessage fm = new FacesMessage("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
			e.printStackTrace();
		} catch (FailedPeriodicCSVException e) {
			System.out.println("> descarga.descargaPeriodica() : FailedPeriodicCSVException : Error " + e);
			FacesMessage fm = new FacesMessage("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
			e.printStackTrace();
		} catch (EburyAppException e) {
			System.out.println("> descarga.descargaPeriodica() : EburyAppException : Error Debe de ser administrativo\n" + e);
			FacesMessage fm = new FacesMessage("Error 401: Debe de ser administrativo\n" + e);
            FacesContext.getCurrentInstance().addMessage(null, fm);
			e.printStackTrace();
		}
    }
    
    private void download(String path) throws IOException, EburyAppException {
//		if(!sesion.getUsuario().isEsAdministrativo())
//			throw new EburyAppException("NO ES ADMIN");
    	
    	System.out.println("> descarga.donwload() : PRE : file");

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        File file = new File(path);
        System.out.println("> descarga.donwload() : POST : file : " + file);
        
        String fileName = file.getName();
        String contentType = ec.getMimeType(fileName); // JSF 1.x: ((ServletContext) ec.getContext()).getMimeType(fileName);
        int contentLength = (int) file.length();
        
        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseContentLength(contentLength); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream output = ec.getResponseOutputStream();
        // Now you can write the InputStream of the file to the above OutputStream the usual way.
        
        FileInputStream source = new FileInputStream(file);
        
        System.out.println("> descarga.donwload() : PRE : output.write()");
        
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            output.write(buf, 0, length);
        }
        source.close();
        
        System.out.println("> descarga.donwload() : POST : output.write()");

        fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }
}
