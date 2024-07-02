package me.chickxn.global.group;

import dev.httpmarco.evelon.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Groups {

    @PrimaryKey
    private String groupName;
    private String groupPrefix;
    private String groupSuffix;
    private String groupNameColour;
    private int groupID;
    private List<String> permissions;

}
