package com.xeiam.xchange.bitcoinium.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoinium.BitcoiniumAdapters;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.service.marketdata.BitcoiniumDepthJSONTest;
import com.xeiam.xchange.bitcoinium.service.marketdata.BitcoiniumTickerJSONTest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * Tests the BitcoiniumAdapter class
 */
public class BitcoiniumAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumOrderbook bitcoiniumDepth = mapper.readValue(is, BitcoiniumOrderbook.class);

    OrderBook orderBook = BitcoiniumAdapters.adaptOrderbook(bitcoiniumDepth, CurrencyPair.BTC_USD);

    // Verify all fields filled
    assertThat(orderBook.getAsks().get(0).getLimitPrice().doubleValue()).isEqualTo(132.79);
    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(orderBook.getAsks().get(0).getTradableAmount().doubleValue()).isEqualTo(45.98);
    assertThat(orderBook.getAsks().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTicker BitcoiniumTicker = mapper.readValue(is, BitcoiniumTicker.class);

    Ticker ticker = BitcoiniumAdapters.adaptTicker(BitcoiniumTicker, CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("914.88696");
    assertThat(ticker.getLow().toString()).isEqualTo("848.479");
    assertThat(ticker.getHigh().toString()).isEqualTo("932.38");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("13425"));

  }
}
