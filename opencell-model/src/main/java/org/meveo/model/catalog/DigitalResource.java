package org.meveo.model.catalog;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.BusinessEntity;
import org.meveo.model.ExportIdentifier;

/**
 * @author Edward P. Legaspi
 */
@Entity
@ExportIdentifier({ "code"})
@Table(name = "CAT_DIGITAL_RESOURCE", uniqueConstraints = @UniqueConstraint(columnNames = { "CODE"}))
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "CAT_DIGITAL_RESOURCE_SEQ"), })
public class DigitalResource extends BusinessEntity {

	private static final long serialVersionUID = -7528761006943581984L;

	@Column(name = "URI", length = 255)
	private String uri;

	@Column(name = "MIME_TYPE", length = 50)
	@Size(max = 50)
	private String mimeType;

	@ManyToMany(mappedBy = "attachments")
	private List<ProductOffering> productOfferings;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public List<ProductOffering> getProductOfferings() {
		return productOfferings;
	}

	public void setProductOfferings(List<ProductOffering> productOfferings) {
		this.productOfferings = productOfferings;
	}

}
