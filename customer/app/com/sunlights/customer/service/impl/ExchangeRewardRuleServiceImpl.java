package com.sunlights.customer.service.impl;


import com.sunlights.customer.dal.ExchangeRewardRuleDao;
import com.sunlights.customer.dal.impl.ExchangeRewardRuleDaoImpl;
import com.sunlights.customer.service.ExchangeRewardRuleService;
import models.ExchangeRewardRule;

/**
 * Created by tangweiqun on 2014/11/19.
 */
public class ExchangeRewardRuleServiceImpl  implements ExchangeRewardRuleService {
    //TODO
    private ExchangeRewardRuleDao exchangeRewardRuleDao = new ExchangeRewardRuleDaoImpl();

    @Override
    public ExchangeRewardRule findByRewardType(String rewardType) {
        //TODO


        return exchangeRewardRuleDao.findByRewardType(rewardType);
    }
}