package traffic.model;

import simulaltion.queue.*;


public class CommandQueue extends MsgQueue{
	/* ��â�� �߰�
	 * (non-Javadoc)
	 * @see msg.queue.MsgQueue#append(msg.node.MsgNode)
	 */
	public synchronized boolean append(QueueNode msgNode)
	{		
		if (super.append(msgNode))
		{
			notifyAll();
		}
		
		return true;
	}
	/* ��â�� �߰�
	 * (non-Javadoc)
	 * @see msg.queue.MsgQueue#append(msg.node.MsgNode)
	 */
	public synchronized QueueNode poll() 
	{
		QueueNode node = null;
		
		while((node = super.poll()) == null)
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				//e.printStackTrace();
			}
		}
		
		return node;
	}

}
