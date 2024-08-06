package com.quizguru.quizzes.utils;

import com.quizguru.quizzes.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class FileExtractor {
    public static String extractDocxToString(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new InvalidRequestException(Constant.INVALID_EMPTY_FILE_REQUEST);
        }
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                if (originalFilename.toLowerCase().endsWith(".docx")) {
                    try (XWPFDocument document = new XWPFDocument(file.getInputStream());
                         XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
                        return extractor.getText();
                    }
                } else {
                    throw new InvalidRequestException(Constant.INVALID_NOT_SUPPORTED_FILE_REQUEST);
                }
            } else {
                throw new InvalidRequestException(Constant.INVALID_FILE_MSG);
            }
        } catch (IOException e) {
            throw new InvalidRequestException(Constant.INVALID_FILE_MSG);
        }
    }

    public static String extractPDFToString(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new InvalidRequestException(Constant.INVALID_EMPTY_FILE_REQUEST);
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || (!originalFilename.toLowerCase().endsWith(".pdf"))) {
            throw new InvalidRequestException(Constant.INVALID_NOT_SUPPORTED_FILE_REQUEST);
        }
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new InvalidRequestException(Constant.INVALID_FILE_MSG);
        }
    }

    public static String extractTxtToString(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new InvalidRequestException(Constant.INVALID_EMPTY_FILE_REQUEST);
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || (!originalFilename.toLowerCase().endsWith(".txt"))) {
            throw new InvalidRequestException(Constant.INVALID_NOT_SUPPORTED_FILE_REQUEST);
        }
        try{
            return new String(file.getBytes());
        }catch (Exception e){
            throw new InvalidRequestException(Constant.INVALID_FILE_MSG);
        }
    }
}

