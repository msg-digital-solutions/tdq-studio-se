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
package org.talend.dq.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.talend.cwm.management.i18n.Messages;
import org.talend.dataquality.domain.pattern.Pattern;
import org.talend.dataquality.helpers.MetadataHelper;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.definition.DefinitionFactory;
import org.talend.dataquality.indicators.definition.IndicatorCategory;
import org.talend.dataquality.indicators.definition.IndicatorDefinition;
import org.talend.dataquality.indicators.sql.UserDefIndicator;
import org.talend.dq.helper.resourcehelper.UDIResourceFileHelper;
import org.talend.dq.indicators.definitions.DefinitionHandler;
import org.talend.utils.sugars.ReturnCode;
import orgomg.cwm.objectmodel.core.Expression;

/**
 * DOC xqliu class global comment. Detailled comment
 */
public final class UDIHelper {

    private UDIHelper() {
    }

    private static Logger log = Logger.getLogger(UDIHelper.class);

    // ("select * from *").length
    private static final int MIN_EXPRESSION_LENGTH = 16;

    public static IndicatorCategory getUDICategory(IndicatorDefinition indicatorDefinition) {
        if (indicatorDefinition != null) {
            EList<IndicatorCategory> categories = indicatorDefinition.getCategories();
            if (categories != null && categories.size() > 0) {
                return categories.get(0);
            }
        }
        return null;
    }

    public static IndicatorCategory getUDICategory(Indicator indicator) {
        if (indicator != null) {
            return getUDICategory(indicator.getIndicatorDefinition());
        }
        return null;
    }

    /**
     * Set the category of the IndicatorDefinition, if the category is null set UserDefinedCount category.
     * 
     * @param definition
     * @param category
     */
    public static void setUDICategory(IndicatorDefinition definition, IndicatorCategory category) {
        if (definition != null) {
            if (category == null) {
                category = DefinitionHandler.getInstance().getUserDefinedCountIndicatorCategory();
            }

            EList<IndicatorCategory> categories = definition.getCategories();
            if (categories != null) {
                categories.clear();
                categories.add(category);
            }
        }
    }

    public static void setUDICategory(IndicatorDefinition indicatorDefinition, String categoryLabel) {
        setUDICategory(indicatorDefinition, DefinitionHandler.getInstance().getIndicatorCategoryByLabel(categoryLabel));
    }

    public static void setUDICategory(Indicator indicator, String categoryLabel) {
        if (indicator != null) {
            setUDICategory(indicator.getIndicatorDefinition(), categoryLabel);
        }
    }

    public static IndicatorDefinition createUDI(String name, String author, String description, String purpose, String status,
            String category) {
        IndicatorDefinition id = DefinitionFactory.eINSTANCE.createIndicatorDefinition();
        id.setName(name);
        MetadataHelper.setAuthor(id, author == null ? "" : author); //$NON-NLS-1$
        MetadataHelper.setDescription(description == null ? "" : description, id); //$NON-NLS-1$
        MetadataHelper.setPurpose(purpose == null ? "" : purpose, id); //$NON-NLS-1$
        // MOD mzhao feature 7479 2009-10-16
        MetadataHelper.setDevStatus(id, status == null ? "" : status); //$NON-NLS-1$
        setUDICategory(id, category);
        return id;
    }

    public static Set<String> getAllIndicatorNames(IFolder folder) {
        Set<String> list = new HashSet<String>();
        return getNestFolderIndicatorNames(list, folder);
    }

    private static Set<String> getNestFolderIndicatorNames(Set<String> list, IFolder folder) {
        try {
            for (IResource resource : folder.members()) {
                if (resource instanceof IFile) {
                    IndicatorDefinition id = UDIResourceFileHelper.getInstance().findUDI((IFile) resource);
                    if (id != null) {
                        list.add(id.getName());
                    }
                } else {
                    getNestFolderIndicatorNames(list, (IFolder) resource);
                }
            }
        } catch (CoreException e) {
            log.error(e, e);
        }
        return list;
    }

    public static String getMatchingIndicatorName(IndicatorDefinition indicatorDefinition, Pattern pattern) {
        if (indicatorDefinition != null) {
            return pattern.getName() + "(" + indicatorDefinition.getName() + ")";
        } else {
            return pattern.getName();
        }
    }

    public static boolean isCount(Indicator indicator) {
        return isCategory(indicator, DefinitionHandler.getInstance().getUserDefinedCountIndicatorCategory());
    }

    public static boolean isFrequency(Indicator indicator) {
        return isCategory(indicator, DefinitionHandler.getInstance().getUserDefinedFrequencyIndicatorCategory());
    }

    public static boolean isMatching(Indicator indicator) {
        return isCategory(indicator, DefinitionHandler.getInstance().getUserDefinedMatchIndicatorCategory());
    }

    public static boolean isCategory(Indicator indicator, IndicatorCategory indicatorCategory) {
        if (indicator != null) {
            return isCategory(indicator.getIndicatorDefinition(), indicatorCategory);
        }
        return false;
    }

    public static boolean isCategory(IndicatorDefinition indicatorDefinition, IndicatorCategory indicatorCategory) {
        if (indicatorDefinition != null && indicatorCategory != null) {
            return indicatorCategory.equals(getUDICategory(indicatorDefinition));
        }
        return false;
    }

    public static boolean isUDI(Indicator indicator) {
        return indicator instanceof UserDefIndicator;
    }

    /**
     * yyi 2009-09-22 To check the expression is null, empty or less than 16 characters Feature : 8866
     */
    public static boolean isUDIValid(IndicatorDefinition indicatorDefinition) {

        if (0 == indicatorDefinition.getSqlGenericExpression().size()) {
            return false;
        }

        if ("".equals(indicatorDefinition.getName())) {
            return false;
        }

        if ('\'' == indicatorDefinition.getName().charAt(0)) {
            return false;
        }

        for (Expression exp : indicatorDefinition.getSqlGenericExpression()) {
            if (null == exp.getBody() || exp.getBody().length() + 1 < MIN_EXPRESSION_LENGTH) {
                return false;
            }
        }

        return true;
    }

    public static ReturnCode validate(IndicatorDefinition indicatorDefinition) {

        ReturnCode rc = new ReturnCode(true);
        List<String> errorList = new ArrayList<String>();

        if (0 == indicatorDefinition.getSqlGenericExpression().size()) {
            errorList.add(Messages.getString("UDIHelper.validateNoExpression"));
            rc.setOk(false);
        }

        if ("".equals(indicatorDefinition.getName())) {
            errorList.add(Messages.getString("UDIHelper.validateNoName"));
            rc.setOk(false);
        }

        for (Expression exp : indicatorDefinition.getSqlGenericExpression()) {
            if (null == exp.getBody() || exp.getBody().length() + 1 < MIN_EXPRESSION_LENGTH) {
                errorList.add(Messages.getString("UDIHelper.validateTooShort"));
                rc.setOk(false);
            }
        }

        String message = Messages.getString("UDIHelper.validateCannotSave");
        String wrap = System.getProperty("line.separator");
        for (int i = 0; i < errorList.size(); i++) {
            message += wrap + (i + 1) + "." + errorList.get(i);
        }
        rc.setMessage(message);

        return rc;
    }
}
