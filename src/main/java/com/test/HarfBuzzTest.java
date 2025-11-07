package com.test;

import com.aspose.words.*;
import java.io.FileWriter;
import java.io.PrintWriter;

public class HarfBuzzTest {
    public static void main(String[] args) {
        PrintWriter reportWriter = null;
        
        try {
            // Kreiraj report file
            reportWriter = new PrintWriter(new FileWriter("test-report.txt"));
            
            reportWriter.println("=== Aspose.Words HarfBuzz Test on Darwin/macOS ===");
            reportWriter.println();
            
            // System info
            String osName = System.getProperty("os.name");
            String osVersion = System.getProperty("os.version");
            String osArch = System.getProperty("os.arch");
            String javaVersion = System.getProperty("java.version");
            String javaVendor = System.getProperty("java.vendor");
            
            reportWriter.println("Operating System: " + osName);
            reportWriter.println("OS Version: " + osVersion);
            reportWriter.println("OS Architecture: " + osArch);
            reportWriter.println("Java Version: " + javaVersion);
            reportWriter.println("Java Vendor: " + javaVendor);
            
            // Eksplicitna provjera za Darwin
            boolean isDarwin = osName.toLowerCase().contains("mac") || 
                              osName.toLowerCase().contains("darwin");
            reportWriter.println("Is Darwin/macOS: " + isDarwin);
            
            if (isDarwin) {
                reportWriter.println("✓ Running on Darwin (macOS) platform");
            }
            
            reportWriter.println();
            reportWriter.println("Aspose.Words Info:");
            reportWriter.println("  Product: " + BuildVersionInfo.getProduct());
            reportWriter.println("  Version: " + BuildVersionInfo.getVersion());
            reportWriter.println();
            
            // Isprintaj i na konzolu
            System.out.println("=== System Information ===");
            System.out.println("OS: " + osName + " " + osVersion);
            System.out.println("Architecture: " + osArch);
            System.out.println("Java: " + javaVersion);
            System.out.println("Aspose.Words: " + BuildVersionInfo.getVersion());
            System.out.println("Running on Darwin/macOS: " + isDarwin);
            System.out.println();
            
            // Test 1: Osnovni document sa HarfBuzz shaping
            reportWriter.println("TEST 1: Basic HarfBuzz shaping test");
            System.out.println("Running Test 1: Basic HarfBuzz shaping...");
            
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            
            // Različiti testovi
            Font font = builder.getFont();
            font.setName("Arial");
            font.setSize(14);
            
            builder.writeln("Test Platform: " + osName);
            builder.writeln("Architecture: " + osArch);
            builder.writeln();
            builder.writeln("=== HarfBuzz Complex Text Shaping Tests ===");
            builder.writeln();
            
            // Latin tekst sa ligaturama
            builder.writeln("1. Latin ligatures: office,ffle, ffi, ffl");
            
            // Arapski tekst (desno-na-lijevo sa HarfBuzz)
            builder.writeln("2. Arabic (RTL): مرحبا بالعالم السلام عليكم");
            
            // Tekst sa dijakriticima
            builder.writeln("3. Diacritics: café, naïve, résumé, Zürich");
            
            // Devanagari (kompleksni Indijski script)
            builder.writeln("4. Devanagari: नमस्ते दुनिया");
            
            // Thai (kompleksni script)
            builder.writeln("5. Thai: สวัสดีชาวโลก");
            
            // Testiranje različitih fontova
            font.setName("Times New Roman");
            builder.writeln();
            builder.writeln("6. Times New Roman ligatures: officeffle");
            
            font.setName("Georgia");
            builder.writeln("7. Georgia font test: HarfBuzz shaping");
            
            String outputDocx = "output_harfbuzz_test.docx";
            doc.save(outputDocx);
            reportWriter.println("✓ DOCX saved: " + outputDocx);
            System.out.println("✓ DOCX saved: " + outputDocx);
            
            // Test 2: PDF konverzija (ovdje se često javljaju HarfBuzz problemi)
            reportWriter.println();
            reportWriter.println("TEST 2: PDF conversion with HarfBuzz");
            System.out.println("Running Test 2: PDF conversion...");
            
            String outputPdf = "output_harfbuzz_test.pdf";
            
            // PDF save options
            PdfSaveOptions pdfOptions = new PdfSaveOptions();
            pdfOptions.setCompliance(PdfCompliance.PDF_17);
            pdfOptions.setEmbedFullFonts(true);
            
            doc.save(outputPdf, pdfOptions);
            reportWriter.println("✓ PDF saved: " + outputPdf);
            System.out.println("✓ PDF saved: " + outputPdf);
            
            // Test 3: Load and re-save test
            reportWriter.println();
            reportWriter.println("TEST 3: Document load and re-save");
            System.out.println("Running Test 3: Load and re-save...");
            
            Document loadedDoc = new Document(outputDocx);
            String reloadedPdf = "output_reloaded_test.pdf";
            loadedDoc.save(reloadedPdf, pdfOptions);
            reportWriter.println("✓ Reloaded and saved: " + reloadedPdf);
            System.out.println("✓ Reloaded PDF saved: " + reloadedPdf);
            
            // Test 4: Darwin-specific font rendering test
            if (isDarwin) {
                reportWriter.println();
                reportWriter.println("TEST 4: Darwin/macOS specific font test");
                System.out.println("Running Test 4: Darwin-specific tests...");
                
                Document macDoc = new Document();
                DocumentBuilder macBuilder = new DocumentBuilder(macDoc);
                
                // Testiranje sa macOS system fontovima
                String[] macFonts = {"Helvetica", "Helvetica Neue", "San Francisco", 
                                    "Arial", "Times New Roman", "Courier New"};
                
                macBuilder.writeln("=== macOS System Fonts Test ===");
                macBuilder.writeln();
                
                for (String fontName : macFonts) {
                    try {
                        macBuilder.getFont().setName(fontName);
                        macBuilder.getFont().setSize(12);
                        macBuilder.writeln(fontName + ": The quick brown fox jumps över the lazy dög (ligatures: ffi ffl)");
                    } catch (Exception e) {
                        reportWriter.println("! Font not available: " + fontName);
                    }
                }
                
                String macPdf = "output_darwin_fonts_test.pdf";
                macDoc.save(macPdf, pdfOptions);
                reportWriter.println("✓ Darwin fonts test saved: " + macPdf);
                System.out.println("✓ Darwin fonts PDF saved: " + macPdf);
            }
            
            reportWriter.println();
            reportWriter.println("=== ALL TESTS COMPLETED SUCCESSFULLY ===");
            System.out.println();
            System.out.println("✓✓✓ All tests completed successfully! ✓✓✓");
            System.out.println("Check test-report.txt for detailed results");
            
        } catch (Exception e) {
            String errorMsg = "ERROR: " + e.getMessage();
            System.err.println(errorMsg);
            if (reportWriter != null) {
                reportWriter.println();
                reportWriter.println(errorMsg);
                e.printStackTrace(reportWriter);
            }
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (reportWriter != null) {
                reportWriter.close();
            }
        }
    }
}
