package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Graphic {

    final JFrame frame;
    private Object[][] arrayNow;


    private String getFilePath(String title){
        File inputArrayFile = chooseFileDialog(title);
        if(inputArrayFile == null){
            System.err.println("File not chose");
            System.exit(-1);
        }
        String inputFilePath = inputArrayFile.getPath();
        System.out.print("input file path: ");
        System.out.println(inputFilePath);
        return  inputFilePath;
    }

    private static Integer[][] openArrayFromFile(String path){
        ArrayReader arrayReader = null;
        Integer[][] gotArray = null;
        try {
            arrayReader = new ArrayReader(path);
        } catch (java.io.IOException e) {
            System.err.println("file not found");
            System.exit(-1);
        }

        try {
            gotArray = arrayReader.getArray();
        } catch (ArrayReader.FileDataException e) {
            System.err.println("invalid file");
            System.exit(-1);
        }
        return gotArray;
    }

    private static void saveArrayToFile(Object[][] array, String path){
        ArrayWriter arrayWriter = null;
        try {
            arrayWriter = new ArrayWriter(path);
        } catch (IOException e){
            System.err.println("can't open file to output");
            System.exit(-1);
        }

        try {
            arrayWriter.writeMatrix(array);
        } catch (IOException e){
            System.err.println("can't write array in output file");
            System.exit(-1);
        }
    }


    public Graphic(){
        this.frame = new JFrame();
        openTableScreen(openArrayFromFile(getFilePath("Choose file to open")));
    }

    public File chooseFileDialog(String title){
        this.frame.setAlwaysOnTop(true);    // ****
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.requestFocus();

        File workingDirectory = new File(System.getProperty("user.dir"));
        JFileChooser jfc = new JFileChooser(workingDirectory);
        jfc.setDialogTitle(title);

        int returnValue = jfc.showOpenDialog(this.frame);
        clear();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }
        return null;
    }

    public void clear(){
        this.frame.getContentPane().removeAll();
        this.frame.repaint();
    }

    public Object[][] getArrayNow(){
        return arrayNow;
    }

    public void openTableScreen(Object[][] inputArray){
        clear();
        arrayNow = inputArray;

        int arrayWidth = inputArray[0].length;
        int arrayHeight = inputArray.length;
        int tableWidth = arrayWidth * 100;
        int tableHeight = arrayHeight * 50;

        JTable table = setTable(inputArray);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(tableWidth,tableHeight);
        // create panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        // pack buttons and table
        topPanel.add(scrollPane);
        packButtonInPanel(topPanel, tableWidth, tableHeight);
        // draw window
        this.frame.getContentPane().add(topPanel);
        this.frame.setSize(tableWidth,tableHeight+60);
        this.frame.setVisible(true);
    }

    private void packButtonInPanel(JPanel panel, int displayWidth, int displayHeight){
        int buttonWidth = 90;
        int buttonHeight = 30;
        JButton openButton=new JButton("open");
        openButton.setBounds(0,displayHeight,buttonWidth,buttonHeight);
        openButton.addActionListener(e -> {
            this.clear();
            this.openTableScreen(openArrayFromFile(getFilePath( "Choose file to open")));
        });

        JButton moverRow=new JButton("up");
        moverRow.setBounds(displayWidth/3-buttonWidth/2,displayHeight,buttonWidth,buttonHeight);
        moverRow.addActionListener(e -> {
            Integer[][] arrayNow = (Integer[][])this.getArrayNow();
            Integer[][] result = MatrixCalculator.moveRows(arrayNow, 1);
            this.clear();
            this.openTableScreen(result);
        });

        JButton moverColumn=new JButton("left");
        moverColumn.setBounds(displayWidth/3*2-buttonWidth/2,displayHeight,buttonWidth,buttonHeight);
        moverColumn.addActionListener(e -> {
            Integer[][] arrayNow = (Integer[][])this.getArrayNow();
            Integer[][] result = MatrixCalculator.moveColumns(arrayNow, 1);
            this.clear();
            this.openTableScreen(result);
        });

        JButton saveButton=new JButton("save");
        saveButton.setBounds(displayWidth-buttonWidth,displayHeight,buttonWidth,buttonHeight);
        saveButton.addActionListener(e -> {
            System.out.println("clicked save");
            Object[][] arrayNow = this.getArrayNow();
            saveArrayToFile(arrayNow, getFilePath( "Choose file to save"));

            this.clear();
            this.openTableScreen(this.getArrayNow());

        });

        panel.add(openButton);
        panel.add(moverRow);
        panel.add(moverColumn);
        panel.add(saveButton);
    }


    private static JTable setTable(Object[][] inputArray){
        EditableTableModel model = new EditableTableModel(inputArray);
        model.isCellEditable = true;

        JTable jt=new JTable(model);
        jt.setFocusable(false);
        jt.setRowSelectionAllowed(false);
        // show grid
        jt.setRowHeight(40);
        jt.setShowGrid(true);
        jt.setGridColor(Color.BLACK);
        jt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // set font
        Font tableFont = new Font("Times New Roman", Font.PLAIN, 40);
        // set the font in edit cell mode
        DefaultCellEditor editor = (DefaultCellEditor)jt.getDefaultEditor(Object.class);
        editor.getComponent().setFont(tableFont);

        for (int i = 0; i < model.getColumnCount(); i++) {
            jt.getColumnModel().getColumn(i).setCellEditor(editor);
            jt.getColumnModel().getColumn(i).setCellRenderer(new MyTableCellRenderer(tableFont));
        }
        return jt;
    }

    static class MyTableCellRenderer extends JLabel implements TableCellRenderer {
        private final Font font;
        public MyTableCellRenderer(Font font){
            this.font = font;
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int rowIndex, int vColIndex) {
            setText(value.toString());
            setFont(font);
            setHorizontalAlignment(SwingConstants.RIGHT);

            return this;
        }
    }


    private static class EditableTableModel extends AbstractTableModel {
        String[] columnTitles;

        Object[][] dataEntries;

        public boolean isCellEditable = true;

        public EditableTableModel(Object[][] dataEntries) {
            this.columnTitles = new String[dataEntries[0].length];
            this.dataEntries = dataEntries;
        }

        public int getRowCount() {
            return dataEntries.length;
        }

        public int getColumnCount() {
            return columnTitles.length;
        }

        public Object getValueAt(int row, int column) {
            return dataEntries[row][column];
        }

        public String getColumnName(int column) {
            return columnTitles[column];
        }

        public Class<?> getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }

        public boolean isCellEditable(int row, int column) {
            return isCellEditable;
        }

        public void setValueAt(Object value, int row, int column) {
            if (value != null) dataEntries[row][column] = value;
        }
    }

}
