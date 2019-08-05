package org.weili.json.parse_json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class App {
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		if (args.length != 2) {
			System.err.println("Wrong usage: pass in mappings url and output directory name");
		}
		
		String mappingsURL = args[0];
		String outputDirectory = args[1];
		
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		
		//Map<String, Object> jsonMap = objectMapper.readValue(new URL("http://localhost:8080/__admin/mappings"), new TypeReference<Map<String,Object>>(){});
		Map<String, Object> jsonMap = objectMapper.readValue(new URL(mappingsURL), new TypeReference<Map<String,Object>>(){});
		
		Object mappings = jsonMap.get("mappings");
		String mappingsString = objectMapper.writeValueAsString(mappings);
		List<Object> list  = objectMapper.readValue(mappingsString, new TypeReference<List<Object>>(){});
		
		for (Object o :  list) {
			String content = objectMapper.writeValueAsString(o);
		    Map<String, Object> temp = objectMapper.readValue(content, new TypeReference<Map<String,Object>>(){});
		    String id = (String)temp.get("id");
        	String name = (String)temp.get("name");
        	//FileWriter writer = new FileWriter("C:\\Users\\Wei\\Desktop\\wm-mappings\\mappings\\" + name + "-" + id + ".json");
        	FileWriter writer = new FileWriter(outputDirectory + name + "-" + id + ".json");
        	writer.write(content);
        	writer.close();
		}
		
	}

	public static void main1(String[] args) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Reader reader = new FileReader("C:\\Users\\Wei\\Desktop\\wm-mappings\\mappings.json")) {
		
            JsonElement json = gson.fromJson(reader, JsonElement.class);
            
            //String jsonInString = gson.toJson(json);
            //System.out.println(jsonInString);
            
            Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
            // map.forEach((x, y) -> System.out.println("key : " + x + " , value : " + y));

            Object mappings = map.get("mappings");
            //System.out.println(gson.toJson(mappings));
            
            json = gson.fromJson(gson.toJson(mappings), JsonElement.class);
            
            List<Object> list = gson.fromJson(json, new TypeToken<List<Object>>(){}.getType());
            
            for(Object o : list) {
                json = gson.fromJson(gson.toJson(o), JsonElement.class);
                //String temp = json.toString();
            	//System.out.println(temp);

            	Map<String, Object> map1 = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());

            	String id = (String)map1.get("id");
            	String name = (String)map1.get("name");
                //System.out.println(json);
            	//gson.toJson(temp, new FileWriter("C:\\Users\\Wei\\Desktop\\wm-mappings\\mappings\\" + name + "-" + id + ".json"));
            	FileWriter writer = new FileWriter("C:\\Users\\Wei\\Desktop\\wm-mappings\\mappings\\" + name + "-" + id + ".json");
            	writer.write(gson.toJson(json));
            	writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
