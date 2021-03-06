package org.knowm.xchange.poloniex;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.service.polling.PoloniexAccountService;
import org.knowm.xchange.poloniex.service.polling.PoloniexMarketDataService;
import org.knowm.xchange.poloniex.service.polling.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.poloniex.service.polling.PoloniexTradeService;
import org.knowm.xchange.utils.nonce.TimestampIncrementingNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Zach Holmes
 */

public class PoloniexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new PoloniexMarketDataService(this);
    this.pollingAccountService = new PoloniexAccountService(this);
    this.pollingTradeService = new PoloniexTradeService(this);
  }

  @Override
  public void remoteInit() throws IOException {

    PoloniexMarketDataServiceRaw poloniexMarketDataServiceRaw = (PoloniexMarketDataServiceRaw) pollingMarketDataService;

    Map<String, PoloniexCurrencyInfo> poloniexCurrencyInfoMap = poloniexMarketDataServiceRaw.getPoloniexCurrencyInfo();
    Map<String, PoloniexMarketData> poloniexMarketDataMap = poloniexMarketDataServiceRaw.getAllPoloniexTickers();

    metaData = PoloniexAdapters.adaptToExchangeMetaData(poloniexCurrencyInfoMap, poloniexMarketDataMap, metaData);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://poloniex.com/");
    exchangeSpecification.setHost("poloniex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Poloniex");
    exchangeSpecification.setExchangeDescription("Poloniex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
