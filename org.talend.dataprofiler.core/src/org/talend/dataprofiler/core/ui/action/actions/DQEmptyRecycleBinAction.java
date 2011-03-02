// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.action.actions;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.talend.dataprofiler.core.CorePlugin;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.recycle.impl.RecycleBinManager;
import org.talend.dataprofiler.core.ui.dialog.message.DeleteModelElementConfirmDialog;
import org.talend.dataprofiler.core.ui.views.DQRespositoryView;
import org.talend.dq.helper.RepositoryNodeHelper;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.ui.actions.EmptyRecycleBinAction;

/**
 * DOC qiongli class global comment. Detailled comment
 */
public class DQEmptyRecycleBinAction extends EmptyRecycleBinAction {

    public DQEmptyRecycleBinAction() {
        super();
        setText(super.getText());
        setImageDescriptor(super.getImageDescriptor());
    }

    @Override
    public ISelection getSelection() {
        DQRespositoryView findView = CorePlugin.getDefault().getRepositoryView();
        ISelection selection = findView.getCommonViewer().getSelection();
        return selection;
    }

    @Override
    public void run() {
        // if these items in recycle bin are depended by others,show a warning dialog and return.
        boolean hasDependencyItem = false;
        List<IRepositoryNode> children = RecycleBinManager.getInstance().getRecycleBinChildren();
        for (IRepositoryNode obj : children) {
            if (RepositoryNodeHelper.hasDependencyClients(obj)) {
                hasDependencyItem = true;
                break;
            }
        }
        if (hasDependencyItem) {
            DeleteModelElementConfirmDialog.showDialog(null, children,
                    DefaultMessagesImpl.getString("DQEmptyRecycleBinAction.allDependencies"));
            return;
        }

        super.run();

        CorePlugin.getDefault().refreshDQView();
        CorePlugin.getDefault().refreshWorkSpace();
    }
}
