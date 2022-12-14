package com.its.board.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.its.board.dto.FileTestDTO;
import com.its.board.service.FileTestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file-test")
public class FileTestController {
	private final FileTestService fileTestService;
	
	@GetMapping("/save-form")
	public String saveForm() {
		return "test/save";
	}
	
//	@PostMapping("/save")
//	public String save(MultipartFile[] blobFile, Model model) throws IOException {
//		for (MultipartFile m: blobFile) {
//			String fileName = m.getOriginalFilename();
//			File f = new File("D:\\image", fileName);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			FileInputStream fis = new FileInputStream(f);
//			
//			while (true) {
//				int x = fis.read();
//				if (x == -1) break;
//				bos.write(x);
//			}
//			fis.close();
//			bos.close();
//			
//			byte[] bis = bos.toByteArray();
//			
//			FileTestDTO fileTestDTO = new FileTestDTO();
//			fileTestDTO.setBlobFile(bis);
//			fileTestDTO.setFileName(fileName);
//			fileTestDTO.setFileSize(m.getSize());
//			
//			fileTestService.save(fileTestDTO);
//		}
//		return "redirect:/file-test/findAll";
//	}
	
	
	
	@PostMapping("/save2")
	public String save2(MultipartFile blobFile) throws IOException {
		String fileName = blobFile.getOriginalFilename();
		File f = new File("D:\\image", fileName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(f);
		
//		FileOutputStream fos = new FileOutputStream("D:\\files\\" + blobFile.getOriginalFilename());
//		InputStream is = blobFile.getInputStream();
		
		int readCount = 0;
		byte[] buffer = new byte[1024];
		while((readCount = fis.read(buffer)) != -1) {
			bos.write(buffer, 0, readCount);
		}
		
		byte[] bis = bos.toByteArray();
		
		FileTestDTO fileTestDTO = new FileTestDTO();
		fileTestDTO.setBlobFile(bis);
		fileTestDTO.setFileName(fileName);
		fileTestDTO.setFileSize(blobFile.getSize());
		
		fileTestService.save(fileTestDTO);
		return "index";
	}
	
	
	
	@GetMapping("/download3/{id}")
	public void download3(HttpServletResponse response, @PathVariable Long id) throws IOException {
		FileTestDTO fileTestDTO = fileTestService.findById(id);
		String fileName = fileTestDTO.getFileName();
		byte[] bytes = fileTestDTO.getBlobFile();
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		OutputStream os = response.getOutputStream();
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setContentType("application/octer-stream");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		byte[] buffer = new byte[1024*8];
		while(true) {
			int x = bis.read(buffer);
			if (x == -1) {
				break;
			}
			os.write(buffer, 0, x);
		}
		os.close();
		bis.close();
	}
	
	
//	@PostMapping("/save3")
//	public String save3(MultipartFile[] blobFile, Model model) throws IOException {
//		for (MultipartFile m: blobFile) {
//			String fileName = m.getOriginalFilename();
//			File f = new File("D:\\image", fileName);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			FileInputStream fis = new FileInputStream(m);
//			
//			while (true) {
//				int x = fis.read();
//				if (x == -1) break;
//				bos.write(x);
//			}
//			fis.close();
//			bos.close();
//			
//			byte[] bis = bos.toByteArray();
//			
//			FileTestDTO fileTestDTO = new FileTestDTO();
//			fileTestDTO.setBlobFile(bis);
//			fileTestDTO.setFileName(fileName);
//			fileTestDTO.setFileSize(m.getSize());
//			
//			fileTestService.save(fileTestDTO);
//		}
//		return "redirect:/file-test/findAll";
//	}
	
	
	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id) {
		FileTestDTO fileTestDTO = fileTestService.findById(id);
		Resource resource = new FileSystemResource("D:\\files\\" + fileTestDTO.getFileName());
		System.out.println("resource: " + resource);
		
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Disposition", "attachment; filename="+ new String(fileTestDTO.getFileName().getBytes("UTF-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileTestDTO.getFileName() + "")
                .body(resource);
	}
	
	
	@GetMapping("/findAll")
	public String findAll(Model model) throws IOException {
		List<FileTestDTO> fileTestDTOList = fileTestService.findAll();
		model.addAttribute("fileList", fileTestDTOList);
		return "test/list";
	}
	
	
	@GetMapping("/download2/{id}")
	public void download2(HttpServletResponse response, @PathVariable Long id) throws IOException {
		FileTestDTO fileTestDTO = fileTestService.findById(id);
		String fileName = fileTestDTO.getFileName();
//		byte[] bytes = fileTestDTO.getBlobFile();
//		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		OutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream("D:\\files\\" + fileName);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		byte[] buffer = new byte[1024*8];
		while(true) {
			int x = fis.read(buffer);
			if (x == -1) {
				break;
			}
			os.write(buffer, 0, x);
		}
		fis.close();
		os.close();
	}
	
//	@PostMapping("/save")
//	public @ResponseBody String save2(@RequestParam Map<String, Object> paramMap,
//									MultipartHttpServletRequest multipartRequest) throws Exception {
//		MultipartFile file = multipartRequest.getFile("blob");
//	    
//	    if(!file.isEmpty()){			
//			paramMap.put("VIDEO", convertFileToByte(file));
//		}
//	    
//		fileTestService.save2(paramMap);
//	    
//		return "";
//	}
	
	//????????? byte[] ??? ??????
	private byte[] convertFileToByte(MultipartFile mfile) throws Exception {
			File file = new File(mfile.getOriginalFilename());
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mfile.getBytes());
			
			byte[] returnValue = null;		
			ByteArrayOutputStream baos = null;	    
		    FileInputStream fis = null;
		  
		    try {
		    	baos = new ByteArrayOutputStream();
		    	fis = new FileInputStream(file);
		    		    	
		        byte[] buf = new byte[1024];
		        int read = 0;
		        
		        while ((read=fis.read(buf,0,buf.length)) != -1){
		        	baos.write(buf,0,read);
		        }
		        returnValue = baos.toByteArray();
		   
		    } catch (Exception e) {
		        System.out.println("Exception e: " + e);
		    } finally {
		            if (baos != null) {
		            	baos.close();
		            }
		            if (fis != null) {
		            	fis.close();
		            }
		    }
		    fos.close();
		    return returnValue;
		}
	
	
}
