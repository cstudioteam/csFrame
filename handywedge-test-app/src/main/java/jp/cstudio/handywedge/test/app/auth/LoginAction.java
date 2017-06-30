package jp.cstudio.handywedge.test.app.auth;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.handywedge.common.FWStringUtil;
import com.handywedge.context.FWContext;
import com.handywedge.user.auth.FWLoginManager;

import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named("loginMgr")
public class LoginAction {

  @Setter
  @Getter
  private String id = "csf-test-app"; // デフォルトユーザ（入力を省略したい）

  @Setter
  @Getter
  private String password = "password";

  @Inject
  private FWContext ctx;

  @Inject
  private FWLoginManager loginMgr;

  public String login() throws IOException {

    boolean auth = loginMgr.login(id, password);
    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext ec = fc.getExternalContext();
    if (auth) {
      ec.redirect(ctx.getContextPath());
    } else {
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "login failed.", null));
      ec.getFlash().setKeepMessages(true);
      ec.redirect(FWStringUtil.getLoginUrl());
    }
    fc.responseComplete();
    return null;
  }

  public String logout() {

    loginMgr.logout();

    return "/contents/auth/logout.xhtml";
  }
}
