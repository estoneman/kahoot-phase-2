//mykong.com/java/java-properties-file-examples/

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

class WriteToProperties {

    static Properties dBInformation = null;

    public static void main(String[] args) {

        try {
            OutputStream stream = new FileOutputStream(System.getProperty("user.dir") + "/src/main/java/database_config.properties");

            dBInformation = new Properties();

            //set values
            dBInformation.setProperty("mysqlurl", "jdbc:mysql://192.168.0.107:3306/");
            dBInformation.setProperty("jdbc.package", "com.mysql.cj.jdbc.Driver");
            dBInformation.setProperty("ethan.user", "MySQLAdmin");
            dBInformation.setProperty("ethan.password", "19Soccer99(");

            dBInformation.store(stream, null);

        }
        catch (IOException iOE) {
            iOE.printStackTrace();
        }

    }

}
