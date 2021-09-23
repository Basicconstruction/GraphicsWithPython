package boostup;

import tacklefile.PythonFileWriter;
import tacklefile.Kind;

public class Main {
    public Main(){
        int[][] h = {{1,2},{2,4},{3,6},{4,8}};
        PythonFileWriter jf = new PythonFileWriter(h, Kind.Line);
    }
}
