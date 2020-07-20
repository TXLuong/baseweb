package com.hust.baseweb.applications.specialpurpose.saleslogmongo.repository;

import com.hust.baseweb.applications.specialpurpose.saleslogmongo.document.ProductPrice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Hien Hoang (hienhoang2702@gmail.com)
 */
public interface ProductPriceRepository extends MongoRepository<ProductPrice, ObjectId> {

}
