package bntu.accounting.application.iojson;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class ReportJsonHelper {
    private final String filePath = "src\\main\\resources\\jsons\\report_data.json";
    private final File jsonFile = new File(filePath);
    private final ObjectMapper mapper = new ObjectMapper();

    public void writeToJson(ReportData reportData){
        try {
            mapper.writeValue(jsonFile,reportData);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ReportData readFromJson(){
        try {
            ReportData reportData = mapper.readValue(jsonFile, ReportData.class);
            return reportData;
        }
        catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        }catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
