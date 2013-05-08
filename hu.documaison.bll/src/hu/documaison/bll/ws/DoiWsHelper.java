package hu.documaison.bll.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.entities.MetadataType;
import hu.documaison.doiwsproxy.DOIWsProxy;
import hu.documaison.doiwsproxy.DOIWsProxy_Service;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

import javax.xml.ws.Service;

import net.java.dev.jaxb.array.StringArray;

public class DoiWsHelper {
	public static void fillDocumentMetadata(String doi, Document document)
			throws MalformedURLException {
		// get the key for the crossref service
		SettingsData settings;
		try {
			settings = SettingsManager.getCurrentSettings();
		} catch (Exception e) {
			System.err.println("Unable to load settings.");
			return;
		}
		String crossrefkey = settings.getCrossRefKey();
		String doiwsproxy = settings.getDoiWsProxyAddress();

		// locate the service
		URL wsdlURL = new URL("http://" + doiwsproxy
				+ "/DOIWsProxy/DOIWsProxy?wsdl");
		QName SERVICE_NAME = new QName("http://doiwsproxy.documaison.hu/",
				"DOIWsProxy");
		Service service = Service.create(wsdlURL, SERVICE_NAME);
		DOIWsProxy client = service.getPort(DOIWsProxy.class);

		// invoke
		List<StringArray> result = client.query(doi, crossrefkey);
		// fill
		for (StringArray sa : result) {
			// remove old metadata
			Metadata oldmd = document.getMetadata(sa.getItem().get(0));
			if (oldmd != null){
				document.removeMetadata(oldmd);
			}
			
			document.addMetadata(new Metadata(document, sa.getItem().get(0), sa
					.getItem().get(1)));
		}
	}
}
