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
package org.meveo.service.payments.impl;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.meveo.admin.exception.BusinessException;
import org.meveo.commons.utils.QueryBuilder;
import org.meveo.model.payments.AccountOperation;
import org.meveo.model.payments.MatchingAmount;
import org.meveo.model.payments.MatchingStatusEnum;
import org.meveo.service.base.PersistenceService;

@Stateless
public class MatchingAmountService extends PersistenceService<MatchingAmount> {

	@Inject
	private AccountOperationService accountOperationService;

	public void unmatching(Long idMatchingAmount) throws BusinessException {
		log.info("start cancelMatchingAmount with id:#0,user:#1", idMatchingAmount);
        if (idMatchingAmount == null) {
            throw new BusinessException("Error when idMatchingAmount is null!");
        }
		MatchingAmount matchingAmount = findById(idMatchingAmount);
		if (matchingAmount == null) {
			log.warn("Error when found a null matchingCode!");
			throw new BusinessException("Error when found a null matchingCode!");
		}

		AccountOperation operation = matchingAmount.getAccountOperation();
		if (operation.getMatchingStatus() != MatchingStatusEnum.P
				&& operation.getMatchingStatus() != MatchingStatusEnum.L) {
			throw new BusinessException("Error:matchingCode containt unMatching operation");
		}
		operation.setUnMatchingAmount(operation.getUnMatchingAmount().add(matchingAmount.getMatchingAmount()));
		operation.setMatchingAmount(operation.getMatchingAmount().subtract(matchingAmount.getMatchingAmount()));
		if (BigDecimal.ZERO.compareTo(operation.getMatchingAmount()) == 0) {
			operation.setMatchingStatus(MatchingStatusEnum.O);
		} else {
			operation.setMatchingStatus(MatchingStatusEnum.P);
		}
		operation.getMatchingAmounts().remove(matchingAmount);
		accountOperationService.updateNoCheck(operation);
		log.info("cancel one accountOperation!");

		log.info("successfully end cancelMatching!");
	}

	public MatchingAmount findByCode(String matchingCode) {
		QueryBuilder qb = new QueryBuilder(MatchingAmount.class, "m", null);
		qb.addCriterion("matchingCode", "=", matchingCode, true);

		try {
			return (MatchingAmount) qb.getQuery(getEntityManager()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
