package boostup;

import datacollector.EasySplit;
import datacollector.SpaceFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal1 {
    public static void main(String[] args) throws IOException {
        System.out.println(SpaceFilter.getFilterText("12     34"));
        String s = "12  34";
        for(String d:new EasySplit(" ").getSplits(s)){
            System.out.println(d);
        }
//        Process process = Runtime.getRuntime()
//                .exec("cmd /c dir", null, new File("D://"));
//        printResults(process);
//        Process process2 = Runtime.getRuntime().exec(
//                new String[]{"cmd", "/c", "python D://GraphicsWithPython//py4.py"},
//                null,
//                new File("H://"));
//
//        printResults(process2);
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.command("cmd", "/c", "python D://GraphicsWithPython//py4.py");
//
//        Process process = processBuilder.start();
//        printResults(process);
    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

}
