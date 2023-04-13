package cz.tul.alg2.semestral.utilities;


public class PathBuilder {
    public static String joinPath(String[] partsOfPath) {
        StringBuilder sb = new StringBuilder();
        String pathSeparator = System.getProperty("path.separator");
        for (String part: partsOfPath) {
            sb.append(part);
            sb.append(pathSeparator);
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length()-1);
        //sb.append()
        return sb.toString();
    }
}
