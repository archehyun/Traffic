package traffic.model;

import java.awt.GridLayout; 

import java.awt.datatransfer.DataFlavor; 

import java.awt.datatransfer.Transferable; 

import java.io.File; 

import javax.swing.DefaultListModel; 

import javax.swing.DropMode; 

import javax.swing.JFrame; 

import javax.swing.JList; 

import javax.swing.TransferHandler; 

public class DnDTest extends JFrame { 

   JList list1, list2; 

   DefaultListModel model1, model2; 

   public DnDTest() { 

      setLayout( new GridLayout(1, 2) ); 

      model1 = new DefaultListModel<String>(); 

      String[] files = new File("/").list(); 

      for(String path : files) { 

         model1.addElement( path ); 

      } 

      list1 = new JList( model1 ); 

      list1.setDragEnabled( true ); 

      add( list1 ); 

      model2 = new DefaultListModel<String>(); 

      list2 = new JList( model2 ); 

      list2.setDropMode(DropMode.INSERT); 

      list2.setTransferHandler( new ListHandler() ); 

      add( list2 ); 

      setSize(500, 500); 

      setDefaultCloseOperation(EXIT_ON_CLOSE); 

      setVisible(true); 

   } 

   private class ListHandler extends TransferHandler { 

            public boolean canImport(TransferSupport support) { 

                     if (!support.isDrop()) { 

                            return false; 

                     } 

                     return support.isDataFlavorSupported(DataFlavor.stringFlavor); 

              } 

              public boolean importData(TransferSupport support) { 

                     if (!canImport(support)) { 

                          return false; 

                     } 

                     Transferable transferable = support.getTransferable(); 

                     String line; 

                     try { 

                          line = (String) transferable.getTransferData(DataFlavor.stringFlavor); 
                        } catch (Exception e) { 

                          return false; 

                     } 

                     JList.DropLocation dl = (JList.DropLocation) support.getDropLocation(); 

                     int index = dl.getIndex(); 

                     String[] data = line.split(","); 

                     for (String item: data) { 

                            if (!item.isEmpty()) 

                              model2.add(index++, item.trim()); 

                     } 

                     return true; 

              } 

     } 

   public static void main(String[] args) { 

      new DnDTest(); 

   } 

}

