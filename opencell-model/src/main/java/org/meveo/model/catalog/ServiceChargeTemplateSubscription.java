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
package org.meveo.model.catalog;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.meveo.model.ExportIdentifier;

@Entity
@ExportIdentifier({ "chargeTemplate.code", "serviceTemplate.code"})
@Table(name = "CAT_SERV_SUB_CHARGE_TEMPLATE")
@GenericGenerator(name = "ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {@Parameter(name = "sequence_name", value = "CAT_SERV_SUBCHRG_TEMPLT_SEQ"), })
public class ServiceChargeTemplateSubscription extends ServiceChargeTemplate<OneShotChargeTemplate> {

	private static final long serialVersionUID = 7811269692204342428L;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CAT_SERV_SUB_WALLET_TEMPLATE", joinColumns = @JoinColumn(name = "SERVICE_SUB_TEMPLT_ID"), inverseJoinColumns = @JoinColumn(name = "WALLET_TEMPLATE_ID"))
	@OrderColumn(name = "INDX")
	private List<WalletTemplate> walletTemplates;

	public List<WalletTemplate> getWalletTemplates() {
		return walletTemplates;
	}

	public void setWalletTemplates(List<WalletTemplate> walletTemplates) {
		this.walletTemplates = walletTemplates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof ServiceChargeTemplateSubscription)) {
            return false;
        }
        
		ServiceChargeTemplateSubscription other = (ServiceChargeTemplateSubscription) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
