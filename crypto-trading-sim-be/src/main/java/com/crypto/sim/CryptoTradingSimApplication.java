package com.crypto.sim;

import com.crypto.sim.service.PriceService;
import com.crypto.sim.websocket.KrakenWebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class CryptoTradingSimApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(CryptoTradingSimApplication.class, args);
        var priceService = context.getBean(PriceService.class);

        try {
            KrakenWebSocketClient wsClient = new KrakenWebSocketClient(
                    new URI("wss://ws.kraken.com/v2"), priceService
            );
            wsClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
