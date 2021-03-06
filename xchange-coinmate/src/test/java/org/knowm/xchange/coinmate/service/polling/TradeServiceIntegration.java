package org.knowm.xchange.coinmate.service.polling;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.ExchangeUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

/**
 * Integration tests for TradeService. For these tests to function, a file 'exchangeConfiguration.json' must be on the classpath and contain valid api
 * and secret keys.
 */
public class TradeServiceIntegration {

  @Test
  public void transactionHistoryTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);
    UserTrades trades = service.getTradeHistory(service.createTradeHistoryParams());
    assertNotNull(trades);
    System.out.println("Got " + trades.getUserTrades().size() + " trades.");
    for (Trade trade : trades.getTrades()) {
      System.out.println(trade.toString());
    }
  }

  @Test
  public void openOrdersTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);
    OpenOrders orders = service.getOpenOrders();
    assertNotNull(orders);
    System.out.println("Got " + orders.getOpenOrders().size() + " orders.");
    for (LimitOrder order : orders.getOpenOrders()) {
      System.out.println(order.toString());
    }
  }

  /*
   * @Test public void marketBuyTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); String id = service.placeMarketOrder(new MarketOrder(Order.OrderType.BID, new
   * BigDecimal("0.1"), CurrencyPair.BTC_EUR)); System.out.println("Market buy order id = " + id); }
   */

  @Test
  public void marketSellTest() throws Exception {
    Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration();
    if (exchange == null) {
      return; // forces pass if not configuration is available
    }
    assertNotNull(exchange);
    PollingTradeService service = exchange.getPollingTradeService();
    assertNotNull(service);
    service.placeMarketOrder(new MarketOrder(Order.OrderType.ASK, new BigDecimal("0.001"), CurrencyPair.BTC_EUR));
  }

  /*
   * @Test public void limitBuyTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); String id = service.placeLimitOrder(new LimitOrder(Order.OrderType.BID, new
   * BigDecimal("1.0"), CurrencyPair.BTC_EUR, null, null, new BigDecimal("1.0"))); System.out.println("Limit buy order id = " + id); }
   */

  /*
   * @Test public void limitSellTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); String id = service.placeLimitOrder(new LimitOrder(Order.OrderType.ASK, new
   * BigDecimal("0.0"), CurrencyPair.BTC_EUR, null, null, new BigDecimal("10000.0"))); System.out.println("Limit sell order id = " + id); }
   */

  /*
   * @Test public void cancelOrderTest() throws Exception { Exchange exchange = ExchangeUtils.createExchangeFromJsonConfiguration(); if (exchange ==
   * null) { return; // forces pass if not configuration is available } assertNotNull(exchange); PollingTradeService service =
   * exchange.getPollingTradeService(); assertNotNull(service); service.cancelOrder("784360"); }
   */

}
