package org.hoteia.qalingo.web.rest;


import org.hoteia.qalingo.security.AuthoritiesConstants;
import org.hoteia.qalingo.service.AuditEventService;
import org.hoteia.qalingo.web.propertyeditors.LocaleDateTimeEditor;
import org.joda.time.LocalDateTime;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for getting the audit events.
 */
@RestController
@RequestMapping("/app")
public class AuditResource {

    @Inject
    private AuditEventService auditEventService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new LocaleDateTimeEditor("yyyy-MM-dd", false));
    }

    @RequestMapping(value = "/rest/audits/all",
            method = RequestMethod.GET,
            produces = "application/json")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findAll() {
        return auditEventService.findAll();
    }

    @RequestMapping(value = "/rest/audits/byDates",
            method = RequestMethod.GET,
            produces = "application/json")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findByDates(@RequestParam(value = "fromDate") LocalDateTime fromDate,
                                    @RequestParam(value = "toDate") LocalDateTime toDate) {
        return auditEventService.findByDates(fromDate, toDate);
    }
}
