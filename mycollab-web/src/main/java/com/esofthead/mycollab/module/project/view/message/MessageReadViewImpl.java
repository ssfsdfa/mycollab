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

package com.esofthead.mycollab.module.project.view.message;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.maddon.layouts.MHorizontalLayout;
import org.vaadin.maddon.layouts.MVerticalLayout;

import com.esofthead.mycollab.common.CommentType;
import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.configuration.SiteConfiguration;
import com.esofthead.mycollab.core.utils.DateTimeUtils;
import com.esofthead.mycollab.core.utils.StringUtils;
import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.module.ecm.domain.Content;
import com.esofthead.mycollab.module.ecm.service.ResourceService;
import com.esofthead.mycollab.module.file.AttachmentType;
import com.esofthead.mycollab.module.file.AttachmentUtils;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.domain.SimpleMessage;
import com.esofthead.mycollab.module.project.events.ProjectMemberEvent;
import com.esofthead.mycollab.module.project.i18n.MessageI18nEnum;
import com.esofthead.mycollab.module.project.service.MessageService;
import com.esofthead.mycollab.module.project.ui.components.CommentDisplay;
import com.esofthead.mycollab.module.project.ui.components.ProjectAttachmentDisplayComponentFactory;
import com.esofthead.mycollab.schedule.email.project.MessageRelayEmailNotificationAction;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.HasPreviewFormHandlers;
import com.esofthead.mycollab.vaadin.mvp.AbstractPageView;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.mvp.ViewScope;
import com.esofthead.mycollab.vaadin.ui.AbstractBeanFieldGroupViewFieldFactory;
import com.esofthead.mycollab.vaadin.ui.AdvancedPreviewBeanForm;
import com.esofthead.mycollab.vaadin.ui.ConfirmDialogExt;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.MyCollabResource;
import com.esofthead.mycollab.vaadin.ui.NotificationUtil;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.UserAvatarControlFactory;
import com.esofthead.mycollab.vaadin.ui.WebResourceIds;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 */
@ViewComponent(scope = ViewScope.PROTOTYPE)
public class MessageReadViewImpl extends AbstractPageView implements
		MessageReadView {
	private static final long serialVersionUID = 1L;

	private final AdvancedPreviewBeanForm<SimpleMessage> previewForm;
	private SimpleMessage message;
	private CssLayout contentWrapper;
	private MHorizontalLayout header;
	private CommentDisplay commentDisplay;
	private CheckBox stickyCheck;

	public MessageReadViewImpl() {
		super();

		this.header = new MHorizontalLayout().withStyleName("hdr-view")
				.withWidth("100%").withSpacing(true).withMargin(true);

		this.addComponent(header);
		previewForm = new AdvancedPreviewBeanForm<SimpleMessage>();

		contentWrapper = new CssLayout();
		contentWrapper.setStyleName("content-wrapper");
		contentWrapper.addComponent(previewForm);
		contentWrapper.setWidth("900px");
		this.addComponent(contentWrapper);
		this.setMargin(false);
	}

	@Override
	public HasPreviewFormHandlers<SimpleMessage> getPreviewFormHandlers() {
		return previewForm;
	}

	@Override
	public void previewItem(SimpleMessage item) {
		this.message = item;
		previewForm.setFormLayoutFactory(new FormLayoutFactory());
		previewForm
				.setBeanFormFieldFactory(new AbstractBeanFieldGroupViewFieldFactory<SimpleMessage>(
						previewForm) {
					private static final long serialVersionUID = 1L;

					@Override
					protected Field<?> onCreateField(Object propertyId) {
						return null;
					}

				});
		previewForm.setBean(item);
	}

	@Override
	public SimpleMessage getItem() {
		return message;
	}

	class FormLayoutFactory implements IFormLayoutFactory {
		private static final long serialVersionUID = 1L;

		@Override
		public ComponentContainer getLayout() {
			header.removeAllComponents();

			MVerticalLayout messageAddLayout = new MVerticalLayout()
					.withSpacing(true).withMargin(false).withWidth("100%");

			Label headerText = new Label(message.getTitle());
			headerText.setStyleName("hdr-text");

			Button deleteBtn = new Button(
					AppContext.getMessage(GenericI18Enum.BUTTON_DELETE),
					new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent event) {
							ConfirmDialogExt.show(
									UI.getCurrent(),
									AppContext.getMessage(
											GenericI18Enum.DIALOG_DELETE_TITLE,
											SiteConfiguration.getSiteName()),
									AppContext
											.getMessage(GenericI18Enum.DIALOG_DELETE_SINGLE_ITEM_MESSAGE),
									AppContext
											.getMessage(GenericI18Enum.BUTTON_YES),
									AppContext
											.getMessage(GenericI18Enum.BUTTON_NO),
									new ConfirmDialog.Listener() {
										private static final long serialVersionUID = 1L;

										@Override
										public void onClose(
												final ConfirmDialog dialog) {
											if (dialog.isConfirmed()) {
												final MessageService messageService = ApplicationContextUtil
														.getSpringBean(MessageService.class);
												messageService.removeWithSession(
														message.getId(),
														AppContext
																.getUsername(),
														AppContext
																.getAccountId());
												previewForm
														.fireCancelForm(message);
											}
										}
									});
						}
					});
			deleteBtn.setIcon(MyCollabResource
					.newResource(WebResourceIds._16_delete2));
			deleteBtn.addStyleName(UIConstants.THEME_RED_LINK);
			deleteBtn.setEnabled(CurrentProjectVariables
					.canAccess(ProjectRolePermissionCollections.MESSAGES));

			MHorizontalLayout isSticky = new MHorizontalLayout()
					.withSpacing(true);
			Label isStickyText = new Label(
					AppContext.getMessage(MessageI18nEnum.FORM_IS_STICK));
			isSticky.setStyleName("hdr-text");
			stickyCheck = new CheckBox("", message.getIsstick());

			stickyCheck.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					if (CurrentProjectVariables
							.canWrite(ProjectRolePermissionCollections.MESSAGES)) {
						message.setIsstick(stickyCheck.getValue());
						message.setSaccountid(AppContext.getAccountId());
						final MessageService messageService = ApplicationContextUtil
								.getSpringBean(MessageService.class);
						messageService.updateWithSession(message,
								AppContext.getUsername());
					} else {
						NotificationUtil.showMessagePermissionAlert();
					}

				}
			});

			isSticky.addComponent(isStickyText);
			isSticky.addComponent(stickyCheck);

			Image msgIcon = new Image(null,
					MyCollabResource
							.newResource(WebResourceIds._24_project_message));

			header.with(msgIcon, headerText, isSticky, deleteBtn)
					.withAlign(msgIcon, Alignment.MIDDLE_LEFT)
					.withAlign(headerText, Alignment.MIDDLE_LEFT)
					.withAlign(isSticky, Alignment.MIDDLE_RIGHT)
					.withAlign(deleteBtn, Alignment.MIDDLE_RIGHT)
					.expand(headerText);

			MHorizontalLayout messageLayout = new MHorizontalLayout()
					.withStyleName("message").withWidth("100%")
					.withSpacing(true);

			if (message.getIsstick() != null && message.getIsstick()) {
				messageLayout.addStyleName("important-message");
			}

			MVerticalLayout userBlock = new MVerticalLayout().withWidth("80px")
					.withSpacing(true).withMargin(false);
			userBlock.setDefaultComponentAlignment(Alignment.TOP_CENTER);
			ClickListener gotoUser = new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					EventBusFactory.getInstance().post(
							new ProjectMemberEvent.GotoRead(
									MessageReadViewImpl.this, message
											.getPosteduser()));
				}
			};
			Button userAvatarBtn = UserAvatarControlFactory
					.createUserAvatarButtonLink(
							message.getPostedUserAvatarId(),
							message.getFullPostedUserName());
			userAvatarBtn.addClickListener(gotoUser);
			userBlock.addComponent(userAvatarBtn);

			Button userName = new Button(message.getFullPostedUserName());
			userName.setStyleName("user-name");
			userName.addStyleName("link");
			userName.addStyleName(UIConstants.WORD_WRAP);
			userName.addClickListener(gotoUser);
			userBlock.addComponent(userName);

			messageLayout.addComponent(userBlock);

			final CssLayout rowLayout = new CssLayout();
			rowLayout.setStyleName("message-container");
			rowLayout.setWidth("100%");

			final MHorizontalLayout messageHeader = new MHorizontalLayout()
					.withStyleName("message-header").withSpacing(true)
					.withMargin(new MarginInfo(true, true, false, true))
					.withWidth("100%");
			messageHeader.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

			final Label timePostLbl = new Label(AppContext.getMessage(
					MessageI18nEnum.USER_COMMENT_ADD, message
							.getFullPostedUserName(), DateTimeUtils
							.getPrettyDateValue(message.getPosteddate(),
									AppContext.getUserLocale())),
					ContentMode.HTML);
			timePostLbl.setSizeUndefined();
			timePostLbl.setStyleName("time-post");

			messageHeader.addComponent(timePostLbl);
			messageHeader.setExpandRatio(timePostLbl, 1.0f);

			rowLayout.addComponent(messageHeader);

			final Label messageContent = new Label(
					StringUtils.formatRichText(message.getMessage()),
					ContentMode.HTML);
			messageContent.setStyleName("message-body");
			rowLayout.addComponent(messageContent);

			ResourceService attachmentService = ApplicationContextUtil
					.getSpringBean(ResourceService.class);
			List<Content> attachments = attachmentService
					.getContents(AttachmentUtils
							.getProjectEntityAttachmentPath(
									AppContext.getAccountId(),
									message.getProjectid(),
									AttachmentType.PROJECT_MESSAGE,
									message.getId()));
			if (CollectionUtils.isNotEmpty(attachments)) {
				HorizontalLayout attachmentField = new HorizontalLayout();
				Image attachmentIcon = new Image(null,
						MyCollabResource.newResource("icons/16/attachment.png"));
				attachmentField.addComponent(attachmentIcon);

				Label lbAttachment = new Label(
						AppContext
								.getMessage(MessageI18nEnum.FORM_ATTACHMENT_FIELD));
				attachmentField.addComponent(lbAttachment);

				Component attachmentDisplayComp = ProjectAttachmentDisplayComponentFactory
						.getAttachmentDisplayComponent(message.getProjectid(),
								AttachmentType.PROJECT_MESSAGE, message.getId());

				MVerticalLayout messageFooter = new MVerticalLayout()
						.withWidth("100%").withStyleName("message-footer")
						.withMargin(true).withSpacing(true)
						.with(attachmentField, attachmentDisplayComp);

				rowLayout.addComponent(messageFooter);
			}

			messageLayout.addComponent(rowLayout);
			messageLayout.setExpandRatio(rowLayout, 1.0f);

			messageAddLayout.addComponent(messageLayout);

			if (commentDisplay != null
					&& commentDisplay.getParent() == contentWrapper) {
				contentWrapper.removeComponent(commentDisplay);
			}
			commentDisplay = createCommentPanel();
			contentWrapper.addComponent(commentDisplay);

			return messageAddLayout;
		}

		protected CommentDisplay createCommentPanel() {
			CommentDisplay commentDisplay = new CommentDisplay(
					CommentType.PRJ_MESSAGE,
					CurrentProjectVariables.getProjectId(), true, true,
					MessageRelayEmailNotificationAction.class);
			commentDisplay.loadComments("" + message.getId());
			return commentDisplay;
		}

		@Override
		public void attachField(Object propertyId, Field<?> field) {

		}
	}
}
