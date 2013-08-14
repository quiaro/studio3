package org.craftercms.studio.commons.dto;

/**
 * Container for AnalyticsReport.
 * @author Carlos Ortiz
 * @author Dejan Brkic
 * @author Sumer Jabri
 */
public class AnalyticsReport {

    private String reportName;

    /**
     * Default Ctr.
     */
    public AnalyticsReport() {
    }

    /**
     * Default Ctr with params.
     * @param reportName  Report Name.
     */
    public AnalyticsReport(final String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(final String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnalyticsReport{");
        sb.append("reportName='").append(reportName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
