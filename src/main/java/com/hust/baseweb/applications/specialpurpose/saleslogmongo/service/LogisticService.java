package com.hust.baseweb.applications.specialpurpose.saleslogmongo.service;

import com.hust.baseweb.applications.specialpurpose.saleslogmongo.document.Facility;
import com.hust.baseweb.applications.specialpurpose.saleslogmongo.document.InventoryItem;
import com.hust.baseweb.applications.specialpurpose.saleslogmongo.document.PurchaseOrder;
import com.hust.baseweb.applications.specialpurpose.saleslogmongo.model.CreatePurchaseOrderInputModel;
import com.hust.baseweb.applications.specialpurpose.saleslogmongo.model.GetInventoryItemOutputModel;

import java.util.List;

/**
 * @author Hien Hoang (hienhoang2702@gmail.com)
 */
public interface LogisticService {

    PurchaseOrder createPurchaseOrder(CreatePurchaseOrderInputModel input);

    GetInventoryItemOutputModel getInventoryItems(String facilityId);

    List<Facility> getFacilityOfSalesman(String salesmanId);

    Facility createFacilityOfSalesman(String salesmanId, String facilityName, String address);

    List<InventoryItem> findAllInventoryItemOfProductFromFacilityAndPositiveQuantityOnHand(String facilityId, String productId);
}
