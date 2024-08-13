package ssu.cttkz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private Integer id;
    private String invNumber;
    private String serialNumber;
    private String title;
    private String fullNameMVO;
    private String department; //TODO заменить на модель Department
    private String applicationNumberOriginal;
    private String jobType;
    private String regNumber;
    private String executor; //TODO заменить на модель User
    private String comment;
    private String status;
    private String createdAt;
    private String createUser; //TODO заменить на модель User
    private String updatedAt;
    private String updateReason;
    private String updateUser; //TODO заменить на модель User
}
