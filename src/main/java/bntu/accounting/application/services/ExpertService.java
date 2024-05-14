package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.ExpertDAOImpl;
import bntu.accounting.application.dao.interfaces.ExpertDAO;
import bntu.accounting.application.models.fordb.Expert;

import java.util.List;

public class ExpertService {
    private ExpertDAO expertDAO = new ExpertDAOImpl();
    public List<Expert> getAllExperts(){
        return expertDAO.getAllExperts();
    }
}
