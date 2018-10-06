package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@SuppressWarnings("serial")
public class ReceiveServlet extends HttpServlet {
    private final static String QUEUE_NAME = "queue1";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Receive Servlet");
	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setUsername("rabbitmq");
	connectionFactory.setPassword("rabbitmq");
	connectionFactory.setHost("rabbitmq");
	connectionFactory.setVirtualHost("vhost1");
	try {
	    Connection connection = connectionFactory.newConnection();
	    Channel channel = connection.createChannel();
	    boolean durable = true;
	    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

	    Consumer consumer = new DefaultConsumer(channel) {
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			byte[] body) throws IOException {
		    String message = new String(body, "UTF-8");
		    out.println("Received '" + message + "'");
		}
	    };
	    channel.basicConsume(QUEUE_NAME, true, consumer);
	    channel.close();
	    connection.close();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
