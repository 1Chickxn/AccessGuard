package me.chickxn.paper;

import lombok.Getter;
import net.http.aeon.annotations.Comment;
import net.http.aeon.annotations.Options;

@Getter
@Options(path = "plugins/Vynl-PermissionSystem/", name = "config")
@Comment(comment = "available savetypes: JSON")
public class PaperConfiguration {

    private final String saveType;
    private final String jsonPath;

    public PaperConfiguration() {
        this.saveType = "JSON";
        this.jsonPath = "/plugins/" + PaperPlugin.getInstance().getName() + "/";
    }
}
