
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;

// ClipboardOwner�� Ŭ�����忡 �����͸� �����Ϸ��� Ŭ������ ���� �������̽��̴�.
// �� �������̽��� �ν��Ͻ��� Ŭ�����忡 �ִ� ������ ���������̴�
public final class TextTransfer implements ClipboardOwner {

	public static void main (String...  aArguments ){
		TextTransfer textTransfer = new TextTransfer();

		//���� Ŭ�����忡 � �ؽ�Ʈ�� ����Ǿ� �ִ��� Ȯ������
		
		
		System.out.println("���� Ŭ�����忡 ����ִ� �ؽ�Ʈ:" + textTransfer.getClipboardContents() );

		//Ŭ�����忡 �ٸ� �ؽ�Ʈ�� �ٿ��ְ� Ȯ���غ���.
		textTransfer.setClipboardContents("blah, blah, blah");
		System.out.println("���� �� Ŭ�����忡 ����ִ� �ؽ�Ʈ" + textTransfer.getClipboardContents() );
	}

	//�������̽��� ��� �޼ҵ带 ��������� �Ѵ�. empty implementation���ش�.
	public void lostOwnership( Clipboard aClipboard, Transferable aContents) {
		//�׳� �����⸸ �д�.
	}

	// Ŭ�����忡 ���ڿ��� �ٿ��ְ� �� Ŭ������ Ŭ������ ������ �������ڰ� �ǵ��� �ϴ� �޼ҵ��.
	public void setClipboardContents( String aString ){
		// ������ ���ڿ�(aString)�� ������ �� �ֵ��� Transferable�� �����ؾ��Ѵ�. 
		StringSelection stringSelection = new StringSelection( aString );
		// �÷����� ���ؼ� �����Ǵ� Ŭ������ ��ɰ� ��ȣ �ۿ��ϴ� system Clipboard�� �ν��Ͻ� ��� �ȴ�.
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// ������ transferable�� ��ü�� Ŭ�����忡 �����ϰ� �� ���ο� ���뿡 ���� �������ڷ� ����Ѵ�.
		//setContents(����, ��������)
		clipboard.setContents( stringSelection, this );
	}

	/**
	 * Get the String residing on the clipboard.
	 *
	 * @return any text found on the Clipboard; if none found, return an
	 * empty String.
	 */
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

