package org.meveo.api.dto.billing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.meveo.api.dto.CustomFieldsDto;
import org.meveo.model.catalog.ServiceTemplate;

/**
 * @author Edward P. Legaspi
 **/
@XmlRootElement(name = "ServiceToActivate")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceToActivateDto implements Serializable {

    private static final long serialVersionUID = -3815026205495621916L;

    @XmlAttribute(required = true)
    private String code;

    @XmlElement(required = true)
    private BigDecimal quantity;

    private Date subscriptionDate;
    private ChargeInstanceOverridesDto chargeInstanceOverrides;
    
    private CustomFieldsDto customFields = new CustomFieldsDto();

    @XmlTransient
    // @ApiModelProperty(hidden = true)
    private ServiceTemplate serviceTemplate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getQuantity() {
        if (quantity == null)
            return new BigDecimal(0);
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Override
    public String toString() {
        return String.format("ServiceToActivateDto [code=%s, quantity=%s, subscriptionDate=%s, chargeInstanceOverrides=%s, customFields=%s]", code, quantity, subscriptionDate,
            chargeInstanceOverrides, customFields);
    }

    public ServiceTemplate getServiceTemplate() {
        return serviceTemplate;
    }

    public void setServiceTemplate(ServiceTemplate serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    public ChargeInstanceOverridesDto getChargeInstanceOverrides() {
        return chargeInstanceOverrides;
    }

    public void setChargeInstanceOverrides(ChargeInstanceOverridesDto chargeInstanceOverrides) {
        this.chargeInstanceOverrides = chargeInstanceOverrides;
    }

	public CustomFieldsDto getCustomFields() {
        return customFields;
    }

    public void setCustomFields(CustomFieldsDto customFields) {
        this.customFields = customFields;
    }
}