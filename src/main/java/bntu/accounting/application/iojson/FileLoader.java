package bntu.accounting.application.iojson;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileLoader {
    public JSONObject loadJsonFile(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/jsons/" + fileName)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + fileName);
            }
            try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
                String jsonContent = scanner.hasNext() ? scanner.next() : "";
                return new JSONObject(jsonContent);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
