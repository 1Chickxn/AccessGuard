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
    private int groupID;
    private String groupPrefix;
    private String groupSuffix;
    private String groupNameColor;
    private List<String> permission;

}
