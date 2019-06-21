package com.spirittesting.speisekarte.repository;

import com.spirittesting.speisekarte.domain.Speisekarte;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Speisekarte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeisekarteRepository extends MongoRepository<Speisekarte, String> {

}
