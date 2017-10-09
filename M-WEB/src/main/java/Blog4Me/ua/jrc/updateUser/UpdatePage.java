package Blog4Me.ua.jrc.updateUser;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class UpdatePage extends WebPage {
    public UpdatePage(PageParameters parameters) {
        super();
        System.out.println(parameters);
    }
}
