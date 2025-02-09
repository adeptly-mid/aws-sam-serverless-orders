
package com.orders.aws.lambda.apis;

import java.util.List;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.aws.lambda.apis.model.Order;

public class ReadOrdersLambda {
  public APIGatewayProxyResponseEvent readOrders(APIGatewayProxyRequestEvent request)
      throws JsonProcessingException, JsonMappingException {
    ObjectMapper objectMapper = new ObjectMapper();

    AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("ORDERS_TABLE")));
    List<Order> orders = scanResult.getItems().stream()
        .map(item -> new Order(
            Integer.parseInt(item.get("id").getN()),
            item.get("itemName").getS(),
            Integer.parseInt(item.get("quantity").getN())))
        .toList();
    String jsonOutput = objectMapper.writeValueAsString(orders);
    return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
  }
}
