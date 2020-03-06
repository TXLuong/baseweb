package com.hust.baseweb.applications.tms.service;

import com.hust.baseweb.applications.logistics.entity.Product;
import com.hust.baseweb.applications.logistics.repo.ProductRepo;
import com.hust.baseweb.applications.tms.entity.DeliveryTripDetail;
import com.hust.baseweb.applications.tms.entity.ShipmentItem;
import com.hust.baseweb.applications.tms.model.createdeliverytrip.CreateDeliveryTripDetailInputModel;
import com.hust.baseweb.applications.tms.model.deliverytripdetail.DeliveryTripDetailModel;
import com.hust.baseweb.applications.tms.repo.DeliveryTripDetailRepo;
import com.hust.baseweb.applications.tms.repo.ShipmentItemRepo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class DeliveryTripDetailServiceImpl implements DeliveryTripDetailService {

    private DeliveryTripDetailRepo deliveryTripDetailRepo;
    private ShipmentItemRepo shipmentItemRepo;
    private ProductRepo productRepo;

    @Override
    public int save(String deliveryTripId, List<CreateDeliveryTripDetailInputModel> inputs) {
        UUID deliveryTripIdUuid = UUID.fromString(deliveryTripId);
        for (CreateDeliveryTripDetailInputModel input : inputs) {
            log.info("save, input quantity = " + input.getDeliveryQuantity());
            DeliveryTripDetail deliveryTripDetail = new DeliveryTripDetail();
            deliveryTripDetail.setDeliveryTripId(deliveryTripIdUuid);
            //deliveryTripDetail.setShipmentItem(input.getShipmentId());
            //deliveryTripDetail.setS
            ShipmentItem shipmentItem = shipmentItemRepo.findByShipmentItemId(input.getShipmentItemId());

            log.info("save, find ShipmentItem " + shipmentItem.getShipment().getShipmentId() + "," + shipmentItem.getShipmentItemId() + ", product = " + shipmentItem.getProductId());

            deliveryTripDetail.setShipmentItem(shipmentItem);
            deliveryTripDetail.setDeliveryQuantity(input.getDeliveryQuantity());

            deliveryTripDetailRepo.save(deliveryTripDetail);
        }
        return inputs.size();
    }

    @Override
    public boolean delete(String deliveryTripDetailId) {
        deliveryTripDetailRepo.deleteById(UUID.fromString(deliveryTripDetailId));
        return true;
    }

    @Override
    public Page<DeliveryTripDetail> findAll(String deliveryTripId, Pageable pageable) {
        return deliveryTripDetailRepo.findAllByDeliveryTripId(UUID.fromString(deliveryTripId), pageable);
    }

    @Override
    public List<DeliveryTripDetailModel> findAll(String deliveryTripId) {
        List<DeliveryTripDetail> deliveryTripDetails = deliveryTripDetailRepo.findAllByDeliveryTripId(UUID.fromString(deliveryTripId));
        Map<String, Product> productMap = new HashMap<>();
        productRepo.findAllByProductIdIn(deliveryTripDetails.stream()
                .map(deliveryTripDetail -> deliveryTripDetail.getShipmentItem().getProductId()).distinct()
                .collect(Collectors.toList())).forEach(product -> productMap.put(product.getProductId(), product));
        return deliveryTripDetails.stream()
                .map(deliveryTripDetail -> deliveryTripDetail.toDeliveryTripDetailModel(
                        productMap.get(deliveryTripDetail.getShipmentItem().getProductId())))
                .collect(Collectors.toList());
    }

}
