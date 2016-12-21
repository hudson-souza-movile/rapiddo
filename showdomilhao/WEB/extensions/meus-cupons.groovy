import org.slf4j.Logger
import com.movile.albatross.core.logging.Loggers

import com.movile.albatross.webprovider.subscription.auth.corn.bean.SbtTicketRequest
import com.movile.albatross.webprovider.subscription.auth.corn.bean.SbtTicketResponse
import com.movile.albatross.webprovider.subscription.auth.corn.client.CornApiClient
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.CreateInstallRequest
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.CreateInstallResponse
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.KiwiSbtSignInRequest
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.SbtSignInResponseStatus
import com.movile.albatross.webprovider.subscription.auth.kiwi.client.KiwiAPIClient
import com.movile.albatross.webprovider.subscription.auth.kiwi.to.KiwiSbtSignInResponseTO


def userLogin = new UserLogin(sessionMap, applicationContext, modelMap, response)

def authType = request.getParameter("type")
def action = request.getParameter("action")

modelMap.put("userLogin", sessionMap.get("userLogin"));

modelMap.put("action", action )


if (action == "logout") {
  userLogin.logout()
  response.sendRedirect("/lp/sdm/default/index")

} else if (action == "coupon") {
	userLogin.retrieveCoupons()

} else {

  if (authType == "1") {
      modelMap.put("userLogin", request.getParameter("email"))
      sessionMap.put("userLogin", request.getParameter("email"))
      userLogin.doLogin(request.getParameter("email"), request.getParameter("password"))
  } else if (authType == "2") {
      modelMap.put("userLogin", request.getParameter("fbEmail"))
      sessionMap.put("userLogin", request.getParameter("fbEmail"))
      userLogin.doFacebookLogin(request.getParameter("fbEmail"), request.getParameter("social-id"), request.getParameter("social-access-token"))
  }

  if (!sessionMap.get("loggedIn") && !modelMap.get("isError")) {
      response.sendRedirect("/lp/sdm/default/index")
  } else {
    userLogin.retrieveCoupons()
  }
}

class UserLogin {
    private enum ErrorType {
      GENERIC_ERROR,
      LOGIN_ERROR,
      LOGIN_FACEBOOK_ERROR,
      RETRIEVE_TICKET_ERROR
    }

    static private sessionMap
    static private applicationContext
    static private modelMap
    static private response
    static private String kiwiToken = "16034f7f-ffbb-4864-83e4-4c758d5791a2"
    static final Logger EXTENSION_EXCEPTIONS_LOGGER = Loggers.EXTENSION_EXCEPTIONS.getLogger();

    def UserLogin(sessionMap, applicationContext, modelMap, response) {
        this.sessionMap = sessionMap;
        this.applicationContext = applicationContext;
        this.modelMap = modelMap;
        this.response = response;
    };

    def logout() {
        sessionMap.remove("loggedIn")
        sessionMap.remove("kiwi-account-id")
        sessionMap.remove("loginFb")
        sessionMap.remove("userLogin")
        response.sendRedirect("/lp/sdm/default/index")
    }

    private def returnError(type) {
      modelMap.put("isError", "true")
      response.sendRedirect("/lp/sdm/default/index/?error=" + type.ordinal())
    }

    def doLogin(login, pass) {
        if ( ! sessionMap.get("loggedIn")) {
          def createInstall = createInstallKiwi()
          if (createInstall != null && createInstall.getUser() != null) {
            KiwiAPIClient kiwiApi = applicationContext.getBean("kiwiAPIClient")
            KiwiSbtSignInRequest kiwiSbtSignInRequest = new KiwiSbtSignInRequest(login, pass, "1",
                      createInstall.getUser().getAppInstallId(), createInstall.getUser().getUserId())
            KiwiSbtSignInResponseTO kiwiSbtSignInResponseTO = kiwiApi.doSbtSignIn(kiwiSbtSignInRequest, kiwiToken)
            if (kiwiSbtSignInResponseTO != null && SbtSignInResponseStatus.SUCCESS.equals(kiwiSbtSignInResponseTO.getStatus())) {
              sessionMap.put("loggedIn", "true")
              sessionMap.put("userLogin", login)
              sessionMap.put("kiwi-account-id", kiwiSbtSignInResponseTO.getAccountProfile().getAccountInfo().getAccountUUID().toString())
            } else {
              returnError(ErrorType.LOGIN_ERROR)
            }
          } else {
            returnError(ErrorType.LOGIN_ERROR)
          }
        }
    }

    private def createInstallKiwi() {
      KiwiAPIClient kiwiApi = applicationContext.getBean("kiwiAPIClient")
      CreateInstallRequest createInstallRequest = new CreateInstallRequest()
      CreateInstallResponse createInstallResponse = kiwiApi.createInstall(createInstallRequest, kiwiToken)
      return createInstallResponse
    }

    def doFacebookLogin(email, socialId, socialAccessToken) {
      if (! sessionMap.get("loggedIn")) {
        def createInstall = createInstallKiwi()
        if (createInstall != null && createInstall.getUser() != null) {
          KiwiAPIClient kiwiApi = applicationContext.getBean("kiwiAPIClient")
          KiwiSbtSignInRequest kiwiSbtSignInRequest = new KiwiSbtSignInRequest(email, socialId,  socialAccessToken, "2",
                    createInstall.getUser().getAppInstallId(), createInstall.getUser().getUserId());
          KiwiSbtSignInResponseTO kiwiSbtSignInResponseTO = kiwiApi.doSbtSignIn(kiwiSbtSignInRequest, kiwiToken);
          if (kiwiSbtSignInResponseTO != null && SbtSignInResponseStatus.SUCCESS.equals(kiwiSbtSignInResponseTO.getStatus())) {
            sessionMap.put("loggedIn", "true")
            sessionMap.put("loginFb", "true")
            sessionMap.put("userLogin", email)
            modelMap.put("loginFb", "true")
            sessionMap.put("kiwi-account-id", kiwiSbtSignInResponseTO.getAccountProfile().getAccountInfo().getAccountUUID().toString())
          } else {
            returnError(ErrorType.LOGIN_FACEBOOK_ERROR)
          }
        }else {
          returnError(ErrorType.LOGIN_FACEBOOK_ERROR)
        }
      } else {
        if (sessionMap.get("loginFb")) {
          modelMap.put("loginFb", true);
        }
      }
    }

    def retrieveCoupons() {
      CornApiClient cornAPIClient = applicationContext.getBean("cornAPIClient")
      SbtTicketRequest request = new SbtTicketRequest(sessionMap.get("kiwi-account-id"))
      SbtTicketResponse response = cornAPIClient.getTickets(request)
      if (response != null){
        if (response != null){
          def coupons = []
          if (response.getActiveTickets() != null)
            coupons.addAll(response.getActiveTickets())

          if (response.getRecentTickets() != null)
            coupons.addAll(response.getRecentTickets())

          modelMap.put("id", sessionMap.get("kiwi-account-id"))
          modelMap.put("coupons", coupons)
         }
      } else {
        returnError(ErrorType.RETRIEVE_TICKET_ERROR)
      }
    }

}
