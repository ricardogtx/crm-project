package br.com.crm.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import br.com.crm.backend.entity.Company;
import br.com.crm.backend.entity.Contact;
import br.com.crm.backend.service.CompanyService;
import br.com.crm.backend.service.ContactService;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
	private ContactService contactService;
	private Grid<Contact> grid = new Grid<>(Contact.class);
	private TextField filterText = new TextField();
	private ContactForm form;

	public MainView(ContactService contactService, CompanyService companyService) {
		this.contactService = contactService;
		addClassName("list-view");
		setSizeFull();

		configureGrid();

		form = new ContactForm(companyService.findAll());
		form.addListener(ContactForm.SaveEvent.class, this::saveContact);
		form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
		form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

		Div content = new Div(grid, form);
		content.addClassName("content");
		content.setSizeFull();

		add(getToolbar(), content);
		updateList();
		closeEditor();
	}

	private HorizontalLayout getToolbar() {
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());

		Button addContactButton = new Button("Add contact");
		addContactButton.addClickListener(click -> addContact());
		HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	private void configureGrid() {
		grid.addClassName("contact-grid");
		grid.setSizeFull();
		grid.removeColumnByKey("company");
		grid.setColumns("firstName", "lastName", "email", "status");
		grid.addColumn(contact -> {
			Company company = contact.getCompany();
			return company == null ? "-" : company.getName();
		}).setHeader("Company");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
	}

	private void updateList() {
		grid.setItems(contactService.findAll(filterText.getValue()));
	}

	public void editContact(Contact contact) {
		if (contact == null) {
			closeEditor();
		} else {
			form.setContact(contact);
			form.setVisible(true);
			addClassName("editing");
		}
	}

	private void closeEditor() {
		form.setContact(null);
		form.setVisible(false);
		removeClassName("editing");
	}

	private void saveContact(ContactForm.SaveEvent event) {
		contactService.save(event.getContact());
		updateList();
		closeEditor();
	}

	private void deleteContact(ContactForm.DeleteEvent event) {
		contactService.delete(event.getContact());
		updateList();
		closeEditor();
	}

	void addContact() {
		grid.asSingleSelect().clear();
		editContact(new Contact());
	}
}