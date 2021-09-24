package datacollector;

public class SpaceFilter {
    public static String getFilterText(String text){
        boolean status = false;
        StringBuilder builder = new StringBuilder();
        for(char c : text.toCharArray()){
            if(c==' '){
                if(!status){
                    builder.append(" ");
                }
                status = true;
            }else{
                builder.append(c);
                status = false;
            }
        }
        return builder.toString();
    }
    public static String getFilterText(StringBuilder text){
        return getFilterText(text.toString());
    }
}
