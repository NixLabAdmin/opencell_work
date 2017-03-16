package org.meveo.model.catalog;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.BusinessCFEntity;
import org.meveo.model.CustomFieldEntity;
import org.meveo.model.ExportIdentifier;
import org.meveo.model.ICustomFieldEntity;
import org.meveo.model.ModuleItem;
import org.meveo.model.ObservableEntity;
import org.meveo.model.annotation.ImageType;

/**
 * @author Edward P. Legaspi
 **/
@Entity
@ObservableEntity
@ImageType
@ModuleItem
@CustomFieldEntity(cftCodePrefix = "OFFER_CATEGORY")
@ExportIdentifier({ "code"})
@Table(name = "CAT_OFFER_TEMPLATE_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = { "CODE"}))
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "CAT_OFFER_TEMPLATE_CATEGORY_SEQ"), })
public class OfferTemplateCategory extends BusinessCFEntity implements Comparable<OfferTemplateCategory>, IImageUpload {

    private static final long serialVersionUID = -5088201294684394309L;

    @Column(name = "NAME", nullable = false, length = 100)
    @Size(max = 100)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "OFFER_TEMPLATE_CATEGORY_ID")
    private OfferTemplateCategory offerTemplateCategory;

    @OneToMany(mappedBy = "offerTemplateCategory", cascade = CascadeType.REMOVE)
    private List<OfferTemplateCategory> children;

    @ManyToMany(mappedBy = "offerTemplateCategories")
    private List<ProductOffering> productOffering;

    @Column(name = "LEVEL")
    private int orderLevel = 1;
    
    @ImageType
	@Column(name = "IMAGE_PATH", length = 100)
	@Size(max = 100)
    private String imagePath;

    @Override
    public ICustomFieldEntity[] getParentCFEntities() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescriptionOrCode() {
        if (!StringUtils.isBlank(description)) {
            return description;
        } else if (!(StringUtils.isBlank(name))) {
            return name;
        } else {
            return code;
        }
    }

    public OfferTemplateCategory getOfferTemplateCategory() {
        return offerTemplateCategory;
    }

    public void setOfferTemplateCategory(OfferTemplateCategory offerTemplateCategory) {
        this.offerTemplateCategory = offerTemplateCategory;
    }

    public int getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(int level) {
        this.orderLevel = level;
    }

    public List<OfferTemplateCategory> getChildren() {
        return children;
    }

    public void setChildren(List<OfferTemplateCategory> children) {
        this.children = children;
    }

    public List<ProductOffering> getProductOffering() {
        return productOffering;
    }

    public void setProductOffering(List<ProductOffering> productOffering) {
        this.productOffering = productOffering;
    }

    @Override
    public int compareTo(OfferTemplateCategory o) {
        return o.orderLevel - this.orderLevel;
    }

    /**
     * Check if offer category or any of its subcategories are assigned to any product offering
     * 
     * @return True if offer category or any of its subcategories are assigned to any product offering
     */
    public boolean isAssignedToProductOffering() {
        if (getProductOffering() != null && !getProductOffering().isEmpty()) {
            return true;
        }

        for (OfferTemplateCategory childCategory : getChildren()) {
            if (childCategory.isAssignedToProductOffering()) {
                return true;
            }
        }
        return false;
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}