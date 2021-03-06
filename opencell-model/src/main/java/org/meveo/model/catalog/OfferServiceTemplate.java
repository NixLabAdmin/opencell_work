package org.meveo.model.catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.meveo.model.ExportIdentifier;
import org.meveo.model.IEntity;

@Entity
@ExportIdentifier({ "offerTemplate.code", "serviceTemplate.code" })
@Table(name = "CAT_OFFER_SERV_TEMPLATES")
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "CAT_OFFER_SERV_TEMPLT_SEQ"), })
public class OfferServiceTemplate implements IEntity {
    
    @Id
	@GeneratedValue(generator = "ID_GENERATOR", strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Access(AccessType.PROPERTY)
	protected Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OFFER_TEMPLATE_ID")
    @NotNull
    private OfferTemplate offerTemplate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, optional = false)
    @JoinColumn(name = "SERVICE_TEMPLATE_ID")
    @NotNull
    private ServiceTemplate serviceTemplate;

    @Type(type="numeric_boolean")
    @Column(name = "MANDATORY")
    private boolean mandatory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CAT_OFFER_SERV_INCOMP", joinColumns = @JoinColumn(name = "OFFER_SERVICE_TEMPLATE_ID"), inverseJoinColumns = @JoinColumn(name = "SERVICE_TEMPLATE_ID"))
    private List<ServiceTemplate> incompatibleServices = new ArrayList<>();
    
    @Column(name="VALID_FROM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;
	
	@Column(name="VALID_TO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validTo;

    public OfferTemplate getOfferTemplate() {
        return offerTemplate;
    }

    public void setOfferTemplate(OfferTemplate offerTemplate) {
        this.offerTemplate = offerTemplate;
    }

    public ServiceTemplate getServiceTemplate() {
        return serviceTemplate;
    }

    public void setServiceTemplate(ServiceTemplate serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<ServiceTemplate> getIncompatibleServices() {
        return incompatibleServices;
    }

    public void setIncompatibleServices(List<ServiceTemplate> incompatibleServices) {
        this.incompatibleServices = incompatibleServices;
    }

    public void addIncompatibleServiceTemplate(ServiceTemplate serviceTemplate) {
        if (getIncompatibleServices() == null) {
            incompatibleServices = new ArrayList<ServiceTemplate>();
        }
        incompatibleServices.add(serviceTemplate);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * 1; // super.hashCode();
        result = prime * result + ((incompatibleServices == null) ? 0 : incompatibleServices.hashCode());
        result = prime * result + ((offerTemplate == null) ? 0 : offerTemplate.hashCode());
        result = prime * result + ((serviceTemplate == null) ? 0 : serviceTemplate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof OfferServiceTemplate)) {
            return false;
        }

        OfferServiceTemplate other = (OfferServiceTemplate) obj;

        if (getId() != null && other.getId() != null && getId().equals(other.getId())) {
            // return true;
        }

        if (offerTemplate != null) {
            if (!offerTemplate.equals(other.getOfferTemplate())) {
                return false;
            }
        } else if (other.getOfferTemplate() != null) {
            return false;
        }

        if (serviceTemplate != null) {
            if (!serviceTemplate.equals(other.getServiceTemplate())) {
                return false;
            }
        } else if (other.getServiceTemplate() != null) {
            return false;
        }
        return true;
    }

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean isTransient() {
		return id == null;
	}

    /**
     * Update OfferServiceTemplate properties with properties of another OfferServiceTemplate
     * 
     * @param otherOst Other OfferServiceTemplate, to copy properties from
     */
    public void update(OfferServiceTemplate otherOst) {

        setMandatory(otherOst.isMandatory());
        setValidFrom(otherOst.getValidFrom());
        setValidTo(otherOst.getValidTo());
        setIncompatibleServices(otherOst.getIncompatibleServices());
    }
}
