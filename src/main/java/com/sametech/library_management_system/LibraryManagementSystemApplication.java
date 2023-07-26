package com.sametech.library_management_system;

import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.models.users.Role;
import com.sametech.library_management_system.service.serviceImplementation.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.sametech.library_management_system.data.models.users.Role.ADMIN;
import static com.sametech.library_management_system.data.models.users.Role.LIBRARIAN;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AppUserService userService
//	) {
//		return args -> {
//			var admin = RegisterRequest.builder()
//					.firstName("Samuel")
//					.lastName("Fanu")
//					.email("admin@samtech.com")
//					.password("adminpassword")
//					.role(ADMIN)
//					.build();
//			System.out.printf("Admin token: %s%n", userService.createAdminAndLibrarian(admin).getAccessToken());
//
//			var librarian = RegisterRequest.builder()
//					.firstName("Samuel")
//					.lastName("Fanu")
//					.email("librarian@samtech.com")
//					.password("librarianpassword")
//					.role(LIBRARIAN)
//					.build();
//			System.out.printf("Librarian token: %s%n", userService.createAdminAndLibrarian(librarian).getAccessToken());
//		};
//	}

}
