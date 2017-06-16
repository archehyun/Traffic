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
					// 붙여넣을 테이블에 선택된 시작 셀 위치
					
					int selectedRow=this.getSelectedRow();
					int selectedColum = this.getSelectedColumn();
					
					String copyValue=this.getClipboardContents();
					
					String rowField[]=copyValue.split("\n");
					
					// 선택 지점부터 복사된 값 길이 까지
					for(int tableRowIndex=selectedRow,rowFieldCount=0;tableRowIndex<selectedRow+rowField.length;tableRowIndex++, rowFieldCount++)
					{
						// 붙여넣을 영역이 전체 row 보다 크면 중지
						if(tableRowIndex>=this.getRowCount())
							break;
						
						String columField[] = rowField[rowFieldCount].split("\t");
						for(int tableColumIndex=selectedColum,columFieldCount=0;tableColumIndex<selectedColum+columField.length;tableColumIndex++,columFieldCount++)
						{
							// 붙여넣을 영역이 전체 col 보다 크면 데이터 추출 안하고 다음 row 이동
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
		// 클립보드에 문자열을 붙여넣고 이 클래스가 클립보드 내용의 소유권자가 되도록 하는 메소드다.
		public void setClipboardContents( String aString ){
			
			System.out.println("set\n"+aString);
			// 지정한 문자열(aString)을 전송할 수 있도록 Transferable을 구현해야한다. 
			StringSelection stringSelection = new StringSelection( aString );
			// 플랫폼에 의해서 제공되는 클립보드 기능과 상호 작용하는 system Clipboard의 인스턴스 얻게 된다.
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			// 지정한 transferable한 객체를 클립보드에 설정하고 이 새로운 내용에 대한 소유권자로 등록한다.
			//setContents(내용, 소유권자)
			clipboard.setContents( stringSelection, this );
		}
		public String getClipboardContents() {
			String result = "";
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			// 현재 클립보드의 내용을 나타내는 transferable한 객체를 반환한다.
			//클립보드 데이터를 요청한 객체를 인자로 전송하는 방법이 현재는 사용되지 않는다.
			Transferable contents = clipboard.getContents(null);
			// 클립보드가 비어있지 않고 인자로 전달한 data flavor가 Transferable 객체를 위해 지원
			// 되는지 확인하고 Boolean 값을 hasTransferableText 변수에 저장
			boolean hasTransferableText =
					(contents != null) &&
					contents.isDataFlavorSupported(DataFlavor.stringFlavor);
			// Java Unicode String class를 대표하는 DataFlavor형 stringFlavor를 getTransferData() 
			// 메소드의 인자로 전달한다. getTransferData() 메소드는 전송될 데이터를 대표하는 객체를
			// String 형으로 형변환한 후 반환한다.
			if ( hasTransferableText ) {
				try {
					result = (String)contents.getTransferData(DataFlavor.stringFlavor);
				}
				catch (UnsupportedFlavorException ex){
					// 표준 DataFlavor를 사용하기 때문에 가능성은 매우 희박하지만
					// 다음과 같이 통상 예외처리해줍니다.
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


