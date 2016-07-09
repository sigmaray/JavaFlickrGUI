
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

class JsonDecodeDemo {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;

            System.out.println("The 2nd element of array");
            System.out.println(array.get(1));
            System.out.println();

            JSONObject obj2 = (JSONObject) array.get(1);
            System.out.println("Field \"1\"");
            System.out.println(obj2.get("1"));

            s = "{}";
            obj = parser.parse(s);
            System.out.println(obj);

            s = "[5,]";
            obj = parser.parse(s);
            System.out.println(obj);

            s = "[5,,2]";
            obj = parser.parse(s);
            System.out.println(obj);
        } catch (ParseException pe) {

            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }

        try {
            System.out.println(fileGetContents("http://m.tut.by"));
        } catch (Exception e) {}
    }

//    public static String file_get_contents(String urll) throws IOException {
//        URL url = new URL("http://m.tut.by");
//        InputStreamReader in = new InputStreamReader(url.openStream());
//        StringWriter out = new StringWriter();
//        // TODO: copy all contents from in to out
////       String content = out.toString();
//        return out.toString();
//    }

    public static StringBuffer fileGetContents(String url) {
        String encode = "utf8";
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream is = new URL(url).openStream();
            InputStreamReader isr = new InputStreamReader(is, encode);
            BufferedReader in = new BufferedReader(isr);
            String s = null;
            while ((s = in.readLine()) != null) {
                buffer.append(s).append("\n");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            buffer = null;
        } finally {
            return buffer;
        }
    }
}
