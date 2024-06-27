package me.chickxn.global.group;

import dev.httpmarco.evelon.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;


import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Groups {

    @PrimaryKey
    private String groupName;
    private String groupPrefix;
    private String groupSuffix;
    private ChatColor groupNameColour;
    private int groupID;
    private List<String> permissions;

}
