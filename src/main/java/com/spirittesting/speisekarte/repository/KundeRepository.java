package com.spirittesting.speisekarte.repository;

import com.spirittesting.speisekarte.domain.Kunde;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Kunde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KundeRepository extends MongoRepository<Kunde, String> {

}
