package com.alura.literatura.main;

import java.util.Scanner;

import com.alura.literatura.service.ApiClient;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiClient apiClient = new ApiClient();
    private final String URL_BASE = "https://gutendex.com/books/";

    public void showMenu() {
        String test = apiClient.getData(URL_BASE);
        System.out.println(test);
    }

}