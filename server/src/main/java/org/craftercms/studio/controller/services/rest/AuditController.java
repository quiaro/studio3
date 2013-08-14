package org.craftercms.studio.controller.services.rest;

import java.util.List;
import javax.validation.Valid;

import org.craftercms.studio.api.audit.AuditManager;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Handles the JSON API for Audit module.
 */
@Controller
@RequestMapping("/api/1/audit")
public class AuditController {
    private Logger log = LoggerFactory.getLogger(AuditController.class);
    /**
     * Audit manager instance.
     */
    @Autowired
    private AuditManager auditManager;

    /**
     * Default CTOR.
     */
    public AuditController() {
    }

    @RequestMapping(value = "/activity/{site}", produces = "application/json", method = RequestMethod.GET)
    public List<Activity> getActivities(@PathVariable final String site,
                                        @RequestParam(required = false) final String[] filters) {
        return this.auditManager.getActivities(new Context(), site, filters);
    }

    /**
     * Saves a new activity for the given site.
     *
     * @param site     Site where the activity will be save
     * @param activity Activity to be save (Form Message conversion see {@link Activity})
     * @param result   Spring MVC validation Result
     * @return The Activity that have been save
     * @throws ValidationException If the given object is not valid
     */
    @RequestMapping(value = "/log/{site}", produces = "application/json", method = RequestMethod.POST)
    public Activity logActivity(@PathVariable final String site, @Valid @RequestBody final Activity activity,
                                final BindingResult result) throws ValidationException {
        if ( result.hasErrors() ) {
            final ValidationException validationException = new ValidationException("Unable to save Activity",
                    result.getAllErrors());
            log.error("Unable to save a activity since is not valid", validationException);
            throw validationException;
        } else {
            log.debug("Calling AuditManager#logActivity with {}", activity);
            return this.auditManager.logActivity(new Context(), site, activity);
        }
    }

}
