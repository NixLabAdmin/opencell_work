/*
 * (C) Copyright 2015-2016 Opencell SAS (http://opencellsoft.com/) and contributors.
 * (C) Copyright 2009-2014 Manaty SARL (http://manaty.net/) and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * This program is not suitable for any direct or indirect application in MILITARY industry
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.meveo.model.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.BusinessCFEntity;
import org.meveo.model.CustomFieldEntity;
import org.meveo.model.ExportIdentifier;
import org.meveo.model.catalog.Calendar;

/**
 * Billing cycle.
 */
@Entity
@ExportIdentifier({ "code"})
@CustomFieldEntity(cftCodePrefix = "BILLING_CYCLE")
@Table(name = "BILLING_CYCLE", uniqueConstraints = @UniqueConstraint(columnNames = { "CODE"}))
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
        @Parameter(name = "sequence_name", value = "BILLING_CYCLE_SEQ"), })
public class BillingCycle extends BusinessCFEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "BILLING_TEMPLATE_NAME")
	@Size(max = 50)
	private String billingTemplateName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CALENDAR")
	private Calendar calendar;

    @Column(name = "TRANSACTION_DATE_DELAY")
    private Integer transactionDateDelay;
    
    //used to compute the invoice date from date of billing run
    @Column(name = "INVOICE_DATE_PRODUCTION_DELAY")
    private Integer invoiceDateProductionDelay;
    
    //used for immediate invoicing by oneshot charge
	@Column(name = "INVOICE_DATE_DELAY")
	private Integer invoiceDateDelay;

	@Column(name = "DUE_DATE_DELAY")
	private Integer dueDateDelay;

	@OneToMany(mappedBy = "billingCycle", fetch = FetchType.LAZY)
	private List<BillingAccount> billingAccounts = new ArrayList<BillingAccount>();
	
	@Column(name = "INVOICING_THRESHOLD")
	private BigDecimal invoicingThreshold; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVOICE_TYPE_ID")
	private InvoiceType invoiceType;
	
	@Column(name = "DUE_DATE_DELAY_EL", length = 2000)
	@Size(max = 2000)
	private String dueDateDelayEL;

	public String getBillingTemplateName() {
		return billingTemplateName;
	}

	public void setBillingTemplateName(String billingTemplateName) {
		this.billingTemplateName = billingTemplateName;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Integer getTransactionDateDelay() {
        return transactionDateDelay;
    }

    public void setTransactionDateDelay(Integer transactionDateDelay) {
        this.transactionDateDelay = transactionDateDelay;
    }

    public Integer getInvoiceDateProductionDelay() {
        return invoiceDateProductionDelay;
    }

    public void setInvoiceDateProductionDelay(Integer invoiceDateProductionDelay) {
        this.invoiceDateProductionDelay = invoiceDateProductionDelay;
    }

    public Integer getInvoiceDateDelay() {
		return invoiceDateDelay;
	}

	public void setInvoiceDateDelay(Integer invoiceDateDelay) {
		this.invoiceDateDelay = invoiceDateDelay;
	}

	public Integer getDueDateDelay() {
		return dueDateDelay;
	}

	public void setDueDateDelay(Integer dueDateDelay) {
		this.dueDateDelay = dueDateDelay;
	}

	public List<BillingAccount> getBillingAccounts() {
		return billingAccounts;
	}

	public void setBillingAccounts(List<BillingAccount> billingAccounts) {
		this.billingAccounts = billingAccounts;
	}

	public Date getNextCalendarDate(Date subscriptionDate, Date date) {
		Date result = null;
		if(calendar != null){
			calendar.setInitDate(subscriptionDate);
			result=calendar.nextCalendarDate(date);
		}
		return result;
	}

	public Date getNextCalendarDate(Date subscriptionDate) {
		return getNextCalendarDate(subscriptionDate,new Date());
	}

	/**
	 * @return the invoicingThreshold
	 */
	public BigDecimal getInvoicingThreshold() {
		return invoicingThreshold;
	}

	/**
	 * @param invoicingThreshold the invoicingThreshold to set
	 */
	public void setInvoicingThreshold(BigDecimal invoicingThreshold) {
		this.invoicingThreshold = invoicingThreshold;
	}

	/**
	 * @return the invoiceType
	 */
	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	/**
	 * @param invoiceType the invoiceType to set
	 */
	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getDueDateDelayEL() {
		return dueDateDelayEL;
	}

	public void setDueDateDelayEL(String dueDateDelayEL) {
		this.dueDateDelayEL = dueDateDelayEL;
	}
}