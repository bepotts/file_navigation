import java.io.IOException;

/**
 *
 *  Class controls the processing of the files
 *
 * @author Brandon Potts
 * @version August 29, 2015
 */
public class Processing {

    // File that contains where the results will be posted
    private FileOutput outputFile;
    // File that contains the GIS Records
    private FileNavigator gISRecordFile;
    // File that contains commands
    private FileNavigator commandFile;


    /***
     * Class constructor
     * @param gisRecordPath path to the GIS record
     * @param commandFilePath path to the command record
     */
    public Processing(String gisRecordPath , String commandFilePath){

        gISRecordFile = new FileNavigator(gisRecordPath);
        commandFile = new FileNavigator(commandFilePath);
        outputFile = new FileOutput("Results.txt");
    }

    /***
     * Processes all the files in the appropriate order
     */
    public void processFiles(){

        begin();

        // Process all the record locations
        processRecordLocations();

        // Processes the command file
        processCommands();

        closeFiles();


    }

    /***
     * Closes all the files
     */
    private void closeFiles(){
        gISRecordFile.closeFile();
        commandFile.closeFile();
        outputFile.closeFile();
    }

    /***
     * Processes the command file
     */
    private void processCommands(){

        commandFile.setFinalOffset(gISRecordFile.getFinalOffset());
        commandFile.setFirstRecordOffset(gISRecordFile.getFirstRecordOffset());
        String commandLine = commandFile.getNextCommand();
        String response = "";
        long commandOffset = -1;

        // Keep running until all the commands have been processed
        while (commandLine != null){

            String[] pieces = commandLine.split("\\t");
            // Checks to see if their is an offset
            if(pieces.length > 1){
                commandOffset = Long.parseLong(pieces[1]);
            }
            // Decides which action to take
            switch(pieces[0]) {

                case "show_name":
                    response = gISRecordFile.commandShowName(commandOffset);
                    break;
                case "show_latitude":
                    response = gISRecordFile.commandShowLatitude(commandOffset);
                    break;
                case "show_longitude":
                    response = gISRecordFile.commandShowLongitude(commandOffset);
                    break;
                case "show_elevation":
                    response = gISRecordFile.commandShowElevation(commandOffset);
                    break;
                case "quit":
                    response = "Exiting";
                    break;
                default:
                    // Do Nothing
                    break;
            }

            outputFile.printCommandResponse(commandLine , response);
            commandLine = commandFile.getNextCommand();
        }
    }

    /***
     * Processes all the locations of Records
     */
    private void processRecordLocations(){

        try {
            RecordReporter recordReport = gISRecordFile.processNextRecordLocation();
            // Keeps running until all the records have been processed
            while(recordReport != null){
               outputFile.printRecordReporter(recordReport);
                recordReport = gISRecordFile.processNextRecordLocation();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputFile.printNewLine();
    }

    /***
     * Prints the header information for the Results.txt file
     */
    private void begin(){
        outputFile.printHeader();
    }
}
