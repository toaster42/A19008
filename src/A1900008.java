import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Michael P. Troester
 * @version 1.01 - 10/16/2015
 * @studentid 5061001
 * @email michaelp.troester@gmail.com
 * @assignment.number A1900008
 * @screenprint <a href='A1900008.gif'>ScreenPrint</a>
 * @sampleoutput <a href='../data/UnitedStates.txt'>SampleOutput</a>
 * @prgm.usage Called directly from OS
 * @see <a href='http://jcouture.net/cisc190/A19008.php' target='_blank'>Program Specification</a>
 * @see <br><a href='http://docs.oracle.com/javase/8/docs/technotes/guides/Javadoc/index.html' target='_blank'>Javadoc
 * Documentation</a>
 */
public class A1900008 {

    static String strStationsIndexFile = "data/World.txt";
    static String strOutputFile = "data/UnitedStates.txt";

    /**
     * The purpose of this method is to provide an entry point into this program.  It utilizes the INET class in order
     * to download the "Station Information Text File keyed by index number" file if it does not already exist.  It then
     * processes the file record by record, selecting the stations which start with "K" and outputing the records for
     * those station's IDs, names, states, latitudes and longitudes into a tab-delimeted file called UnitedStates.txt.
     *
     * @param args A placeholder for any arguments which may be passed to the program.  Does nothing
     * @throws Exception A catch all for IOException and MalformedURLException
     */
    public static void main(String[] args) throws Exception {

        INET net = new INET();
        if (! net.fileExists(strStationsIndexFile)) {
            //System.out.println("File does not exist.");         //For testing
            String strWorldIndex = net.getURLRaw("http://weather.noaa.gov/data/nsd_bbsss.txt");
            net.saveToFile(strStationsIndexFile, strWorldIndex);
            net.getRegEx(strWorldIndex, ";");
        }
        else
            System.out.println("File exists.");

        String strFileContents = net.getFromFile(strStationsIndexFile);
        StringTokenizer st = new StringTokenizer(strFileContents, ";", true);
        try (PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter(strOutputFile, false)))) {
            int intDelimeterCounter = 0;
            String[] staFields = new String[13];
            while (st.hasMoreTokens()) {
                String strToken = st.nextToken();

                if (strToken.equals(";")) {                         //If we come across a delimeter
                    intDelimeterCounter++;
                } else {                                            //otherwise, it's a field, add to array
                    staFields[intDelimeterCounter] = strToken;
                }

                if (intDelimeterCounter == 13) {                    //If we've reached the last delimeter.
                    if (staFields[2].substring(0, 1).equals("K")) { //If StationID field begins with "K"
                        outputFile.print(staFields[2] + "\t");
                        //System.out.print(staFields[2] + "\t");  //testing

                        outputFile.print(staFields[3] + "\t");
                        //System.out.print(staFields[3] + "\t");  //testing

                        outputFile.print(staFields[4] + "\t");
                        //System.out.print(staFields[4] + "\t");  //testing

                        outputFile.print(staFields[7] + "\t");
                        //System.out.print(staFields[7] + "\t");  //testing

                        outputFile.print(staFields[8] + "\r\n");
                        //System.out.print(staFields[8] + "\r\n");    //testing
                    }
                    intDelimeterCounter = 0;
                }
            }

        outputFile.close();
    } catch (IOException e) {
        System.out.println("Error IOException" + e.getMessage());
    }

        System.exit(0);
    }
}



