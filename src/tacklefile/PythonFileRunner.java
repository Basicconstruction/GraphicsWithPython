package tacklefile;

import datacollector.TextCollector;
import staticmode.thread.ThreadPriorityHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PythonFileRunner {
    public PythonFileRunner(File file,String text){
        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(text);
            bfw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(()->{
            Runtime rt = Runtime.getRuntime();
            try{
                Process pc = rt.exec(new String[]{"cmd","/c","python "+file.getAbsolutePath()+" > temp.txt"},null,file.getParentFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ThreadPriorityHelper.threadCode = 1;
        });
        thread.start();

    }
    public PythonFileRunner(File file){
        Thread thread = new Thread(()->{
            Runtime rt = Runtime.getRuntime();
            try{
                Process pc = rt.exec(new String[]{"cmd","/c","python "+file.getAbsolutePath()+" > temp.txt"},null,file.getParentFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ThreadPriorityHelper.threadCode = 1;
        });
        thread.start();

    }
    public StringBuilder getResult(){
        StringBuilder sb = new StringBuilder();
        Thread thread = new Thread(()->{
            try {
                while(ThreadPriorityHelper.threadCode!=1){
                    Thread.sleep(100);
                }
                TextCollector tc = new TextCollector("D://GraphicsWithPython//temp.txt");
                sb.append(tc.getAsStringBuilder());
                ThreadPriorityHelper.threadCode = 2;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        thread.start();
        return sb;
    }
}
