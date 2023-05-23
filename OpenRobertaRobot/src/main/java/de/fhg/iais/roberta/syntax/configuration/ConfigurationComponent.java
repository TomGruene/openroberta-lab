package de.fhg.iais.roberta.syntax.configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Instance;
import de.fhg.iais.roberta.components.Category;
import de.fhg.iais.roberta.factory.BlocklyDropdownFactory;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.transformer.Ast2Jaxb;
import de.fhg.iais.roberta.transformer.Jaxb2ConfigurationAst;
import de.fhg.iais.roberta.util.Util;
import de.fhg.iais.roberta.util.ast.BlockDescriptor;
import de.fhg.iais.roberta.util.ast.BlocklyProperties;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.util.syntax.SC;

/**
 * Representation of old/new configuration blocks in the AST. May have subclasses which override {@link ConfigurationComponent#ast2xml()} in order to
 * implement changed behaviour, these must follow the naming structure defined in {@link Jaxb2ConfigurationAst#BRICK_BLOCK_PATTERN} as implemented over in
 * {@link Jaxb2ConfigurationAst#instance2NewConfigComp(Instance, BlocklyDropdownFactory)}
 * TODO: this subclassing should be removed and the class declared final
 * if possible
 * TODO: this class should be made abstract with astToBlock as the abstract method and ConfigurationComponentLeaf as the new normal configurationComponent
 */
public class ConfigurationComponent extends Phrase {
    public final String componentType;
    public final String category;
    public final String userDefinedPortName;
    public final String internalPortName;
    public final LinkedHashMap<String, String> componentProperties;
    public final int x;
    public final int y;

    /**
     * Should only be used by tests! TODO: remove this if possible
     */
    public ConfigurationComponent(
        String name,
        String category,
        String internalPortName,
        String userDefinedPortName,
        Map<String, String> componentProperties) {
        this(
            name,
            category,
            internalPortName,
            userDefinedPortName,
            componentProperties,
            BlocklyProperties.make(name, "1"),
            0,
            0);
    }

    /**
     * Creates a configuration component. Should only be used by {@link Jaxb2ConfigurationAst}.
     *
     * @param name the name of the configuration component
     * @param category whether the component is an actor or not
     * @param internalPortName the internal port name, may represent the name used for generating code
     * @param userDefinedPortName the user defined port name
     * @param componentProperties the map of component properties
     * @param properties the blockly block properties
     * @param comment the blockly comment
     * @param x the x location
     * @param y the y location
     */
    public ConfigurationComponent(
        String name,
        String category,
        String internalPortName,
        String userDefinedPortName,
        Map<String, String> componentProperties,
        BlocklyProperties properties,

        int x,
        int y) {
        super(new BlockDescriptor(name, Category.valueOf(category), ConfigurationComponent.class, new String[0], Collections.emptyMap()), properties);
        Util.sanitizeConfigurationProperties(componentProperties);
        this.componentType = name;
        this.category = category;
        this.internalPortName = Util.sanitizeConfigurationInternalPortName(internalPortName);
        this.userDefinedPortName = userDefinedPortName;
        this.componentProperties = new LinkedHashMap<>(componentProperties);
        this.x = x;
        this.y = y;
    }

    public String category() {
        return this.category;
    }

    public boolean isRegulated() {
        return getProperty(SC.MOTOR_REGULATION).equals(SC.TRUE);
    }

    public boolean isReverse() {
        return getProperty(SC.MOTOR_REVERSE).equals(SC.ON);
    }

    public String getSide() {
        switch ( getProperty(SC.MOTOR_DRIVE) ) {
            case SC.LEFT:
                return SC.LEFT;
            case SC.RIGHT:
                return SC.RIGHT;
            default:
                return SC.NONE;
        }
    }

    public LinkedHashMap<String, List<ConfigurationComponent>> getSubComponents() {
        throw new UnsupportedOperationException("ConfigurationComponent does not have subcomponents");
    }

    public Map<String, String> getComponentProperties() {
        return Collections.unmodifiableMap(this.componentProperties);
    }

    public String getProperty(String propertyName) {
        Assert.nonEmptyString(propertyName, "No valid property name %s", propertyName);
        String propertyValue = this.componentProperties.get(propertyName);
        Assert.notNull(propertyValue, "No property with name %s", propertyName);

        return propertyValue;
    }

    public String getOptProperty(String propertyName) {
        Assert.nonEmptyString(propertyName, "No valid property name %s", propertyName);
        return this.componentProperties.get(propertyName);
    }

    @Override
    public String toString() {
        return "ConfigurationComponent ["
            + "componentType="
            + this.componentType
            + ", category="
            + this.category
            + ", userDefinedName="
            + this.userDefinedPortName
            + ", portName="
            + this.internalPortName
            + ", componentProperties="
            + this.componentProperties
            + "]";
    }

    @Override
    public Block ast2xml() {
        Block destination = new Block();
        Ast2Jaxb.setBasicProperties(this, destination);
        Ast2Jaxb.addField(destination, "NAME", this.userDefinedPortName);
        this.componentProperties.forEach((key, value) -> Ast2Jaxb.addField(destination, key, value));
        return destination;
    }

}
