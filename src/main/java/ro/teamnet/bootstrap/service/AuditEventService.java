package ro.teamnet.bootstrap.service;

import ro.teamnet.bootstrap.config.audit.AuditEventConverter;
import ro.teamnet.bootstrap.domain.PersistentAuditEvent;
import ro.teamnet.bootstrap.repository.PersistenceAuditEventRepository;
import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service for managing audit events.
 * <p/>
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    @Inject
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Inject
    private AuditEventConverter auditEventConverter;

    /**
     * Load all audited events from database.
     * @return a list of AuditEvent objects.
     */
    public List<AuditEvent> findAll() {
        return auditEventConverter.convertToAuditEvent(persistenceAuditEventRepository.findAll());
    }

    /**
     * Load all audited events from database within a given interval.
     * @param fromDate - start date
     * @param toDate - end date
     * @return a list of AuditEvent objects.
     */
    public List<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate) {
        final List<PersistentAuditEvent> persistentAuditEvents =
                persistenceAuditEventRepository.findByDates(fromDate, toDate);

        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }
}
