package tacklefile;

import java.io.*;

public class PythonFileWriter {
    public static int counter = 0;
    public static String default_counter_file = "counter.txt";
    public StringBuilder bufferedData = new StringBuilder();
    public PythonFileWriter(){

    }
    public <T> PythonFileWriter(T[][] array, int kind){
        switch(kind){
            case Kind.Line->{
                bufferedData = lineGraphics(bufferedData,array);
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
        counter++;
        WriteCounter();
        System.out.println("finished");
    }
    public <T> StringBuilder lineGraphics(StringBuilder bufferedData,T[][] array){
        bufferedData.append("import matplotlib.pyplot as plt\n");
        bufferedData.append("x = [");
        for(T [] x:array){
            bufferedData.append(x[0]+",");
        }
        bufferedData.append("]\n");
        bufferedData.append("y = [");
        for(T [] y:array){
            bufferedData.append(y[1]+",");
        }
        bufferedData.append("]\n");
        bufferedData.append("plt.plot(x,y)\n");
        bufferedData.append("plt.show()\nprint(12)\n");
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
