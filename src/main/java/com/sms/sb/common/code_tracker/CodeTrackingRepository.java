package com.sms.sb.common.code_tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeTrackingRepository extends JpaRepository<CodeTracking, Long> {
    Optional<CodeTracking> findByCodeType(CodeType codeType);
}
