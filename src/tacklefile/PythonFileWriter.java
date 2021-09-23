package tacklefile;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class PythonFileWriter {
    public static int counter = 0;
    public static String default_counter_file = "D://GraphicsWithPython//counter.txt";
    public static String julia_file = "D://GraphicsWithPython//juliaBoost.jl";
    public PythonFileWriter(){

    }
    public PythonFileWriter(int[][] array, int kind){
        StringBuilder bufferedData = new StringBuilder();
        switch(kind){
            case Kind.Line->{
                bufferedData = lineGraphics(bufferedData,array);
            }
        }
        File file = WriteCompliedCode(bufferedData);
        run(file);
        counter++;
        WriteCounter();
        System.out.println("finished");
    }
    public File WriteCompliedCode(StringBuilder bufferedData){
        File file = generate_python_file();
        try{
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(bufferedData.toString());
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    public void run(File file){
        String s =
                "cmd = `C://ProgramData//python//python.exe -u \""
                        + convertPath(file.getAbsolutePath())
                        + "\"`\nrun(cmd)\n";
        try{
            File file2 = new File(julia_file);
            if(!file2.exists()){
                file2.createNewFile();
            }
            FileWriter writer2 = new FileWriter(file2);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write(s);
            bw2.close();
            writer2.close();
            Desktop dek = Desktop.getDesktop();
            if(dek.isSupported(Desktop.Action.OPEN)){
                try {
                    dek.open(new File(julia_file));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public StringBuilder lineGraphics(StringBuilder bufferedData,int[][] array){
        bufferedData.append("import matplotlib.pyplot as plt\n");
        bufferedData.append("x = [");
        for(int[] x:array){
            bufferedData.append(x[0]+",");
        }
        bufferedData.append("]\n");
        bufferedData.append("y = [");
        for(int[] y:array){
            bufferedData.append(y[1]+",");
        }
        bufferedData.append("]\n");
        bufferedData.append("plt.plot(x,y)\n");
        bufferedData.append("plt.show()\n");
        return bufferedData;
    }
    public void WriteCounter(){
        File file = new File(default_counter_file);
        try{
            FileWriter fr = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(Integer.toString(counter));
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadCounter(){
        File file = new File(default_counter_file);
        if(!file.exists()){
            try{
                if (!new File("D://GraphicsWithPython").exists()){
                    new File("D://GraphicsWithPython").mkdirs();
                }
                file.createNewFile();
                FileWriter fr = new FileWriter(file);
                BufferedWriter br = new BufferedWriter(fr);
                br.write("1");
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String count = br.readLine();
                counter = convertToInt(count);
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int convertToInt(String count) {
        count = count.trim();
        int res = 0;
        for(char c:count.toCharArray()){
            res *= 10;
            res = res + (c-'0');
        }
        return res;
    }
    public File generate_python_file(){
        loadCounter();
        File file = new File("D://GraphicsWithPython//"+"py"+counter+".py");
        if(file.exists()){
            file.delete();
        }

        return file;
    }
    public String convertPath(String s){
        String[] list = s.split("\\\\");
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < list.length-1;i++){
            sb.append(list[i]).append("\\\\");
        }
        sb.append(list[list.length-1]);
        return sb.toString();
    }
}