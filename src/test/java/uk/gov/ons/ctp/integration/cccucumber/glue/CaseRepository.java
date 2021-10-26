package uk.gov.ons.ctp.integration.cccucumber.glue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.ctp.integration.cccucumber.data.db.Case;

@Repository
public interface CaseRepository extends JpaRepository<Case, UUID> {
  Optional<Case> findByCaseRef(String caseRef);

  List<Case> findByAddressUprn(String uprn);
}
