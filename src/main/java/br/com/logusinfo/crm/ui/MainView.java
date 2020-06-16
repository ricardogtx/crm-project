package br.com.logusinfo.crm.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import br.com.logusinfo.crm.backend.entity.Company;
import br.com.logusinfo.crm.backend.entity.Contact;
import br.com.logusinfo.crm.backend.service.ContactService;

@Route("")
public class MainView extends VerticalLayout {
	private ContactService contactService;
	private Grid<Contact> grid = new Grid<>(Contact.class);

	public MainView(ContactService contactService) {
		this.contactService = contactService;
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		add(grid);
		updateList();
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
	}

	private void updateList() {
		grid.setItems(contactService.findAll());
	}
}