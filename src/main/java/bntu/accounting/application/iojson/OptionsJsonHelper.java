package bntu.accounting.application.iojson;

import bntu.accounting.application.models.serializable.SalaryOptions;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class OptionsJsonHelper {
    private final String filePath = "src/main/resources/jsons/main.json";
    private final File jsonFile = new File(filePath);
    private final ObjectMapper mapper = new ObjectMapper();

    public void writeToJson(SalaryOptions allowance){
        try {
            mapper.writeValue(jsonFile,allowance);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public SalaryOptions readFromJson(){
        try {
            return mapper.readValue(jsonFile, SalaryOptions.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
