package com.orders.aws.lambda.apis.model;

public class Order {
  private int id;
  private String itemName;
  private int quantity;

  public Order() {
  }

  public Order(int id, String itemName, int quantity) {
    this.id = id;
    this.itemName = itemName;
    this.quantity = quantity;
  }

  public int getId() {
    return id;
  }

  public String getItemName() {
    return itemName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
