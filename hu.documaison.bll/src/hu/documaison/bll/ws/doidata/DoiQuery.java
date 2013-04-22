package hu.documaison.bll.ws.doidata;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.entities.MetadataType;
import hu.documaison.data.helper.DataHelper;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

public class DoiQuery {
	private static final Logger logger = Logger.getLogger("DocuMaison.DOI");
	
	public static List<Metadata> query(Document document, String doi)
			throws Exception {			

		if (DataHelper.isDOI(doi) == false){
			throw new IllegalArgumentException();
		}
		
		SettingsData settings = SettingsManager.getCurrentSettings();
		String crossrefkey = settings.getCrossRefKey();
		
		if (crossrefkey == null || crossrefkey.length() < 3){
			logger.error("Not valid CrossRef key!");
			return null;
		}
		
		JAXBContext jc = JAXBContext
				.newInstance("hu.documaison.bll.ws.doidata");

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		URL url = new URL(
				"http://www.crossref.org/openurl/?pid=" +
						crossrefkey + "&id="
						+ doi + "&noredirect=true");

		// BufferedReader in = new BufferedReader(
		// new InputStreamReader(url.openStream()));
		//
		// String inputLine;
		// while ((inputLine = in.readLine()) != null)
		// System.out.println(inputLine);
		// in.close();

		InputStream xml = url.openStream();
		JAXBElement<CrossrefResult> feed = unmarshaller.unmarshal(
				new StreamSource(xml), CrossrefResult.class);
		xml.close();

		List<Metadata> ret = new ArrayList<Metadata>();
		Body body = feed.getValue().getQueryResult().get(0).getBody();
		for (Object o : body.getQuery().get(0).getDoiOrIssnOrIsbn()) {
			if (o == null)
				continue;

			if (o instanceof Issn)
				ret.add(new Metadata(document, "issn", ((Issn) o).value));
			else if (o instanceof ArticleTitle)
				ret.add(new Metadata(document, "title",
						((ArticleTitle) o).value));
			else if (o instanceof Year)
				ret.add(new Metadata(document, "year", ((Year) o).value));
			else if (o instanceof JournalTitle)
				ret.add(new Metadata(document, "journal",
						((JournalTitle) o).value));
			else if (o instanceof Doi)
				ret.add(new Metadata(document, "doi", ((Doi) o).value));
			else if (o instanceof Contributors)
				parseContributors((Contributors) o, document, ret);
			else {
				logger.info("Not parsed: " + o);
			}
		}
		return ret;
	}

	private static void parseContributors(Contributors o, Document document,
			List<Metadata> ret) {
		String authors = "";
		for (Contributor c : o.contributor) {
			if (c.contributorRole.equals("author")) {
				String author = c.givenName.value + " " + c.surname.value;
				if (authors.length() == 0) {
					authors = author;
				} else {
					authors = authors + ", " + author;
				}
			}
		}
		ret.add(new Metadata(document, "author", authors));
	}
}
