package sider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Meddra {
	public static ArrayList<String> Request(String select, String from, String where) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr/gmd";
		String user = "gmd-read";
		String pwd = "esial";
		Connection conn = DriverManager.getConnection(url, user, pwd);
		Statement s =conn.createStatement();
		String kf = "SELECT "+select+" FROM "+from+" WHERE "+where;
		ResultSet rsf = s.executeQuery(kf);
		if(rsf.next()){
		rsf.absolute(1);
		ArrayList<String> s1 = new ArrayList<String>();
		while(!rsf.isAfterLast()){
			s1.add(rsf.getString(1));
			rsf.next();
		}
		return s1;
		}else{return new ArrayList<String>();}
	}
}
