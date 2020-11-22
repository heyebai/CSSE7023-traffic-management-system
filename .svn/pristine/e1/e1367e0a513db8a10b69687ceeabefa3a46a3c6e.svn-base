package tms.congestion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tms.sensors.DemoPressurePad;
import tms.sensors.DemoSpeedCamera;
import tms.sensors.DemoVehicleCount;
import tms.sensors.Sensor;
import tms.util.TimedItemManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AveragingCongestionCalculatorTest {
    private DemoPressurePad dpp;
    private DemoSpeedCamera dsc;
    private DemoVehicleCount dvc;
    private List<Sensor> sensors;
    private AveragingCongestionCalculator acc;
    private int threshold;
    private int[] data;

    @Before
    public void setUp() throws Exception {
        threshold = 100;
        data = new int[]{0, 55, 100, 110};
        dpp = new DemoPressurePad(data, threshold);
        dsc = new DemoSpeedCamera(data, threshold);
        dvc = new DemoVehicleCount(data, threshold);
        sensors = new ArrayList<>();
        acc = new AveragingCongestionCalculator(sensors);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calculateCongestion() {
        assertEquals(0, acc.calculateCongestion());
        sensors.add(dpp);
        assertEquals(0, acc.calculateCongestion());
        sensors.add(dsc);
        assertEquals(50, acc.calculateCongestion());
        sensors.add(dvc);
        assertEquals(67, acc.calculateCongestion());
        TimedItemManager.getTimedItemManager().oneSecond();
        assertEquals(48, acc.calculateCongestion());
        TimedItemManager.getTimedItemManager().oneSecond();
        assertEquals(33, acc.calculateCongestion());
        TimedItemManager.getTimedItemManager().oneSecond();
        assertEquals(33, acc.calculateCongestion());
    }
}
