package org.craftercms.studio.controller.services.rest;

import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsService;
import org.craftercms.studio.commons.dto.AnalyticsReport;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.utils.RestControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Handles the JSON API for Analytics module.
 */
@Controller
@RequestMapping("/api/1/analytics")
public class AnalyticsController {

    private Logger log= LoggerFactory.getLogger(AnalyticsController.class);

    /**
     * Analytics Manager instance.
     */
    @Autowired
    private AnalyticsService analyticsService;

    /**
     * Default Ctr.
     */
    public AnalyticsController() {
    }

    /**
     * Interface to generate a report.
     *
     * @param site   Site on which the report is.
     * @param report Name of the Report to be Run
     * @param params Report specific Parameters
     * @return the result of the report, define in
     *         {@link AnalyticsService#generateReport(org.craftercms.studio.commons.dto.Context, String, String, java.util.Map)}
     */
    @RequestMapping(value = "/report/{site}",
            produces = "application/json",
            method = RequestMethod.GET)
    @ResponseBody
    public AnalyticsReport report(@PathVariable final String site,
                                  @RequestParam final String report,
                                  // Catch all do filter them out before send them to BL
                                  @RequestParam Map<String, Object> params)
            throws StudioException

    {
        log.debug("Filtering \"report\",\"security\" from params map");
        RestControllerUtils.removeParamters(params,"report","security");
        log.debug("Final map is {}",params);
        log.debug("Calling AnalyticsService#report");
        return this.analyticsService.generateReport(null, site, report, params);
    }


}
