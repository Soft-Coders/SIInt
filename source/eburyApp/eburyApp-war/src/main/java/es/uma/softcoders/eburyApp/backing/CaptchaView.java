package es.uma.softcoders.eburyApp.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named (value ="captchaView")
@RequestScoped
public class CaptchaView {

    public void submit() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
        FacesContext.getCurrentInstance().addMessage("admin:card", msg);
    }
}