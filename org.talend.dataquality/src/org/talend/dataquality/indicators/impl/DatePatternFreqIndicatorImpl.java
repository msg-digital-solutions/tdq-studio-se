/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.osgi.framework.Bundle;
import org.talend.dataquality.indicators.DatePatternFreqIndicator;
import org.talend.dataquality.indicators.IndicatorsPackage;
import org.talend.dataquality.matching.date.pattern.DatePatternRetriever;
import org.talend.dataquality.matching.date.pattern.ModelMatcher;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Date Pattern Freq Indicator</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class DatePatternFreqIndicatorImpl extends PatternFreqIndicatorImpl implements DatePatternFreqIndicator {

    private DatePatternRetriever dateRetriever;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected DatePatternFreqIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return IndicatorsPackage.Literals.DATE_PATTERN_FREQ_INDICATOR;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.PatternFreqIndicatorImpl#prepare()
     */
    @Override
    public boolean prepare() {
        dateRetriever = new DatePatternRetriever();
        Bundle bundle = Platform.getBundle(org.talend.dataquality.matching.Activator.PLUGIN_ID);
        URL url = bundle.getResource("PatternsNameAndRegularExpressions.txt");
        String filepath = null;
        try {
            filepath = FileLocator.toFileURL(url).getFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File file = new File(filepath);
        dateRetriever.initModel2Regex(file);

        return super.prepare();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.PatternFreqIndicatorImpl#handle(java.lang.Object)
     */
    @Override
    public boolean handle(Object data) {
        if (data != null) {
            dateRetriever.handle(filterDate(data));
        }

        return super.handle(data);
    }

    @Override
    public Double getFrequency(Object dataValue) {
        if (this.count.compareTo(0L) == 0) {
            return Double.NaN;
        } else

        if (dataValue instanceof ModelMatcher) {
            ModelMatcher dataMatcher = (ModelMatcher) dataValue;
            return ((double) dataMatcher.getScore()) / this.getCount().longValue();
        }
        return super.getFrequency(dataValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#storeSqlResults(java.util.List)
     */
    @Override
    public boolean storeSqlResults(List<Object[]> objects) {
        List<ModelMatcher> modelMatchers = dateRetriever.getModelMatchers();
        ModelMatcher[] matchersArray = modelMatchers.toArray(new ModelMatcher[modelMatchers.size()]);
        ArrayList<Object[]> objects2 = new ArrayList<Object[]>();
        objects2.add(matchersArray);
        return super.storeSqlResults(objects2);
    }

    public List<ModelMatcher> getModelMatcherList() {
        return dateRetriever.getModelMatchers();
    }

    public List<Object> getRealModelMatcherList() {
        List<Object> realModelMatcherList = new ArrayList<Object>();
        for (ModelMatcher matcher : dateRetriever.getModelMatchers()) {
            if (matcher.getScore() > 0) {
                realModelMatcherList.add(matcher);
            }
        }
        return realModelMatcherList;
    }

    public String getModel(Object matcher) {
        if (matcher instanceof ModelMatcher) {
            return ((ModelMatcher) matcher).getModel();
        } else {
            return null;
        }
    }

    public int getScore(Object matcher) {
        if (matcher instanceof ModelMatcher) {
            return ((ModelMatcher) matcher).getScore();
        } else {
            return -1;
        }
    }

    /**
     * 
     * DOC zshen Comment method "filterDate".
     * 
     * @param data the data Object of database.
     * @return Real String value .
     * @Mean if the type is Timestamp,the value will add nanosecond by toString() return.but the nanosecond is not exist
     * in the database and we will not pattern it,so filter the nanosecond in the case.
     */
    public String filterDate(Object data) {
        if (data instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) data;
            if (timestamp.getNanos() == 0) {
                return timestamp.toString().split("\\.")[0];
            }
        }
        return data.toString();
    }

} // DatePatternFreqIndicatorImpl
