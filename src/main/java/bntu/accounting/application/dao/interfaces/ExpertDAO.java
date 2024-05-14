package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.fordb.Expert;

import java.util.List;

public interface ExpertDAO {
    List<Expert> getAllExperts();
    Expert findById(Integer id);
    void removeExpert(Expert expert);
    void updateExpert(Integer id, Expert updatedExpert);
    Integer saveExpert(Expert expert);
}
