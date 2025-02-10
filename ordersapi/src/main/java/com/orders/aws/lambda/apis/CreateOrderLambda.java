package com.orders.aws.lambda.apis;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.aws.lambda.apis.model.Order;
import java.util.HashMap;
import java.util.Map;

public class CreateOrderLambda {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());

  public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    try {
      if (request.getBody() == null || request.getBody().trim().isEmpty()) {
        return response.withStatusCode(400)
            .withBody("{\"error\": \"Request body is empty\"}");
      }

      Order order = objectMapper.readValue(request.getBody(), Order.class);

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("message", "Order Created Successfully");
      responseMap.put("orderId", order.getId());
      String responseBody = objectMapper.writeValueAsString(responseMap);

      Table table = dynamoDB.getTable(System.getenv("ORDERS_TABLE"));
      Item item = new Item().withPrimaryKey("id", order.getId()).withString("itemName", order.getItemName())
          .withInt("quantity", order.getQuantity());
      table.putItem(item);
      return response.withStatusCode(200).withBody(responseBody);

    } catch (JsonMappingException e) {

      return response.withStatusCode(400)
          .withBody("{\"error\": \"Invalid order format: " + e.getMessage() + "\"}");

    } catch (JsonProcessingException e) {

      return response.withStatusCode(500)
          .withBody("{\"error\": \"Error processing JSON: " + e.getMessage() + "\"}");

    } catch (Exception e) {

      return response.withStatusCode(500)
          .withBody("{\"error\": \"Internal server error: " + e.getMessage() + "\"}");

    }
  }
}
