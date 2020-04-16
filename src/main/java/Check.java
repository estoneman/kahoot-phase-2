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
    static boolean tableExists(String tableName) throws SQLException {

        Connection connection = SQLInstructions.connectToPollDB();

        ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);

        while (resultSet.next()) {
            String duplicateTableName = resultSet.getString(tableName);
            if (duplicateTableName != null && duplicateTableName.equals(tableName))
                return true;
        }

        return false;
    }

}
