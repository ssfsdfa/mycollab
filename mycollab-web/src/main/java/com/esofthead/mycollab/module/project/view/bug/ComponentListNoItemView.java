/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.project.view.bug;

import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.events.BugComponentEvent;
import com.esofthead.mycollab.module.project.i18n.BugI18nEnum;
import com.esofthead.mycollab.module.project.i18n.ComponentI18nEnum;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.mvp.AbstractPageView;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.mvp.ViewScope;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.1.2
 * 
 */

@ViewComponent(scope=ViewScope.PROTOTYPE)
public class ComponentListNoItemView extends AbstractPageView {
	private static final long serialVersionUID = -77373259977614467L;

	public ComponentListNoItemView() {
		VerticalLayout layout = new VerticalLayout();
		layout.addStyleName("component-noitem");
		layout.setSpacing(true);
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.setMargin(true);

		Image image = new Image(null,
				MyCollabResource.newResource("icons/48/project/component.png"));
		layout.addComponent(image);

		Label title = new Label(
				AppContext.getMessage(ComponentI18nEnum.VIEW_NO_ITEM_TITLE));
		title.addStyleName("h2");
		title.setWidthUndefined();
		layout.addComponent(title);

		Label body = new Label(
				AppContext.getMessage(ComponentI18nEnum.VIEW_NO_ITEM_HINT));
		body.setWidthUndefined();
		layout.addComponent(body);

		Button createComponentBtn = new Button(
				AppContext.getMessage(BugI18nEnum.BUTTON_NEW_COMPONENT),
				new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(final ClickEvent event) {
						EventBusFactory.getInstance().post(
								new BugComponentEvent.GotoAdd(this, null));
					}
				});
		createComponentBtn.setEnabled(CurrentProjectVariables
				.canWrite(ProjectRolePermissionCollections.VERSIONS));

		HorizontalLayout links = new HorizontalLayout();

		links.addComponent(createComponentBtn);
		createComponentBtn.addStyleName(UIConstants.THEME_GREEN_LINK);
		links.setSpacing(true);

		layout.addComponent(links);
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.TOP_CENTER);
	}
}
