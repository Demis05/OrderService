package com.exadel.fedorov.dto;

import java.math.BigDecimal;

public class OrderPositionDTO {

//    private Product product;
    private BigDecimal totalPrice;
    private Integer count;
    public OrderPositionDTO(/*Product product,*/ Integer count) {
//    public OrderPositionDTO(Product product, Integer count) {
//        this.product = product;
        this.count = count;
        setTotalPrice();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    public Product getProduct() {
//        return product;
//    }

//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    private void setTotalPrice() {
//        this.totalPrice = BigDecimal.valueOf(product.getPrice() * count);
    }
}
