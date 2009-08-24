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
package org.talend.dataprofiler.core.ui.editor.analysis;

import org.apache.log4j.Level;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.part.FileEditorInput;
import org.talend.dataprofiler.core.exception.ExceptionHandler;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.action.actions.RefreshChartAction;
import org.talend.dataprofiler.core.ui.action.actions.RunAnalysisAction;
import org.talend.dataprofiler.core.ui.action.actions.DefaultSaveAction;
import org.talend.dataprofiler.core.ui.editor.CommonFormEditor;
import org.talend.dataprofiler.core.ui.editor.TdEditorToolBar;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.AnalysisType;
import org.talend.dq.helper.resourcehelper.AnaResourceFileHelper;

/**
 * @author rli
 * 
 */
public class AnalysisEditor extends CommonFormEditor {

    private static final String ANALYSIS_RESULTS = DefaultMessagesImpl.getString("AnalysisEditor.analysisResult"); //$NON-NLS-1$

    private static final String SECOND_PAGE = DefaultMessagesImpl.getString("AnalysisEditor.secondPage"); //$NON-NLS-1$

    private static final String MASTER_PAGE = DefaultMessagesImpl.getString("AnalysisEditor.masterPage"); //$NON-NLS-1$

    private static final String ANALYSIS_SETTINGS = DefaultMessagesImpl.getString("AnalysisEditor.analysisSettings"); //$NON-NLS-1$

    private static final int RESULT_PAGE_INDEX = 1;

    private AbstractAnalysisMetadataPage masterPage;

    private IFormPage resultPage;

    // private IFormPage tableResultPage;

    private AnalysisType analysisType;

    private RunAnalysisAction runAction;

    private RefreshChartAction refreshAction;

    // MOD xqliu 2009-07-02 bug 7687
    private DefaultSaveAction saveAction;
    // ~

    private boolean isRefreshResultPage = false;

    /**
     * 
     */
    public AnalysisEditor() {
        
       
    }

    protected void addPages() {
       
        switch (analysisType) {
        
        case COLUMN_CORRELATION:
            masterPage = new ColumnCorrelationNominalAndIntervalMasterPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new ColumnCorrelationNominalIntervalResultPage(this, SECOND_PAGE, ANALYSIS_RESULTS);
            try {
                addPage(masterPage);
                addPage(resultPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case MULTIPLE_COLUMN:
            masterPage = new ColumnMasterDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new ColumnAnalysisResultPage(this, SECOND_PAGE, ANALYSIS_RESULTS);
            try {
                addPage(masterPage);
                addPage(resultPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case CONNECTION:
            masterPage = new ConnectionMasterDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            try {
                addPage(masterPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case CATALOG:
            masterPage = new CatalogMasterDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            try {
                addPage(masterPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case SCHEMA:
            masterPage = new SchemaAnalysisMasterDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            try {
                addPage(masterPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case COLUMNS_COMPARISON:
            masterPage = new ColumnsComparisonMasterDetailsPage(this, MASTER_PAGE, DefaultMessagesImpl
                    .getString("AnalysisEditor.analysisSetting")); //$NON-NLS-1$
            resultPage = new ColumnsComparisonAnalysisResultPage(this, SECOND_PAGE, ANALYSIS_RESULTS);
            try {
                addPage(masterPage);
                addPage(resultPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case TABLE:
            masterPage = new TableMasterDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new TableAnalysisResultPage(this, SECOND_PAGE, ANALYSIS_RESULTS);
            try {
                addPage(masterPage);
                addPage(resultPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        case TABLE_FUNCTIONAL_DEPENDENCY:
            masterPage = new ColumnDependencyMasterDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
//            setPartName(DefaultMessagesImpl.getString("AnalysisEditor.ColumnDependencyAnalysisEditor")); //$NON-NLS-1$
            resultPage = new ColumnDependencyResultPage(this, SECOND_PAGE, ANALYSIS_RESULTS);
            try {
                addPage(masterPage);
                addPage(resultPage);
            } catch (PartInitException e) {
                ExceptionHandler.process(e, Level.ERROR);
            }
            break;
        default:

        }

        TdEditorToolBar toolbar = getToolBar();
        if (toolbar != null && masterPage != null) {
            // MOD xqliu 2009-07-02 bug 7687
            saveAction = new DefaultSaveAction(this);
            runAction = new RunAnalysisAction();
            refreshAction = new RefreshChartAction();
            toolbar.addActions(saveAction, runAction, refreshAction);
            // ~
        }
        if(masterPage.getAnalysis() != null){
            setPartName(masterPage.getCurrentModelName());
        }else{
            setPartName(getEditorInput().getName());
        }

    }

    public void doSave(IProgressMonitor monitor) {
        if (masterPage != null && masterPage.isDirty()) {
            masterPage.doSave(monitor);
        }

        super.doSave(monitor);
    }

    
    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
        super.init(site, input);
      
    }

    protected void firePropertyChange(final int propertyId) {
        if (masterPage.isActive()) {
            // setRunActionButtonState(!isDirty() && masterPage.canRun().isOk());
            setRunActionButtonState(true);
            // MOD xqliu 2009-07-02 bug 7687
            setSaveActionButtonState(masterPage.isDirty());
            // ~
        }
        super.firePropertyChange(propertyId);
    }

    protected void translateInput(IEditorInput input) {
        FileEditorInput fileEditorInput = (FileEditorInput) input;
        String name = fileEditorInput.getFile().getName();
        if (name.endsWith(org.talend.dq.PluginConstant.ANA_SUFFIX)) {
            Analysis findAnalysis = AnaResourceFileHelper.getInstance().findAnalysis(fileEditorInput.getFile());
            analysisType = findAnalysis.getParameters().getAnalysisType();
        }
    }

    @Override
    protected void pageChange(int newPageIndex) {
        super.pageChange(newPageIndex);
        if (masterPage.isDirty() && (newPageIndex == RESULT_PAGE_INDEX)) {
            masterPage.doSave(null);
        }

        if (isRefreshResultPage && resultPage != null && newPageIndex == resultPage.getIndex()
                && resultPage instanceof ColumnAnalysisResultPage) {
            ((ColumnAnalysisResultPage) resultPage).refresh((ColumnMasterDetailsPage) masterPage);
            isRefreshResultPage = false;
        }
        if (isRefreshResultPage && resultPage != null && newPageIndex == resultPage.getIndex()
                && resultPage instanceof ColumnCorrelationNominalIntervalResultPage) {
            ((ColumnCorrelationNominalIntervalResultPage) resultPage)
                    .refresh((ColumnCorrelationNominalAndIntervalMasterPage) masterPage);
            isRefreshResultPage = false;
        }
        if (isRefreshResultPage && resultPage != null && newPageIndex == resultPage.getIndex()
                && resultPage instanceof ColumnsComparisonAnalysisResultPage) {
            ((ColumnsComparisonAnalysisResultPage) resultPage).refresh((ColumnsComparisonMasterDetailsPage) masterPage);
            isRefreshResultPage = false;
        }
        
        if (isRefreshResultPage && resultPage != null && newPageIndex == resultPage.getIndex()
                && resultPage instanceof ColumnDependencyResultPage) {
            ((ColumnDependencyResultPage) resultPage).refresh((ColumnDependencyMasterDetailsPage) masterPage);
            isRefreshResultPage = false;
        }
        
        if (isRefreshResultPage && resultPage != null && newPageIndex == resultPage.getIndex()
                && resultPage instanceof TableAnalysisResultPage) {
            ((TableAnalysisResultPage) resultPage).refresh((TableMasterDetailsPage) masterPage);
            isRefreshResultPage = false;
        }

        if (masterPage != null) {
            // setRunActionButtonState(masterPage.canRun().isOk());
            setRunActionButtonState(true);
            // ADD xqliu 2009-07-02 bug 7686
            setSaveActionButtonState(false);
        }
    }

    /**
     * Getter for masterPage.
     * 
     * @return the masterPage
     */
    public AbstractAnalysisMetadataPage getMasterPage() {
        return this.masterPage;
    }

    public void performGlobalAction(String id) {
        if (analysisType == AnalysisType.MULTIPLE_COLUMN) {
            ((ColumnMasterDetailsPage) masterPage).performGlobalAction(id);
        }
        if (analysisType == AnalysisType.TABLE) {
            ((TableMasterDetailsPage) masterPage).performGlobalAction(id);
        }
    }

    public void setRefreshResultPage(boolean isRefreshResultPage) {
        this.isRefreshResultPage = isRefreshResultPage;
    }

    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setRunActionButtonState(boolean state) {
        if (runAction != null) {
            runAction.setEnabled(state);
        }
    }

    /**
     * DOC xqliu 2009-07-02 bug 7687.
     * 
     * @param state
     */
    public void setSaveActionButtonState(boolean state) {
        if (saveAction != null) {
            saveAction.setEnabled(state);
        }
    }
}
