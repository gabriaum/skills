package com.gabriaum.skills.util.loader;

import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoader {

    public static ArrayList<Class<?>> getClassesForPackage(JavaPlugin plugin, String pkgname) {
        ArrayList<Class<?>> classes = new ArrayList<>();

        CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            processJarfile(src.getLocation(), pkgname, classes);
        }
        return classes;
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class " + className + "");
        }
    }

    private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) {
        final var CLASS_EXTENSION = ".class";
        final var JAR_EXTENSION = ".jar";
        final var FILE_PROTOCOL = "file:";
        final var PATH_SEPARATOR = "/";
        final var PACKAGE_SEPARATOR = ".";

        var relPath = StringUtils.replace(pkgname, PACKAGE_SEPARATOR, PATH_SEPARATOR);
        var decodedPath = URLDecoder.decode(resource.getPath(), StandardCharsets.UTF_8);

        var jarPath = decodedPath.replaceFirst(JAR_EXTENSION + "![^" + PATH_SEPARATOR + "]*$", JAR_EXTENSION).replaceFirst(FILE_PROTOCOL, "");
        var jarFile = new File(jarPath);

        if (!jarFile.exists() || !jarFile.isFile()) {
            throw new IllegalArgumentException("Invalid JAR file path: " + jarPath);
        }

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                var entryPath = entry.getName();

                String className = null;

                if (entryPath.endsWith(CLASS_EXTENSION) && entryPath.startsWith(relPath + PATH_SEPARATOR) && entryPath.length() > (relPath.length() + PATH_SEPARATOR.length())) {
                    className = entryPath.replace(PATH_SEPARATOR, PACKAGE_SEPARATOR).replace(CLASS_EXTENSION, "");
                }
                if (className != null) {
                    classes.add(loadClass(className));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR file " + jarPath, e);
        }
    }
}
