package es.uma.softcoders.eburyApp.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.uma.softcoders.eburyApp.ejb.GestionInformes;
import es.uma.softcoders.eburyApp.exceptions.FailedInitialCSVException;
import es.uma.softcoders.eburyApp.exceptions.FailedPeriodicCSVException;

@Named
@RequestScoped
public class DescargaInformes {
	
	@EJB
	GestionInformes gestionInformes;
	
    public DescargaInformes() {
        
    }
    
    public void descargaInicial() {
    	Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String path;
    	
    	if(request instanceof HttpServletRequest) {
    		path = ((HttpServletRequest) request).getRequestURL().toString();
    	}
    	else
    		throw new RuntimeException("WHY DOWNLOAD IS NOT SERVLET REQUEST?");
    	
    	try {
			gestionInformes.informeAlemaniaInicio(path);
			path+="/informe.csv";
			download(path);
		} catch (FailedInitialCSVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void descargaPeriodica() {
    	Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String path;
    	
    	if(request instanceof HttpServletRequest) {
    		path = ((HttpServletRequest) request).getRequestURL().toString();
    	}
    	else
    		throw new RuntimeException("WHY DOWNLOAD IS NOT SERVLET REQUEST?");
    	
    	try {
			gestionInformes.informeAlemaniaPeriodico(path);
			path+="/informe.csv";
			download(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FailedPeriodicCSVException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void download(String path) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        File file = new File(path);
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
        
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            output.write(buf, 0, length);
        }
        source.close();

        fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }
}
