import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class FileRender {

    private final String propertiseFile = System.getProperty("user.dir") + "/synchronizer.properties";
    private final String resourcePropertiseFile = this.getClass().getClassLoader()
            .getResource("sample/synchronizer.properties").getFile();
    protected ArrayList<String> syncTables = new ArrayList<String>();
    protected String sourceSchema = null;
    protected String targetSchema = null;
    protected String sourceDbHost = null;
    protected String targetDbHost = null;

    protected void ReadSyncTablePropertise() {

        String currentProperty = "empty";


        try {
            File file = new File(propertiseFile);
            if (!file.exists()) {

                Files.move(Paths.get(resourcePropertiseFile), Paths.get(propertiseFile), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new FileReader(propertiseFile))) {

            String sCurrentLine;
            System.out.println("\nReading synchronizer.properties file ....\n");

            while ((sCurrentLine = br.readLine()) != null) {

                if (!sCurrentLine.startsWith("#") && !sCurrentLine.equals("")) {


                    if (sCurrentLine.startsWith("@")) {

                        currentProperty = sCurrentLine.substring(2);
                    } else if (currentProperty.contentEquals("SOURCE_DB_HOST")) {

                        sourceDbHost = sCurrentLine;
                        System.out.println("SOURCE_DB_HOST : " + sourceDbHost);

                    } else if (currentProperty.contentEquals("SOURCE_DB_NAME")) {

                        sourceSchema = sCurrentLine;
                        System.out.println("SOURCE_DB_NAME : " + sourceSchema);

                    } else if (currentProperty.contentEquals("TARGET_DB_HOST")) {

                        targetDbHost = sCurrentLine;
                        System.out.println("TARGET_DB_HOST : " + targetDbHost);

                    } else if (currentProperty.contentEquals("TARGET_DB_NAME")) {


                        targetSchema = sCurrentLine;
                        System.out.println("TARGET_DB_NAME : " + targetSchema + "\n");

                    } else if (currentProperty.contentEquals("SYNC_ENABLED_TABLES")) {

                        syncTables.add(sCurrentLine);
                        System.out.println("SYNC_ENABLED_TABLES : " + sCurrentLine);

                    }

                }

            }

            System.out.println("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
