package bntu.accounting.application.services;

import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.util.CommonData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AllowancesService {
    private static final FileLoader fileLoader = new FileLoader();
    private static final String fileName = "allowances.json";

    public Double getTariffByCategory(String key) {
        return getComplexAllowanceByKey("tariffs",key);
    }

    public Double getExpAllowance(String key){
        return getComplexAllowanceByKey("experience",key);
    }
    public Double getQualAllowance(String key){
        return getComplexAllowanceByKey("qualification",key);
    }
    public Double getWorkInIndustryAllowance(){
        return getSimpleAllowanceByKey("work_in_industry");
    }
    public Double getYoungSpecialistAllowance(){
        return getSimpleAllowanceByKey("young_specialist");
    }
    public Double getProfActivityAllowance(){
        return getSimpleAllowanceByKey("prof_activity");
    }

    private Double getSimpleAllowanceByKey(String key){
        try {
            JSONObject jsonObject = fileLoader.loadJsonFile(fileName);
            return jsonObject.getDouble(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Double getComplexAllowanceByKey(String containerName,String key){
        try {
            JSONObject jsonObject = fileLoader.loadJsonFile(fileName);
            JSONObject container = jsonObject.getJSONObject(containerName);
            return container.getDouble(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
