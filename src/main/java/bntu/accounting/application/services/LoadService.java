package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.normalization.Normalizer;

public class LoadService {
    private LoadDAO loadDAO = new LoadDAOImpl();
    // расчёт общей нагрузки
    public Double findTotalHours(Load load){
        Double res = load.getAcademicHours() +
                load.getAdditionalHours() + load.getOrganizationHours();
        load.setTotalHours(res);
        return res;
    }
    public void updateLoad(Integer id,Load load){
        Normalizer.normalizeLoad(load);
        loadDAO.updateLoad(id,load);
        EmployeesInstance.getInstance().notifyObservers();
    }
}
