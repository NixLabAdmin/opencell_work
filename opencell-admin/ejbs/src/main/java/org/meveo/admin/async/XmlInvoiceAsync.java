/**
 * 
 */
package org.meveo.admin.async;

import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.meveo.model.billing.Invoice;
import org.meveo.model.jobs.JobExecutionResultImpl;
import org.meveo.service.billing.impl.InvoiceService;
import org.slf4j.Logger;

/**
 * @author anasseh
 *
 */

@Stateless
public class XmlInvoiceAsync {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    protected Logger log;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Future<String> launchAndForget(List<Invoice> invoices, JobExecutionResultImpl result) {

        for (Invoice invoice : invoices) {
            long startDate = System.currentTimeMillis();
            try {
                invoiceService.produceInvoiceXmlInNewTransaction(invoice.getId());
                result.registerSucces();
            } catch (Exception e) {
                result.registerError(e.getMessage());
                log.error("Failed to create XML invoice", e);
            }
            log.info("Invoice creation delay :" + (System.currentTimeMillis() - startDate));
        }

        return new AsyncResult<String>("OK");
    }
}
