package eventlist.etl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonFileLoader {

    private static final Type EVENT_LIST_TYPE = new TypeToken<List<EventRecord>>() {
    }.getType();

    public List<EventRecord> Load(String filename) {

        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(filename));
            List<EventRecord> data = gson.fromJson(reader, EVENT_LIST_TYPE);
            return data;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
