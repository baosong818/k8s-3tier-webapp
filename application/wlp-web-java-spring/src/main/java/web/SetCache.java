package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;

import web.util.CreateId;
import web.util.GetConfig;

public class SetCache extends HttpServlet {
    private static String serverconf = GetConfig.getResourceBundle("cache.server.conf");
    private static String message = GetConfig.getResourceBundle("common.message");

	// コネクションプールの初期化
	static {
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(new String[] { serverconf });
		pool.initialize();
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Set Cache");

	String id = String.valueOf(CreateId.createid());
	out.println("id: " + id);
	out.println("msg: " + message);

	MemcachedClient mcc = new MemcachedClient();
	try {
	    mcc.set("id", id);
	    mcc.set("msg", message);

	    System.out.println("Set: id: " + id + ", msg:" + message);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}