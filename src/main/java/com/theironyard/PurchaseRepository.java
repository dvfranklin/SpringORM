package com.theironyard;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {

    Iterable<Purchase> findByCategory(String category);
}
