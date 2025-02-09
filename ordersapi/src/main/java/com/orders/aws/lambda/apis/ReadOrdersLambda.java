
package com.orders.aws.lambda.apis;

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
    Order order = new Order(123, "mbp", 100);
    String jsonOutput = objectMapper.writeValueAsString(order);
    return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
  }
}
