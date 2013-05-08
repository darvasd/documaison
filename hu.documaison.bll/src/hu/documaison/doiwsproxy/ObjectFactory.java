
package hu.documaison.doiwsproxy;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hu.documaison.doiwsproxy package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryTitle_QNAME = new QName("http://doiwsproxy.documaison.hu/", "queryTitle");
    private final static QName _QueryAuthorResponse_QNAME = new QName("http://doiwsproxy.documaison.hu/", "queryAuthorResponse");
    private final static QName _Query_QNAME = new QName("http://doiwsproxy.documaison.hu/", "query");
    private final static QName _QueryResponse_QNAME = new QName("http://doiwsproxy.documaison.hu/", "queryResponse");
    private final static QName _QueryTitleResponse_QNAME = new QName("http://doiwsproxy.documaison.hu/", "queryTitleResponse");
    private final static QName _QueryAuthor_QNAME = new QName("http://doiwsproxy.documaison.hu/", "queryAuthor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hu.documaison.doiwsproxy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryTitle }
     * 
     */
    public QueryTitle createQueryTitle() {
        return new QueryTitle();
    }

    /**
     * Create an instance of {@link QueryAuthorResponse }
     * 
     */
    public QueryAuthorResponse createQueryAuthorResponse() {
        return new QueryAuthorResponse();
    }

    /**
     * Create an instance of {@link Query }
     * 
     */
    public Query createQuery() {
        return new Query();
    }

    /**
     * Create an instance of {@link QueryResponse }
     * 
     */
    public QueryResponse createQueryResponse() {
        return new QueryResponse();
    }

    /**
     * Create an instance of {@link QueryTitleResponse }
     * 
     */
    public QueryTitleResponse createQueryTitleResponse() {
        return new QueryTitleResponse();
    }

    /**
     * Create an instance of {@link QueryAuthor }
     * 
     */
    public QueryAuthor createQueryAuthor() {
        return new QueryAuthor();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTitle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doiwsproxy.documaison.hu/", name = "queryTitle")
    public JAXBElement<QueryTitle> createQueryTitle(QueryTitle value) {
        return new JAXBElement<QueryTitle>(_QueryTitle_QNAME, QueryTitle.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAuthorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doiwsproxy.documaison.hu/", name = "queryAuthorResponse")
    public JAXBElement<QueryAuthorResponse> createQueryAuthorResponse(QueryAuthorResponse value) {
        return new JAXBElement<QueryAuthorResponse>(_QueryAuthorResponse_QNAME, QueryAuthorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Query }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doiwsproxy.documaison.hu/", name = "query")
    public JAXBElement<Query> createQuery(Query value) {
        return new JAXBElement<Query>(_Query_QNAME, Query.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doiwsproxy.documaison.hu/", name = "queryResponse")
    public JAXBElement<QueryResponse> createQueryResponse(QueryResponse value) {
        return new JAXBElement<QueryResponse>(_QueryResponse_QNAME, QueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryTitleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doiwsproxy.documaison.hu/", name = "queryTitleResponse")
    public JAXBElement<QueryTitleResponse> createQueryTitleResponse(QueryTitleResponse value) {
        return new JAXBElement<QueryTitleResponse>(_QueryTitleResponse_QNAME, QueryTitleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryAuthor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://doiwsproxy.documaison.hu/", name = "queryAuthor")
    public JAXBElement<QueryAuthor> createQueryAuthor(QueryAuthor value) {
        return new JAXBElement<QueryAuthor>(_QueryAuthor_QNAME, QueryAuthor.class, null, value);
    }

}
