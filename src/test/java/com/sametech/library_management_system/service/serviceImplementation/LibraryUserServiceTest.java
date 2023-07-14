package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.models.users.Role;
import com.sametech.library_management_system.service.serviceInterface.ILibraryUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class LibraryUserServiceTest {
    @Autowired
    private ILibraryUserService libraryUserService;
    private RegisterRequest request;

    @BeforeEach
    public void setUp() throws IOException{
        request = new RegisterRequest();
        request.setFirstName("Samuel");
        request.setLastName("Fanu");
        request.setEmail("fanusamuel@gmail.com");
        request.setPassword("testpassword");
        request.setRole(Role.LIBRARY_USER);
    }

    @Test
    void registerTest() {
        var requestResponse = libraryUserService.register(request);
        assertThat(requestResponse).isNotNull();
    }

    @Test
    void getLibraryUserById() {
    }

    @Test
    void getLibraryBy() {
    }

    @Test
    void updateLibraryUser() {
    }

    @Test
    void getAllLibraryUsersPerPage() {
    }

    @Test
    void testUpdateLibraryUser() {
    }

    @Test
    void deleteLibraryUser() {
    }
}