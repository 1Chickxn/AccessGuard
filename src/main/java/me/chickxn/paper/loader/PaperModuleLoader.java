package me.chickxn.paper.loader;

import lombok.Getter;
import lombok.SneakyThrows;
import me.chickxn.paper.loader.module.PaperModule;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@Getter
public class PaperModuleLoader {

    private List<PaperModule> moduleList = new ArrayList<>();

    public void loadModules(File file) {
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles((directory, name) -> name.endsWith(".jar"));
        if (files == null) {
            return;
        }
        for (File jarFile : files) {
            loadJarFile(jarFile);
        }
    }

    @SneakyThrows
    private void loadJarFile(File file) {
        JarFile jarFile = new JarFile(file);
        URL[] urls = {file.toURI().toURL()};
        URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls, getClass().getClassLoader());
        JarEntry jarEntry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
        if (jarEntry != null) {
            try (InputStream inputStream = jarFile.getInputStream(jarEntry)){
                Manifest manifest = new Manifest(inputStream);
                String mainClass = manifest.getMainAttributes().getValue("Main-Class");
                if (mainClass != null) {
                    Class<?> clazz = urlClassLoader.loadClass(mainClass);
                    if (PaperModule.class.isAssignableFrom(clazz)) {
                        PaperModule paperModule = (PaperModule) clazz.getDeclaredConstructor().newInstance();
                        moduleList.add(paperModule);
                        paperModule.onLoad();
                    }
                }
            }
        }
    }
}
