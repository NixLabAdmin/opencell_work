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
package org.meveo.admin.action.admin;

import java.sql.BatchUpdateException;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.builder.BundleKey;
import org.meveo.admin.action.BaseBean;
import org.meveo.admin.exception.BusinessEntityException;
import org.meveo.admin.exception.BusinessException;
import org.meveo.admin.web.interceptor.ActionMethod;
import org.meveo.model.admin.Currency;
import org.meveo.model.billing.TradingCurrency;
import org.meveo.service.admin.impl.TradingCurrencyService;
import org.meveo.service.base.local.IPersistenceService;
import org.meveo.service.crm.impl.ProviderService;
import org.primefaces.event.SelectEvent;

/**
 * Standard backing bean for {@link TradingCurrency} (extends {@link BaseBean} that provides almost all common methods to handle entities filtering/sorting in datatable, their create, edit,
 * view, delete operations). It works with Manaty custom JSF components.
 */
@Named
@ViewScoped
public class TradingCurrencyBean extends BaseBean<TradingCurrency> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TradingCurrencyService tradingCurrencyService;

	@Inject
	ProviderService providerService;

	public TradingCurrencyBean() {
		super(TradingCurrency.class);
	}

	public List<TradingCurrency> listAll() {
		getFilters();
		if (filters.containsKey("currencyCode")) {
			filters.put("currency.currencyCode", filters.get("currencyCode"));
			filters.remove("currencyCode");
		} else if (filters.containsKey("currency.currencyCode")) {
			filters.remove("currency.currencyCode");
		}
		return super.listAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.meveo.admin.action.BaseBean#saveOrUpdate(boolean)
	 */
	@Override
	@ActionMethod
	public String saveOrUpdate(boolean killConversation) throws BusinessException {
		String back = null;
		try {
			for (TradingCurrency tr : tradingCurrencyService.list()) {
				if (tr.getCurrency()
						.getCurrencyCode()
						.equalsIgnoreCase(
								entity.getCurrency().getCurrencyCode())
						&& !tr.getId().equals(entity.getId())) {
					throw new BusinessEntityException();

				}
			}
			back = super.saveOrUpdate(killConversation);

		} catch (BusinessEntityException e) {
			messages.error(new BundleKey("messages",
					"tradingCurrency.uniqueField"));
		} catch (Exception e) {
			log.error("failed to save or update trading currency");
			messages.error(new BundleKey("messages",
					"tradingCurrency.uniqueField"));
		}
		return back;

	}

	public void populateCurrencies(Currency currency) {
		log.info("populatCurrencies currency",
				currency != null ? currency.getCurrencyCode() : null);
		if (currency != null) {
			entity.setCurrency(currency);
			entity.setPrDescription(currency.getDescriptionEn());
		}
	}

	public void onCurrencySelect(SelectEvent event) {
		if (event.getObject() instanceof Currency) {
			Currency currency = (Currency) event.getObject();
			log.info("populatCurrencies currency",
					currency != null ? currency.getCurrencyCode() : null);
			if (currency != null) {
				entity.setCurrency(currency);
				entity.setPrDescription(currency.getDescriptionEn());
			}
		}

	}

	/**
	 * @see org.meveo.admin.action.BaseBean#getPersistenceService()
	 */
	@Override
	protected IPersistenceService<TradingCurrency> getPersistenceService() {
		return tradingCurrencyService;
	}

	public void test() throws BatchUpdateException {
		throw new BatchUpdateException();
	}

	@Override
	protected String getListViewName() {
		return "tradingCurrencies";
	}

	@Override
	public String getNewViewName() {
		return "tradingCurrencyDetail";
	}

	@Override
	protected List<String> getFormFieldsToFetch() {
		return Arrays.asList("currency");
	}

	@Override
	protected List<String> getListFieldsToFetch() {
		return Arrays.asList("currency");
	}

	@Override
	protected String getDefaultSort() {
		return "currency.currencyCode";
	}

}