package Blog4Me.ua.jrc.login;

import Blog4Me.ua.jrc.main.MainPage;
import Blog4Me.ua.jrc.registration.RegistrationPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ua.jrc.db.domain.StartDB;
import ua.jrc.db.entity.User;
import ua.jrc.db.repository.UserRepository;

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

				//Check for empty area login
				if(login.getInput() != ""){
					//Check for empty area password
					if(password.getInput() != "") {

						//Check login for a syntax error
						char [] loginC = login.getInput().toCharArray();
						boolean loginSyntaxError = false;
						for(char c: loginC){
							if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122){
							} else loginSyntaxError = true;
						}
						if(!loginSyntaxError){

							//Check password for a syntax error
							char [] passwordC = password.getInput().toCharArray();
							boolean passwordSyntaxError = false;
							for(char c: passwordC){
								if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122){
								} else passwordSyntaxError = true;
							}
							if(!passwordSyntaxError){

								//If syntax is right
								//Connect to DB
								//Look for login
								UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);
								String loginDB = "";
								for(User userDB: userRepository.findAll()){
									if(login.getInput().equals(userDB.getLogin())){
										loginDB = login.getInput();
										break;
									}
								}
								//If login is found
								if(loginDB != ""){
									//Check for password
									if(password.getInput().equals(userRepository.findOne(loginDB).getPassword())){
										//All checks is true
										//Creating parameters for MainPage
										PageParameters parameters = new PageParameters();
										//Add login to parameters
										parameters.add("loginU", loginDB);

										//Go to MainPage
										setResponsePage(MainPage.class, parameters);

									} else {
										passwordM.setDefaultModelObject("Password is not correct!");
										passwordM.setVisible(true);
										loginM.setVisible(false);
									}
								} else {
									loginM.setDefaultModelObject("Login does not exist!");
									loginM.setVisible(true);
									passwordM.setVisible(false);
								}
							} else {
								passwordM.setDefaultModelObject("Password must be composed only of latin and numbers!");
								passwordM.setVisible(true);
								loginM.setVisible(false);
							}
						} else {
							loginM.setDefaultModelObject("Login must be composed only of latin and numbers!");
							loginM.setVisible(true);
							passwordM.setVisible(false);
						}
					} else {
						passwordM.setDefaultModelObject("Password is empty!");
						passwordM.setVisible(true);
						loginM.setVisible(false);
					}
				} else {
					loginM.setDefaultModelObject("Login is empty!");
					loginM.setVisible(true);
					passwordM.setVisible(false);
				}

				//Update message labels for login and password
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
					setResponsePage(RegistrationPage.class);
				}
		};

		add(formRegistration);

		formRegistration.add(registration);
    }
}
