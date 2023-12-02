//Klasa narzędziowa, której głównym celem jest dostarczenie narzędzi do obsługi bazy danych w kontekście połączenia i konfiguracji.
// Wewnątrz tej klasy mogą znajdować się metody odpowiedzialne za ustanawianie połączenia z bazą danych,
// zamykanie połączenia, obsługę transakcji,
// a także inne narzędzia związane z bazą danych,
// takie jak inicjalizacja schematu bazy danych, itp.

package twojaOpinia.util;
import java.sql.*;

public class DataBaseUtil {
	
 	private final static String dburl = "jdbc:mysql://127.0.0.1:3306/twojaOpinia";
 	private final static String dbuser = "root";
 	private final static String dbpass = "";
 	
	public static Connection connect()
 	{
 		Connection connection = null;
 		try 
 		{
 			
			connection = DriverManager.getConnection(dburl, dbuser, dbpass);

			
 		} catch (SQLException e) {
 			
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		return connection;
 	}

}
