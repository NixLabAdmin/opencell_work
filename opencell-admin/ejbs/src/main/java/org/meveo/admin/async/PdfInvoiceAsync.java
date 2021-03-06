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
import org.slf4j.LoggerFactory;

/**
 * @author anasseh
 * 
 */

@Stateless
public class PdfInvoiceAsync {

    @Inject
    private InvoiceService invoiceService;
        
    /** Logger. */
    protected org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Future<String> launchAndForget(List<Invoice> invoices, JobExecutionResultImpl result) {
        for (Invoice invoice : invoices) {
            try {
                invoiceService.produceInvoicePdfInNewTransaction(invoice.getId());
                result.registerSucces();                              
            } catch (Exception e) {
                result.registerError(invoice.getInvoiceNumber(), e.getMessage());
                log.error("Failed to process invoice {}", invoice, e);
            }
        }

        return new AsyncResult<String>("OK");
    }
}
