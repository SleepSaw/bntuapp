package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.Load;

import java.util.List;

public interface LoadDAO {
    List<Load> getAllLoads();
    Integer saveLoad(Load load);

    Load findLoadById(Integer id);

    void updateLoad(Integer id, Load updatedLoad);

    void removeLoad(Load load);

    void removeLoad(Integer id);

}
