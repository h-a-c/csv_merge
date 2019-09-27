/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poon.csv_merge;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author King
 */
public class csvManager {
    LinkedList<String> fileList = new LinkedList<String>();
    List<String> headers = new LinkedList<String>();
    private int[] headers2;
    public LinkedList addList(String location) {
        fileList.add(location);
        return fileList;
    }
    
    public LinkedList removeList(String location) {
        System.out.println(location);
        if(fileList.contains(location)) {
            fileList.remove(location);
        }
        return fileList;
    }
    
    public String[] getHeaders(String location) {
        try {
            CSVReader reader = new CSVReader(new FileReader(location));
            String[] headers;
            headers = reader.readNext();
            return headers;
        } catch (FileNotFoundException ex) {
            System.out.println("No file specified");
        } catch (IOException ex) {
            Logger.getLogger(csvManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void selectHeaders(List<String> selectedValuesList) {
       headers = selectedValuesList;
    }
    
    public void mergeFiles(String fileName) {
        if(!fileName.contains(".csv")) {
            fileName = fileName + ".csv";
        }
        
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));
            String str[] = new String[headers.size()];
            for (int x = 0; x < headers.size(); x++) { 
                str[x] = headers.get(x); 
            }
            csvWriter.writeNext(str);
            for ( int i = 0; i < fileList.size(); i++) {
                String currentFile = fileList.get(i);
                Reader reader = Files.newBufferedReader(Paths.get(currentFile));
                CSVReader csvReader = new CSVReader(reader, ',','"', '|');
                String[] nextRecord;
                while ((nextRecord = csvReader.readNext()) !=  null) {
                    String[] line = new String[headers2.length];
                    for(int j = 0; j < headers2.length; j++) {
                        line[j] = nextRecord[headers2[j]];
                        System.out.println(nextRecord[headers2[j]]);
                    }
                    if(line != str) {
                        csvWriter.writeNext(line);
                    }                     
                }
            }
            csvWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(csvManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void selectHeaders2(int[] selectedValuesList) {
        headers2 = selectedValuesList;
    }

}
