import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

public class QueueProducer {
	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("=====Starting JMS Producer======");
		Context context = QueueConsumer.getInitialContext();
		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		Queue queue = (Queue) context.lookup("queue/myAppQueue");
		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		QueueSession session =  queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		queueConnection.start();
		try{
			QueueProducer queueProducer = new QueueProducer();

			int n=10;
			int transmittedTime = 100;




			//Prepare message size to be transmitted
			byte[] byteMsg = new byte[1024*n];
			String strMsg = new String(byteMsg);

			System.out.println("Start sending messages");
			System.out.println("n=" + n);
			long startTime = System.nanoTime();
			for(int i=0; i<transmittedTime; i++){
				System.out.println("MSG being sent"+i);
				queueProducer.sendMessage(strMsg, session, queue);
			}
			long endTime = System.nanoTime();

			//System.out.println("Duration = " + (endTime-startTime));




			System.out.println("=====Exiting JMS Producer======");
		}
		finally{
			queueConnection.close();
		}


	}

	public void sendMessage(String text, QueueSession session, Queue queue) throws JMSException{
		QueueSender queueSender = session.createSender(queue);
		try {
			TextMessage txtMsg = session.createTextMessage(text);
			queueSender.send(txtMsg);
		}
		finally {
			queueSender.close();
		}
	}


}
