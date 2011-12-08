package info.folone.roy.maven;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * Goal which compiles roy files.
 *
 * @goal compile
 * 
 * @phase process-sources
 */
public class RoyMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     * @parameter expression="${js-directory}"
     * @required
     */
    private File outputDirectory;

    /**
     * Location of the file.
     * @parameter expression="${roy-directory}"
     * @required
     */
    private File inputDirectory;

    public void execute()
        throws MojoExecutionException
    {
        MojoHelper.doActualWork(inputDirectory, outputDirectory, getLog());
    }
}
