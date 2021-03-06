package org.meveo.model.catalog;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.BusinessEntity;
import org.meveo.model.ExportIdentifier;

/**
 * @author Edward P. Legaspi
 */
@Entity
@ExportIdentifier({ "code"})
@Table(name = "CAT_CHANNEL", uniqueConstraints = @UniqueConstraint(columnNames = { "CODE"}))
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "CAT_CHANNEL_SEQ"), })
public class Channel extends BusinessEntity {

	private static final long serialVersionUID = 6877386866687396135L;

}
