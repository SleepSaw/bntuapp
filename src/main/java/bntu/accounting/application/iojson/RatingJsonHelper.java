package bntu.accounting.application.iojson;

import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.models.serializable.SalaryOptions;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class RatingJsonHelper {
    private final String filePath = "src/main/resources/jsons/rating_data.json";
    private final File jsonFile = new File(filePath);
    private final ObjectMapper mapper = new ObjectMapper();

    public void writeToJson(RatingOptions options) {
        try {
            mapper.writeValue(jsonFile, options);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RatingOptions readFromJson() {
        try {
            RatingOptions options = mapper.readValue(jsonFile, RatingOptions.class);
            options.setActualFirstRate(options.getDefaultFirstRate());
            options.setActualSecondRate(options.getDefaultSecondRate());
            options.setActualThirdRate(options.getDefaultThirdRate());
            return options;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
