package cz.repository;

import cz.client.entity.BMR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BmrRepository extends JpaRepository<BMR, Long> {
}
