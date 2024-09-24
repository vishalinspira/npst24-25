package com.nps.fee.letter.portlet;

import com.itextpdf.io.util.FileUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


public class ConvertPDFtoWord {
	private static Log _log=LogFactoryUtil.getLog(ConvertPDFtoWord.class.getName());
	
	public static void convertPdftoWord(File pdfFile) {
		_log.info("method callledd:convertPdftoWord  ");
		File file=null;
		String text ="";
        try { 
        	file=FileUtil.createTempFile("PDFTOWORD.docx");
        	//File pdfFile=new File("input.pdf");
        	PDDocument document = PDDocument.load(pdfFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            
             text = pdfStripper.getText(document);
             _log.info("text : "+text);
            // Save the text to a file or use it in the next step
        } catch (IOException e) {
        	_log.error(e);
        }
    
    	 XWPFDocument document = new XWPFDocument();
         XWPFParagraph paragraph = document.createParagraph();
         XWPFRun run = paragraph.createRun();
         run.setText(text);
         
         try { 
        	 
        	 FileOutputStream out = new FileOutputStream(file);
             document.write(out);
             out.close();
             document.close();
             
            long id= uploadFile(file);
            _log.info("word file entry id: "+id);
         
     } catch (IOException e) {
    	 _log.error(e);
     }


	}
	
	
	private static long uploadFile(File file){
		Date date=new Date();
	
		String fileName = date.getTime()+"_"+""+"PDFTOWORD.docx";
		String title = fileName; 
		String description = "Convert PDF to word";
		String mimeType =  MimeTypesUtil.getContentType(file);
		try {
			ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
			long repositoryId = serviceContext.getScopeGroupId();
			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(repositoryId, 0, fileName, mimeType, title, description,
					"", file, serviceContext);
			return fileEntry.getFileEntryId();
		}catch (Exception e) {
			_log.error("error while uploading file:  "+e.getMessage());
		}
		return 0;
			

		
	}
}
