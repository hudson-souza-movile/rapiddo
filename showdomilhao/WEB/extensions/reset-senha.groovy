import com.movile.albatross.webprovider.subscription.auth.kiwi.client.KiwiAPIClient;
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.KiwiResetPasswordRequest;
import com.movile.albatross.webprovider.subscription.auth.kiwi.enumeration.ForgotPasswordOperationStatus;
import org.slf4j.Logger
import com.movile.albatross.core.logging.Loggers

Logger logger = Loggers.EXTENSION.getLogger();
def password = request.getParameter("pwd");
def token = request.getParameter("token");
def ipi = request.getParameter("ipi");
def aid = request.getParameter("aid");

if (ipi && aid && token) {
	sessionMap.put("token", token);
	sessionMap.put("ipi", ipi);
	sessionMap.put("aid", aid);
} else {
	token = sessionMap.get("token");
	ipi = sessionMap.get("ipi");
	aid = sessionMap.get("aid");
}

if (ipi && aid && token && password) {
	KiwiAPIClient kiwiApiClient = applicationContext.getBean("kiwiAPIClient");

	def kiwiApiResponse = kiwiApiClient.resetPassword(new KiwiResetPasswordRequest(token, password, ipi, aid), "16034f7f-ffbb-4864-83e4-4c758d5791a2");
	logger.info(kiwiApiResponse);

	if (!kiwiApiResponse.toLowerCase().contains("status=success")) {
		modelMap.put("error", true)
	} else {
		response.sendRedirect("reset-senha-sucesso")
	}
}
