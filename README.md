# Orders API

A serverless REST API built with AWS SAM and Java that handles order management using Lambda functions and DynamoDB.

## Project Structure

```
ordersapi/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── orders/
│                   └── aws/
│                       └── lambda/
│                           └── apis/
│                               ├── model/
│                               │   └── Order.java
│                               ├── CreateOrderLambda.java
│                               └── ReadOrdersLambda.java
├── events/
│   └── event.json
├── target/
├── pom.xml
├── template.yaml
├── samconfig.toml
└── README.md
```

## Prerequisites

- AWS CLI installed and configured
- AWS SAM CLI installed
- Java 17 or later
- Maven
- An AWS account with appropriate permissions

## Local Development

### Build the Project

```bash
mvn clean package
```

### Run Local DynamoDB

```bash
docker run -p 8000:8000 amazon/dynamodb-local
```

### Start Local API

```bash
sam local start-api
```

### Run Tests

```bash
mvn test
```

## Deployment Commands

### Build the Application

```bash
sam build
```

### Deploy for First Time

```bash
sam deploy --guided
```

### Deploy Changes

```bash
sam deploy
```

### Delete Stack

```bash
sam delete
```

## Important SAM CLI Commands

### Validate Template

```bash
sam validate
```

### Generate Event Payloads

```bash
# Generate sample API Gateway event
sam local generate-event apigateway http-api-proxy > events/event.json
```

### Invoke Functions Locally

```bash
# Test specific function with event
sam local invoke "CreateOrderFunction" -e events/event.json

# Test with environment variables
sam local invoke -n env.json "CreateOrderFunction"
```

### View Logs

```bash
sam logs -n CreateOrderFunction --stack-name ordersapi --tail
```

## API Endpoints

- POST /orders - Create a new order
- GET /orders - List all orders

## License

This project is licensed under the MIT License - see the LICENSE file for details.
