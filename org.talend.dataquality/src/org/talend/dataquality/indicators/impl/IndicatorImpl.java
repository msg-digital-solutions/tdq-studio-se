/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.impl;

import java.sql.Types;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.relational.TdColumn;
import org.talend.dataquality.helpers.MetadataHelper;
import org.talend.dataquality.indicators.DataminingType;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.IndicatorParameters;
import org.talend.dataquality.indicators.IndicatorValueType;
import org.talend.dataquality.indicators.IndicatorsPackage;
import org.talend.dataquality.indicators.definition.IndicatorDefinition;
import orgomg.cwm.objectmodel.core.Expression;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.objectmodel.core.impl.ModelElementImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Indicator</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getCount <em>Count</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getNullCount <em>Null Count</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getAnalyzedElement <em>Analyzed Element</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getDataminingType <em>Datamining Type</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getIndicatorDefinition <em>Indicator Definition</em>}
 * </li>
 * <li>{@link org.talend.dataquality.indicators.impl.IndicatorImpl#getInstantiatedExpressions <em>Instantiated
 * Expressions</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class IndicatorImpl extends ModelElementImpl implements Indicator {

    private static Logger log = Logger.getLogger(IndicatorImpl.class);

    /**
     * The default value of the '{@link #getCount() <em>Count</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getCount()
     * @generated
     * @ordered
     */
    protected static final Long COUNT_EDEFAULT = new Long(0L);

    /**
     * The cached value of the '{@link #getCount() <em>Count</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getCount()
     * @generated
     * @ordered
     */
    protected Long count = COUNT_EDEFAULT;

    /**
     * The default value of the '{@link #getNullCount() <em>Null Count</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getNullCount()
     * @generated
     * @ordered
     */
    protected static final Long NULL_COUNT_EDEFAULT = new Long(0L);

    /**
     * The cached value of the '{@link #getNullCount() <em>Null Count</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getNullCount()
     * @generated
     * @ordered
     */
    protected Long nullCount = NULL_COUNT_EDEFAULT;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected IndicatorParameters parameters;

    /**
     * The cached value of the '{@link #getAnalyzedElement() <em>Analyzed Element</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getAnalyzedElement()
     * @generated
     * @ordered
     */
    protected ModelElement analyzedElement;

    /**
     * The default value of the '{@link #getDataminingType() <em>Datamining Type</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getDataminingType()
     * @generated
     * @ordered
     */
    protected static final DataminingType DATAMINING_TYPE_EDEFAULT = DataminingType.NOMINAL;

    /**
     * The cached value of the '{@link #getDataminingType() <em>Datamining Type</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getDataminingType()
     * @generated
     * @ordered
     */
    protected DataminingType dataminingType = DATAMINING_TYPE_EDEFAULT;

    /**
     * The cached value of the '{@link #getIndicatorDefinition() <em>Indicator Definition</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getIndicatorDefinition()
     * @generated
     * @ordered
     */
    protected IndicatorDefinition indicatorDefinition;

    /**
     * The cached value of the '{@link #getInstantiatedExpressions() <em>Instantiated Expressions</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInstantiatedExpressions()
     * @generated
     * @ordered
     */
    protected EList<Expression> instantiatedExpressions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected IndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return IndicatorsPackage.Literals.INDICATOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Long getCount() {
        return count;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setCount(Long newCount) {
        Long oldCount = count;
        count = newCount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.INDICATOR__COUNT, oldCount, count));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Long getNullCount() {
        return nullCount;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setNullCount(Long newNullCount) {
        Long oldNullCount = nullCount;
        nullCount = newNullCount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.INDICATOR__NULL_COUNT, oldNullCount,
                    nullCount));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public IndicatorParameters getParameters() {
        return parameters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetParameters(IndicatorParameters newParameters, NotificationChain msgs) {
        IndicatorParameters oldParameters = parameters;
        parameters = newParameters;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.INDICATOR__PARAMETERS, oldParameters, newParameters);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setParameters(IndicatorParameters newParameters) {
        if (newParameters != parameters) {
            NotificationChain msgs = null;
            if (parameters != null)
                msgs = ((InternalEObject) parameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.INDICATOR__PARAMETERS, null, msgs);
            if (newParameters != null)
                msgs = ((InternalEObject) newParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.INDICATOR__PARAMETERS, null, msgs);
            msgs = basicSetParameters(newParameters, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.INDICATOR__PARAMETERS, newParameters,
                    newParameters));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ModelElement getAnalyzedElement() {
        if (analyzedElement != null && analyzedElement.eIsProxy()) {
            InternalEObject oldAnalyzedElement = (InternalEObject) analyzedElement;
            analyzedElement = (ModelElement) eResolveProxy(oldAnalyzedElement);
            if (analyzedElement != oldAnalyzedElement) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndicatorsPackage.INDICATOR__ANALYZED_ELEMENT,
                            oldAnalyzedElement, analyzedElement));
            }
        }
        return analyzedElement;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ModelElement basicGetAnalyzedElement() {
        return analyzedElement;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setAnalyzedElement(ModelElement newAnalyzedElement) {
        ModelElement oldAnalyzedElement = analyzedElement;
        analyzedElement = newAnalyzedElement;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.INDICATOR__ANALYZED_ELEMENT,
                    oldAnalyzedElement, analyzedElement));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public DataminingType getDataminingType() {
        ModelElement elt = getAnalyzedElement();
        if (elt == null) {
            return getDataminingTypeGen();
        }
        TdColumn col = SwitchHelpers.COLUMN_SWITCH.doSwitch(elt);
        if (col == null) {
            return getDataminingTypeGen();
        }
        DataminingType type = MetadataHelper.getDataminingType(col);
        if (type == null) {
            // try the default code
            return getDataminingTypeGen();
        }
        return type;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DataminingType getDataminingTypeGen() {
        return dataminingType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDataminingType(DataminingType newDataminingType) {
        DataminingType oldDataminingType = dataminingType;
        dataminingType = newDataminingType == null ? DATAMINING_TYPE_EDEFAULT : newDataminingType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.INDICATOR__DATAMINING_TYPE,
                    oldDataminingType, dataminingType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public IndicatorDefinition getIndicatorDefinition() {
        if (indicatorDefinition != null && indicatorDefinition.eIsProxy()) {
            InternalEObject oldIndicatorDefinition = (InternalEObject) indicatorDefinition;
            indicatorDefinition = (IndicatorDefinition) eResolveProxy(oldIndicatorDefinition);
            if (indicatorDefinition != oldIndicatorDefinition) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndicatorsPackage.INDICATOR__INDICATOR_DEFINITION,
                            oldIndicatorDefinition, indicatorDefinition));
            }
        }
        return indicatorDefinition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public IndicatorDefinition basicGetIndicatorDefinition() {
        return indicatorDefinition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setIndicatorDefinition(IndicatorDefinition newIndicatorDefinition) {
        IndicatorDefinition oldIndicatorDefinition = indicatorDefinition;
        indicatorDefinition = newIndicatorDefinition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.INDICATOR__INDICATOR_DEFINITION,
                    oldIndicatorDefinition, indicatorDefinition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Expression> getInstantiatedExpressions() {
        if (instantiatedExpressions == null) {
            instantiatedExpressions = new EObjectContainmentEList<Expression>(Expression.class, this,
                    IndicatorsPackage.INDICATOR__INSTANTIATED_EXPRESSIONS);
        }
        return instantiatedExpressions;
    }

    /**
     * <!-- begin-user-doc --> Increments counts for each given data. <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean handle(Object data) {
        if (data == null) {
            nullCount++;
        }
        count++;
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean reset() {
        count = COUNT_EDEFAULT;
        nullCount = NULL_COUNT_EDEFAULT;
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getPurpose() {
        IndicatorDefinition def = this.getIndicatorDefinition();
        return (def != null) ? TaggedValueHelper.getPurpose(def) : "?? no purpose found for " + this.getName() + " ??";
        // return IndicatorDocumentationHandler.getPurpose(this.eClass().getClassifierID());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getLongDescription() {
        IndicatorDefinition def = this.getIndicatorDefinition();
        return (def != null) ? TaggedValueHelper.getDescription(def) : "?? no description found for " + this.getName() + " ??";
        // return IndicatorDocumentationHandler.getLongDescription(this.eClass().getClassifierID());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean prepare() {
        return this.reset();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean finalizeComputation() {
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean storeSqlResults(List<Object[]> objects) {
        // nothing to implement here, a generic indicator does not know how to handle a result.
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT scorreia 2008-04-30
     */
    public Expression getInstantiatedExpressions(String language) {
        if (language == null) {
            return null;
        }
        EList<Expression> expressions = this.getInstantiatedExpressions();
        for (Expression expression : expressions) {
            if (expression == null) {
                continue;
            }
            if (language.compareTo(expression.getLanguage()) == 0) {
                return expression;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT scorreia 2008-04-30
     */
    public boolean setInstantiatedExpression(Expression expression) {
        if (expression == null) {
            return false;
        }
        String language = expression.getLanguage();
        if (language == null) {
            return false;
        }
        Expression found = getInstantiatedExpressions(language);
        if (found != null) {
            found.setBody(expression.getBody());
        } else {
            getInstantiatedExpressions().add(expression);
        }
        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Long getIntegerValue() {
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Double getRealValue() {
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public IndicatorValueType getValueType() {
        return IndicatorValueType.INTEGER_VALUE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getInstanceValue() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see orgomg.cwm.objectmodel.core.impl.ModelElementImpl#getName()
     * 
     * ADDED scorreia 2008-05-02 return a default name if none has been set.
     */
    @Override
    public String getName() {
        String n = super.getName();
        if (n != null) {
            return n;
        }
        // else
        IndicatorDefinition def = getIndicatorDefinition();
        if (def != null) {
            return def.getName();
        }
        // else
        return this.eClass().getName();
    }

    /**
     * Method "checkResults" checks whether the result has the right number of elements (but some elements could be
     * null).
     * 
     * @param objects the results of the query
     * @param expectedSize the expected number of elements in the resulting array "objects"
     * @return true if ok
     */
    protected boolean checkResults(List<Object[]> objects, final int expectedSize) {
        if (objects == null || objects.isEmpty()) {
            log.error("Unexpected result: Result set is null or empty for the query.");
            return false;
        }
        for (Object[] array : objects) {
            if (array == null || expectedSize != array.length) {
                log.error("Unexpected result: " + array + ". Expected " + expectedSize + " columns as a result of the query.");
                return false;
            }
            if (log.isDebugEnabled()) {
                log.debug("Result of query: " + ArrayUtils.toString(array));
            }
            // for (int i = 0; i < array.length; i++) {
            // Object object = array[i];
            // // assume last column is not null (for example in frequency table result)
            // if (object == null && i == array.length - 1) {
            // log.error("Unexpected result: " + object + ". One of the column returned by the query is null!");
            // return false;
            // }
            // }
        }

        return true;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case IndicatorsPackage.INDICATOR__PARAMETERS:
            return basicSetParameters(null, msgs);
        case IndicatorsPackage.INDICATOR__INSTANTIATED_EXPRESSIONS:
            return ((InternalEList<?>) getInstantiatedExpressions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case IndicatorsPackage.INDICATOR__COUNT:
            return getCount();
        case IndicatorsPackage.INDICATOR__NULL_COUNT:
            return getNullCount();
        case IndicatorsPackage.INDICATOR__PARAMETERS:
            return getParameters();
        case IndicatorsPackage.INDICATOR__ANALYZED_ELEMENT:
            if (resolve)
                return getAnalyzedElement();
            return basicGetAnalyzedElement();
        case IndicatorsPackage.INDICATOR__DATAMINING_TYPE:
            return getDataminingType();
        case IndicatorsPackage.INDICATOR__INDICATOR_DEFINITION:
            if (resolve)
                return getIndicatorDefinition();
            return basicGetIndicatorDefinition();
        case IndicatorsPackage.INDICATOR__INSTANTIATED_EXPRESSIONS:
            return getInstantiatedExpressions();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case IndicatorsPackage.INDICATOR__COUNT:
            setCount((Long) newValue);
            return;
        case IndicatorsPackage.INDICATOR__NULL_COUNT:
            setNullCount((Long) newValue);
            return;
        case IndicatorsPackage.INDICATOR__PARAMETERS:
            setParameters((IndicatorParameters) newValue);
            return;
        case IndicatorsPackage.INDICATOR__ANALYZED_ELEMENT:
            setAnalyzedElement((ModelElement) newValue);
            return;
        case IndicatorsPackage.INDICATOR__DATAMINING_TYPE:
            setDataminingType((DataminingType) newValue);
            return;
        case IndicatorsPackage.INDICATOR__INDICATOR_DEFINITION:
            setIndicatorDefinition((IndicatorDefinition) newValue);
            return;
        case IndicatorsPackage.INDICATOR__INSTANTIATED_EXPRESSIONS:
            getInstantiatedExpressions().clear();
            getInstantiatedExpressions().addAll((Collection<? extends Expression>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case IndicatorsPackage.INDICATOR__COUNT:
            setCount(COUNT_EDEFAULT);
            return;
        case IndicatorsPackage.INDICATOR__NULL_COUNT:
            setNullCount(NULL_COUNT_EDEFAULT);
            return;
        case IndicatorsPackage.INDICATOR__PARAMETERS:
            setParameters((IndicatorParameters) null);
            return;
        case IndicatorsPackage.INDICATOR__ANALYZED_ELEMENT:
            setAnalyzedElement((ModelElement) null);
            return;
        case IndicatorsPackage.INDICATOR__DATAMINING_TYPE:
            setDataminingType(DATAMINING_TYPE_EDEFAULT);
            return;
        case IndicatorsPackage.INDICATOR__INDICATOR_DEFINITION:
            setIndicatorDefinition((IndicatorDefinition) null);
            return;
        case IndicatorsPackage.INDICATOR__INSTANTIATED_EXPRESSIONS:
            getInstantiatedExpressions().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case IndicatorsPackage.INDICATOR__COUNT:
            return COUNT_EDEFAULT == null ? count != null : !COUNT_EDEFAULT.equals(count);
        case IndicatorsPackage.INDICATOR__NULL_COUNT:
            return NULL_COUNT_EDEFAULT == null ? nullCount != null : !NULL_COUNT_EDEFAULT.equals(nullCount);
        case IndicatorsPackage.INDICATOR__PARAMETERS:
            return parameters != null;
        case IndicatorsPackage.INDICATOR__ANALYZED_ELEMENT:
            return analyzedElement != null;
        case IndicatorsPackage.INDICATOR__DATAMINING_TYPE:
            return dataminingType != DATAMINING_TYPE_EDEFAULT;
        case IndicatorsPackage.INDICATOR__INDICATOR_DEFINITION:
            return indicatorDefinition != null;
        case IndicatorsPackage.INDICATOR__INSTANTIATED_EXPRESSIONS:
            return instantiatedExpressions != null && !instantiatedExpressions.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * Method "getColumnType".
     * 
     * @return the column type of the analyzed object (when the analyzed object is a column). Otherwise, it returns
     * Types.JAVA_OBJECT.
     */
    protected int getColumnType() {
        int javaType = Types.JAVA_OBJECT; // default type
        ModelElement elt = this.getAnalyzedElement();
        if (elt != null) {
            TdColumn col = SwitchHelpers.COLUMN_SWITCH.doSwitch(elt);
            if (col != null) {
                javaType = col.getJavaType();
            }
        }
        return javaType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (count: ");
        result.append(count);
        result.append(", nullCount: ");
        result.append(nullCount);
        result.append(", dataminingType: ");
        result.append(dataminingType);
        result.append(')');
        return result.toString();
    }

} // IndicatorImpl
