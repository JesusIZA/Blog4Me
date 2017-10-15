package Blog4Me.ua.jrc.addPost;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class AddPostPage extends WebPage {
    public AddPostPage(PageParameters parameters) {
        super(parameters);
        System.out.println(parameters);
    }
}
