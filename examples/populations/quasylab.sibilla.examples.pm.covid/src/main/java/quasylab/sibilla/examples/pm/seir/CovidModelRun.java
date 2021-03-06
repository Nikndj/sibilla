/*
 * Sibilla:  a Java framework designed to support analysis of Collective
 * Adaptive Systems.
 *
 * Copyright (C) 2020.
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package quasylab.sibilla.examples.pm.seir;

import quasilab.sibilla.core.ExecutionEnvironment;
import quasylab.sibilla.core.models.pm.PopulationState;
import quasylab.sibilla.core.simulator.DefaultRandomGenerator;
import quasylab.sibilla.core.simulator.SimulationEnvironment;
import quasylab.sibilla.core.simulator.sampling.SamplingCollection;
import quasylab.sibilla.core.simulator.sampling.StatisticSampling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author loreti
 *
 */
public class CovidModelRun {



    public static void main(String[] argv) throws IOException {
        CovidDefinition def = new CovidDefinition();
        ExecutionEnvironment<PopulationState> ee = new ExecutionEnvironment<>(
                new DefaultRandomGenerator(),
                def.createModel(),
                def.state()
        );
        start(ee);
    }

    private static void start(ExecutionEnvironment<PopulationState> ee) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        do {
            System.out.println("STEPS: "+ee.steps());
            System.out.println("TIME UNITS: "+ee.currentTime());
            System.out.println(ee.currentState().toString());
            String cmd = input.readLine();
            if (!cmd.isEmpty()) {
                flag = execute(cmd.charAt(0),ee);
            }
        } while (flag);
    }

    private static boolean execute(char c, ExecutionEnvironment<PopulationState> ee) {
        switch (c) {
            case 'n':
                ee.step();
                return true;
            case 'p':
                ee.previous();
                return true;
            case 'r':
                ee.restart();
                return true;
            case 'q':
                return false;
            default:
                System.err.println("Unknown command "+c);
                System.err.println("Use n, p, r or q.");
        }
        return true;
    }


}
