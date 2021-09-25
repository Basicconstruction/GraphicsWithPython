package tacklefile;

import staticmode.Kind;

import java.io.*;

public class PythonFileWriter {
    public static String tmp_filepath = "D://GraphicsWithPython//temp.py";
    private File tmp_file;
    public StringBuilder bufferedData = new StringBuilder();
    public PythonFileWriter(){

    }
    public PythonFileWriter(double[][] array, int kind){
        this.tmp_file = validate();
        switch(kind){
            case Kind.Line->{
                bufferedData = lineGraphics(this.bufferedData,array);
                WriteCompliedCode(bufferedData);
            }
        }

    }


    public File getFile() {
        return this.tmp_file;
    }
    public void WriteCompliedCode(StringBuilder bufferedData){
        try{
            FileWriter writer = new FileWriter(this.tmp_file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(bufferedData.toString());
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**额外线程，避免调用的python进程阻塞主进程*/
    public void run(){
        PythonFileRunner pfr = new PythonFileRunner(this.tmp_file);
//        Thread staticmode.thread = new Thread(() -> {
//            try{
//                Process pc = Runtime.getRuntime().exec(new String[]{"cmd","/c","python "+getFile().getAbsolutePath()},
//                        null,new File(this.tmp_file.getParent()));
//                InputStreamReader isr = new InputStreamReader(pc.getInputStream());
//                BufferedReader bf = new BufferedReader(isr);
//                StringBuilder sb = new StringBuilder();
//                String s;
//                while((s=bf.readLine())!=null){
//                    sb.append(s).append("\n");
//                }
//                System.out.println(sb);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("finished");
//        });
//        staticmode.thread.start();
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

    public String convertPath(String s){
        String[] list = s.split("\\\\");
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < list.length-1;i++){
            sb.append(list[i]).append("\\\\");
        }
        sb.append(list[list.length-1]);
        return sb.toString();
    }
    public static File validate(){
        boolean d_disk_exists = new File("D://").exists();
        if(d_disk_exists){
            File dirt = new File("D://GraphicsWithPython2//");
            boolean dirt_exists = dirt.exists();
            if(!dirt_exists){
                dirt.mkdirs();
            }
            File py_file = new File(tmp_filepath);
            if(!py_file.exists()){
                try{
                    py_file.createNewFile();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            return py_file;
        }else{
            File dirt = new File("C://GraphicsWithPython2//");
            boolean dirt_exists = dirt.exists();
            if(!dirt_exists){
                dirt.mkdirs();
            }
            File py_file = new File("C://GraphicsWithPython2//temp.py");
            if(!py_file.exists()){
                try{
                    py_file.createNewFile();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            return py_file;
        }

    }
}
