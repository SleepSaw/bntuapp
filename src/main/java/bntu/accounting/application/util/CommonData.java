package bntu.accounting.application.util;

import bntu.accounting.application.iojson.FileLoader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Optional;

public class CommonData {
    private static final FileLoader fileLoader = new FileLoader();
    private JSONObject optionsJsonObject;
    // Имя файла с опциями
    private static final String optionsFileName = "options.json";

    private final String allowancesFileName = "allowances.json";

    // Получение базовой ставки
    public static Double getBaseRate() {
        try {
            JSONObject jsonObject = fileLoader.loadJsonFile(optionsFileName);
            return jsonObject.getDouble("base_rate");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
