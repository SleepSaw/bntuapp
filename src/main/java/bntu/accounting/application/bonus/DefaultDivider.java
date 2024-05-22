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

   public Result divideFund(List<Employee> employees){
      Result result = new Result();
      result.setFirstRate(options.getDefaultFirstRate());
      result.setSecondRate(options.getDefaultSecondRate());
      result.setThirdRate(options.getDefaultThirdRate());
      double balance = bonusService.findBalance(employees,
              result.getFirstRate(), result.getSecondRate(), result.getThirdRate());
      result.setBalance(balance);
      return result;
   }
}
