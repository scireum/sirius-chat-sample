package search;

import sirius.biz.elastic.SearchableEntity;
import sirius.biz.web.BizController;
import sirius.biz.web.ElasticPageHelper;
import sirius.db.mixing.DateRange;
import sirius.db.mixing.query.QueryField;
import sirius.kernel.di.std.Register;
import sirius.web.controller.Controller;
import sirius.web.controller.DefaultRoute;
import sirius.web.controller.Page;
import sirius.web.controller.Routed;
import sirius.web.http.WebContext;

/**
 * Simple controller which provides a search view for {@link ChatMessage}
 */
@Register(classes = Controller.class)
public class SearchMessagesController extends BizController {

    /**
     * Route that creates a {@link Page} of {@link ChatMessage}s and sends it to a pasta template containing a search view.
     */
    @DefaultRoute
    @Routed("/search")
    public void search(WebContext webContext) {
        Page<ChatMessage> chatMessagePage =
                ElasticPageHelper.withQuery(elastic.select(ChatMessage.class).orderDesc(ChatMessage.SEND_AT))
                                 .withContext(webContext)
                                 .addTimeAggregation(ChatMessage.SEND_AT,
                                                     DateRange.lastFiveMinutes(),
                                                     DateRange.lastFiveteenMinutes(),
                                                     DateRange.lastHour())
                                 .addTermAggregation(ChatMessage.SENDER)
                                 .withSearchFields(QueryField.contains(SearchableEntity.SEARCH_FIELD))
                                 .asPage();
        webContext.respondWith().template("/templates/search.html.pasta", chatMessagePage);
    }
}
