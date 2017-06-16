
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;

// ClipboardOwner는 클립보드에 데이터를 제공하려는 클래스를 위한 인터페이스이다.
// 이 인터페이스의 인스턴스는 클립보드에 있는 내용의 소유권자이다
public final class TextTransfer implements ClipboardOwner {

	public static void main (String...  aArguments ){
		TextTransfer textTransfer = new TextTransfer();

		//지금 클립보드에 어떤 텍스트가 저장되어 있는지 확인하자
		
		
		System.out.println("현재 클립보드에 담겨있는 텍스트:" + textTransfer.getClipboardContents() );

		//클립보드에 다른 텍스트를 붙여넣고 확인해보자.
		textTransfer.setClipboardContents("blah, blah, blah");
		System.out.println("변경 후 클립보드에 담겨있는 텍스트" + textTransfer.getClipboardContents() );
	}

	//인터페이스의 모든 메소드를 구현해줘야 한다. empty implementation해준다.
	public void lostOwnership( Clipboard aClipboard, Transferable aContents) {
		//그냥 껍데기만 둔다.
	}

	// 클립보드에 문자열을 붙여넣고 이 클래스가 클립보드 내용의 소유권자가 되도록 하는 메소드다.
	public void setClipboardContents( String aString ){
		// 지정한 문자열(aString)을 전송할 수 있도록 Transferable을 구현해야한다. 
		StringSelection stringSelection = new StringSelection( aString );
		// 플랫폼에 의해서 제공되는 클립보드 기능과 상호 작용하는 system Clipboard의 인스턴스 얻게 된다.
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 지정한 transferable한 객체를 클립보드에 설정하고 이 새로운 내용에 대한 소유권자로 등록한다.
		//setContents(내용, 소유권자)
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

