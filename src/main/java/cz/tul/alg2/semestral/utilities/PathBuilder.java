package cz.tul.alg2.semestral.utilities;

import java.io.File;

/**
The PathBuilder class provides a static method to join an array of strings into a file system path
using the system-specific path separator.
*/
public class PathBuilder {
    /**
     * Joins an array of strings into a file system path using the system-specific path separator.
     *
     * @param partsOfPath an array of strings representing the parts of the path
     * @return a string representing the joined path
     */
    public static String joinPath(String[] partsOfPath) {
        StringBuilder sb = new StringBuilder();
        String pathSeparator = File.separator;
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
