package com.project.bookseller.repository;

import com.project.bookseller.entity.address.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    List<State> findStatesByCountry_CountryId(long countryId);

}
