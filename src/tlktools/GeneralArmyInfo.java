package tlktools;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
/**
 *
 * @author EDDY
 */
public class GeneralArmyInfo extends JPanel {
       private boolean DEBUG = false;

    public GeneralArmyInfo(List<String[]> list) {
        //super(new GridLayout(1,0));
        //super(new GridLayout(1,0));
        super(new GridLayout(1,0));
        
        String[] columnNames = {"Status",
                                "Rank",
                                "Name",
                                "Standing",
                                "Total",
                                "MP"};
        
        
        Object[][] data= new Object[list.size()][6];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }
        

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    protected static void createAndShowGUI(List <String[]> list) {
        //Create and set up the window.
        String fName = "Unchanged" + list.get(0)[2];
        for (int i = 0; i < TLKTools.aInfo.size(); i++) {
            if ( TLKTools.aInfo.get(i).contains((list.get(0)[2]).split(" ")[1]) ) {
                fName= TLKTools.aInfo.get(i-1);
            }
        }
        JFrame frame = new JFrame(fName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        GeneralArmyInfo newContentPane = new GeneralArmyInfo(list);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void generateTable(List <String[]> list) {
        //TLKTools.runProg();
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(list);
            }
        });
    }
}
