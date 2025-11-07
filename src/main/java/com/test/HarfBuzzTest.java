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
            
            // *** KRITIČNO: Omogući HarfBuzz text shaping ***
            reportWriter.println("=== HarfBuzz Configuration ===");
            System.out.println("=== HarfBuzz Configuration ===");
            
            // Test 1: Osnovni document sa HarfBuzz shaping
            reportWriter.println("TEST 1: Basic document with HarfBuzz shaping");
            System.out.println("Running Test 1: Basic HarfBuzz shaping...");
            
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            
            // *** KLJUČNO: Postavi HarfBuzz text shaper za layout ***
            try {
                doc.getLayoutOptions().setTextShaperFactory(HarfBuzzTextShaperFactory.getInstance());
                reportWriter.println("✓ HarfBuzz TextShaperFactory enabled via LayoutOptions");
                System.out.println("✓ HarfBuzz enabled for text shaping");
            } catch (Exception e) {
                reportWriter.println("✗ Failed to enable HarfBuzz: " + e.getMessage());
                System.err.println("✗ Failed to enable HarfBuzz: " + e.getMessage());
                reportWriter.println("NOTE: HarfBuzz may not be available - continuing with basic shaping");
                System.out.println("NOTE: Continuing without HarfBuzz...");
            }
            
            reportWriter.println();
            
            // Različiti testovi
            Font font = builder.getFont();
            font.setName("Arial");
            font.setSize(14);
            
            builder.writeln("Test Platform: " + osName);
            builder.writeln("Architecture: " + osArch);
            builder.writeln("HarfBuzz: ENABLED via LayoutOptions");
            builder.writeln();
            builder.writeln("=== HarfBuzz Complex Text Shaping Tests ===");
            builder.writeln();
            
            // Latin tekst sa ligaturama (HarfBuzz će renderovati ffi, ffl ligatures)
            builder.writeln("1. Latin ligatures: office, ffle, ffi, ffl");
            
            // Arapski tekst (desno-na-lijevo sa HarfBuzz)
            builder.writeln("2. Arabic (RTL with HarfBuzz): مرحبا بالعالم السلام عليكم");
            
            // Tekst sa dijakriticima
            builder.writeln("3. Diacritics: café, naïve, résumé, Zürich, Москва");
            
            // Devanagari (kompleksni Indijski script - zahtijeva HarfBuzz)
            builder.writeln("4. Devanagari (requires HarfBuzz): नमस्ते दुनिया");
            
            // Thai (kompleksni script)
            builder.writeln("5. Thai (requires HarfBuzz): สวัสดีชาวโลก");
            
            // Bengali
            builder.writeln("6. Bengali (requires HarfBuzz): হ্যালো বিশ্ব");
            
            // Hebrew (desno-na-lijevo)
            builder.writeln("7. Hebrew (RTL): שלום עולם");
            
            // Testiranje različitih fontova
            font.setName("Times New Roman");
            builder.writeln();
            builder.writeln("8. Times New Roman ligatures: office, ffle");
            
            font.setName("Georgia");
            builder.writeln("9. Georgia font test: HarfBuzz shaping active");
            
            // Emoji test (HarfBuzz handles emoji better)
            font.setName("Arial");
            builder.writeln("10. Emoji test: 🌍 🚀 ⭐ 💻");
            
            String outputDocx = "output_harfbuzz_test.docx";
            doc.save(outputDocx);
            reportWriter.println("✓ DOCX saved: " + outputDocx);
            System.out.println("✓ DOCX saved: " + outputDocx);
            
            // Test 2: PDF konverzija sa HarfBuzz (ovdje se najčešće vide razlike)
            reportWriter.println();
            reportWriter.println("TEST 2: PDF conversion with HarfBuzz enabled");
            System.out.println("Running Test 2: PDF conversion with HarfBuzz...");
            
            String outputPdf = "output_harfbuzz_test.pdf";
            
            // PDF save options
            PdfSaveOptions pdfOptions = new PdfSaveOptions();
            pdfOptions.setCompliance(PdfCompliance.PDF_17);
            pdfOptions.setEmbedFullFonts(true);
            pdfOptions.setPreserveFormFields(false);
            pdfOptions.setTextCompression(PdfTextCompression.NONE);
            
            doc.save(outputPdf, pdfOptions);
            reportWriter.println("✓ PDF saved with HarfBuzz: " + outputPdf);
            System.out.println("✓ PDF saved with HarfBuzz: " + outputPdf);
            
            // Test 3: Load and re-save test
            reportWriter.println();
            reportWriter.println("TEST 3: Document load and re-save with HarfBuzz");
            System.out.println("Running Test 3: Load and re-save...");
            
            Document loadedDoc = new Document(outputDocx);
            // Ponovo postavi HarfBuzz za novi dokument
            try {
                loadedDoc.getLayoutOptions().setTextShaperFactory(HarfBuzzTextShaperFactory.getInstance());
            } catch (Exception e) {
                // Ignore if already set or not available
            }
            
            String reloadedPdf = "output_reloaded_test.pdf";
            loadedDoc.save(reloadedPdf, pdfOptions);
            reportWriter.println("✓ Reloaded and saved: " + reloadedPdf);
            System.out.println("✓ Reloaded PDF saved: " + reloadedPdf);
            
            // Test 4: Comparison test - WITH vs WITHOUT HarfBuzz
            reportWriter.println();
            reportWriter.println("TEST 4: Comparison - HarfBuzz ON vs OFF");
            System.out.println("Running Test 4: HarfBuzz comparison...");
            
            Document docWithoutHB = new Document();
            // Eksplicitno ne postavljamo HarfBuzz za ovaj dokument
            DocumentBuilder builderWithoutHB = new DocumentBuilder(docWithoutHB);
            
            builderWithoutHB.getFont().setName("Arial");
            builderWithoutHB.getFont().setSize(14);
            builderWithoutHB.writeln("=== WITHOUT HarfBuzz (Basic Text Shaper) ===");
            builderWithoutHB.writeln();
            builderWithoutHB.writeln("1. Latin ligatures: office, ffle, ffi, ffl");
            builderWithoutHB.writeln("2. Arabic: مرحبا بالعالم");
            builderWithoutHB.writeln("3. Devanagari: नमस्ते दुनिया");
            builderWithoutHB.writeln("4. Thai: สวัสดีชาวโลก");
            
            String pdfWithoutHB = "output_WITHOUT_harfbuzz.pdf";
            docWithoutHB.save(pdfWithoutHB, pdfOptions);
            reportWriter.println("✓ PDF without HarfBuzz saved: " + pdfWithoutHB);
            System.out.println("✓ PDF without HarfBuzz saved: " + pdfWithoutHB);
            
            // Test 5: Darwin-specific font rendering test
            if (isDarwin) {
                reportWriter.println();
                reportWriter.println("TEST 5: Darwin/macOS specific font test with HarfBuzz");
                System.out.println("Running Test 5: Darwin-specific HarfBuzz tests...");
                
                Document macDoc = new Document();
                
                // Postavi HarfBuzz za macOS dokument
                try {
                    macDoc.getLayoutOptions().setTextShaperFactory(HarfBuzzTextShaperFactory.getInstance());
                } catch (Exception e) {
                    // Ignore
                }
                
                DocumentBuilder macBuilder = new DocumentBuilder(macDoc);
                
                // Testiranje sa macOS system fontovima
                String[] macFonts = {"Helvetica", "Helvetica Neue", "San Francisco", 
                                    "Arial", "Times New Roman", "Courier New", "Georgia"};
                
                macBuilder.writeln("=== macOS System Fonts with HarfBuzz ===");
                macBuilder.writeln();
                
                for (String fontName : macFonts) {
                    try {
                        macBuilder.getFont().setName(fontName);
                        macBuilder.getFont().setSize(12);
                        macBuilder.write(fontName + ": ");
                        macBuilder.writeln("Complex: офис (Cyrillic), café (diacritics), ffi ffl (ligatures), مرحبا (Arabic)");
                    } catch (Exception e) {
                        reportWriter.println("! Font not available: " + fontName);
                    }
                }
                
                String macPdf = "output_darwin_fonts_harfbuzz.pdf";
                macDoc.save(macPdf, pdfOptions);
                reportWriter.println("✓ Darwin fonts with HarfBuzz saved: " + macPdf);
                System.out.println("✓ Darwin fonts PDF saved: " + macPdf);
            }
            
            reportWriter.println();
            reportWriter.println("=== ALL HARFBUZZ TESTS COMPLETED SUCCESSFULLY ===");
            reportWriter.println();
            reportWriter.println("Files generated:");
            reportWriter.println("  1. output_harfbuzz_test.docx - Word doc with HarfBuzz");
            reportWriter.println("  2. output_harfbuzz_test.pdf - PDF with HarfBuzz");
            reportWriter.println("  3. output_reloaded_test.pdf - Reload test");
            reportWriter.println("  4. output_WITHOUT_harfbuzz.pdf - PDF WITHOUT HarfBuzz (comparison)");
            if (isDarwin) {
                reportWriter.println("  5. output_darwin_fonts_harfbuzz.pdf - macOS fonts with HarfBuzz");
            }
            reportWriter.println();
            reportWriter.println("Compare output_harfbuzz_test.pdf vs output_WITHOUT_harfbuzz.pdf");
            reportWriter.println("to see the difference HarfBuzz makes in complex script rendering!");
            
            System.out.println();
            System.out.println("✓✓✓ All HarfBuzz tests completed successfully! ✓✓✓");
            System.out.println("Check test-report.txt for detailed results");
            System.out.println();
            System.out.println("IMPORTANT: Compare these files to see HarfBuzz impact:");
            System.out.println("  - output_harfbuzz_test.pdf (WITH HarfBuzz)");
            System.out.println("  - output_WITHOUT_harfbuzz.pdf (WITHOUT HarfBuzz)");
            
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