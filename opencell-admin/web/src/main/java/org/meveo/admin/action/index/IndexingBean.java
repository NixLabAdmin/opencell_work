package org.meveo.admin.action.index;

import java.io.Serializable;
import java.util.concurrent.Future;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;
import org.meveo.service.index.ElasticClient;
import org.meveo.service.index.ReindexingStatistics;
import org.slf4j.Logger;

@Named
@SessionScoped
public class IndexingBean implements Serializable {

    private static final long serialVersionUID = 7051728474316387375L;

    @Inject
    private ElasticClient elasticClient;

    @Inject
    protected Messages messages;

    @Inject
    private Logger log;

    private Future<ReindexingStatistics> reindexingFuture;

    public void cleanAndReindex() {
        reindexingFuture = null;

        if (!elasticClient.isEnabled()) {
            messages.error(new BundleKey("messages", "indexing.notEnabled"));
            return;
        }

        try {
            reindexingFuture = elasticClient.cleanAndReindex();
            messages.info(new BundleKey("messages", "indexing.started"));

        } catch (Exception e) {
            log.error("Failed to initiate Elastic Search cleanup and population", e);
            messages.info(new BundleKey("messages", "indexing.startFailed"), e.getMessage());
        }
    }

    public Future<ReindexingStatistics> getReindexingFuture() {
        return reindexingFuture;
    }

    public boolean isEnabled() {
        return elasticClient.isEnabled();
    }
}