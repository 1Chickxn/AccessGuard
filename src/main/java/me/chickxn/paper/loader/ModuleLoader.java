package me.chickxn.paper.loader;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ModuleLoader {

    private final File moduleFolder;
    private final List<Module> loadedModules = new ArrayList<>();

    public ModuleLoader(File moduleFolder) {
        this.moduleFolder = moduleFolder;
        this.moduleFolder.mkdirs();
    }

    @SneakyThrows
    public void loadModules() {
        File[] moduleFiles = moduleFolder.listFiles();
        if (moduleFiles != null) {
            for (File moduleFile : moduleFiles) {
                if (moduleFile.isFile() && moduleFile.getName().endsWith(".jar")) {
                    try {
                        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{moduleFile.toURI().toURL()}, getClass().getClassLoader());
                        String mainClassName = readMainClassName(moduleFile);
                        Class<?> clazz = Class.forName(mainClassName, true, urlClassLoader);
                        Module moduleInstance = (Module) clazz.getDeclaredConstructor().newInstance();
                        loadedModules.add(moduleInstance);
                        moduleInstance.onLoad();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String readMainClassName(File moduleFile) {
        try (JarFile jarFile = new JarFile(moduleFile)) {
            Manifest manifest = jarFile.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            return attributes.getValue("Main-Class");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void enableModules() {
        for (Module module : loadedModules) {
            module.onEnable();
        }
    }

    public void disableModules() {
        for (Module module : loadedModules) {
            module.onDisable();
        }
    }
}
