package boostup;

import datacollector.TextIterator;
import tacklefile.Kind;
import tacklefile.LineDataRevolver;
import tacklefile.Mode;
import tacklefile.PythonFileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main extends JFrame {
    private final JLabel dataLabel = new JLabel("temporary data");
    private final JTextArea dataArea = new JTextArea();
    private final JScrollPane dataPane = new JScrollPane(dataArea);
    private final JLabel pythonLabel = new JLabel(".py");
    private final JTextArea pythonArea = new JTextArea();
    private final JScrollPane pythonPane = new JScrollPane(pythonArea);
    private final JPanel rootPane = new JPanel(null);

    private final JLabel splitLabel = new JLabel("select split value",JLabel.CENTER);
    private final JComboBox<String> splitComboBox = new JComboBox<>(new MyComboBox("space", new String[]{"space", "comma"}));
    private final JLabel splitPreview = new JLabel("\" \"",JLabel.CENTER);
    private String splitValue = " ";

    private final JLabel drawLabel = new JLabel("Select drawing type",JLabel.CENTER);
    /** 直方图，饼图，折线图，柱形图  Histogram, Pie Chart, Line Chart, Column Chart*/
    private final JComboBox<String> drawComboBox = new JComboBox<>(new MyComboBox("Line", new String[]{"Line", "Column", "Pie", "Histogram"}));
    private final JLabel drawPreview = new JLabel("Line Chart",JLabel.CENTER);
    private int selectedKind = Kind.Line;

    private final JButton compileButton = new JButton("compile");
    private final JButton debug = new JButton("debug");

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
    private final int drawStartX = dataAreaWidth + drawLabelWidth + drawComboBoxWidth;//620
    private final int drawStartY = dataLabelHeight + splitLabelHeight;//110

    private final int debugX = 500;
    private final int debugY = 260;
    private final int compileX = 500;
    private final int compileY = 300;

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
        dataPane.setBounds(0,30,dataAreaWidth,600);
        dataArea.setFont(new Font("微软雅黑",Font.PLAIN,18));
        pythonLabel.setBounds(700,0,100,dataLabelHeight);
        pythonPane.setBounds(700,30,700,600);
        pythonArea.setFont(new Font("微软雅黑",Font.PLAIN,18));
        getContentPane().add(dataLabel);
        getContentPane().add(dataPane);
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

        debug.setBounds(debugX,debugY,100,40);
        compileButton.setBounds(compileX,compileY,100,40);
        getContentPane().add(debug);
        getContentPane().add(compileButton);
        debug.addActionListener(e->{
            dataAreaDataRevolver(Mode.debug);
        });
        compileButton.addActionListener(e->{
            dataAreaDataRevolver(Mode.compile);
        });

    }
    private void dataAreaDataRevolver(int mode){
        if(!dataArea.getText().equals("")){
            StringBuilder s = new StringBuilder().append(dataArea.getText());
            switch(selectedKind){
                case Kind.Line->{
                    LineDataRevolver ldr = new LineDataRevolver(s,this.splitValue);
                    double[][] transferred = ldr.getData();
                    PythonFileWriter pfw = new PythonFileWriter(transferred,this.selectedKind);
                    pythonArea.setText("");
                    Thread thread = new Thread(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pythonArea.setText(pfw.bufferedData.toString());
                        }
                    });
                    thread.start();
                    if(mode==Mode.debug){
                        pfw.run();
                    }
                }
                case Kind.Column->{

                }
                case Kind.Pie->{

                }
                case Kind.Histogram->{

                }
            }
        }else{
            System.out.println("you have not type in any char.");
        }

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
    static class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String>{
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
    public int convertToInt(String number) {
        assert(number.split("\\.").length<2);
        number = number.trim();
        int res = 0;
        for(char c:number.toCharArray()){
            res *= 10;
            res = res + (c-'0');
        }
        return res;
    }
    public double convertToDouble(String number){
        //Double.parseDouble("129.89")
        assert(number.split("\\.").length<3);
        double res = 0;
        if(number.split("\\.").length==1){
            return convertToInt(number);
        }else{
            int f = number.split("\\.")[0].length();
            for(int i = 0;i<f;i++){
                res *= 10;
                res += (number.charAt(i)-'0');
            }
            int power = 1;
            for(int j = f+1;j<number.length();j++){
                res += Math.pow(0.1,power) * (number.charAt(j)-'0');
                power ++;
            }
            return res;
        }
    }
}
//    int[][] h = {{1,2},{2,4},{3,6},{4,8}};
//    PythonFileWriter jf = new PythonFileWriter(h, Kind.Line);