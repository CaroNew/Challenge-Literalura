package com.challenge.literalura;

import com.challenge.literalura.mainclass.MainMenu;
import com.challenge.literalura.service.ApiRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	static final String RUTE = "https://gutendex.com/books";
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//MainMenu menu = new MainMenu();
		//menu.showMenu();
		String rute = "http://www.gutendex.com/books/1";

		ApiRequest apiRequest = new ApiRequest();
		String data = apiRequest.getData("https://gutendex.com/books/1");
		System.out.println("*****************************************\n");
		System.out.println("data: " + apiRequest.getData(rute));
		System.out.println("*****************************************\n");


	}
}
