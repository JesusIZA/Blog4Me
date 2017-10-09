package Blog4Me.ua.jrc.main;

import Blog4Me.ua.jrc.login.LoginPage;
import Blog4Me.ua.jrc.updateUser.UpdatePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.UserRepository;

public class MainPage extends WebPage {

	public MainPage() {
		super();
		setResponsePage(LoginPage.class);
	}

	public MainPage(final PageParameters parameters) {
		super();

		UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);

		if(userRepository.findOne(parameters.get("loginU").toString()) == null) {
			setResponsePage(LoginPage.class);
		}

		String loginDB = parameters.get("loginU").toString();
		User user =  userRepository.findOne(loginDB);

		String userInitials = user.getLogin() + "(" + user.getFirstName() + " " + user.getLastName() + ")";

		add(new Label("userInitials", userInitials));

		Form formUpdate = new Form("formUpdate");

		Button update = new Button("update") {
			@Override
			public void onSubmit(){
				super.onSubmit();

				setResponsePage(UpdatePage.class, parameters);
			}
		};

		add(formUpdate);

		formUpdate.add(update);

		Form formSignOut = new Form("formSignOut");

		Button signOut = new Button("signOut") {
			@Override
			public void onSubmit(){
				super.onSubmit();

				setResponsePage(LoginPage.class);
			}
		};

		add(formSignOut);

		formSignOut.add(signOut);

	}
}
