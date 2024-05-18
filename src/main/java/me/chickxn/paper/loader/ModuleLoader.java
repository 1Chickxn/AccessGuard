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
    private URLClassLoader mainPluginClassLoader;


    public ModuleLoader(File moduleFolder, File mainPluginJar) {
        this.moduleFolder = moduleFolder;
        this.moduleFolder.mkdirs();
        this.mainPluginClassLoader = createMainPluginClassLoader(mainPluginJar);
    }

    private URLClassLoader createMainPluginClassLoader(File mainPluginJar) {
        try {
            return new URLClassLoader(new URL[]{mainPluginJar.toURI().toURL()}, getClass().getClassLoader());
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Haupt-JAR", e);
        }
    }

    @SneakyThrows
    public void loadModules() {
        File[] moduleFiles = moduleFolder.listFiles();
        if (moduleFiles != null) {
            for (File moduleFile : moduleFiles) {
                if (moduleFile.isFile() && moduleFile.getName().endsWith(".jar")) {
                    String mainClassName = readMainClassName(moduleFile);
                    Class<?> clazz = Class.forName(mainClassName, true, mainPluginClassLoader);
                    Module moduleInstance = (Module) clazz.getDeclaredConstructor().newInstance();
                    loadedModules.add(moduleInstance);
                    moduleInstance.onLoad();
                }
            }
        }
    }

    private String readMainClassName(File moduleFile) {
        try (JarFile jarFile = new JarFile(moduleFile)) {
            Manifest manifest = jarFile.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String mainClassName = attributes.getValue("Main-Class");
            System.out.println("Lade Hauptklasse: " + mainClassName + " aus " + moduleFile.getName());
            return mainClassName;
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Manifest-Datei von " + moduleFile.getName());
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
