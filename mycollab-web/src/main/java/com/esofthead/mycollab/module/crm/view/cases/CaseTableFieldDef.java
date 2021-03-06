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
package com.esofthead.mycollab.module.crm.view.cases;

import com.esofthead.mycollab.common.TableViewField;
import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.module.crm.i18n.CaseI18nEnum;
import com.esofthead.mycollab.vaadin.ui.UIConstants;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public interface CaseTableFieldDef {
	public static TableViewField selected = new TableViewField(null,
			"selected", UIConstants.TABLE_CONTROL_WIDTH);

	public static TableViewField action = new TableViewField(null, "id");

	public static TableViewField priority = new TableViewField(
			CaseI18nEnum.FORM_PRIORITY, "priority",
			UIConstants.TABLE_S_LABEL_WIDTH);

	public static TableViewField status = new TableViewField(
			CaseI18nEnum.FORM_STATUS, "status", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField account = new TableViewField(
			CaseI18nEnum.FORM_ACCOUNT, "accountName",
			UIConstants.TABLE_X_LABEL_WIDTH);

	public static TableViewField origin = new TableViewField(
			CaseI18nEnum.FORM_ORIGIN, "origin", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField phone = new TableViewField(
			CaseI18nEnum.FORM_PHONE, "phonenumber",
			UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField type = new TableViewField(
			CaseI18nEnum.FORM_TYPE, "type", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField reason = new TableViewField(
			CaseI18nEnum.FORM_REASON, "reason",
			UIConstants.TABLE_EX_LABEL_WIDTH);

	public static TableViewField subject = new TableViewField(
			CaseI18nEnum.FORM_SUBJECT, "subject",
			UIConstants.TABLE_EX_LABEL_WIDTH);

	public static TableViewField email = new TableViewField(
			CaseI18nEnum.FORM_EMAIL, "email", UIConstants.TABLE_EMAIL_WIDTH);

	public static TableViewField assignUser = new TableViewField(
			GenericI18Enum.FORM_ASSIGNEE, "assignUserFullName",
			UIConstants.TABLE_X_LABEL_WIDTH);

	public static TableViewField createdTime = new TableViewField(
			GenericI18Enum.FORM_CREATED_TIME, "createdtime",
			UIConstants.TABLE_DATE_TIME_WIDTH);

	public static TableViewField lastUpdatedTime = new TableViewField(
			GenericI18Enum.FORM_LAST_UPDATED_TIME, "lastupdatedtime",
			UIConstants.TABLE_DATE_TIME_WIDTH);
}
