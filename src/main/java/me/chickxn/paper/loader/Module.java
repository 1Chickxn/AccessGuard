package me.chickxn.paper.loader;

public abstract class Module {

    private boolean isEnabled = false;

    public abstract void onLoad();

    public void onEnable() {
        isEnabled = true;
    }

    public void onDisable() {
        isEnabled = false;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
