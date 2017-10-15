package Blog4Me.ua.jrc.editPost;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class EditPostPage extends WebPage {

    public EditPostPage(PageParameters parameters) {
        super(parameters);
        System.out.println(parameters);
    }
}
