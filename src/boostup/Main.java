package boostup;

import tacklefile.Kind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main extends JFrame {
    private final JLabel dataLabel = new JLabel("temporary data");
    private final JTextArea dataArea = new JTextArea();
    private final JLabel pythonLabel = new JLabel(".py");
    private final JTextArea pythonArea = new JTextArea();
    private final JScrollPane pythonPane = new JScrollPane(pythonArea);
    private final JPanel rootPane = new JPanel(null);

    private final JLabel splitLabel = new JLabel("select split value",JLabel.CENTER);
    private final JComboBox<String> splitComboBox = new JComboBox<>(new MyComboBox("space",new String[]{"space","comma"}));
    private final JLabel splitPreview = new JLabel("\" \"",JLabel.CENTER);
    private String splitValue = " ";

    private final JLabel drawLabel = new JLabel("Select drawing type",JLabel.CENTER);
    /** 直方图，饼图，折线图，柱形图  Histogram, Pie Chart, Line Chart, Column Chart*/
    private final JComboBox<String> drawComboBox = new JComboBox<>(new MyComboBox("Line",new String[]{"Line","Column","Pie","Histogram"}));
    private final JLabel drawPreview = new JLabel("Line Chart",JLabel.CENTER);
    private int selectedKind = Kind.Line;
    private final int width = 1400;
    private final int height = 800;
    private final int dataAreaWidth = 400;
    private final int dataLabelHeight = 30;
    private final int splitLabelWidth = 120;
    private final int splitLabelHeight = 40;
    private final int splitComboBoxWidth = 100;
    private final int splitPreviewWidth = 80;

    private final int drawLabelWidth = 120;
    private final int drawComboBoxWidth = 100;
    private final int drawLabelHeight = 40;
    private final int drawPreViewWidth = 80;
    private final int drawStartX = dataAreaWidth + drawLabelWidth + drawComboBoxWidth;
    private final int drawStartY = dataLabelHeight + splitLabelHeight;

    public Main(){
        super();
        setTitle("Graphics and Analysis");
        setSize(width,height);
        setPreferredSize(new Dimension(width,height));
        setLocation(300,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenuBar();
        rootPane.setBackground(Color.pink);
        setContentPane(rootPane);
        dataLabel.setBounds(0,0,100,dataLabelHeight);
        dataArea.setBounds(0,30,dataAreaWidth,600);
        pythonLabel.setBounds(700,0,100,dataLabelHeight);
        pythonPane.setBounds(700,30,700,600);
        getContentPane().add(dataLabel);
        getContentPane().add(dataArea);
        getContentPane().add(pythonLabel);
        getContentPane().add(pythonPane);

        splitLabel.setBounds(dataAreaWidth,dataLabelHeight,splitLabelWidth,splitLabelHeight);
        splitComboBox.setBounds(dataAreaWidth+splitLabelWidth,dataLabelHeight,splitComboBoxWidth,splitLabelHeight);
        splitPreview.setBounds(dataAreaWidth+splitLabelWidth+splitComboBoxWidth,dataLabelHeight, splitPreviewWidth,splitLabelHeight);
        getContentPane().add(splitLabel);
        getContentPane().add(splitComboBox);
        getContentPane().add(splitPreview);
        addItemListenerForSelectSplitComboBox();

        drawLabel.setBounds(dataAreaWidth,dataLabelHeight+splitLabelHeight,drawLabelWidth,drawLabelHeight);
        drawComboBox.setBounds(dataAreaWidth+drawLabelWidth,dataLabelHeight+splitLabelHeight,drawComboBoxWidth,drawLabelHeight);
        drawPreview.setBounds(drawStartX,drawStartY,drawPreViewWidth,drawLabelHeight);
        getContentPane().add(drawLabel);
        getContentPane().add(drawComboBox);
        getContentPane().add(drawPreview);
        addItemListenerForSelectChartComboBox();

    }
    private void addItemListenerForSelectSplitComboBox(){
        this.splitComboBox.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                String item = e.getItem().toString();
                if(stateChange==ItemEvent.SELECTED){
                    switch(item){
                        case "space"->{
                            splitValue = " ";
                            splitPreview.setText("\" \"");
                        }
                        case "comma"->{
                            splitValue = ",";
                            splitPreview.setText("\",\"");
                        }
                    }
                }
            }
        });
    }
    private void addItemListenerForSelectChartComboBox(){
        this.drawComboBox.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                String item = e.getItem().toString();
                if(stateChange==ItemEvent.SELECTED){
                    switch(item){
                        case "Line"->{
                            selectedKind = Kind.Line;
                            drawPreview.setText("Line Chart");
                        }
                        case "Column"->{
                            selectedKind = Kind.Column;
                            drawPreview.setText("Column Chart");
                        }
                        case "Pie"->{
                            selectedKind = Kind.Pie;
                            drawPreview.setText("Pie Chart");
                        }
                        case "Histogram"->{
                            selectedKind = Kind.Histogram;
                            drawPreview.setText("Histogram");
                        }
                    }
                }
            }
        });
    }
    private void addMenuBar(){
        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);
        JMenu jm = new JMenu("菜单");
        jmb.add(jm);
        JMenuItem jmi = new JMenuItem("退出");
        jm.add(jmi);

    }
    public void auto_size(){
        int ref_width = this.getWidth();
        int ref_height = this.getHeight();
        System.out.println(this.getInsets().left+" "+this.getInsets().right+" "+this.getInsets().top+" "+this.getInsets().bottom);

    }
    public void printThisSize(){
        System.out.println(this.getWidth()+"\n"+this.getHeight()+"\n");
    }
    public void printContentPaneSize(){
        System.out.println(getContentPane().getWidth()+"\n"+getContentPane().getHeight()+"\n");
    }
    class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String>{
        String selectedItem;
        String[] items;
        public MyComboBox(String selectedItem,String[] items){
            this.selectedItem = selectedItem;
            this.items = items;
        }
        @Override
        public void setSelectedItem(Object anItem) {
            selectedItem = (String)anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selectedItem;
        }

        @Override
        public int getSize() {
            return items.length;
        }

        @Override
        public String getElementAt(int index) {
            return items[index];
        }
    }
}
//    int[][] h = {{1,2},{2,4},{3,6},{4,8}};
//    PythonFileWriter jf = new PythonFileWriter(h, Kind.Line);