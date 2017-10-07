package Blog4Me.ua.jrc.registration;

import Blog4Me.ua.jrc.login.LoginPage;
import Blog4Me.ua.jrc.main.MainPage;
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

public class RegistrationPage extends WebPage {

	public RegistrationPage() {
		super();

		final User user = new User();

		Form formRegistration = new Form("formRegistration");

		final Label loginM = new Label("loginM", "");
		loginM.setOutputMarkupId(true);
		loginM.setOutputMarkupPlaceholderTag(true);
		loginM.setVisible(false);

		final Label firstNameM = new Label("firstNameM", "");
		firstNameM.setOutputMarkupId(true);
		firstNameM.setOutputMarkupPlaceholderTag(true);
		firstNameM.setVisible(false);

		final Label lastNameM = new Label("lastNameM", "");
		lastNameM.setOutputMarkupId(true);
		lastNameM.setOutputMarkupPlaceholderTag(true);
		lastNameM.setVisible(false);

		final Label passwordM = new Label("passwordM", "");
		passwordM.setOutputMarkupId(true);
		passwordM.setOutputMarkupPlaceholderTag(true);
		passwordM.setVisible(false);

		final TextField login = new TextField("login", new PropertyModel(user, "login"));
		login.setOutputMarkupId(true);

		final TextField firstName = new TextField("firstName", new PropertyModel(user, "login"));
		firstName.setOutputMarkupId(true);

		final TextField lastName = new TextField("lastName", new PropertyModel(user, "login"));
		lastName.setOutputMarkupId(true);

		final TextField password = new TextField("password", new PropertyModel(user, "login"));
		password.setOutputMarkupId(true);

		AjaxButton registration = new AjaxButton("registration") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				super.onSubmit();
				//Check for empty area login
				if(login.getInput() != "") {
					//Check for empty area firstName
					if (firstName.getInput() != "") {
						//Check for empty area lastName
						if (lastName.getInput() != "") {
							//Check for empty area password
							if (password.getInput() != "") {

								//If each area are filled
								//Check for length area login
								if(login.getInput().length() >= 4) {
									//Check for length area password
									if(password.getInput().length() >= 8) {

										//Check login for a syntax error
										char [] loginC = login.getInput().toCharArray();
										boolean loginSyntaxError = false;
										for(char c: loginC){
											if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122){
											} else loginSyntaxError = true;
										}
										if(!loginSyntaxError) {

											//Check firstName for a syntax error
											char [] firstNameC = firstName.getInput().toCharArray();
											boolean firstNameSyntaxError = false;
											for(char c: firstNameC){
												if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122){
												} else firstNameSyntaxError = true;
											}
											if(!firstNameSyntaxError) {

												//Check lastName for a syntax error
												char[] lastNameC = lastName.getInput().toCharArray();
												boolean lastNameSyntaxError = false;
												for (char c : lastNameC) {
													if (c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122) {
													} else lastNameSyntaxError = true;
												}
												if (!lastNameSyntaxError) {

													//Check password for a syntax error
													char[] passwordC = password.getInput().toCharArray();
													boolean passwordSyntaxError = false;
													for (char c : passwordC) {
														if (c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122) {
														} else passwordSyntaxError = true;
													}
													if (!passwordSyntaxError) {

														//If syntax is right
														//Connect to DB
														//Look for login
														UserRepository userRepository = StartDB.getContext().getBean(UserRepository.class);
														String loginDB = login.getInput();
														for(User userDB: userRepository.findAll()){
															if(login.getInput().equals(userDB.getLogin())){
																loginDB = "";
																break;
															}
														}
														//If login is not found
														if(loginDB.equals(login.getInput())){

															//If login does not exist in DB
															PageParameters parameters = new PageParameters();
															parameters.add("loginU", loginDB);

															User tempUser = new User();
															tempUser.setLogin(loginDB);
															tempUser.setFirstName(firstName.getInput());
															tempUser.setLastName(lastName.getInput());
															tempUser.setPassword(password.getInput());

															userRepository.save(tempUser);

															setResponsePage(MainPage.class, parameters);

														} else {
															loginM.setDefaultModelObject("This login does exist!");
															loginM.setVisible(true);
															lastName.setVisible(false);
															firstNameM.setVisible(false);
															passwordM.setVisible(false);
														}
													} else {
														passwordM.setDefaultModelObject("Password must be composed only of latin and numbers!");
														passwordM.setVisible(true);
														lastName.setVisible(false);
														firstNameM.setVisible(false);
														loginM.setVisible(false);
													}
												} else {
													lastName.setDefaultModelObject("Last name must be composed only of latin and numbers!");
													lastName.setVisible(true);
													passwordM.setVisible(false);
													firstNameM.setVisible(false);
													loginM.setVisible(false);
												}
											} else {
												firstNameM.setDefaultModelObject("First name must be composed only of latin and numbers!");
												firstNameM.setVisible(true);
												lastName.setVisible(false);
												loginM.setVisible(false);
												passwordM.setVisible(false);
											}
										} else {
											loginM.setDefaultModelObject("Login must be composed only of latin and numbers!");
											loginM.setVisible(true);
											lastName.setVisible(false);
											firstNameM.setVisible(false);
											passwordM.setVisible(false);
										}
									} else {
										passwordM.setDefaultModelObject("Password is too short! (must be more then 7 symbols)");
										passwordM.setVisible(true);
										loginM.setVisible(false);
										firstNameM.setVisible(false);
										lastNameM.setVisible(false);
									}
								} else {
									loginM.setDefaultModelObject("Login is too short! (must be more then 3 symbols)");
									loginM.setVisible(true);
									firstNameM.setVisible(false);
									lastNameM.setVisible(false);
									passwordM.setVisible(false);
								}
							} else {
								passwordM.setDefaultModelObject("Password is empty!");
								passwordM.setVisible(true);
								firstNameM.setVisible(false);
								lastNameM.setVisible(false);
								loginM.setVisible(false);
							}
						} else {
							lastNameM.setDefaultModelObject("Last name is empty!");
							lastNameM.setVisible(true);
							passwordM.setVisible(false);
							firstNameM.setVisible(false);
							loginM.setVisible(false);
						}
					} else {
						firstNameM.setDefaultModelObject("First name is empty!");
						firstNameM.setVisible(true);
						lastNameM.setVisible(false);
						passwordM.setVisible(false);
						loginM.setVisible(false);
					}
				} else {
					loginM.setDefaultModelObject("Login is empty!");
					loginM.setVisible(true);
					firstNameM.setVisible(false);
					lastNameM.setVisible(false);
					passwordM.setVisible(false);
				}

				//Update message labels for login, firstName, lastName and password
				target.add(loginM);
				target.add(firstNameM);
				target.add(lastNameM);
				target.add(passwordM);
			}
		};

		add(formRegistration);

		formRegistration.add(login);
		formRegistration.add(loginM);

		formRegistration.add(firstName);
		formRegistration.add(firstNameM);

		formRegistration.add(lastName);
		formRegistration.add(lastNameM);

		formRegistration.add(password);
		formRegistration.add(passwordM);

		formRegistration.add(registration);

		Form formLogin = new Form("formLogin");

		Button signIn = new Button("signIn") {
			@Override
			public void onSubmit(){
				super.onSubmit();
				setResponsePage(LoginPage.class);
			}
		};

		add(formLogin);

		formLogin.add(signIn);
	}
}
