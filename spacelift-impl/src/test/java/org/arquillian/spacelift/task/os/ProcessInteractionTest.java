/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.spacelift.task.os;

import static org.hamcrest.CoreMatchers.is;

import org.apache.commons.lang3.SystemUtils;
import org.arquillian.spacelift.Spacelift;
import org.arquillian.spacelift.execution.Execution;
import org.arquillian.spacelift.process.ProcessInteractionBuilder;
import org.arquillian.spacelift.process.ProcessResult;
import org.arquillian.spacelift.task.os.CommandTool;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Checks basic process IO support
 *
 * @author <a href="kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class ProcessInteractionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void startText() {

        // run only on linux
        Assume.assumeThat(SystemUtils.IS_OS_LINUX, is(true));

        Execution<ProcessResult> cat = Spacelift.task(CommandTool.class)
            .programName("cat")
            .shouldExitWith(143)
            .interaction(new ProcessInteractionBuilder()
                .whenStarts()
                .typeIn("hello")
                .when("hello")
                .replyWith(" Spacelift")
                .when(".*Spacelift")
                .terminate())
            .execute();

        cat.await();
    }

    @Test
    public void endlineIO() {
        // run only on linux
        Assume.assumeThat(SystemUtils.IS_OS_LINUX, is(true));

        Execution<ProcessResult> cat = Spacelift.task(CommandTool.class)
            .programName("cat")
            .shouldExitWith(143)
            .interaction(new ProcessInteractionBuilder()
                .whenStarts()
                .typeIn("hello")
                .when("hello")
                .replyWith("Spacelift\n")
                .when("Spacelift")
                .terminate())
            .execute();

        cat.await();
    }
}
