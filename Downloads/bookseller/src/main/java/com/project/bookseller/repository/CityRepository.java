package com.project.bookseller.repository;

import com.project.bookseller.entity.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findCitiesByState_StateId(Long stateId);
}
