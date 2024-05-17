package me.chickxn.global.user;

import dev.httpmarco.evelon.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class User {

    @PrimaryKey
    private final UUID uuid;
    private String groupName;
    private List<String> permissions;

}
