//On my honor:
//        - I have not discussed the Java language code in my program with
//        anyone other than my instructor or the teaching assistants
//        assigned to this course.
//        - I have not used Java language code obtained from another student,
//        or any other unauthorized source, either modified or unmodified.
//        - If any Java language code or documentation used in my program
//        was obtained from another source, such as a text book or course
//        notes, that has been clearly noted with a proper citation in
//        the comments of my program.
//        - I have not designed this program in such a way as to defeat or
//        interfere with the normal operation of the Curator System.
//<Brandon Emerson Potts>

/**
 *
 * Main method class that starts the program
 *
 *
 * This program is designed to parse a Command File
 * and execute the commands on a GISData file, and
 * print the results in a Results file.
 *
 * 
 *
 * @author Brandon Potts
 * @version August 29, 2015
 */
public class Project1 {

    public static void main(String[] args) {

        // Gets the gis Record and command file paths
        String gisRecordPath = args[0];
        String commandFilePath = args[1];

        // Processes the command file
        Processing process = new Processing(gisRecordPath , commandFilePath);
        process.processFiles();

    }
}