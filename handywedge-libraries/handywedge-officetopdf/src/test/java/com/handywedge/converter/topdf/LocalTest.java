package com.handywedge.converter.topdf;

import com.handywedge.converter.topdf.exceptions.FWConvertProcessException;
import com.handywedge.converter.topdf.exceptions.FWConvertTimeoutException;
import com.handywedge.converter.topdf.exceptions.FWUnsupportedFormatException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang3.ObjectUtils;
import org.jodconverter.core.office.OfficeException;

import java.io.File;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class LocalTest extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public LocalTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(LocalTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testAppDocx() {
    System.out.println("======[DOCX -> SVG]======");
    String docName = "doc/Talendジョブ保守ガイド.docx";
    File file = new File(docName);

    FWDocumentConverter converter = new FWDocumentConverter();
    try {
      File pdfFile = converter.fileToPdf(file);
      if (ObjectUtils.isEmpty(pdfFile)) {
        System.out.println("SVG Converter is empty");
        assertTrue(false);
      } else {
        assertTrue(true);
      }
    } catch (FWUnsupportedFormatException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertTimeoutException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertProcessException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  public void testAppDoc() {
    System.out.println("======[DOC -> SVG]======");
    // File file = new File("doc/情報システム運用規程.doc");
    File file = new File("doc/情報システム運用規程2.doc");

    FWDocumentConverter converter = new FWDocumentConverter();
    try {
      File pdfFile = converter.fileToPdf(file);
      if (ObjectUtils.isEmpty(pdfFile)) {
        System.out.println("SVG Converter is empty");
        assertTrue(false);
      } else {
        assertTrue(true);
      }
    } catch (FWUnsupportedFormatException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertTimeoutException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertProcessException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  public void testAppXlsx() {
    System.out.println("======[XLSX -> SVG]======");
    File file = new File("doc/年次会計報告書.xlsx");

    FWDocumentConverter converter = new FWDocumentConverter();
    try {
      File pdfFile = converter.fileToPdf(file);
      if (ObjectUtils.isEmpty(pdfFile)) {
        System.out.println("SVG Converter is empty");
        assertTrue(false);
      } else {
        assertTrue(true);
      }
    } catch (FWUnsupportedFormatException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertTimeoutException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertProcessException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  public void testAppXls() {
    System.out.println("======[XLS -> SVG]======");
    File file = new File("doc/申込書.xls");

    FWDocumentConverter converter = new FWDocumentConverter();
    try {
      File pdfFile = converter.fileToPdf(file);
      if (ObjectUtils.isEmpty(pdfFile)) {
        System.out.println("SVG Converter is empty");
        assertTrue(false);
      } else {
        assertTrue(true);
      }
    } catch (FWUnsupportedFormatException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertTimeoutException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertProcessException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  public void testAppPptx() {
    System.out.println("======[PPTX -> SVG]======");
    File file = new File("doc/ppt-sample.pptx");

    FWDocumentConverter converter = new FWDocumentConverter();
    try {
      File pdfFile = converter.fileToPdf(file);
      if (ObjectUtils.isEmpty(pdfFile)) {
          System.out.println("SVG Converter is empty");
          assertTrue(false);
      } else {
          assertTrue(true);
      }
    } catch (FWUnsupportedFormatException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertTimeoutException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertProcessException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

  public void testAppPpt() {
    System.out.println("======[PPT -> SVG]======");
    File file = new File("doc/プレゼンテーション.ppt");

    FWDocumentConverter converter = new FWDocumentConverter();
    try {
      File pdfFile = converter.fileToPdf(file);
      if (ObjectUtils.isEmpty(pdfFile)) {
        System.out.println("SVG Converter is empty");
        assertTrue(false);
      } else {
        assertTrue(true);
      }
    } catch (FWUnsupportedFormatException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertTimeoutException e) {
      e.printStackTrace();
      assertTrue(false);
    } catch (FWConvertProcessException e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }

}
