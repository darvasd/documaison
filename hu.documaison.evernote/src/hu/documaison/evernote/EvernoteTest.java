package hu.documaison.evernote;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.EvernoteApi;
import org.scribe.oauth.OAuthService;

public class EvernoteTest {

	public static void main(String[] args) {
		
		
		OAuthService service = new ServiceBuilder()
		.provider(EvernoteApi.class)
		.apiKey("pumafi")
		.apiSecret("bbbf54372e8bd929")
		.build();
		
		System.out.println(service.getAuthorizationUrl(service.getRequestToken()));
		
	}
	
}
