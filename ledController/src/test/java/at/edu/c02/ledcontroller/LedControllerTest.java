package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


public class LedControllerTest {
    /**
     * This test is just here to check if tests get executed. Feel free to delete it when adding your own tests.
     * Take a look at the stack calculator tests again if you are unsure where to start.
     */
    private JSONObject readJsonFile (String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        return new JSONObject(br.readLine());
    }
    @Test
    public void dummyTest() {
        assertEquals(1, 1);
    }

    @Test
    public void testGetGroupLeds() throws IOException {
       ApiService apiService = mock(ApiService.class);
       LedController ledController = new LedControllerImpl(apiService);

       when(apiService.getLights()).thenReturn(readJsonFile("src/test/resources/getLights.json"));
        JSONObject groupObject = readJsonFile("src/test/resources/groupByGroup.json");
        JSONArray group = groupObject.getJSONArray("lights");
       assertEquals(ledController.getGroupLeds().length(),group.length());
       assertEquals(ledController.getGroupLeds().get(0).toString(),group.get(0).toString());

    }

    @Test
    public void testDoCircleLight() throws Exception {
        // Arrange
        ApiService apiService = Mockito.mock(ApiService.class);
        LedController ledController = new LedControllerImpl(apiService);
        JSONObject light = new JSONObject();
        light.put("id", 1);
        JSONArray lights = new JSONArray();
        lights.put(light);
        when(apiService.getLights()).thenReturn(readJsonFile("src/test/resources/getLights.json"));

        ledController.doCircleLight();

        Mockito.verify(apiService, times(1)).setLight("20", "#f00", true);
        Mockito.verify(apiService, times(2)).setLight("20", "#f00", false);
    }

}
