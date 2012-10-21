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

	private <T extends DatabaseObject> T genericCreate(Class<T> c, String info) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<T, Integer> dao = DaoManager.createDao(connectionSource, c);

			// add
			T ret = c.newInstance();
			dao.create(ret);
			return ret;

		} catch (IllegalAccessException e) {
			return null;
		} catch (InstantiationException e) {
			return null;
		} catch (SQLException e) {
			HandleSQLException(e, info);
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, info);
				}
			}
		}
		return null;
	}

	private <T extends DatabaseObject> void genericDelete(int id, Class<T> c,
			String info) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<T, Integer> dao = DaoManager.createDao(connectionSource, c);

			// delete
			dao.deleteById(id);

		} catch (SQLException e) {
			HandleSQLException(e, info);
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, info);
				}
			}
		}
	}

	private <T extends DatabaseObject> void genericUpdate(T entity, Class<T> c,
			String info) {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<T, Integer> dao = DaoManager.createDao(connectionSource, c);

			// update
			dao.update(entity);

		} catch (SQLException e) {
			HandleSQLException(e, info);
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, info);
				}
			}
		}
	}

	
	@Override
	public Comment createComment() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Comment, Integer> dao = DaoManager.createDao(connectionSource,
					Comment.class);

			// add
			Comment comment = new Comment();
			dao.create(comment);
			return comment;

		} catch (SQLException e) {
			HandleSQLException(e, "createComment");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "createComment");
				}
			}
		}
		return null;
	}

	@Override
	public DefaultMetadata createDefaultMetadata() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<DefaultMetadata, Integer> dao = DaoManager.createDao(
					connectionSource, DefaultMetadata.class);

			// add
			DefaultMetadata ret = new DefaultMetadata();
			dao.create(ret);
			return ret;

		} catch (SQLException e) {
			HandleSQLException(e, "createDefaultMetadata");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "createDefaultMetadata");
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
			Document newDocument = new Document(documentType, dao);
			for (DefaultMetadata md : documentType
					.getDefaultMetadataCollection()) {
				newDocument.addMetadata(md.createMetadata());
			}
			newDocument.setThumbnailBytes(documentType
					.getDefaultThumbnailBytes().clone());
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
	public Metadata createMetadata() {
		// create a connection source to our database
		ConnectionSource connectionSource = null;

		try {
			// create connection
			connectionSource = DatabaseUtils.getConnectionSource();

			// instantiate the dao
			Dao<Metadata, Integer> dao = DaoManager.createDao(connectionSource,
					Metadata.class);

			// add
			Metadata ret = new Metadata();
			dao.create(ret);
			return ret;

		} catch (SQLException e) {
			HandleSQLException(e, "createMetadata");
		} finally {
			// close connection
			if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					HandleSQLException(e, "createMetadata");
				}
			}
		}
		return null;
	}

	@Override
	public Tag createTag() {
		return genericCreate(Tag.class, "createTag");
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

	// @Override
	// public void addTag(Tag tag) {
	// // create a connection source to our database
	// ConnectionSource connectionSource = null;
	//
	// try {
	// // create connection
	// connectionSource = DatabaseUtils.getConnectionSource();
	//
	// // instantiate the dao
	// Dao<Tag, Integer> dao = DaoManager.createDao(connectionSource,
	// Tag.class);
	//
	// // add
	// dao.create(tag);
	//
	// } catch (SQLException e) {
	// HandleSQLException(e, "addTag");
	// } finally {
	// // close connection
	// if (connectionSource != null) {
	// try {
	// connectionSource.close();
	// } catch (SQLException e) {
	// HandleSQLException(e, "addTag");
	// }
	// }
	// }
	// }

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

	// @Override
	// public void addDocumentType(DocumentType documentType) {
	// // create a connection source to our database
	// ConnectionSource connectionSource = null;
	//
	// try {
	// // create connection
	// connectionSource = DatabaseUtils.getConnectionSource();
	//
	// // instantiate the dao
	// Dao<DocumentType, Integer> dao = DaoManager.createDao(
	// connectionSource, DocumentType.class);
	//
	// // add
	// dao.create(documentType);
	//
	// } catch (SQLException e) {
	// HandleSQLException(e, "addDocumentType");
	// } finally {
	// // close connection
	// if (connectionSource != null) {
	// try {
	// connectionSource.close();
	// } catch (SQLException e) {
	// HandleSQLException(e, "addDocumentType");
	// }
	// }
	// }
	// }

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

	private void HandleSQLException(SQLException e, String method) {
		System.err.println("Exception @ " + method);
		e.printStackTrace();
	}

	@Override
	public void removeComment(int id) {
		genericDelete(id, Comment.class, "removeComment");
	}

	@Override
	public void removeDefaultMetadata(int id) {
		genericDelete(id, DefaultMetadata.class, "removeDefaultMetadata");
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
	public void removeMetadata(int id) {
		genericDelete(id, Metadata.class, "removeMetadata");
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
	public void updateComment(Comment comment) {
		genericUpdate(comment, Comment.class, "updateComment");
	}

	@Override
	public void updateDefaultMetadata(DefaultMetadata metadata) {
		genericUpdate(metadata, DefaultMetadata.class, "updateDefaultMetadata");
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
	public void updateMetadata(Metadata metadata) {
		genericUpdate(metadata, Metadata.class, "updateMetadata");

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
}
