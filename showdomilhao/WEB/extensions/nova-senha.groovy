import com.movile.albatross.webprovider.subscription.auth.kiwi.client.KiwiAPIClient;
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.KiwiChangePasswordRequest;
import com.movile.albatross.webprovider.subscription.auth.kiwi.bean.KiwiSignInRequest;

import com.movile.albatross.webprovider.subscription.auth.kiwi.to.CredentialsTO;
import com.movile.albatross.webprovider.subscription.auth.kiwi.enumeration.ChangePasswordOperationStatus;

import org.slf4j.Logger
import com.movile.albatross.core.logging.Loggers

Logger logger = Loggers.EXTENSION.getLogger();
def kiwiHeader = sessionMap.get("_KIWI_HEADER");
modelMap.put("kiwiHeader", kiwiHeader);

logger.info("kiwi header: {}", kiwiHeader);

def pwdNew = request.getParameter("kiwi-pass");

KiwiAPIClient kiwiApiClient = applicationContext.getBean("kiwiAPIClient");

def kiwiResponse = null;
if (kiwiHeader && pwdNew) {
	kiwiResponse = kiwiApiClient.changePassword(new KiwiChangePasswordRequest(pwdNew), "16034f7f-ffbb-4864-83e4-4c758d5791a2", kiwiHeader.getxAuthToken());
}

if (kiwiResponse) {
	if (kiwiResponse.getStatus() != ChangePasswordOperationStatus.SUCCESS) {
		modelMap.put("error", true)
	} else {
		response.sendRedirect("nova-senha-sucesso")
	}
}
