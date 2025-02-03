package godxero.util;

import godxero.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
	private CrudUtil () {}

	@SuppressWarnings("unchecked")
	public static <T>T execute (String sql, Object... dataList) throws SQLException {
		final PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
		final int dataLength = dataList.length;

		if (dataLength != 0) for (int a = 0; a < dataLength; a++) preparedStatement.setObject(a + 1, dataList[a]);

		if (sql.matches("(?i)^select.*")) return (T) preparedStatement.executeQuery();

		return (T)((Integer) preparedStatement.executeUpdate());
	}
}
