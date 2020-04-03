import java.util.*;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;

public class ReadJSON {

    String[] questionAnswerArray;

    //hash maps to store question and answers as key, value pairs
    HashMap<String, String> tFHashMap;
    HashMap<String, String> mCHashMap;
    HashMap<String, String> mHashMap;
    HashMap<String, String> fBHashMap;

    //general JSONArray for accessing objects within json files
    JSONArray jsonArray;

    //general iterator to iterate through JSONArray above
    Iterator<?> iter;

    //file to be read by methods
    Object fileRead;

    //general JSONObject to be cast to fileRead in order to use json-simple libraries
    JSONObject jsonObject;

//    public static void main(String[] args) {
//
//        try {
//            Object fileRead = new JSONParser().parse(new FileReader(new CreateQuiz().getTRUE_FALSE_PATH()));//reads file into program
//
//            JSONObject jsonObject = (JSONObject) fileRead;//casts to type that the imported json-simple library can handle
//
//            System.out.println(new ReadJSON().readTFObject(jsonObject));//main executable to read into a hashmap (output is a hash map)
//
//        }
//
//        catch(Exception e) {
//            System.out.println(e.toString());
//        }
//
//    }

    //reads the true false json file into a hash map
    public HashMap<String, String> readTFObject() {
        try {
            fileRead = new JSONParser().parse(new FileReader(new CreateQuiz().getTRUE_FALSE_PATH()));

            jsonObject = (JSONObject) fileRead;

            tFHashMap = new HashMap<String, String>();

            jsonArray = (JSONArray) jsonObject.get("tf");//array of true false question and answers
            iter = jsonArray.iterator();//iterate through array of objects

            String question = "";
            String answer = "";

            while (iter.hasNext()) {
                questionAnswerArray = parseJSONObject(iter.next());

                question = questionAnswerArray[0].substring(1, questionAnswerArray[0].length() - 1);//stores the question for each question and answer pair
                answer = questionAnswerArray[1].substring(1, questionAnswerArray[1].length() - 1);//    ""   ""   answer  ""   ""     ""    ""    ""    ""

                tFHashMap.put(question, answer);//populates hash map for true false questions and answers
            }

            return tFHashMap;
        }

        catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }


    }

    //reads multiple choice json file into hashmap
    public HashMap<String, String> readMCObject(JSONObject jsonObject) {
        try {
            fileRead = new JSONParser().parse(new FileReader(new CreateQuiz().getMULTIPLE_CHOICE_PATH()));

            jsonObject = (JSONObject) fileRead;

            mCHashMap = new HashMap<String, String>();

            jsonArray = (JSONArray) jsonObject.get("mc");
            iter = jsonArray.iterator();

            String question = "";
            String options = "";
            String answer = "";

            while (iter.hasNext()) {
                questionAnswerArray = parseJSONObject(iter.next());

                question = questionAnswerArray[0];
                options = questionAnswerArray[1];
                answer = questionAnswerArray[2];

                mCHashMap.put(question, answer);
            }

            return mCHashMap;
        }

        catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

    //reads fill in the blank json file into hash map
    public HashMap<String, String> readFBObject(JSONObject jsonObject) {
        try {

            fileRead = new JSONParser().parse(new FileReader(new CreateQuiz().getFILL_IN_BLANK_PATH()));

            jsonObject = (JSONObject) fileRead;

            fBHashMap = new HashMap<String, String>();

            jsonArray = (JSONArray) jsonObject.get("fb");
            iter = jsonArray.iterator();

            String question = "";
            String answer = "";

            while (iter.hasNext()) {
                questionAnswerArray = parseJSONObject(iter.next());

                question = questionAnswerArray[0];
                answer = questionAnswerArray[1];

                fBHashMap.put(question, answer);
            }

            return fBHashMap;
        }

        catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

    //reads matching json file into hash map
    public HashMap<String, String> readMObject(JSONObject jsonObject) {

        try {
            fileRead = new JSONParser().parse(new FileReader(new CreateQuiz().getFILL_IN_BLANK_PATH()));

            jsonObject = (JSONObject) fileRead;

            HashMap<String, String> mHashMap = new HashMap<String, String>();

            jsonArray = (JSONArray) jsonObject.get("m");
            iter = jsonArray.iterator();

            String term = "";
            String matchedTerm = "";

            while (iter.hasNext()) {
                questionAnswerArray = parseJSONObject(iter.next());

                term = questionAnswerArray[0];
                matchedTerm = questionAnswerArray[1];

                mHashMap.put(term, matchedTerm);
            }

            return mHashMap;
        }

        catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

    //allows for easier parsing by splitting the already formatted json file into separate indices
    //better for accessing questions and answers as their own entity
    public String[] parseJSONObject(Object obj) {
        String objString = obj.toString();
        objString = objString.substring(1, objString.length() - 1);//gets rid of braces at the start and end of the JSONObject
        String[] result = objString.split(":");
        return result;
    }

}