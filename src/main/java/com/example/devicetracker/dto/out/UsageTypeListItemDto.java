package com.example.devicetracker.dto.out;

import com.example.devicetracker.domain.UsageType;
import lombok.Data;

@Data
public class UsageTypeListItemDto {
    private String name;
    private String displayName;


    public UsageTypeListItemDto(UsageType usageType) {
        this.name = usageType.toString();
        this.displayName = usageType.getDisplayName();
    }

}
