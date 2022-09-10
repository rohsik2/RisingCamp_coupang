package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    ORDER_CANCELING("A", "주문 취소요청중"),
    ORDER_CANCELED("B", "주문 취소됨"),

    ORDER_READY("C", "상품 준비중"),
    ORDER_DELIVERING("D", "상품 배송중"),
    ORDER_DELIVERED("E", "상품이 이미 배송됨"),

    ORDER_REFUNDING("F", "상품 환불 신청됨"),
    ORDER_REFUNDED("G", "상품 환불됨");

    private final String charCode;
    private final String detail;
}
