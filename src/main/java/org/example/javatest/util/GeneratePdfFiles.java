package org.example.javatest.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class GeneratePdfFiles {

  public static void main() {
    String fecha = getTodayDate(); //ddmmaaaa
    int cedula = 1113010400; //se incrementa de uno en uno hasta el valor de cantidadCedulas
    int nit = 123456780; //se incrementa de uno en uno hasta el valor de cantidadCedulas
    int cantidadCedulas = 3;
    int counter = 1;

    String fileToCopy = "fileToCopy2.pdf";
    Path baseNamesFile = Paths.get("src/main/resources/fileNamesNew.txt");
    String resultFolder = "src/main/resources/out/E1/";

    List<String> lines = null;
    List<String> filesToCreate = new ArrayList<>();

    // Leer archivo txt base con nombres de los documentos
    try {
      lines = Files.readAllLines(baseNamesFile);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    System.out.println("LEIDO ARCHIVOS BASE CON NOMBRES DE "+lines.size() +" DOCUMENTOS");

    // crear lista de archivos a crear con cada uno de los documentos para cada una de las cedulas
    if (!lines.isEmpty()) {
      do {
        for (String ln : lines) {
          filesToCreate.add(ln.replace("cedula", String.valueOf(cedula)).replace("fecha",fecha).replace("nit",String.valueOf(nit)));
        }
        counter++;
        cedula++;
        nit++;
      } while (counter <= cantidadCedulas);
    }

    System.out.println("CREADA LISTA DE ARCHIVOS A GENERAR tamaño:"+filesToCreate.size());

    // iterar lista para crear archivo por cada elemento
    for (String fi : filesToCreate) {
      File source = new File("src/main/resources/"+fileToCopy);
      File destination = new File(resultFolder + fi);
      try {
        FileUtils.copyFile(source, destination);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    System.out.println(filesToCreate.size()+" ARCHIVOS GENERADOS EN "+resultFolder);
  }

  public static String getTodayDate() {
    // Obtener la fecha actual
    LocalDate fechaActual = LocalDate.now();

    // Formatear la fecha en ddmmaaaa
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyy");
    String fechaFormateada = fechaActual.format(formato);

    // Imprimir la fecha formateada
    System.out.println("Fecha actual en formato ddmmaaaa: " + fechaFormateada);
    return fechaFormateada;
  }
}