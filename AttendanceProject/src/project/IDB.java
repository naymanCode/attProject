package project;

import java.sql.Connection;

public interface IDB {
	Connection connect(String URL,String username,String password);
}
