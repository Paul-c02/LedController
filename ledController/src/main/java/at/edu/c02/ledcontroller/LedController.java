package at.edu.c02.ledcontroller;

import org.json.JSONArray;

import java.io.IOException;

public interface LedController {
    void demo() throws IOException;
    JSONArray getGroupLeds() throws IOException;

    void turnOffAllLeds() throws IOException;

    void outputGroupStatus() throws IOException;

    void outputStatus(String id) throws IOException;

    void doCircleLight() throws Exception;
}
