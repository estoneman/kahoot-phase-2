import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import org.json.simple.parser.*;

import java.util.*;

//Inspriation from: https://www.geeksforgeeks.org/parse-json-java/

//in order for these import statements to work I used the following linux command:
//export CLASSPATH=$CLASSPATH:/path/to/json-simple.jar/file

//DO NOT RUN THIS CLASS BY ITSELF, IT WILL FAIL TRYING TO OPEN JSON FILES

class ReadJSONFile {

    //time complexity of O(2n^2) (not 100% sure)
    public static HashMap<String, String> populateHashMap(HashMap<String, String> JSONHashMap) {

        try {
            //read json file into this class in order to parse
            //Object fileRead = new JSONParser().parse(new FileReader("/Users/Ethan/AndroidStudioProjects/COMP330/json/trueFalse.json"));
            Object fileRead = new JSONParser().parse(new FileReader("/Users/thomasdimonte/StudioProjects/COMP330/json/questions.json"));

            // typecasting obj to JSONObject in order to use JSONSimple library
            JSONObject jsonObject = (JSONObject) fileRead;

            System.out.println("fileRead created");

            //getting our  questions; in this case we stored the question and answers as array
            JSONArray trueFalseArray = (JSONArray) jsonObject.get("tf");
            JSONArray fillInBlankArray = (JSONArray) jsonObject.get("fb");

            //to iterate through objects in array of t/f and fill in the blank
            Iterator<?> tfIter = trueFalseArray.iterator();//true false iterator
            Iterator<?> fbIter = fillInBlankArray.iterator();//fill in the blank iterator

            //for separating questions and answers into their own index for easier parsing
            String[] questionAnswerArray;

            //iterates through the true false section of the json file
            while (tfIter.hasNext()) {
                questionAnswerArray = parseTFObject(tfIter.next());

                String question = questionAnswerArray[0].substring(5, questionAnswerArray[0].length() - 1);//gets rid of unneeded characters
                String answer = questionAnswerArray[1].substring(5, questionAnswerArray[1].length() - 1);//gets rid of unneeded characters

                JSONHashMap.put(question, answer);//populates the hash map with correct values
            }

            //iterates through fill in the blank section of the json file
            while (fbIter.hasNext()) {
                questionAnswerArray = parseTFObject(fbIter.next());

                String question = questionAnswerArray[0].substring(5, questionAnswerArray[0].length() - 1);//gets rid of unneeded characters
                String answer = questionAnswerArray[1].substring(5, questionAnswerArray[1].length() - 1);//gets rid of unneeded characters

                JSONHashMap.put(question, answer);//populates the hash map with correct values
            }

        }

        //for the purpose of handling if the json file cannot be found mostly
        //however, other exceptions can be caught, such as NullPointerException
        catch(Exception e) {
            System.out.println("reached exception");
            System.out.println(e);
        }

        return JSONHashMap;//populated hash map with questions and answers of the true false section in json file

    }

    //time complexity of O(2n)
    public static String[] parseTFObject(Object obj) {
        String objString = obj.toString();//converts iter.next() which is of type Object to String
        objString = objString.substring(1, objString.length() - 1);//gets rid of leading and trailing braces in string
        String[] result = objString.split(",");//puts elements into their own index for easier parsing
        return result;
    }

//    public static void main(String[] args) {
//        HashMap<String, String> hm = new HashMap<String, String>();
//        hm = populateHashMap(hm);
//        System.out.println(hm);
//        System.out.println("reached end of main");
//    }

    //get key and value pair
//    public HashMap<String, String> getJSONHashMap() {
//        return JSONHashMap;
//    }


}
