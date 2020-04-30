package pojo;

import java.util.List;

public class courses {
    private List<api> api;
    private List<mobile> mobile;
    private List<webautomation> webautomation;

    public List<pojo.api> getApi() {
        return api;
    }

    public void setApi(List<pojo.api> api) {
        this.api = api;
    }

    public List<pojo.mobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<pojo.mobile> mobile) {
        this.mobile = mobile;
    }

    public List<pojo.webautomation> getWebautomation() {
        return webautomation;
    }

    public void setWebautomation(List<pojo.webautomation> webautomation) {
        this.webautomation = webautomation;
    }


}
