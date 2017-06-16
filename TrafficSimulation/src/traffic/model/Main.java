package traffic.model;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageConsumer;
import java.io.IOException;
import java.util.Date;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main {

	String[] headings = { "Name", "Customer ID", "Order #", "Status" };

	Object[][] data = {
			{ "A", new Integer(3), "0", new Date() },
			{ "B", new Integer(6), "4", new Date() },
			{ "C", new Integer(9), "9", new Date() },
			{ "D", new Integer(7), "1", new Date() },
			{ "E", new Integer(4), "1", new Date() },
			{ "F", new Integer(8), "2", new Date() },
			{ "G", new Integer(6), "1", new Date() }
	};
	Object[][] data2 = {
			{ "A", new Integer(3), "0", new Date() },
			{ "B", new Integer(6), "4", new Date() },
			{ "C", new Integer(9), "9", new Date() },
			{ "D", new Integer(7), "1", new Date() },
			{ "E", new Integer(4), "1", new Date() },
			{ "F", new Integer(8), "2", new Date() },
			{ "G", new Integer(6), "1", new Date() }
	};

	TestTable jtabOrders = new TestTable(data, headings);
	TestTable jtabOrders2 = new TestTable(data2, headings);
	Clipboard clipboard;

	private JFrame jfrm;

	Main() {
		jfrm = new JFrame("JTable Demo");
		jfrm.setLayout(new FlowLayout());

		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane jscrlp = new JScrollPane(jtabOrders);
		JScrollPane jscrlp2 = new JScrollPane(jtabOrders2);
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();


		//jtabOrders.setPreferredScrollableViewportSize(new Dimension(420, 60));


		jfrm.setVisible(true);

		JPanel pnGird = new JPanel(new GridLayout(1, 2));
		pnGird.add(jscrlp);
		pnGird.add(jscrlp2);

		jfrm.getContentPane().add(pnGird);



		jfrm.pack();

	}
	class SelectionListener implements ListSelectionListener {
		JTable table;
		int selectedRows[];
		int selectedColums[];



		public SelectionListener(JTable table) {
			this.table = table;
		}
		public String getSelectedValue()
		{
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<selectedRows.length;i++)
			{
				for(int j=0;j<selectedColums.length;j++)
				{
					buffer.append(table.getValueAt(selectedRows[i], selectedColums[j]));
					if(j<selectedColums.length)
						buffer.append("\t");

				}
				if(i<selectedRows.length)
					buffer.append("\n");

			}
			return buffer.toString();
		}
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()) {

				//DefaultListSelectionModel model = (DefaultListSelectionModel) e.getSource();

				selectedRows=table.getSelectedRows();
				selectedColums=table.getSelectedColumns();
				System.out.println("valueChange");

			}

		}
	}


	public static void main(String args[]) {
		new Main();
	}

	class TestTable extends JTable implements ClipboardOwner, KeyListener
	{
		int prevKeyCode;
		KeyStroke CNTR_C_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_CANCEL,InputEvent.CTRL_MASK);
		KeyStroke CNTR_V_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK);


		private SelectionListener listener;

		public TestTable(Object[][] data, String[] headings) {
			super(data,headings);
			setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			setCellSelectionEnabled(true);
			listener = new SelectionListener(this);

			getSelectionModel().addListSelectionListener(listener);
			this.addKeyListener(this);
		}

		@Override
		public void lostOwnership(Clipboard clipboard, Transferable contents) {}

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_C)
			{
				if(prevKeyCode == KeyEvent.VK_CONTROL)
				{
					System.out.println("ctrl+c");
					this.setClipboardContents(listener.getSelectedValue());
				}
			}

			else if(e.getKeyCode()==KeyEvent.VK_V)
			{
				if(prevKeyCode == KeyEvent.VK_CONTROL)
				{
					// �ٿ����� ���̺� ���õ� ���� �� ��ġ
					
					int selectedRow=this.getSelectedRow();
					int selectedColum = this.getSelectedColumn();
					
					String copyValue=this.getClipboardContents();
					
					String rowField[]=copyValue.split("\n");
					
					// ���� �������� ����� �� ���� ����
					for(int tableRowIndex=selectedRow,rowFieldCount=0;tableRowIndex<selectedRow+rowField.length;tableRowIndex++, rowFieldCount++)
					{
						// �ٿ����� ������ ��ü row ���� ũ�� ����
						if(tableRowIndex>=this.getRowCount())
							break;
						
						String columField[] = rowField[rowFieldCount].split("\t");
						for(int tableColumIndex=selectedColum,columFieldCount=0;tableColumIndex<selectedColum+columField.length;tableColumIndex++,columFieldCount++)
						{
							// �ٿ����� ������ ��ü col ���� ũ�� ������ ���� ���ϰ� ���� row �̵�
							if(tableColumIndex>=this.getColumnCount())
								continue;
							
							this.setValueAt(columField[columFieldCount], tableRowIndex, tableColumIndex);
							System.out.println("set value:"+columField[columFieldCount]);
						}
					}
					System.out.println();
					
					
					

					//this.setClipboardContents("");
				}
			}
			else
			{
				prevKeyCode = e.getKeyCode();
			}

		}
		// Ŭ�����忡 ���ڿ��� �ٿ��ְ� �� Ŭ������ Ŭ������ ������ �������ڰ� �ǵ��� �ϴ� �޼ҵ��.
		public void setClipboardContents( String aString ){
			
			System.out.println("set\n"+aString);
			// ������ ���ڿ�(aString)�� ������ �� �ֵ��� Transferable�� �����ؾ��Ѵ�. 
			StringSelection stringSelection = new StringSelection( aString );
			// �÷����� ���ؼ� �����Ǵ� Ŭ������ ��ɰ� ��ȣ �ۿ��ϴ� system Clipboard�� �ν��Ͻ� ��� �ȴ�.
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			// ������ transferable�� ��ü�� Ŭ�����忡 �����ϰ� �� ���ο� ���뿡 ���� �������ڷ� ����Ѵ�.
			//setContents(����, ��������)
			clipboard.setContents( stringSelection, this );
		}
		public String getClipboardContents() {
			String result = "";
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			// ���� Ŭ�������� ������ ��Ÿ���� transferable�� ��ü�� ��ȯ�Ѵ�.
			//Ŭ������ �����͸� ��û�� ��ü�� ���ڷ� �����ϴ� ����� ����� ������ �ʴ´�.
			Transferable contents = clipboard.getContents(null);
			// Ŭ�����尡 ������� �ʰ� ���ڷ� ������ data flavor�� Transferable ��ü�� ���� ����
			// �Ǵ��� Ȯ���ϰ� Boolean ���� hasTransferableText ������ ����
			boolean hasTransferableText =
					(contents != null) &&
					contents.isDataFlavorSupported(DataFlavor.stringFlavor);
			// Java Unicode String class�� ��ǥ�ϴ� DataFlavor�� stringFlavor�� getTransferData() 
			// �޼ҵ��� ���ڷ� �����Ѵ�. getTransferData() �޼ҵ�� ���۵� �����͸� ��ǥ�ϴ� ��ü��
			// String ������ ����ȯ�� �� ��ȯ�Ѵ�.
			if ( hasTransferableText ) {
				try {
					result = (String)contents.getTransferData(DataFlavor.stringFlavor);
				}
				catch (UnsupportedFlavorException ex){
					// ǥ�� DataFlavor�� ����ϱ� ������ ���ɼ��� �ſ� ���������
					// ������ ���� ��� ����ó�����ݴϴ�.
					System.out.println(ex);
					ex.printStackTrace();
				}
				catch (IOException ex) {
					System.out.println(ex);
					ex.printStackTrace();
				}
			}
			return result;
		}

	}
}


