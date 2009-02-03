// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
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

import org.apache.commons.lang.math.NumberUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.talend.dataprofiler.core.ui.editor.analysis.AnalysisEditor;
import org.talend.dataprofiler.core.ui.editor.analysis.ColumnCorrelationNominalAndIntervalMasterPage;
import org.talend.dataprofiler.core.ui.editor.analysis.ColumnMasterDetailsPage;
import org.talend.dataprofiler.core.ui.editor.analysis.ColumnsComparisonMasterDetailsPage;
import org.talend.dataquality.analysis.AnalysisType;

/**
 * DOC zqin class global comment. MOD mzhao 2009-02-03 Make this class common to
 * service all category analysis<br>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z zqin $
 * 
 */
public class OpenColumnSelectorAction extends Action implements
		ICheatSheetAction {

	public OpenColumnSelectorAction() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.cheatsheets.ICheatSheetAction#run(java.lang.String[],
	 * org.eclipse.ui.cheatsheets.ICheatSheetManager)
	 */
	public void run(String[] params, ICheatSheetManager manager) {
		if (params == null || params.length == 0) {
			return;
		}
		Integer analysisCatigory = null;
		if (NumberUtils.isNumber(params[0])) {
			analysisCatigory = NumberUtils.toInt(params[0]);
		}
		if (analysisCatigory != null) {
			AnalysisEditor editor = (AnalysisEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (editor == null) {
				return;
			}
			switch (analysisCatigory) {
			case AnalysisType.MULTIPLE_COLUMN_VALUE:
				ColumnMasterDetailsPage page = (ColumnMasterDetailsPage) editor
						.getMasterPage();
				page.openColumnsSelectionDialog();
				page.doSave(null);
				break;
			case AnalysisType.COLUMNS_COMPARISON_VALUE:
				ColumnsComparisonMasterDetailsPage page1 = (ColumnsComparisonMasterDetailsPage) editor
						.getMasterPage();
				if (params[1] != null) {
					if (NumberUtils.isNumber(params[1])) {
						int pos = NumberUtils.toInt(params[1]);
						if (pos == 0) {
							page1.openColumnsSetASelectionDialog();
						} else {
							page1.openColumnsSetBSelectionDialog();
						}
					}
				}

				break;
			case AnalysisType.COLUMN_CORRELATION_VALUE:
				ColumnCorrelationNominalAndIntervalMasterPage page2 = (ColumnCorrelationNominalAndIntervalMasterPage) editor
						.getMasterPage();
				page2.openColumnsSelectionDialog();
				page2.doSave(null);
				break;
			default:
				break;
			}
		}
	}

}
