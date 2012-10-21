/**
 * 
 */
package hu.documaison.dal.interfaceimpl;

import hu.documaison.dal.database.DatabaseUtils;
import hu.documaison.data.entities.*;
import hu.documaison.dal.interfaces.CoreInterface;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @author Dani
 * 
 */
public class CoreImplementation implements CoreInterface {
	private void HandleSQLException(SQLException e, String method) {
		System.err.println("Exception @ " + method);
		e.printStackTrace();
	}

	@Override
	public Collection<Document> getDocuments() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// query
			List<Document> ret = dao.queryForAll();

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getDocuments");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getDocuments");
				}
			}
		}

		return null;
	}

	@Override
	public Collection<Document> getDocuments(Tag tag) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// query
			QueryBuilder<Document, Integer> qb = dao.queryBuilder();
			// qb.selectRaw("*");
			qb.where().in(Document.TAGS, tag.getId());
			List<Document> ret = dao.query(qb.prepare());

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getDocuments(Tag)");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getDocuments(Tag)");
				}
			}
		}

		return null;
	}

	@Override
	public Document getDocument(int id) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// query
			Document ret = dao.queryForId(id);

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getDocument");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getDocument");
				}
			}
		}

		return null;
	}

	@Override
	public Document createDocument(int typeId) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			DocumentType documentType = this.getDocumentType(typeId);
			
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// add
			Document newDocument = new Document(documentType);
			for (DefaultMetadata md : documentType.getDefaultMetadataCollection())
			{
				newDocument.addMetadata(md.createMetadata());
			}
			newDocument.setThumbnailBytes(documentType.getDefaultThumbnailBytes().clone());
			newDocument.setDateAdded(new Date());
			
			dao.create(newDocument);
			return newDocument;

		} catch (SQLException e) {
			HandleSQLException(e, "createDocument");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "createDocument");
				}
			}
		}
		return null;
	}

	@Override
	public void addDocument(Document document) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// add
			dao.create(document);

		} catch (SQLException e) {
			HandleSQLException(e, "addDocument");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "addDocument");
				}
			}
		}
	}

	@Override
	public void removeDocument(int id) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// delete
			dao.deleteById(id);

		} catch (SQLException e) {
			HandleSQLException(e, "removeDocument");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "removeDocument");
				}
			}
		}
	}

	@Override
	public void updateDocument(Document document) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Document, Integer> dao = DaoManager.createDao(connectionSource,
					Document.class);

			// update
			dao.update(document);

		} catch (SQLException e) {
			HandleSQLException(e, "updateDocument");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "updateDocument");
				}
			}
		}
	}

	@Override
	public void addTag(Tag tag) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
					Tag.class);

			// add
			dao.create(tag);

		} catch (SQLException e) {
			HandleSQLException(e, "addTag");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "addTag");
				}
			}
		}
	}

	@Override
	public void updateTag(Tag tag) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
					Tag.class);

			// update
			dao.update(tag);

		} catch (SQLException e) {
			HandleSQLException(e, "updateTag");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "updateTag");
				}
			}
		}
	}

	@Override
	public Collection<Tag> getTags() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
					Tag.class);

			// query
			List<Tag> ret = dao.queryForAll();

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getTags");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getTags");
				}
			}
		}

		return null;
	}

	@Override
	public Tag getTag(int id) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
					Tag.class);

			// query
			Tag ret = dao.queryForId(id);

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getTags");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getTags");
				}
			}
		}

		return null;
	}

	@Override
	public Tag getTag(String name) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
					Tag.class);

			// query
			Tag ret = dao.queryForFirst(dao.queryBuilder().where()
					.eq(Tag.NAME, name).prepare());

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getTag");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getTag");
				}
			}
		}

		return null;
	}

	@Override
	public void removeTag(int id) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
					Tag.class);

			// delete
			dao.deleteById(id);

		} catch (SQLException e) {
			HandleSQLException(e, "removeTag");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "removeTag");
				}
			}
		}
	}

	@Override
	public void addDocumentType(DocumentType documentType) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DocumentType, Integer> dao = DaoManager.createDao(
					connectionSource, DocumentType.class);

			// add
			dao.create(documentType);

		} catch (SQLException e) {
			HandleSQLException(e, "addDocumentType");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "addDocumentType");
				}
			}
		}
	}
	
	@Override
	public DocumentType createDocumentType() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DocumentType, Integer> dao = DaoManager.createDao(
					connectionSource, DocumentType.class);

			// add
			DocumentType documentType = new DocumentType();
			dao.create(documentType);
			return documentType;

		} catch (SQLException e) {
			HandleSQLException(e, "createDocumentType");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "createDocumentType");
				}
			}
		}
		return null;
	}

	@Override
	public void removeDocumentType(int id) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DocumentType, Integer> dao = DaoManager.createDao(
					connectionSource, DocumentType.class);

			// add
			dao.deleteById(id);

		} catch (SQLException e) {
			HandleSQLException(e, "removeDocumentType");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "removeDocumentType");
				}
			}
		}
	}

	@Override
	public void updateDocumentType(DocumentType documentType) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DocumentType, Integer> dao = DaoManager.createDao(
					connectionSource, DocumentType.class);

			// update
			dao.update(documentType);

		} catch (SQLException e) {
			HandleSQLException(e, "updateDocumentType");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "updateDocumentType");
				}
			}
		}
	}

	@Override
	public Collection<DocumentType> getDocumentTypes() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DocumentType, Integer> dao = DaoManager.createDao(
					connectionSource, DocumentType.class);

			// query
			List<DocumentType> ret = dao.queryForAll();

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getDocumentTypes");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getDocumentTypes");
				}
			}
		}

		return null;
	}
	
	@Override
	public DocumentType getDocumentType(int id) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DocumentType, Integer> dao = DaoManager.createDao(
					connectionSource, DocumentType.class);

			// query
			DocumentType ret = dao.queryForId(id);

			// return
			return ret;
		} catch (SQLException e) {
			HandleSQLException(e, "getDocumentType");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "getDocumentType");
				}
			}
		}

		return null;
	}
}
