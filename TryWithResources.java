package com.hello.exception

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;

public class TryWithResources {

	public static void main(String[] args) {
                /* we have created resource in the try instead of writing it in the try block */
		try (MyResource mr = new MyResource()) {
			System.out.println("MyResource created in try-with-resources");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Out of try-catch block.");
	}

	static class MyResource implements AutoCloseable{
            /* we are over riding the close method of the AutoCloseable this method will be called when the MyResource is closed*/ 
		@Override
		public void close() throws Exception {
			System.out.println("Closing MyResource");
		}

	}
}