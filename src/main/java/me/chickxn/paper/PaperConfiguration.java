package me.chickxn.paper;

import lombok.Getter;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "plugins/", name = "AccessGuard")
public class PaperConfiguration {

    private final String defaultGroup;

    public PaperConfiguration() {
        this.defaultGroup = "default";
    }
}
