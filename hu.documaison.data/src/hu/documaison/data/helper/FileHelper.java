package hu.documaison.data.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {

	/**
	 * Copies one file to another place. (Simple implementation.) based on:
	 * http:
	 * //www.programmersheaven.com/mb/java/314759/314759/copymove-or-deleting
	 * -file/
	 * 
	 * @param source
	 *            Source file.
	 * @param destination
	 *            Target file.
	 * @return True, if the operation was successful.
	 */
	public static boolean copy(File source, File destination)
			throws IOException {

		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(destination);
			byte[] buffer = new byte[8 * 1024];

			while (true) {
				int bytesRead = in.read(buffer);
				if (bytesRead <= 0) {
					break;
				}
				out.write(buffer, 0, bytesRead);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					return false;
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		return true;
	}

	public static String fileExtension(String path) {
		int lastDot = path.lastIndexOf('.');
		return path.substring(lastDot + 1);
	}

	public static File createFileObject(String location) {
		File ret;
		try {
			ret = new File(location);
			return ret;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public static String fileName(String path) {
		return (new File(path)).getName();
	}

}
