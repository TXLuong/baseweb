package com.hust.baseweb.applications.logistics.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelCreateProductInput {
    private String productId;
    private String quantityUomId;
    private String type;
    private String productName;
    private List<String> content;

    public ModelCreateProductInput(String productId, String quantityUomId, String type, String productName) {
        this.productId = productId;
        this.quantityUomId = quantityUomId;
        this.type = type;
        this.productName = productName;
    }


}
