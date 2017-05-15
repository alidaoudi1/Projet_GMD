package hpo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Hpoan {
	public static ArrayList<String> search(String id) throws SQLException, ClassNotFoundException{
		Connection c = null;
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:hpo_annotations.sqlite");
		c.setAutoCommit(false);
		Statement s = c.createStatement();
		String q ="SELECT disease_id, disease_db FROM phenotype_annotation WHERE sign_id='"+id+"'";
		ResultSet r = s.executeQuery(q);
		ArrayList<String> res= new ArrayList<String>();
		while(r!=null&&r.next()){
			String s1 = "";
			if (r.getString(2).equals("OMIM") ||r.getString(2).equals("ORPHA")){
			s1=r.getString(2)+"//"+r.getString(1);
			}
			res.add(s1);
		}
		return res;
	}
}