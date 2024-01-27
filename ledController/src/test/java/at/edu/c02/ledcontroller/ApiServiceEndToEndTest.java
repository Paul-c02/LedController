package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiServiceEndToEndTest {

    private ApiService apiService;
    private LedController ledController;

    public ApiServiceEndToEndTest() {
        this.apiService = new ApiServiceImpl();
        this.ledController = new LedControllerImpl(this.apiService);
    }

    protected int getFirstLightIDOfGroup() throws Exception {
        JSONArray array = this.ledController.getGroupLeds();
        JSONObject light = array.getJSONObject(0);
        return light.getInt("id");
    }

    @Test
    public void testSetAndGetLight() throws Exception {
        // Set the light
        String id = getFirstLightIDOfGroup()+"";
        String color = "#f00";
        boolean state = true;
        this.apiService.setLight(id, color, state);

        System.out.println("Set light " + id + " to color " + color + " and state " + state);

        // Get the light
        JSONObject light = this.apiService.getLight(id);

        // Check if the light has the correct color and state
        Assert.assertEquals(color, light.getString("color"));
        Assert.assertEquals(state, light.getBoolean("on"));
    }
    @Test
    public void testMockAPIAllOff() throws IOException {
        ApiService apiService = mock(ApiService.class);
        LedController ledController = new LedControllerImpl(apiService);
        ledController.turnOffAllLeds();

    }
}
