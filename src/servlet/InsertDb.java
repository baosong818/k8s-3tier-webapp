package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import servlet.util.CreateId;
import servlet.util.GetConfig;

public class InsertDb extends HttpServlet {
    private static String jndiname = GetConfig.getResourceBundle("jndi.name");
    private static String sqlkey = GetConfig.getResourceBundle("insert.msg.id");
    private DataSource ds;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	Connection con = null;
	String id = String.valueOf(CreateId.createid());
	String sql = GetConfig.getResourceBundle("insert.msg");

	PrintWriter out = response.getWriter();
	out.println("Insert DB");

	try {
	    sql = sql.replace(sqlkey, id);
	    System.out.println("Execute SQL: " + sql);
	    out.println("Execute SQL: " + sql);

	    InitialContext ctx = new InitialContext();
	    ds = (DataSource) ctx.lookup(jndiname);
	    con = ds.getConnection();
	    Statement stmt = con.createStatement();

	    stmt.executeUpdate(sql);
	} catch (NamingException | SQLException e) {
	    e.printStackTrace();
	} finally {
	    if (con != null) {
		try {
		    con.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
}