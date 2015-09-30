/*
 * (C) Copyright 2009-2014 Manaty SARL (http://manaty.net/) and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.meveo.service.catalog.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.meveo.commons.utils.QueryBuilder;
import org.meveo.model.catalog.OneShotChargeTemplate;
import org.meveo.model.catalog.ServiceChargeTemplateSubscription;
import org.meveo.model.catalog.ServiceTemplate;
import org.meveo.model.catalog.WalletTemplate;
import org.meveo.model.crm.Provider;
import org.meveo.service.base.PersistenceService;

@Stateless
public class ServiceChargeTemplateSubscriptionService extends PersistenceService<ServiceChargeTemplateSubscription> {

    // public void removeByPrefix(EntityManager em, String prefix, Provider provider) {
    // Query query = em.createQuery("DELETE ServiceChargeTemplateSubscription t WHERE t.chargeTemplate.code LIKE '"
    // + prefix + "%' AND t.provider=:provider");
    // query.setParameter("provider", provider);
    // query.executeUpdate();
    // }

    @SuppressWarnings("unchecked")
    public List<ServiceChargeTemplateSubscription> findBySubscriptionChargeTemplate(OneShotChargeTemplate chargeTemplate, Provider provider) {

        QueryBuilder qb = new QueryBuilder(ServiceChargeTemplateSubscription.class, "a");
        qb.addCriterionEntity("chargeTemplate", chargeTemplate);
        qb.addCriterionEntity("provider", provider);

        return (List<ServiceChargeTemplateSubscription>) qb.getQuery(getEntityManager()).getResultList();
    }

    public void removeByServiceTemplate(ServiceTemplate serviceTemplate, Provider provider) {
        Query query = getEntityManager().createQuery("DELETE ServiceChargeTemplateSubscription t WHERE t.serviceTemplate=:serviceTemplate AND t.provider=:provider");
        query.setParameter("serviceTemplate", serviceTemplate);
        query.setParameter("provider", provider);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<ServiceChargeTemplateSubscription> findByWalletTemplate(WalletTemplate walletTemplate) {
        QueryBuilder qb = new QueryBuilder(ServiceChargeTemplateSubscription.class, "s");
        qb.addCriterionEntity("walletTemplate", walletTemplate);
        return qb.find(getEntityManager());
    }
}