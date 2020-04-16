import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Check {

    private static Scanner keyboard = new Scanner(System.in);

    static boolean isValidNonNumericalInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty");
            return false;
        }
        //returns true when isNumeric() returns false and vice versa
        if (isNumeric(input)) {
            System.out.println("Input cannot be a number");
            return false;
        }

        return true;

    }

    //checks validity of filename given by user
    static boolean isValidFile(File file) {
        if (hasPeriod(file)) {
            System.out.println("Please remove '.' : '.json' will be automatically added for you.");
            return false;
        }
        if (exists(file)) {
            System.out.println(file.getName() + " already exists. Please choose a different file name.");
            return false;
        }
        return true;
    }

    //checks if given input string is numerical
    static boolean isNumeric(String s) {
        if (s == null)
            return false;
        try {
            int parsedInt = Integer.parseInt(s);
        }
        catch (NumberFormatException nFE) {
            return false;
        }
        return true;
    }

    //checks if user entered in a '.'
    private static boolean hasPeriod(File file) {
        return file.getName().contains(".");
    }

    //checks if file exists in relative directory
    private static boolean exists(File file) {
        return new File(file.getName() + ".json").exists() || file.exists();
    }

    //validates the creation of desired filename
    static File makeFile() {
        System.out.println("*-------------------------------------------*");
        System.out.println("What would you like to name the file?: ");
        System.out.println("*-------------------------------------------*");
        String filename = keyboard.nextLine();

        File file = new File(filename);//file to be written

        while (!isValidFile(file)) {
            filename = keyboard.nextLine();
            file = new File(filename);
        }

        return new File(filename + ".json");

    }

    //https://stackoverflow.com/questions/2942788/check-if-table-exists
    static boolean tableExists(String tableName, String dBName) {

        Connection connection = null;

        try {
            connection = SQLInstructions.connectToPollDB(dBName);

            ResultSet resultSet = connection.getMetaData().getTables(dBName, null, tableName, null);

            if (resultSet.next()) {
                return true;
            }

            resultSet.close();
        }
        catch (SQLException sQLE) {
            sQLE.printStackTrace();
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException sQLE) {
                sQLE.printStackTrace();
            }
        }
        return false;

    }

    static boolean dBExists(String dBName) {

        Connection connection = null;

        try {
            //connects to the database to check if the database exists
            connection = SQLInstructions.connectToSQL();

            //returns all of the database names inside of result set
            ResultSet resultSet = connection.getMetaData().getCatalogs();

            //loops through each database to see if it already exists
            while (resultSet.next()) {
                String duplicateDB = resultSet.getString(1);
                //if it is found, then close result set and return true
                if (duplicateDB.equals(dBName)) {
                    resultSet.close();
                    return true;
                }
            }

        }
        catch (SQLException sQLE) {
            sQLE.printStackTrace();
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException sQLE) {
                sQLE.printStackTrace();
            }
        }
        return false;
    }

}
