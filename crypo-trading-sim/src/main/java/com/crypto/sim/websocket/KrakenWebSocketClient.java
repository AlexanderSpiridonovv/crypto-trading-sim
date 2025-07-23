package com.crypto.sim.websocket;

import com.crypto.sim.model.TickerUpdate;
import com.crypto.sim.service.PriceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;


public class KrakenWebSocketClient extends WebSocketClient {

    private static final List<String> PAIRS = List.of(
            "BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "ADA/USD",
            "DOGE/USD", "AVAX/USD", "DOT/USD", "LINK/USD", "TRX/USD",
            "MATIC/USD", "BCH/USD", "LTC/USD", "UNI/USD", "ATOM/USD",
            "ETC/USD", "FIL/USD", "ICP/USD", "XLM/USD", "XMR/USD"
    );

    private final PriceService priceService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KrakenWebSocketClient(URI serverUri, PriceService priceService) {
        super(serverUri);
        this.priceService = priceService;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        StringBuilder pairs = new StringBuilder();
        for (String pair : PAIRS) {
            pairs.append("\"").append(pair).append("\",");
        }

        String payload = """
            {
              "method": "subscribe",
              "params": {
                "channel": "ticker",
                "symbol": [%s]
              },
              "req_id": 1
            }
            """.formatted(pairs.substring(0, pairs.length() - 1));

        send(payload);
        System.out.println("Connected to Kraken WebSocket and subscribed to ticker feed.");
    }

    @Override
    public void onMessage(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);

            if (root.has("channel") && root.get("channel").asText().equals("ticker")) {

                JsonNode data = root.get("data").get(0);

                String symbol = data.get("symbol").asText();
                String priceStr = data.get("last").asText();

                //for testing
                System.out.println(symbol + ':' + priceStr);

                double price = Double.parseDouble(priceStr);
                TickerUpdate update = new TickerUpdate(symbol, price);
                priceService.updatePrice(update);

            }
        } catch (Exception e) {
            System.err.println("Failed to parse WebSocket message: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}