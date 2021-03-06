/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.arquillian.spacelift.task.io;

import org.arquillian.spacelift.task.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An utility task for file selection. It has no input and outputs a {@link List} of {@link File}s. This is basically
 * just a more fluent way of running
 * <code>Tasks.chain(Arrays.asList(File, File, File), Class<Task>)</code>
 * which lets you select the files by using its methods.
 */
public class FileSelector extends Task<Object, List<File>> {

    private List<File> files = new ArrayList<File>();

    /**
     * Adds the given collection of files into the selection.
     */
    public FileSelector select(Collection<File> files) {
        this.files.addAll(files);
        return this;
    }

    /**
     * Adds one or multiple given files into the selection.
     */
    public FileSelector select(File requiredFile, File... files) {
        this.files.add(requiredFile);
        Collections.addAll(this.files, files);
        return this;
    }

    /**
     * Converts the given paths to instances of {@link File} and add them into the selection.
     */
    public FileSelector select(String requiredPath, String... paths) {
        select(new File(requiredPath));
        for (String path : paths) {
            select(new File(path));
        }
        return this;
    }

    @Override
    protected List<File> process(Object input) throws Exception {
        return files;
    }
}
