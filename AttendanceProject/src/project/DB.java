package project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB implements IDB{
	@Override
    public Connection connect(String URL,String username,String password) {
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(URL, username, password);

            return connection;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
