package saved;

import root.SFrame;

import javax.swing.*;
import java.awt.*;
/** deprecated */
public class Main extends SFrame {
    private JTextArea dataArea = new JTextArea();
    private JPanel rootPane = new JPanel(null);
    public Main(){
        super();
        setUndecorated(true);
        setTitle("Graphics and Analysis");
        setSize(1000,800);
        setPreferredSize(new Dimension(1000,800));
        setLocation(300,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rootPane.setSize(1000,800);
        rootPane.setBackground(new Color(0,0,0));
        setContentPane(rootPane);


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
}
//    int[][] h = {{1,2},{2,4},{3,6},{4,8}};
//    PythonFileWriter jf = new PythonFileWriter(h, Kind.Line);
