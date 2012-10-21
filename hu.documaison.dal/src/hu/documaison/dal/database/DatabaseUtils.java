package hu.documaison.dal.database;

import hu.documaison.data.entities.*;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseUtils {
	private static final String databaseUrl = "jdbc:sqlite:d:/temp/documaison.sqlite:documaison";

	public static ConnectionSource getConnectionSource() throws SQLException {
		ConnectionSource connectionSource = new JdbcConnectionSource(
				databaseUrl);
		return connectionSource;
	}

	// public static <X extends Object> Dao<X, String> getDao(ConnectionSource
	// connectionSource)
	// {
	// // instantiate the dao
	// Dao<X, String> dao =
	// DaoManager.createDao(connectionSource, X.class);
	// return dao;
	// }

	public static void createTables() throws SQLException {
		ConnectionSource connectionSource = getConnectionSource();
		try {
			// the order of creation is important because of the foreign keys!
			TableUtils.createTable(connectionSource, Tag.class);
			TableUtils.createTable(connectionSource, Comment.class);
			TableUtils.createTable(connectionSource, Metadata.class);
			TableUtils.createTable(connectionSource, DefaultMetadata.class);
			TableUtils.createTable(connectionSource, DocumentType.class);
			TableUtils.createTable(connectionSource, Document.class);
		} finally {
			connectionSource.close();
		}
	}

	public static void createTablesBestEffort() {
		// the order of creation is important because of the foreign keys!
		tryCreateTable(Tag.class);
		tryCreateTable(Comment.class);
		tryCreateTable(Metadata.class);
		tryCreateTable(DefaultMetadata.class);
		tryCreateTable(DocumentType.class);
		tryCreateTable(Document.class);
	}

	private static <T> void tryCreateTable(Class<T> tableClass) {
		ConnectionSource connectionSource = null;
		try {
			connectionSource = getConnectionSource();
			TableUtils.createTable(connectionSource, tableClass);
		} catch (SQLException e) {
			// best effort manner
		} finally {
			try {
				if (connectionSource != null) {
					connectionSource.close();
				}
			} catch (SQLException e) {
				// best effort manner
			}
		}
	}
}
