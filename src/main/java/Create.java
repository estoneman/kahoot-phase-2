import java.sql.*;

class Create {

    private String dBName, format, tableName, questionType, question, answer;
    private String[] options;

    //for building a quiz object
    Create(String dBName, String format, String tableName, String questionType, String question, String[] options, String answer) {
        this.dBName = dBName;
        this.format = format;
        this.tableName = tableName;
        this.questionType = questionType;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    //for building a poll object
    Create(String dBName, String format, String tableName, String question, String[] options) {
        this.dBName = dBName;
        this.format = format;
        this.tableName = tableName;
        this.question = question;
        this.options = options;
    }

    //generic method to create a quiz or a poll depending on which constructor was chosen
    void create() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        //set to variables so that retrieval only occurs one time and not multiple
        String dBName = getDBName();
        String tableName = getTableName();
        String format = getFormat();
        String questionType = getQuestionType();
        String question = getQuestion();
        String[] options = getOptions();
        String answer = getAnswer();

        String sqlInstructions;
        StringBuilder stringBuilder = new StringBuilder();

        try {

            if (!Check.dBExists(dBName))
                SQLInstructions.createDatabase(dBName);

            for (String option : options)
                stringBuilder.append(option).append(":");

            String optionsString = stringBuilder.toString();
            optionsString = optionsString.substring(0, optionsString.length() - 1);

            //now the database can be connected to once a valid database name is chosen
            connection = SQLInstructions.connectToDB(dBName);

            //if user wants to create a quiz
            if (format.toLowerCase().trim().equals("quiz")) {
                if (!Check.tableExists(tableName, dBName))
                    SQLInstructions.createQuizQuestionsTable(tableName, dBName);

                sqlInstructions = "INSERT INTO " + dBName + "." + tableName +
                        " (QuestionType, Question, `Options`, Answer)" +
                        " VALUES (?, ?, ?, ?);";

                preparedStatement = connection.prepareStatement(sqlInstructions);

                preparedStatement.setString(1, questionType);
                preparedStatement.setString(2, question);
                preparedStatement.setString(3, optionsString);
                preparedStatement.setString(4, answer);

                preparedStatement.executeUpdate();

            }

            //if user wants to create a poll
            else {
                if (!Check.tableExists(tableName, dBName))
                    SQLInstructions.createPollQuestionsTable(tableName, dBName);

                sqlInstructions = "INSERT INTO " + dBName + "." + tableName +
                        " (Question, Options)" +
                        " VALUES (" + question + ", " + optionsString + ");";

                preparedStatement = connection.prepareStatement(sqlInstructions);

                preparedStatement.executeUpdate();
            }

        }
        catch (SQLException sQLE) {
            sQLE.printStackTrace();
        }
        //wiping any data buffer
        finally {
            try {
                if (connection != null)
                    connection.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            }
            catch (SQLException sQLE) {
                sQLE.printStackTrace();
            }
        }

    }

    String getDBName() {
        return this.dBName;
    }

    String getFormat() {
        return this.format;
    }

    String getTableName() {
        return this.tableName;
    }

    String getQuestionType() {
        return this.questionType;
    }

    String getQuestion() {
        return this.question;
    }

    String[] getOptions() {
        return this.options;
    }

    String getAnswer() {
        return this.answer;
    }

}