package web.mq;

import javax.servlet.http.HttpServlet;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import web.util.CreateId;
import web.util.GetConfig;

public class PutMqBatch extends HttpServlet {
    private static String queuename = GetConfig.getResourceBundle("batch.queue.name");
    private static String username = GetConfig.getResourceBundle("jms.username");
    private static String password = GetConfig.getResourceBundle("jms.password");
    private static String host = GetConfig.getResourceBundle("jms.host");
    private static String vhost = GetConfig.getResourceBundle("jms.vhost");
    private static String message = GetConfig.getResourceBundle("batch.message");
    private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

    public String putMessageQueueBatch() {
	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setUsername(username);
	connectionFactory.setPassword(password);
	connectionFactory.setHost(host);
	connectionFactory.setVirtualHost(vhost);
	String fullmsg = null;

	try {
	    Connection connection = connectionFactory.newConnection();
	    Channel channel = connection.createChannel();

	    String id = String.valueOf(CreateId.createid());

	    StringBuilder buf = new StringBuilder();
	    buf.append(id);
	    buf.append(splitkey);
	    buf.append(message);
	    String body = buf.toString();

	    channel.basicPublish("", queuename, null, body.getBytes());

	    fullmsg = "Set id: " + id + ", msg: " + message;
		System.out.println(fullmsg);

	    channel.close();
	    connection.close();

	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	return fullmsg;
    }
}