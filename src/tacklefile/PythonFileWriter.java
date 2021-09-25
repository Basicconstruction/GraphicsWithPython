package tacklefile;

import java.io.*;

public class PythonFileWriter {
    public StringBuilder bufferedData = new StringBuilder();
    public PythonFileWriter(){

    }
    public PythonFileWriter(double[][] array, int kind){
        switch(kind){
            case Kind.Line->{
                bufferedData = lineGraphics(this.bufferedData,array);
            }
        }

    }

    public File getFile() {
        return WriteCompliedCode(bufferedData);
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
    public void run(){
        try{
            Process pc = Runtime.getRuntime().exec(new String[]{"cmd","/c","python "+getFile().getAbsolutePath()},
                    null,new File("D://GraphicsWithPython//"));
            InputStreamReader isr = new InputStreamReader(pc.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String s;
            while((s=bf.readLine())!=null){
                sb.append(s).append("\n");
            }
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finished");
    }
    public StringBuilder lineGraphics(StringBuilder bufferedData,double[][] array){
        bufferedData.append("import matplotlib.pyplot as plt\n");
        bufferedData.append("x = [");
        for(double [] x:array){
            bufferedData.append(x[0]+",");
        }
        bufferedData.append("]\n");
        bufferedData.append("y = [");
        for(double [] y:array){
            bufferedData.append(y[1]+",");
        }
        bufferedData.append("]\n");
        bufferedData.append("plt.plot(x,y)\n");
        bufferedData.append("plt.show()\nprint(12)\n");
        return bufferedData;
    }

    public File generate_python_file(){
        if (!new File("D://GraphicsWithPython").exists()){
            new File("D://GraphicsWithPython").mkdirs();
        }
        File file = new File("D://GraphicsWithPython//"+"Graphics.py");
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
