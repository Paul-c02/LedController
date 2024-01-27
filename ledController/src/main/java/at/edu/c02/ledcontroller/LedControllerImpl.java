package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This class handles the actual logic
 */
public class LedControllerImpl implements LedController {
    private final ApiService apiService;

    public LedControllerImpl(ApiService apiService)
    {
        this.apiService = apiService;
    }

    @Override
    public void demo() throws IOException
    {
        // Call `getLights`, the response is a json object in the form `{ "lights": [ { ... }, { ... } ] }`
        JSONObject response = apiService.getLights();
        // get the "lights" array from the response
        JSONArray lights = response.getJSONArray("lights");
        // read the first json object of the lights array
        JSONObject firstLight = lights.getJSONObject(0);
        // read int and string properties of the light
        System.out.println("First light id is: " + firstLight.getInt("id"));
        System.out.println("First light color is: " + firstLight.getString("color"));
    }

    public JSONArray getGroupLeds() throws IOException {

        JSONObject response = apiService.getLights();
        JSONArray lights = response.getJSONArray("lights");

        JSONArray result = new JSONArray();

        for (int i = 0; i < lights.length(); i++) {

            JSONObject temp = lights.getJSONObject(i);
            if(temp.getJSONObject("groupByGroup").getString("name").equals("C")){
                result.put(temp);
                System.out.println(temp);
            }
        }


        return result;
    }

    @Override
    public void turnOffAllLeds() throws IOException {
        Integer[] group = {20, 21, 22, 23, 24, 25, 26, 27};
        for (int i = 0; i < group.length; i++) {
            this.apiService.setLight(group[i].toString(), "#f00", false);
        }
    }


    @Override
    public void outputGroupStatus() throws IOException {
        JSONArray lights = getGroupLeds();
        for (int i = 0; i < lights.length(); i++) {
            JSONObject light = lights.getJSONObject(i);
            System.out.println("LED " + light.getInt("id") + " is currently " + (light.getBoolean("on") ? "on" : "off") + ". Color: " + light.getString("color"));
        }
    }

    @Override
    public void outputStatus(String id) throws IOException {
        JSONObject light = apiService.getLight(id);
        System.out.println("LED " + light.getInt("id") + " is currently " + (light.getBoolean("on") ? "on" : "off") + ". Color: " + light.getString("color"));
    }

    public void doCircleLight() throws Exception {
        // switch off all lights
        turnOffAllLeds();

        JSONArray lights = getGroupLeds();
        for (int i = 0; i < lights.length(); i++) {
            JSONObject light = lights.getJSONObject(i);
            apiService.setLight(light.getInt("id")+"", "#f00", true);
            // wait 1s
            Thread.sleep(1000);
            apiService.setLight(light.getInt("id")+"", "#f00", false);
        }
    }

}
