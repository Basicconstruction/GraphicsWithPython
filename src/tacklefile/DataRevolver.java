package tacklefile;

import datacollector.EasySplit;
import datacollector.TextIterator;

public class DataRevolver {
    double[][] res;
    public DataRevolver(StringBuilder sb, String splits,int part){
        TextIterator ti = new TextIterator(sb);
        res = new double[ti.getDataList().size()][part];
        for(int i = 0; i < res.length;i++){
            String text = ti.getLine();
            EasySplit es = new EasySplit(splits);
            String[] got = es.getSplits(text);
            for(int j = 0;j < part;j++){
                res[i][j] = Double.parseDouble(got[j]);
            }
        }
    }
    public double[][] getData(){
        return this.res;
    }
}
