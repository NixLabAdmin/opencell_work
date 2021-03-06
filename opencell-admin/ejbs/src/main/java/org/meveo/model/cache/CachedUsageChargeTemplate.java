package org.meveo.model.cache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.meveo.model.catalog.RoundingModeEnum;
import org.meveo.model.catalog.TriggeredEDRTemplate;
import org.meveo.model.catalog.UsageChargeTemplate;

public class CachedUsageChargeTemplate {

    private Long id;
    private String code;
    private Date lastUpdate;
    private int priority;
    private String filterExpression;
    private String filter1;
    private String filter2;
    private String filter3;
    private String filter4;
    private BigDecimal unitMultiplicator = BigDecimal.ONE;
    private int unitNbDecimal = 2;
    private RoundingModeEnum roundingMode;
    private Set<CachedTriggeredEDR> edrTemplates = new HashSet<CachedTriggeredEDR>();
    private Set<Long> subscriptionIds = new HashSet<Long>();
    private String ratingUnitDescription;
    private String inputUnitDescription;
    private String invoiceSubCategoryCode;

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public int getPriority() {
        return priority;
    }

    public String getFilterExpression() {
        return filterExpression;
    }

    public String getFilter1() {
        return filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public String getFilter3() {
        return filter3;
    }

    public String getFilter4() {
        return filter4;
    }

    public String getRatingUnitDescription() {
        return ratingUnitDescription;
    }

    public String getInputUnitDescription() {
        return inputUnitDescription;
    }

    public Set<CachedTriggeredEDR> getEdrTemplates() {
        return edrTemplates;
    }

    public Set<Long> getSubscriptionIds() {
        return subscriptionIds;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getUnitMultiplicator() {
        return unitMultiplicator;
    }

    public int getUnitNbDecimal() {
        return unitNbDecimal;
    }

    public RoundingModeEnum getRoundingMode() {
        return roundingMode;
    }
    
    public String getInvoiceSubCategoryCode() {
        return invoiceSubCategoryCode;
    }

    @Override
    public String toString() {
        return String
            .format(
                "CachedUsageChargeTemplate [id=%s, code=%s, lastUpdate=%s, priority=%s, filterExpression=%s, filter1=%s, filter2=%s, filter3=%s, filter4=%s, unityMultiplicator=%s, unityNbDecimal=%s, roundingModeEnum=%s, edrTemplates=%s, subscriptionIds=%s]",
                id, code, lastUpdate, priority, filterExpression, filter1, filter2, filter3, filter4, unitMultiplicator, unitNbDecimal, roundingMode, edrTemplates,
                subscriptionIds);
    }

    public void populateFromUsageChargeTemplate(UsageChargeTemplate usageChargeTemplate) {

        id = usageChargeTemplate.getId();
        code = usageChargeTemplate.getCode();
        filterExpression = StringUtils.stripToNull(usageChargeTemplate.getFilterExpression());
        filter1 = StringUtils.stripToNull(usageChargeTemplate.getFilterParam1());
        filter2 = StringUtils.stripToNull(usageChargeTemplate.getFilterParam2());
        filter3 = StringUtils.stripToNull(usageChargeTemplate.getFilterParam3());
        filter4 = StringUtils.stripToNull(usageChargeTemplate.getFilterParam4());
        unitNbDecimal = usageChargeTemplate.getUnitNbDecimal();
        roundingMode = usageChargeTemplate.getRoundingMode();
        unitMultiplicator = usageChargeTemplate.getUnitMultiplicator();
        ratingUnitDescription = usageChargeTemplate.getRatingUnitDescription();
        inputUnitDescription = usageChargeTemplate.getInputUnitDescription();
        invoiceSubCategoryCode = usageChargeTemplate.getInvoiceSubCategory().getCode();
        
        if (unitMultiplicator == null) {
            unitMultiplicator = BigDecimal.ONE;
        }

        edrTemplates = new HashSet<CachedTriggeredEDR>();
        if (usageChargeTemplate.getEdrTemplates() != null && usageChargeTemplate.getEdrTemplates().size() > 0) {
            for (TriggeredEDRTemplate edrTemplate : usageChargeTemplate.getEdrTemplates()) {
                CachedTriggeredEDR trigerredEDRCache = new CachedTriggeredEDR(edrTemplate);
                edrTemplates.add(trigerredEDRCache);
            }
        }
        if (getPriority() != usageChargeTemplate.getPriority()) {
            priority = usageChargeTemplate.getPriority();
        }
    }
}