package com.rjuarez.webapp.spring;

import java.util.List;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Adds commons validator configuration files to an existing Spring commons
 * Validator Factory bean, possibly defined within a seperate Spring
 * configuration file in a seperate jar file. By using this extension factory
 * developers can add validation configuration for their own persistent classes
 * to an Sharity application without modifying any of the existing Sharity
 * Spring configuration or jar distribution files.
 * 
 * As an example consider the following Spring bean configuration:
 * 
 * <pre>
 * &lt;bean class=&quot;com.rjuarez.spring.ValidatorExtensionPostProcessor&quot;&gt;
 *   &lt;property name=&quot;validationConfigLocations&quot;&gt;
 *     &lt;list&gt;
 *       &lt;value&gt;/WEB-INF/foo-validation.xml&lt;/value&gt;
 *     &lt;/list&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * The sample adds a single validation configuration file (foo-validation.xml)
 * to an existing Spring commons Validator Factory bean (a bean of class
 * org.springmodules.validation.commons.DefaultValidatorFactory). Assuming the
 * validator extension is included in a Spring configuration file called
 * applicationContext-foo-validation.xml, and that this configuration file is
 * added directly below WEB-INF in the Foo web project, then the normal war
 * overlay process coupled with Sharity's configuration file auto detection will
 * ensure that the validation extension is automatically included into the
 * application without requiring any modification of Sharity's existing config
 * files.
 *
 * @author Michael Horwitz
 */
public class ValidatorExtensionPostProcessor implements BeanFactoryPostProcessor {
    private String validatorFactoryBeanName = "validatorFactory";
    private List<Map<String, Object[]>> validationConfigLocations;

    /**
     * Adds the validation configuration files to the list already held in the
     * validator factory bean configuration.
     * 
     * @param configurableListableBeanFactory
     *            the bean factory
     */
    @Override
    @SuppressWarnings("unchecked")
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) {
        if (configurableListableBeanFactory.containsBean(validatorFactoryBeanName)) {
            final BeanDefinition validatorFactoryBeanDefinition = configurableListableBeanFactory.getBeanDefinition(validatorFactoryBeanName);
            final MutablePropertyValues propertyValues = validatorFactoryBeanDefinition.getPropertyValues();
            final PropertyValue propertyValue = propertyValues.getPropertyValue("validationConfigLocations");

            // value is expected to be a list.
            final List<Map<String, Object[]>> existingValidationConfigLocations = (List<Map<String, Object[]>>) propertyValue.getValue();
            existingValidationConfigLocations.addAll(validationConfigLocations);
        }
    }

    /**
     * Set the name of the validator factory bean. This defaults to
     * &quot;validatorFactory&quot;
     *
     * @param validatorFactoryBeanName
     *            The validator factory bean name.
     */
    public void setValidatorFactoryBeanName(final String validatorFactoryBeanName) {
        this.validatorFactoryBeanName = validatorFactoryBeanName;
    }

    /**
     * The list of validation config locations to be added to the validator
     * factory.
     *
     * @param validationConfigLocations
     *            The list of additional validation configuration locations.
     */
    public void setValidationConfigLocations(final List<Map<String, Object[]>> validationConfigLocations) {
        this.validationConfigLocations = validationConfigLocations;
    }
}
