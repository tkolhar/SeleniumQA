package eventlist.etl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonStreamLoader {

    @SuppressWarnings("unused")
	private static final Type EVENT_LIST_TYPE = new TypeToken<List<EventRecord>>(){}.getType();
    private static final Logger logger = Logger.getLogger(JsonStreamLoader.class.getName());
    private JsonReader jsonReader = null;
    private Gson gson = null;

    public JsonStreamLoader(InputStream jsonInputStream) {

        gson = new Gson();
        
        try {
            jsonReader = new JsonReader(new InputStreamReader(jsonInputStream, "UTF-8"));
            jsonReader.setLenient(true);
            jsonReader.beginArray();
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
        
    public EventRecord ReadNext(){
        try {
            if(jsonReader.hasNext()){
                return gson.fromJson(jsonReader, EventRecord.class);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void Close(){
        if(jsonReader != null){
            try {
                jsonReader.close();
                jsonReader = null;
                gson = null;
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
