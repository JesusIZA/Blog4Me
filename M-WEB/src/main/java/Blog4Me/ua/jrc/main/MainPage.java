package Blog4Me.ua.jrc.main;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MainPage extends WebPage {

	public MainPage(PageParameters parameters) {
		super();
		System.out.println("main " + parameters.get("loginU"));
	}
}
