package com.tw.ecommerce.repository;

import com.tw.ecommerce.model.OrderProduct;
import com.tw.ecommerce.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
