/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.impl;

import org.eclipse.emf.ecore.EClass;
import org.talend.dataquality.indicators.AvgLengthWithBlankIndicator;
import org.talend.dataquality.indicators.IndicatorParameters;
import org.talend.dataquality.indicators.IndicatorsFactory;
import org.talend.dataquality.indicators.IndicatorsPackage;
import org.talend.dataquality.indicators.TextParameters;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Avg Length With Blank Indicator</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class AvgLengthWithBlankIndicatorImpl extends AverageLengthIndicatorImpl implements AvgLengthWithBlankIndicator {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected AvgLengthWithBlankIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return IndicatorsPackage.Literals.AVG_LENGTH_WITH_BLANK_INDICATOR;
    }


    @Override
    public IndicatorParameters getParameters() {
        // MOD yyi 2010-12-23 17740:enable thresholds
        if (parameters != null) {
            TextParameters textParameters = parameters.getTextParameter();
            if (textParameters == null) {
                textParameters = IndicatorsFactory.eINSTANCE.createTextParameters();
            }
            textParameters.setUseNulls(false);
            textParameters.setUseBlank(true);
            parameters.setTextParameter(textParameters);
        }
        return parameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public boolean handle(Object data) {
        // override super.handle(data);
        if (data == null) {
            nullCount++;
        } else {
            count++;

            // blank strings count as zero length strings
            if (((String) data).trim().length() > 0) {
                String str = (String) data;
                sumLength += str.length();
            }
        }
        return true;
    }


} // AvgLengthWithBlankIndicatorImpl
