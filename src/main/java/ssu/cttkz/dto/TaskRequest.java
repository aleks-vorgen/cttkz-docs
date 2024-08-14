package ssu.cttkz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskRequest {
    private String invNumber;
    private String serialNumber;
    private String title;
    private String fullNameMVO;
    private String department;
    private String applicationNumberOriginal;
    private String jobType;
    private String executor;
    private String comment;
}
