package by.msa.traveljournal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.msa.traveljournal.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
