package com.constellation.catalogservice.repository;

import com.constellation.catalogservice.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    @Query("SELECT i FROM Item i WHERE (:search is null or lower(i.name) like lower(concat('%', :search, '%')))")
    List<Item> findByOptionalSearch(@Param("search") String search);
}