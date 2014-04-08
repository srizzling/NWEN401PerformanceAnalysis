import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueConsumer implements MessageListener {

	public static boolean recieve=true;

	public static void main(String[] args) throws JMSException, NamingException {
		try {
			System.out.println("=====Starting JMS Consumer======");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Context context = QueueConsumer.getInitialContext();
		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		Queue queue = (Queue) context.lookup("queue/myAppQueue");
		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		QueueSession session =  queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		QueueReceiver receiver = session.createReceiver(queue);
		receiver.setMessageListener(new QueueConsumer());
		try{
			while(recieve) {
				queueConnection.start();
			}
		} finally{
			queueConnection.close();
		}
		System.out.println("=====Exiting JMS Consumer=======");

	}

	@Override
	public void onMessage(Message msg) {
		try{
			System.out.println("Incoming messages: " + msg.getJMSMessageID());
		} catch (JMSException e){
			e.printStackTrace();
		}

	}

	public static Context getInitialContext()  throws JMSException, NamingException{
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.factory.url.pkgs","org.jboss.naming");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		Context context = new InitialContext(props);
		return context;
	}

}
