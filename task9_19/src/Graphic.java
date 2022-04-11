import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

class Graphic{
    final JFrame frame;
    int arrayWidth;
    private List<Integer> arrayNow;

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

    private static List<Integer> openArrayFromFile(String path){
        List<Integer> gotArray = null;
        try {
            gotArray = ArrayReader.getArray(path);
        } catch (IOException e) {
            System.err.println("недопустимый файл");
            System.exit(-1);
        }

        if (gotArray == null) {
            System.err.println("недопустимый файл");
            Graphic.showError("Ошибка!", "Не удается получить массив из файла, неверные данные:\n"+ path);
            System.exit(-1);
        }
        return gotArray;
    }

    private static void saveArrayToFile(List<Integer> array, String path){
        try {
            ArrayWriter.writeArray(path, array);
        } catch (IOException e){
            System.err.println("Невозможно записать массив в выходной файл");
            System.exit(-1);
        }
    }


    public Graphic(){
        this.frame = new JFrame();
        openTableScreen(openArrayFromFile(getFilePath("Выберите файл для открытия")), 7);
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


    public List<Integer> getArrayNow(){
        return arrayNow;
    }

    public void openTableScreen(List<Integer> inputArray, int arrayWidth){
        clear();
        this.arrayWidth = arrayWidth;
        arrayNow = inputArray;
        // if we have only one row
        if (arrayWidth > inputArray.size()) arrayWidth = inputArray.size();

        int arrayHeight = getTableHeight(inputArray.size(), arrayWidth);
        int tableWidth = arrayWidth * 100;
        int tableHeight = arrayHeight * 50;

        JTable table = setTable(inputArray, arrayWidth);
        table.setSize(tableWidth, tableHeight);
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

    ActionListener add = e -> {
        arrayNow.add(0);
        openTableScreen(arrayNow, arrayWidth);
    };

    private void packButtonInPanel(JPanel panel, int displayWidth, int displayHeight){
        int buttonWidth = 90;
        int buttonHeight = 30;
        JButton openButton=new JButton("Открыть");
        openButton.setBounds(0,displayHeight,buttonWidth,buttonHeight);
        openButton.addActionListener(e -> {
            this.clear();
            this.openTableScreen(openArrayFromFile(getFilePath("Выберите файл для открытия")), 7);
        });

        JButton moveRightButton=new JButton("<-");
        moveRightButton.setBounds(displayWidth/2-buttonWidth/2-50,displayHeight,buttonWidth,buttonHeight);
        moveRightButton.addActionListener(e -> {
            List<Integer> arrayNow = this.getArrayNow();
            List<Integer> result = MatrixCalculator.createNewList(arrayNow, 1);
            this.clear();
            this.openTableScreen(result, 7);
        });

        JButton moveLeftButton=new JButton("->");
        moveLeftButton.setBounds(displayWidth/2-buttonWidth/2+50,displayHeight,buttonWidth,buttonHeight);
        moveLeftButton.addActionListener(e -> {
            List<Integer> arrayNow = this.getArrayNow();
            List<Integer> result = MatrixCalculator.createNewList(arrayNow, -1);
            this.clear();
            this.openTableScreen(result, 7);
        });

        JButton addButton=new JButton("добавить");
        addButton.setBounds(displayWidth/2-buttonWidth/2+170,displayHeight,buttonWidth,buttonHeight);
        addButton.addActionListener(add);

        JButton saveButton=new JButton("сохранить");
        saveButton.setBounds(displayWidth-buttonWidth,displayHeight,buttonWidth,buttonHeight);
        saveButton.addActionListener(e -> {
            System.out.println("нажато сохранить");
            List<Integer> arrayNow = this.getArrayNow();
            saveArrayToFile(arrayNow, getFilePath("Выберите файл для сохранения"));

            this.clear();
            this.openTableScreen(this.getArrayNow(), 7);

        });

        panel.add(openButton);
        panel.add(moveRightButton);
        panel.add(moveLeftButton);
        panel.add(saveButton);
        panel.add(addButton);
    }

    public static void showError(String title, String msg)
    {
        JFrame frame = new JFrame();

        // show a JOptionPane dialog using showMessageDialog
        JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.ERROR_MESSAGE);
    }


    private static JTable setTable(List<Integer> inputArray, int tableWidth){
        EditableTableModel model = new EditableTableModel(inputArray, tableWidth);
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
            if(value != null) setText(value.toString());
            else setText("-");
            setFont(font);
            setHorizontalAlignment(SwingConstants.RIGHT);

            return this;
        }
    }


    private static class EditableTableModel extends AbstractTableModel {

        List<Integer> dataEntries;
        int tableWidth;

        public boolean isCellEditable = true;

        public EditableTableModel(List<Integer> dataEntries, int tableWidth) {
            this.dataEntries = dataEntries;
            this.tableWidth = tableWidth;
        }

        public int getRowCount() {
            return getTableHeight(dataEntries.size(), tableWidth);
        }

        public int getColumnCount() {
            return tableWidth;
        }

        public Object getValueAt(int row, int column) {
            int pos = getPos(row, column);
            if(pos >= dataEntries.size()) return "-";
            return dataEntries.get(row*tableWidth+column);
        }

        public String getColumnName(int column) {
            return "";
        }

        public Class<?> getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }

        public boolean isCellEditable(int row, int column) {
            int pos = getPos(row, column);
            return pos < dataEntries.size();
        }

        public void setValueAt(Object value, int row, int column) {
            int pos = getPos(row, column);
            if (value != null) dataEntries.set(pos, (Integer) value);
        }

        private int getPos(int row, int column){
            return row*tableWidth+column;
        }
    }

    private static int getTableHeight(int arrayLen, int tableWidth) {
        return (arrayLen-1) / tableWidth + 1;
    }

}