package se.kth.depclean.gradle.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public final class JarUtils {

    /**
     * Size of the buffer to read/write data.
     */
    private static final int BUFFER_SIZE = 16384;

    //--------------------------------/
    //-------- CONSTRUCTOR/S --------/
    //------------------------------/

    private JarUtils() {
    }

    //--------------------------------/
    //------- PUBLIC METHOD/S -------/
    //------------------------------/

    /**
     * Decompress all jar files located in a given directory.
     *
     * @param outputDirectory The directory path to put the decompressed files.
     */
    public static void decompressJars(final String outputDirectory) {
        File files = new File(outputDirectory);
        for (File f : Objects.requireNonNull(files.listFiles())) {
            if (f.getName().endsWith(".jar")) {
                try {
                    JarUtils.decompressJarFile(outputDirectory, f.getAbsolutePath());
                    // delete the original dependency jar file
                    f.delete();
                } catch (IOException e) {
                    System.err.println("Problem decompressing jar file.");
                }
            }
        }
    }

    //--------------------------------/
    //------ PRIVATE METHOD/S -------/
    //------------------------------/

    /**
     * Decompress a jar file in a path to a directory (will be created if it doesn't exists).
     */
    private static void decompressJarFile(String destDirectory, String jarFilePath) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarFilePath));
        JarEntry entry = jarIn.getNextJarEntry();
        // iterates over all the entries in the jar file
        while (entry != null) {
            String filePath = destDirectory + "/" + entry.getName();
            if (!entry.isDirectory()) {
                new File(filePath).getParentFile().mkdirs();
                // if the entry is a file, extracts it
                extractFile(jarIn, filePath);
            }/* else {
                System.out.println("New dir: " + filePath);
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
                System.out.println(dir.canWrite());
            }*/
            jarIn.closeEntry();
            entry = jarIn.getNextJarEntry();
        }
        jarIn.close();
    }

    /**
     * Extracts an entry file.
     *
     * @param jarIn The jar file to be extracted.
     * @param filePath Path to the file.
     * @throws IOException In case of IO issues.
     */
    private static void extractFile(final JarInputStream jarIn, final String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = jarIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
