package org.meveo.model.catalog;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.EnableEntity;
import org.meveo.model.ExportIdentifier;
import org.meveo.model.billing.InvoiceCategory;
import org.meveo.model.billing.InvoiceSubCategory;

/**
 * @author Edward P. Legaspi
 **/
@Entity
@ExportIdentifier({ "code"})
@Table(name = "CAT_DISCOUNT_PLAN_ITEM", uniqueConstraints = { @UniqueConstraint(columnNames = { "CODE"}) })
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "CAT_DISCOUNT_PLAN_ITEM_SEQ"), })
public class DiscountPlanItem extends EnableEntity {

	private static final long serialVersionUID = 4543503736567841084L;

	@Column(name = "CODE", length = 255, nullable = false)
	@Size(max = 255, min = 1)
	@NotNull
	private String code;

	@ManyToOne
	@JoinColumn(name = "DISCOUNT_PLAN_ID", nullable = false)
    @NotNull
	private DiscountPlan discountPlan;

	@ManyToOne
	@JoinColumn(name = "INVOICE_CATEGORY_ID")
	private InvoiceCategory invoiceCategory;

	@ManyToOne
	@JoinColumn(name = "INVOICE_SUB_CATEGORY_ID")
	private InvoiceSubCategory invoiceSubCategory;

	@Column(name = "DISCOUNT_PERCENT", precision = NB_PRECISION, scale = NB_DECIMALS)
	@Digits(integer = NB_PRECISION, fraction = NB_DECIMALS)
	@Min(0)
	@Max(100)
	private BigDecimal percent = new BigDecimal(0);

	@Column(name = "ACCOUNTING_CODE", length = 255)
	@Size(max = 255)
	private String accountingCode;
	
	@Column(name = "EXPRESSION_EL", length = 2000)
   	@Size(max = 2000)
   	private String expressionEl;

	public DiscountPlan getDiscountPlan() {
		return discountPlan;
	}

	public void setDiscountPlan(DiscountPlan discountPlan) {
		this.discountPlan = discountPlan;
	}

	public InvoiceCategory getInvoiceCategory() {
		return invoiceCategory;
	}

	public void setInvoiceCategory(InvoiceCategory invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}

	public InvoiceSubCategory getInvoiceSubCategory() {
		return invoiceSubCategory;
	}

	public void setInvoiceSubCategory(InvoiceSubCategory invoiceSubCategory) {
		this.invoiceSubCategory = invoiceSubCategory;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExpressionEl() {
		return expressionEl;
	}

	public void setExpressionEl(String expressionEl) {
		this.expressionEl = expressionEl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof DiscountPlanItem)) {
            return false;
        }
        
		DiscountPlanItem other = (DiscountPlanItem) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

}
