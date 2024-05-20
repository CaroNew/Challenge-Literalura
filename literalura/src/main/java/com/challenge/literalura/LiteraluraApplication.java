package com.challenge.literalura;

import com.challenge.literalura.mainclass.MainMenu;
import com.challenge.literalura.models.DatosLibro;
import com.challenge.literalura.service.ApiRequest;
import com.challenge.literalura.service.DataConversion;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		MainMenu menu = new MainMenu();
		menu.showMenu();
		String rute = "https://gutendex.com/books/1";
		//https://gutendex.com/books/?search=quijote+de+la+mancha

//		ApiRequest apiRequest = new ApiRequest();
//		String data = apiRequest.getData(rute);
		System.out.println("*****************************************\n");
//		DataConversion dataJson = new DataConversion();
//		DatosLibro libro = dataJson.convertData(data, DatosLibro.class);
//
//
//		System.out.println("data:");
//		System.out.println(libro);
//		System.out.println(libro.getClass().getSimpleName());
//		//System.out.println(data);
//		System.out.println("*****************************************\n");


	}
}
