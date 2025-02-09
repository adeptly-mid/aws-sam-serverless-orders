
package com.orders.aws.lambda.apis;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.aws.lambda.apis.model.Order;

public class ReadOrdersLambda {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    try {
      if (request.getBody() == null || request.getBody().trim().isEmpty()) {
        return response.withStatusCode(400)
            .withBody("{\"error\": \"Request body is empty\"}");
      }
      Order order = new Order(123, "MBP", 100);
      String jsonOutput = objectMapper.writeValueAsString(order);
      return response.withStatusCode(200).withBody(jsonOutput);
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
