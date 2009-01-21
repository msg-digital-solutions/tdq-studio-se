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

package org.talend.cwm.compare.factory.comparisonlevel;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.compare.match.api.MatchOptions;
import org.eclipse.emf.ecore.resource.Resource;
import org.talend.commons.emf.EMFSharedResources;
import org.talend.cwm.compare.DQStructureComparer;
import org.talend.cwm.compare.exception.ReloadCompareException;
import org.talend.cwm.compare.factory.IComparisonLevel;
import org.talend.cwm.compare.factory.IUIHandler;
import org.talend.cwm.helper.DataProviderHelper;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.management.api.DqRepositoryViewService;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dq.helper.resourcehelper.PrvResourceFileHelper;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.objectmodel.core.Package;
import orgomg.cwm.resource.relational.Column;
import orgomg.cwm.resource.relational.ColumnSet;

/**
 * 
 * DOC mzhao class global comment. Compare two selected element in local
 * structure.
 */
public class SelectedLocalComparison implements IComparisonLevel {
	private static final int LEFT_RESOURCE = 0;
	private static final int RIGHT_RESOURCE = 1;
	private Object firstSelectedObj = null, secondSelectedObj = null;
	private TdDataProvider firstSelectedDataProvider;

	private TdDataProvider secondSelectedDataProvider;

	private TdDataProvider tempFirstSelectedDataProvider;

	private TdDataProvider tempSecondSelectedDataProvider;
	private Map<String, Object> options;

	public SelectedLocalComparison(Object firstSelectedObj,
			Object secondSelectedObj) {
		this.firstSelectedObj = firstSelectedObj;
		this.secondSelectedObj = secondSelectedObj;
		options = new HashMap<String, Object>();
		options.put(MatchOptions.OPTION_IGNORE_XMI_ID, true);
	}

	public void popComparisonUI(IUIHandler uiHandler)
			throws ReloadCompareException {

		// Judge selected elements types.
		ModelElementAdapter meAdapter = new ModelElementAdapter();
		firstSelectedDataProvider = meAdapter
				.getAdaptableProvider(firstSelectedObj);
		secondSelectedDataProvider = meAdapter
				.getAdaptableProvider(secondSelectedObj);

		if (firstSelectedDataProvider == null
				|| secondSelectedDataProvider == null) {
			return;
		}

		DQStructureComparer.deleteFirstResourceFile();
		DQStructureComparer.deleteSecondResourceFile();

		createTempConnectionFile();
		// createCopyedProvider();
		DQStructureComparer.openDiffCompareEditor(getResource(LEFT_RESOURCE),
				getResource(RIGHT_RESOURCE), options, uiHandler,
				DQStructureComparer.getLocalDiffResourceFile());

	}

	protected void createTempConnectionFile() throws ReloadCompareException {
		// First resource.
		IFile selectedFile1 = PrvResourceFileHelper.getInstance()
				.findCorrespondingFile(firstSelectedDataProvider);
		IFile firstConnectionFile = DQStructureComparer
				.getFirstComparisonLocalFile();
		IFile copyedFile1 = DQStructureComparer.copyedToDestinationFile(
				selectedFile1, firstConnectionFile);
		TypedReturnCode<TdDataProvider> returnProvider = DqRepositoryViewService
				.readFromFile(copyedFile1);
		if (!returnProvider.isOk()) {
			throw new ReloadCompareException(returnProvider.getMessage());
		}
		tempFirstSelectedDataProvider = returnProvider.getObject();
		tempFirstSelectedDataProvider.setComponent(firstSelectedDataProvider
				.getComponent());
		DqRepositoryViewService.saveDataProviderResource(
				tempFirstSelectedDataProvider, (IFolder) firstConnectionFile
						.getParent(), firstConnectionFile);
		tempFirstSelectedDataProvider.setComponent(null);

		// Second resource.
		IFile selectedFile2 = PrvResourceFileHelper.getInstance()
				.findCorrespondingFile(secondSelectedDataProvider);
		IFile secondConnectionFile = DQStructureComparer
				.getSecondComparisonLocalFile();
		IFile copyedFile2 = DQStructureComparer.copyedToDestinationFile(
				selectedFile2, secondConnectionFile);
		TypedReturnCode<TdDataProvider> returnProvider2 = DqRepositoryViewService
				.readFromFile(copyedFile2);
		if (!returnProvider2.isOk()) {
			throw new ReloadCompareException(returnProvider2.getMessage());
		}
		tempSecondSelectedDataProvider = returnProvider2.getObject();
		tempSecondSelectedDataProvider.setComponent(secondSelectedDataProvider
				.getComponent());
		DqRepositoryViewService.saveDataProviderResource(
				tempSecondSelectedDataProvider, (IFolder) secondConnectionFile
						.getParent(), secondConnectionFile);
		tempSecondSelectedDataProvider.setComponent(null);
	}

	private Resource getResource(int pos) throws ReloadCompareException {
		TdDataProvider tdProvider = null;
		Object selectedObj = null;
		switch (pos) {
		case LEFT_RESOURCE:
			selectedObj = firstSelectedObj;
			tdProvider = tempFirstSelectedDataProvider;
			break;
		case RIGHT_RESOURCE:
			selectedObj = secondSelectedObj;
			tdProvider = tempSecondSelectedDataProvider;
			break;
		default:
			break;
		}

		ModelElementAdapter meAdapter = new ModelElementAdapter();

		Object rootElement = meAdapter.getListModelElements(selectedObj,
				tdProvider);
		Resource leftResource = null;
		if (rootElement instanceof Resource) {
			leftResource = (Resource) rootElement;
		} else {
			// leftResource = tdProvider.eResource();
			// leftResource.getContents().clear();
			leftResource = ((ModelElement) rootElement).eResource();
			leftResource.getContents().clear();
			leftResource.getContents().add((ModelElement) rootElement);
		}
		EMFSharedResources.getInstance().saveResource(leftResource);
		return leftResource;
	}

	public void reloadCurrentLevelElement() throws ReloadCompareException {
	}

	/**
	 * 
	 * DOC mzhao Interface that do instanceof converter to provider common
	 * object to client.
	 */
	private class ModelElementAdapter {
		public TdDataProvider getAdaptableProvider(Object element) {
			TdDataProvider adaptedDataProvider = null;

			if (element instanceof IFile) {
				// IFile
				TypedReturnCode<TdDataProvider> returnVlaue = PrvResourceFileHelper
						.getInstance().findProvider((IFile) element);
				adaptedDataProvider = returnVlaue.getObject();
			} else {

				Package package1 = SwitchHelpers.PACKAGE_SWITCH
						.doSwitch((ModelElement) element);

				if (package1 != null) {
					adaptedDataProvider = DataProviderHelper
							.getTdDataProvider(package1);
				} else {
					ColumnSet columnSet1 = SwitchHelpers.COLUMN_SET_SWITCH
							.doSwitch((ModelElement) element);
					if (columnSet1 != null) {
						adaptedDataProvider = DataProviderHelper
								.getDataProvider(columnSet1);
					} else {
						Column column1 = SwitchHelpers.COLUMN_SWITCH
								.doSwitch((Column) element);
						if (column1 != null) {
							adaptedDataProvider = DataProviderHelper
									.getTdDataProvider(column1);
						}
					}

				}
			}
			return adaptedDataProvider;
		}

		public Object getListModelElements(Object element,
				TdDataProvider tdProvider) throws ReloadCompareException {

			Object rootElement = null;
			// List<ModelElement> meList = new ArrayList<ModelElement>();

			if (element instanceof IFile) {
				rootElement = tdProvider.eResource();
			} else {
				Package package1 = SwitchHelpers.PACKAGE_SWITCH
						.doSwitch((ModelElement) element);

				if (package1 != null) {
					Package findMatchPackage = DQStructureComparer
							.findMatchedPackage((Package) element, tdProvider);
					findMatchPackage.getDataManager().clear();
					rootElement = findMatchPackage;
					// meList.addAll(PackageHelper.getTables(findMatchPackage));
					// meList.addAll(PackageHelper.getViews(findMatchPackage));
				} else {
					ColumnSet columnSet1 = SwitchHelpers.COLUMN_SET_SWITCH
							.doSwitch((ModelElement) element);
					if (columnSet1 != null) {
						ColumnSet findMatchedColumnSet = DQStructureComparer
								.findMatchedColumnSet(columnSet1, tdProvider);
						rootElement = findMatchedColumnSet;
						// meList.addAll(ColumnSetHelper
						// .getColumns(findMatchedColumnSet));
					} else {
						Column column1 = SwitchHelpers.COLUMN_SWITCH
								.doSwitch((Column) element);
						if (column1 != null) {
							Column findMathedColumn = DQStructureComparer
									.findMatchedColumn(column1, tdProvider);
							rootElement = findMathedColumn;
							// meList.add(findMathedColumn);
						}
					}

				}
			}

			return rootElement;

		}
	}
}
