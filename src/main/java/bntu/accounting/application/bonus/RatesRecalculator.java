package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.services.AllowancesService;
import bntu.accounting.application.services.BonusService;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class RatesRecalculator {

    private BonusService bonusService = new BonusService();
    private AllowancesService allowancesService = new AllowancesService();
    private RatingOptions options;
    private Double[] basisAllowances;
    private Double fund;

    public RatesRecalculator(RatingOptions options) {
        this.options = options;
        this.basisAllowances = new Double[]{options.getDefaultThirdRate(), options.getDefaultSecondRate(), options.getDefaultFirstRate()};
    }

    public Result recalculateBonusRates(Double eps,Double balance, List<Employee> employees) {
        // Нужно будет добавить в параметры, но пока оставлю так
        Double delta = 0.05d;

        Result result = new Result();
        result.setFund(fund);
        result.setBalance(0d);
        result.setFirstRate(options.getDefaultFirstRate());
        result.setSecondRate(options.getDefaultSecondRate());
        result.setThirdRate(options.getDefaultThirdRate());

        fund = bonusService.findBonusFund(employees);
        // Уменьшение ставок надбавки за 1 и 2 степени качества работы
        basisAllowances[1] -= delta;
        basisAllowances[2] -= delta;
        result.setFirstRate(basisAllowances[2]);
        result.setSecondRate(basisAllowances[1]);
        // Определение остатка после распределения по новому тарифу
        fund = bonusService.findBalance(employees, result.getFirstRate(), result.getSecondRate(), result.getThirdRate());
        // Уменьшение шага изменения величины ставок вдвое
        delta /= 2;
        // Флаг для определения дефицита или профицита средств
        boolean isPositive = fund > 0;
        // Продолжать процесс пересчёта тарифа пока не будет достигнута необходимая точность
        while (Math.abs(fund) > eps) {
            // Если профицит средств, то необходимо поднять одну из ставок
            if (fund > 0) {
                /** Если флаг отрицательный, значит остаток из состояния дефицита перешел в состояние профицита
                 * Если знак поменялся, значит шаг оказался слишком велик и его нужно уменьшить вдвое.
                 * */
                if (!isPositive) {
                    delta /= 2;
                }
                /** В первую очередь проверяем возможно ли поднять ставку 2 степени
                 * Должно соблюдаться условие, что ставка за 2 степень как минимум в 1.5 раз меньше ставки за 1 степень.
                 * */
                if (basisAllowances[2] + delta <= 1.5 * basisAllowances[1]) {
                    basisAllowances[2] += delta;
                }
                // Если условие не соблюдается, то поднимается ставка за 1 степень качества работы на величину delta
                else {
                    basisAllowances[1] += delta;
                }
                // Переключение флага для пометки, что остаток положительный
                isPositive = true;
            }
            // Если дефицит средств, то необходимо уменьшить одну из ставок
            if (fund < 0) {
                /** Если флаг положительный, значит остаток из состояния профицита перешел в состояние дефицита
                 * Если знак поменялся, значит шаг оказался слишком велик и его нужно уменьшить вдвое.
                 * */
                if (isPositive) {
                    delta /= 2;
                }
                /** В первую очередь проверяем возможно ли уменьшить ставку 1 степени
                 * Должно соблюдаться условие, что ставка за 1 степень как минимум в 1.5 раз больше ставки за 2 степень.
                 * */
                if (basisAllowances[1] - delta >= basisAllowances[2] / 1.5) {
                    basisAllowances[1] -= delta;
                }
                // Если условие не соблюдается, то уменьшается ставка за 2 степень качества работы на величину delta
                else {
                    basisAllowances[2] -= delta;
                }
                // Переключение флага для пометки, что остаток отрицательный
                isPositive = false;
            }
            result.setFirstRate(basisAllowances[2]);
            result.setSecondRate(basisAllowances[1]);
            result.setThirdRate(basisAllowances[0]);
            // пересчёт по новому тарифу
            fund = bonusService.findBalance(employees,result.getFirstRate(),result.getSecondRate(),result.getThirdRate());
            if (basisAllowances[2] > options.getDefaultFirstRate()) break;

        }
        result.setBalance(0d);
        return result;
    }
}
