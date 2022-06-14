package de.fhg.iais.roberta.syntax.sensor.generic;

import de.fhg.iais.roberta.syntax.sensor.ExternalSensor;
import de.fhg.iais.roberta.transformer.forClass.NepoExternalSensor;
import de.fhg.iais.roberta.transformer.forClass.NepoExpr;
import de.fhg.iais.roberta.util.syntax.BlocklyBlockProperties;
import de.fhg.iais.roberta.util.syntax.BlocklyComment;
import de.fhg.iais.roberta.util.syntax.SensorMetaDataBean;

@NepoExpr(category = "SENSOR", blocklyNames = {"robsensors_colourtcs3472_getsample", "sim_colour_getSample", "robSensors_colour_getSample"}, containerType = "COLOR_SENSING")
@NepoExternalSensor()
public class ColorSensor<V> extends ExternalSensor<V> {

    public ColorSensor(BlocklyBlockProperties properties, BlocklyComment comment, SensorMetaDataBean sensorMetaDataBean) {
        super(properties, comment, sensorMetaDataBean);
        setReadOnly();
    }

    public static <V> ColorSensor<V> make(SensorMetaDataBean sensorMetaDataBean, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new ColorSensor<V>(properties, comment, sensorMetaDataBean);
    }

}
