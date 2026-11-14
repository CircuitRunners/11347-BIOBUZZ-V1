package org.firstinspires.ftc.teamcode.Subsystems.Subclasses;

import org.firstinspires.ftc.teamcode.Util.enums.COMMAND;
import org.firstinspires.ftc.teamcode.Util.enums.GAMEPIECE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CaseTable {
    private HashMap<List<GAMEPIECE>, Object[]> cases = new HashMap<>();

    public CaseTable() {
        cases.put(Arrays.asList(GAMEPIECE.POLLEN, GAMEPIECE.POLLEN),
                new Object[]{
                        COMMAND.START,
                        0.2,
                        COMMAND.POLLEN,
                        0.3,
                        COMMAND.ENGAGE,
                        0.15,
                        COMMAND.DISENGAGE,
                        0.15,
                        COMMAND.CAP,
                        0.25,
                        COMMAND.CAP_2,
                        0.1,
                        COMMAND.ENGAGE,
                        0.2,
                        COMMAND.STOP
                });
        cases.put(Arrays.asList(GAMEPIECE.NECTAR, GAMEPIECE.NECTAR),
                new Object[]{
                        COMMAND.START,
                        0.2,
                        COMMAND.NECTAR,
                        0.4,
                        COMMAND.ENGAGE,
                        0.20,
                        COMMAND.DISENGAGE,
                        0.1,
                        COMMAND.CAP,
                        0.2,
                        COMMAND.CAP_2,
                        0.1,
                        COMMAND.ENGAGE,
                        0.2,
                        COMMAND.STOP
                });
        cases.put(Arrays.asList(GAMEPIECE.NECTAR, GAMEPIECE.POLLEN), //DONE
                new Object[]{
                        COMMAND.START,
                        0.2,
                        COMMAND.POLLEN,
                        0.4,
                        COMMAND.ENGAGE,
                        0.2,
                        COMMAND.DISENGAGE,
                        0.1,
                        COMMAND.CAP,
                        0.2,
                        COMMAND.CAP_2,
                        0.15,
                        COMMAND.NECTAR,
                        0.35,
                        COMMAND.ENGAGE,
                        0.2,
                        COMMAND.STOP
                });
        cases.put(Arrays.asList(GAMEPIECE.POLLEN, GAMEPIECE.NECTAR), //DONE
                new Object[]{
                        COMMAND.START,
                        0.2,
                        COMMAND.NECTAR,
                        0.5,
                        COMMAND.ENGAGE,
                        0.15,
                        COMMAND.DISENGAGE,
                        0.1,
                        COMMAND.CAP,
                        0.2,
                        COMMAND.CAP_2,
                        0.1,
                        COMMAND.POLLEN,
                        0.25,
                        COMMAND.ENGAGE,
                        0.2,
                        COMMAND.STOP
                });
        cases.put(Arrays.asList(GAMEPIECE.NONE, GAMEPIECE.POLLEN),
                new Object[]{
                        COMMAND.START,
                        0.2,
                        COMMAND.POLLEN,
                        0.3,
                        COMMAND.ENGAGE,
                        0.25,
                        COMMAND.DISENGAGE,
                        0.1,
                        COMMAND.STOP
                });
        cases.put(Arrays.asList(GAMEPIECE.NONE, GAMEPIECE.NECTAR),
                new Object[]{
                        COMMAND.START,
                        0.2,
                        COMMAND.NECTAR,
                        0.35,
                        COMMAND.ENGAGE,
                        0.25,
                        COMMAND.DISENGAGE,
                        0.1,
                        COMMAND.STOP
                });
    }

    public Object[] getOrder(GAMEPIECE[] input) {
        Object[] order = cases.get(Arrays.asList(input));
        if (order == null) {
            return new Object[]{
                    COMMAND.START,
                    0.6,
                    COMMAND.STOP
            };
        }
        return order;
    }
}
