package hu.documaison.bll.ws.doidata;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.helper.DataHelper;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

public class DoiQuery {
	private static final Logger logger = Logger.getLogger("DocuMaison.DOI");
	
	
	/***
	 * Creates Metadata objects based on a given DOI for the given document.
	 * @param document The document corrensponds to the DOI.
	 * @param doi The DOI identifier (in doi:... format) 
	 * @return The list of queried metadata.
	 * @throws Exception Parse error of not valid DOI.
	 */
	public static List<Metadata> query(Document document, String doi)
			throws Exception {			

		// check if the DOI is valid
		if (DataHelper.isDOI(doi) == false){
			throw new IllegalArgumentException();
		}
		
		// get the key for the crossref service
		SettingsData settings = SettingsManager.getCurrentSettings();
		String crossrefkey = settings.getCrossRefKey();
		
		// basic key check
		if (crossrefkey == null || crossrefkey.length() < 3){
			logger.error("Not valid CrossRef key!");
			// fail silent!
			return null;
		}
		
		// parser init
		JAXBContext jc = JAXBContext
				.newInstance("hu.documaison.bll.ws.doidata");

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		URL url = new URL(
				"http://www.crossref.org/openurl/?pid=" +
						crossrefkey + "&id="
						+ doi + "&noredirect=true");

		// parse the xml structure
		InputStream xml = url.openStream();
		JAXBElement<CrossrefResult> feed = unmarshaller.unmarshal(
				new StreamSource(xml), CrossrefResult.class);
		xml.close();

		// create metadata based on the XML result
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
				// log the unparsed expressions
				logger.info("Not parsed: " + o);
			}
		}
		return ret;
	}

	/***
	 * Helper method to parse the contributors (authors) of an article.
	 * @param o The set of contributors.
	 * @param document The document.
	 * @param ret The list which will be extended with the contributors.
	 */
	private static void parseContributors(Contributors o, Document document,
			List<Metadata> ret) {
		String authors = "";
		
		// foreach contributor
		for (Contributor c : o.contributor) {
			if (c.contributorRole.equals("author")) {
				// only if the role of this contributor is author
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
