package Blog4Me.ua.jrc.login;

import Blog4Me.ua.jrc.registration.Registration;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import ua.jrc.db.entity.User;

public class LoginPage extends WebPage {

	public LoginPage() {
		super();

		final User user = new User();

		Form formLogin = new Form("formLogin");

		final Label loginM = new Label("loginM", "");
		loginM.setOutputMarkupId(true);
		loginM.setOutputMarkupPlaceholderTag(true);
		loginM.setVisible(false);

		final Label passwordM = new Label("passwordM", "");
		passwordM.setOutputMarkupId(true);
		passwordM.setOutputMarkupPlaceholderTag(true);
		passwordM.setVisible(false);

		final TextField login = new TextField("login", new PropertyModel(user, "login"));
		login.setOutputMarkupId(true);

		final TextField password = new TextField("password", new PropertyModel(user, "password"));
		password.setOutputMarkupId(true);

		AjaxButton signIn = new AjaxButton("signIn"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form){
				super.onSubmit();

				System.out.println("Sign in");
				target.add(loginM);
				target.add(passwordM);

			}
		};

		add(formLogin);

		formLogin.add(login);
		formLogin.add(loginM);

		formLogin.add(password);
		formLogin.add(passwordM);

		formLogin.add(signIn);

		Form formRegistration = new Form("formRegistration");

		Button registration = new Button("registration") {
				@Override
				public void onSubmit(){
					super.onSubmit();
					System.out.println("Registration");
					//setResponsePage(Registration.class);
				}
		};

		add(formRegistration);

		formRegistration.add(registration);
    }
}
