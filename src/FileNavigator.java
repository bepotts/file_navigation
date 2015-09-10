import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Object handles the seeking across the file
 *
 * @author Brandon Potts
 * @version August 29, 2015
 */
public class FileNavigator {


    private String lineTerminator;
    private RandomAccessFile currentFile;

    /***
     * Class constructor
     * @param fileName is the name of the file that will be seeked through
     */
    public FileNavigator(String fileName){

        File file = new File(fileName);
        try {
            currentFile = new RandomAccessFile(file , "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lineTerminator = System.getProperty("line.separator");
    }


    /***
     * Processes the next line of Records
     * @return RecordReporter of the next record
     *         null if the file has reached the end
     */
    public RecordReporter processNextRecordLocation() throws IOException {

        // Discards the header line of the text file
        if(currentFile.getFilePointer() == 0 ){
            currentFile.readLine();
        }

        long offset = currentFile.getFilePointer();
        String currentLine = currentFile.readLine();

        // Checks to see if the end has been reached
        if(currentLine == null){

            resetFilePointer();
            return null;
        }

        LineParser parsedLine = new LineParser(currentLine);
        GISRecord gisRecord = parsedLine.buildGISRecord();

        return new RecordReporter(offset , gisRecord.getfId());
    }

    /***
     * Closes the current file
     */
    public void closeFile(){
        try {
            currentFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Returns the next command
     * @return String that contains the next command
     */
    public String getNextCommand(){

        String currentLine = null;

        try {
            currentLine = currentFile.readLine();

            // Checks to see if the end has been reached
            if(currentLine == null){
                return null;
            }
            while(currentLine.charAt(0) == ';'){
                currentLine = currentFile.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentLine;
    }

    /***
     * Executes the "Show Name" command
     * @param offset is the offset in the file
     * @return
     */
    public long  commandShowName(long offset){
        return -1;
    }

    /***
     *
     * @param offset
     * @return
     */
    public long commandShowLatitude(long offset){
        return -1;
    }

    /***
     *
     * @param offset
     * @return
     */
    public long commandShowLongitude(long offset){
        return -1;
    }

    /***
     *
     * @param offset
     * @return
     */
    public long commandShowElevation(long offset){
        return -1;
    }




    /***
     * Resets the location of the file pointer
     */
    private void resetFilePointer(){
        try {
            currentFile.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
