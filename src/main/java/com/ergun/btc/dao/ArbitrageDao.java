package com.ergun.btc.dao;

import com.ergun.btc.model.Arbitrage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by yahyaergun on 03/07/2017.
 */
public interface ArbitrageDao extends MongoRepository<Arbitrage, String> {
}
