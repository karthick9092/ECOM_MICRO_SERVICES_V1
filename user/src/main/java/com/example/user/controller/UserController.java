package com.example.user.controller;

import com.example.user.config.AppConfig;
import com.example.user.dto.ErrorResponseDto;
import com.example.user.dto.ResponseDto;
import com.example.user.dto.UserDto;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController @Validated
@RequestMapping("api/users")
    @EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@Tag(name = "User Management API", description = "CRUD operations for user management")
public class UserController {

    UserService userService;

    AppConfig appConfig;

//    @Value("${app-info.description}")
//    private String appInfo;

    @Autowired
    Environment environment;

    public UserController (UserService userService, AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
        }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<UserDto>> createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
        ResponseDto<UserDto> responseDto = ResponseDto.<UserDto>builder().status("success").msg("User created successfully").
                                                data(userDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/get/{id}")
    public ResponseDto<UserDto> getUser(@PathVariable
                                        @Pattern(regexp = "^[0-9]+$", message = "Id must be a number")
                                            String id) {
        UserDto userDto = userService.getUser(Integer.valueOf(id));
        ResponseDto<UserDto> responseDto = ResponseDto.<UserDto>builder().status("success").msg("User fetched successfully").
                                                data(userDto).build();
        return responseDto;
    }

    @PutMapping("/update")
    public ResponseDto<UserDto> getUser(@RequestBody @Valid UserDto userDto) {
        userService.updateUser(userDto);
        ResponseDto<UserDto> responseDto = ResponseDto.<UserDto>builder().status("success").msg("User updated successfully").
                data(userDto).build();
        return  responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto getUser(@PathVariable
                               @Pattern(regexp = "^[0-9]+$", message = "Id must be a number")
                                   int id) {
        userService.deleteUser(id);
        ResponseDto responseDto = ResponseDto.builder().status("success").msg("User Deleted successfully").build();
        return responseDto;
    }

    @Operation(summary = "Get application info", description = "Fetches application information such as name, version, description, developer and contact details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "App info fetched successfully",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ResponseDto.class)
                )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )),
    })
    @GetMapping("/get/appinfo")
    public ResponseDto<AppConfig> getAppInfo() {
        ResponseDto<AppConfig> responseDto = ResponseDto.<AppConfig>builder().status("success").msg("App info fetched successfully").
                data(appConfig).build();
        return responseDto;
    }

    @GetMapping("/get/java-version")
    public ResponseDto<String> getJavaVersion() {
        ResponseDto<String> responseDto = ResponseDto.<String>builder().status("success").msg("Java version fetched successfully").
                data(environment.getProperty("MAVEN_HOME")).build();
        return responseDto;
    }
}
