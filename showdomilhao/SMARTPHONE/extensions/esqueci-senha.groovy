import com.movile.albatross.webprovider.subscription.auth.kiwi.client.KiwiAPIClient;
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.KiwiSbtForgotPasswordRequest;
import org.slf4j.Logger
import com.movile.albatross.core.logging.Loggers

Logger logger = Loggers.EXTENSION.getLogger();
def email = request.getParameter("kiwi-email");

if (email) {
	KiwiAPIClient kiwiApiClient = applicationContext.getBean("kiwiAPIClient");

	def kiwiResponse = kiwiApiClient.doSbtForgotPassword(new KiwiSbtForgotPasswordRequest(email), "16034f7f-ffbb-4864-83e4-4c758d5791a2");
	logger.info("Kiwi Forgot Password Response{}",kiwiResponse)
	if (kiwiResponse.getStatus() != 200) {
		modelMap.put("error", true)
	} else {
		response.sendRedirect("esqueci-senha-sucesso")
	}
}
