package com.hust.baseweb.applications.tms.model.deliverytrip;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteDeliveryShipmentItemsInputModel {
	private CompleteDeliveryShipmentItemInputModel[] items;
}
