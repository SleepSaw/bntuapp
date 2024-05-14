package bntu.accounting.application.bonus;

import bntu.accounting.application.iojson.RatingJsonHelper;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Rating;
import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.services.BonusService;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class DefaultDivider {
   private BonusService bonusService = new BonusService();
   private RatingOptions options;

   public DefaultDivider(RatingOptions options) {
      this.options = options;
   }

   public Double divideFund(List<Employee> employees){
      double balance = bonusService.findBalance(employees,
              options.getDefaultFirstRate(), options.getDefaultSecondRate(), options.getDefaultThirdRate());
      return Normalizer.roundValue(balance);
   }
}
