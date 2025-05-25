package com.cc.sc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CcStore {

  // Order DB. It contains existing orders
  private static final Set<String> orderDb = new HashSet<>(){{
    add("order=001234567|2025-05-20|0 49000 01234 1:10,0 49000 04321 1:10");
    add("order=002345678|2025-05-21|0 49000 05678 2:20,0 49000 05432 2:20");
    add("order=003456789|2025-05-22|0 49000 09123 3:30,0 49000 07654 3:30");
  }};

  public static void main(String[] args) throws Exception {
    String json = new String(Files.readAllBytes(Paths.get("sample_order.json")));
    String parsedOrder = parseOrder(json);

    System.out.println("Order Received. order=" + parsedOrder);

    if (orderDb.contains(parsedOrder)) {
      System.out.println("Not processing. Duplicate Order. order=" + parsedOrder);
    } else {
      orderDb.add(parsedOrder);
      System.out.println("Processing order. order=" + parsedOrder);
    }
  }

  private static String parseOrder(String json) throws JsonProcessingException {
    JsonNode order = new ObjectMapper().readTree(json);
    String outletId = order.get("outletId").asText();

    String items = StreamSupport.stream(order.get("item").spliterator(), false)
        .map(i -> i.get("UPC_Code").asText() + ":" + i.get("qty").asText())
        .sorted()
        .collect(Collectors.joining(","));

    return outletId + "|" + LocalDate.now() + "|" + items;
  }
}
