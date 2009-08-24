// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.editor.connection;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;
import org.talend.cwm.helper.DataProviderHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.management.api.ConnectionService;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.cwm.softwaredeployment.TdProviderConnection;
import org.talend.dataprofiler.core.PluginConstant;
import org.talend.dataprofiler.core.exception.ExceptionHandler;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.editor.AbstractMetadataFormPage;
import org.talend.dataquality.exception.DataprofilerCoreException;
import org.talend.dq.helper.resourcehelper.PrvResourceFileHelper;
import org.talend.utils.sugars.ReturnCode;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC rli class global comment. Detailled comment
 */
public class ConnectionInfoPage extends AbstractMetadataFormPage {

    private static Logger log = Logger.getLogger(ConnectionInfoPage.class);

    private TdDataProvider tdDataProvider;

    private Text loginText;

    private Text passwordText;

    private Text urlText;

    private Section infomatioinSection = null;

    public ConnectionInfoPage(FormEditor editor, String id, String title) {
        super(editor, id, title);
    }

    @Override
    protected ModelElement getCurrentModelElement(FormEditor editor) {
        FileEditorInput input = (FileEditorInput) editor.getEditorInput();
        tdDataProvider = PrvResourceFileHelper.getInstance().findProvider(input.getFile()).getObject();
        return tdDataProvider;
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        super.createFormContent(managedForm);
        final ScrolledForm form = managedForm.getForm();
        form.setText(DefaultMessagesImpl.getString("ConnectionInfoPage.connectionSettings")); //$NON-NLS-1$
        this.metadataSection.setText(DefaultMessagesImpl.getString("ConnectionInfoPage.connectionMetadata")); //$NON-NLS-1$
        this.metadataSection.setDescription(DefaultMessagesImpl.getString("ConnectionInfoPage.propertiesOConnnection")); //$NON-NLS-1$
        createInformationSection(form, topComp);

        Button checkBtn = toolkit.createButton(topComp, DefaultMessagesImpl.getString("ConnectionInfoPage.check"), SWT.NONE); //$NON-NLS-1$
        GridData gd = new GridData();
        // gd.horizontalSpan = 2;
        gd.verticalSpan = 20;
        gd.horizontalAlignment = SWT.CENTER;
        checkBtn.setLayoutData(gd);

        checkBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                ReturnCode code = checkDBConnection();
                if (code.isOk()) {
                    MessageDialog
                            .openInformation(
                                    null,
                                    DefaultMessagesImpl.getString("ConnectionInfoPage.checkConnections"), DefaultMessagesImpl.getString("ConnectionInfoPage.checkConnectionSuccessful")); //$NON-NLS-1$ //$NON-NLS-2$
                } else {
                    MessageDialog
                            .openInformation(
                                    null,
                                    DefaultMessagesImpl.getString("ConnectionInfoPage.checkConnection"), DefaultMessagesImpl.getString("ConnectionInfoPage.CheckConnectionFailure", code.getMessage())); //$NON-NLS-1$ //$NON-NLS-2$ 
                }
            }

        });
        // MOD xqliu 2009-06-23 bug 7481
        foldingSections(new Section[] { metadataSection, infomatioinSection });
        // ~
        currentEditor.registerSections(new Section[] { infomatioinSection });
    }

    /**
     * @param form
     * @param toolkit
     * @param topComp
     */
    void createInformationSection(final ScrolledForm form, Composite topComp) {
        infomatioinSection = createSection(
                form,
                topComp,
                DefaultMessagesImpl.getString("ConnectionInfoPage.connectionInformations"), false, DefaultMessagesImpl.getString("ConnectionInfoPage.informationsOfConnection")); //$NON-NLS-1$ //$NON-NLS-2$

        Composite sectionClient = toolkit.createComposite(infomatioinSection);
        sectionClient.setLayout(new GridLayout(2, false));
        Label loginLabel = new Label(sectionClient, SWT.NONE);
        loginLabel.setText(DefaultMessagesImpl.getString("ConnectionInfoPage.Login")); //$NON-NLS-1$

        loginText = new Text(sectionClient, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(loginText);
        Label passwordLabel = new Label(sectionClient, SWT.NONE);
        passwordLabel.setText(DefaultMessagesImpl.getString("ConnectionInfoPage.Password")); //$NON-NLS-1$
        passwordText = new Text(sectionClient, SWT.BORDER | SWT.PASSWORD);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(passwordText);

        TdProviderConnection connection = DataProviderHelper.getTdProviderConnection(tdDataProvider).getObject();
        String loginValue = DataProviderHelper.getUser(connection);
        loginText.setText(loginValue == null ? PluginConstant.EMPTY_STRING : loginValue);

        // MOD scorreia 2009-01-09 handle encrypted password
        String passwordValue = DataProviderHelper.getClearTextPassword(connection);
        passwordText.setText(passwordValue == null ? PluginConstant.EMPTY_STRING : passwordValue);

        Label urlLabel = new Label(sectionClient, SWT.NONE);
        urlLabel.setText(DefaultMessagesImpl.getString("ConnectionInfoPage.Url")); //$NON-NLS-1$
        urlText = new Text(sectionClient, SWT.BORDER);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(urlText);
        TypedReturnCode<TdProviderConnection> trc = DataProviderHelper.getTdProviderConnection(tdDataProvider);
        String urlValue = (trc.isOk()) ? trc.getObject().getConnectionString() : PluginConstant.EMPTY_STRING;
        urlText.setText(urlValue == null ? PluginConstant.EMPTY_STRING : urlValue);
        urlText.setEnabled(false);
        if (trc.getObject().getDriverClassName().startsWith("org.sqlite")) { //$NON-NLS-1$
            loginText.setEnabled(false);
            passwordText.setEnabled(false);
        }
        ModifyListener listener = new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                setDirty(true);
                // saveTextChange();
            }

        };
        loginText.addModifyListener(listener);
        passwordText.addModifyListener(listener);
        infomatioinSection.setClient(sectionClient);
    }

    private ReturnCode checkDBConnection() {
        Properties props = new Properties();
        props.put(TaggedValueHelper.USER, loginText.getText());
        props.put(TaggedValueHelper.PASSWORD, passwordText.getText());
        TdProviderConnection connection = DataProviderHelper.getTdProviderConnection(tdDataProvider).getObject();
        ReturnCode returnCode = ConnectionService.checkConnection(this.urlText.getText(), connection.getDriverClassName(), props);
        return returnCode;
    }

    @Override
    public void setDirty(boolean isDirty) {
        if (this.isDirty != isDirty) {
            this.isDirty = isDirty;
            ((ConnectionEditor) this.getEditor()).firePropertyChange(IEditorPart.PROP_DIRTY);
        }

    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        super.doSave(monitor);
        try {
            saveConnectionInfo();
            this.isDirty = false;
        } catch (DataprofilerCoreException e) {
            ExceptionHandler.process(e, Level.ERROR);
            log.error(e, e);
        }
    }

    private void saveConnectionInfo() throws DataprofilerCoreException {
        ReturnCode returnCode = PrvResourceFileHelper.getInstance().save(tdDataProvider);
        if (returnCode.isOk()) {
            if (log.isDebugEnabled()) {
                log.debug("Saved in  " + tdDataProvider.eResource().getURI().toFileString() + " successful"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            throw new DataprofilerCoreException(
                    DefaultMessagesImpl
                            .getString(
                                    "ConnectionInfoPage.ProblemSavingFile", tdDataProvider.eResource().getURI().toFileString(), returnCode.getMessage())); //$NON-NLS-1$
        }
    }

}
