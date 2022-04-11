import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

class Graphic{
    final JFrame frame;
    private ArrayList<Triangle> arrayNow;

    private String getFilePath(String title){
        File inputArrayFile = chooseFileDialog(title);
        if(inputArrayFile == null){
            System.err.println("Файл не выбран");
            return null;
            //System.exit(-1);
        }
        String inputFilePath = inputArrayFile.getPath();
        System.out.print("путь к входному файлу: ");
        System.out.println(inputFilePath);
        return  inputFilePath;
    }

    private static ArrayList<Triangle> openArrayFromFile(String inputFilePath){
        ArrayList<Triangle> input = null;

        try {
            input = ListReader.readFromFile(Paths.get(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return input;
    }

    private static void saveArrayToFile(ArrayList<Triangle> result, String outputFilePath){
        try {
            ListWriter.writeToFile(outputFilePath, result);
        } catch (IOException e){
            System.err.println("невозможно записать массив в выходной файл");
            System.exit(-1);
        }
    }


    public Graphic(){
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String filePath = getFilePath("Выберите файл для открытия");
        if(filePath != null) {
            openTableScreen(openArrayFromFile(filePath));
        }
        else{
            System.out.println("Файл не выбран");
            openTableScreen(new ArrayList<>());
        }
    }

    public static void showError(String title, String msg)
    {
        JFrame frame = new JFrame();

        // show a JOptionPane dialog using showMessageDialog
        JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.ERROR_MESSAGE);
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


    public ArrayList<Triangle> getArrayNow(){
        return arrayNow;
    }

    public void openTableScreen(ArrayList<Triangle> input){
        clear();
        arrayNow = input;

        int arrayHeight = input.size();
        int tableWidth = 700;
        int tableHeight = arrayHeight * 20+40;
        if( tableHeight > 600) tableHeight = 600;

        JTable table = setTable(input);
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
        arrayNow.add(new Triangle(new Point[]{new Point(0, 0), new Point(0, 0), new Point(0, 0)}));
        openTableScreen(arrayNow);
    };
    ActionListener remove = e -> {
        arrayNow.remove(arrayNow.size()-1);
        openTableScreen(arrayNow);
    };

    private void packButtonInPanel(JPanel panel, int displayWidth, int displayHeight){
        int buttonWidth = 90;
        int buttonHeight = 30;
        JButton openButton=new JButton("Открыть");
        openButton.setBounds(0,displayHeight,buttonWidth,buttonHeight);
        openButton.addActionListener(e -> {
            clear();
            String filePath = getFilePath("Выберите файл для открытия");
            if(filePath != null) {
                openTableScreen(openArrayFromFile(filePath));
            }
            else{
                System.out.println("Файл не выбран");
                openTableScreen(getArrayNow());
            }
        });

        JButton convertButton=new JButton("преобразовать");
        convertButton.setBounds(displayWidth/2-buttonWidth/2-50,displayHeight,buttonWidth,buttonHeight);
        convertButton.addActionListener(e -> {
            ArrayList<Triangle> result = new ArrayList<>();
            for (Triangle triangle : getArrayNow()) {
                System.out.println(triangle);
                System.out.println("лежит во всех: " + triangle.checkIfInAllCoordQuarter());
                if (triangle.checkIfInAllCoordQuarter()) result.add(triangle);
            }
            clear();
            openTableScreen(result);
        });

        JButton addButton=new JButton("добавить");
        addButton.setBounds(displayWidth/2-buttonWidth/2+50,displayHeight,buttonWidth,buttonHeight);
        addButton.addActionListener(add);

        JButton removeButton=new JButton("убрать");
        removeButton.setBounds(displayWidth/2-buttonWidth/2+150,displayHeight,buttonWidth,buttonHeight);
        removeButton.addActionListener(remove);

        JButton saveButton=new JButton("сохранить");
        saveButton.setBounds(displayWidth-buttonWidth,displayHeight,buttonWidth,buttonHeight);
        saveButton.addActionListener(e -> {
            String filePath = getFilePath("выбрать файл для сохранения");
            if(filePath != null) {
                saveArrayToFile(getArrayNow(), getFilePath("выбрать файл для сохранения"));
            }
            else{
                System.out.println("файл не найден");
            }
            clear();
            openTableScreen(getArrayNow());
        });

        panel.add(openButton);
        panel.add(convertButton);
        panel.add(saveButton);
        panel.add(addButton);
        panel.add(removeButton);
    }


    private static JTable setTable(ArrayList<Triangle> inputArray){
        TriangleTableModel model = new TriangleTableModel(inputArray, new String[]{"x1", "y1", "x2", "y2", "x3", "y3"});
        model.isCellEditable = true;

        JTable jt=new JTable(model);
        jt.setFocusable(false);
        jt.setRowSelectionAllowed(false);
        // show grid
        jt.setRowHeight(20);
        jt.setShowGrid(true);
        jt.setGridColor(Color.BLACK);
        jt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // set font
        Font tableFont = new Font("Times New Roman", Font.PLAIN, 14);
        // set the font in edit cell mode
        DefaultCellEditor editor = (DefaultCellEditor)jt.getDefaultEditor(Object.class);
        editor.getComponent().setFont(tableFont);

        // TODO: удалить этот костыль
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


    private static class TriangleTableModel extends AbstractTableModel {
        String[] columnTitles;

        ArrayList<Triangle> dataEntries;

        public boolean isCellEditable = true;

        public TriangleTableModel(ArrayList<Triangle> dataEntries, String[] columnTitles) {
            this.columnTitles = columnTitles;
            this.dataEntries = dataEntries;
        }

        public int getRowCount() {
            return dataEntries.size();
        }

        public int getColumnCount() {
            return columnTitles.length;
        }

        public Object getValueAt(int row, int column) {
            if(column % 2 == 0) return dataEntries.get(row).points[column/2].x;
            else return dataEntries.get(row).points[column/2].y;
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
            if (value == null) return;
            if(column % 2 == 0) dataEntries.get(row).points[column/2].x = Integer.parseInt((String) value);
            else dataEntries.get(row).points[column/2].y = Integer.parseInt((String) value);
        }
    }
}