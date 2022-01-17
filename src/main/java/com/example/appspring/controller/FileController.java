package com.example.appspring.controller;


import com.example.appspring.service.FileService;
import com.example.appspring.model.Data;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Controller
public class FileController {
    private static final List<Data> datas = new ArrayList<Data>();

    static {
        datas.add(new Data("0", "1", "3"));
    }

    @Autowired
    FileService fileService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {

        model.addAttribute("datas", datas);
        return "index";
    }

    @PostMapping("/uploadFiles")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {

        Arrays.asList(files)
                .stream()
                .forEach(file -> fileService.uploadFile(file));

        redirectAttributes.addFlashAttribute("message",
                "Вы успешно загрузили все файлы!");
        return "redirect:/";
    }

    @GetMapping(value = {"/result"})
    public String result(Model model) {

        model.addAttribute("datas", datas);
        return "result";
    }

    @PostMapping("/resultCSV")
    public String resultCSV(@RequestParam("file") MultipartFile file, Model model) {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            // create csv bean reader
            CsvToBean<Data> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Data.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // Считывание данных с csv файла
            List<Data> datas = csvToBean.parse();

            // тут будет сохранение данных в бд

            // сохраните список в модели
            model.addAttribute("datas", datas);

        } catch (Exception ex) {
            model.addAttribute("message", "Что то пошло не так");
        }

        //datas.add(new Data("o", "1", "0 1"));
        model.addAttribute("datas", datas);
        return "redirect:/";
    }
}
