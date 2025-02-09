package com.orders.aws.lambda.apis;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.aws.lambda.apis.model.Order;

/**
 * CreateOrderLamda
 */
public class CreateOrderLamda {

  public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request)
      throws JsonMappingException, JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Order order = objectMapper.readValue(request.getBody(), Order.class);
    return new APIGatewayProxyResponseEvent().withStatusCode(200)
        .withBody("Order Created for Order ID" + order.getId());
  }
}
