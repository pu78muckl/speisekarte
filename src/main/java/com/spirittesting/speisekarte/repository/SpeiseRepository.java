package com.spirittesting.speisekarte.repository;

import com.spirittesting.speisekarte.domain.Speise;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Speise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeiseRepository extends MongoRepository<Speise, String> {

}
