package com.web.SpringService.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.SpringService.repositories.ClienteRepository;
import com.web.SpringService.service.exceptions.FileException;

@Service
public class ImageService {
	
	@Value("${img.profile.size}")
	private Integer size;
	
	@Autowired
	ClienteRepository repository;
	
	@Autowired
	S3Service s3Service;
	
	@Autowired
	ClienteService service;
	
	public URI uplodProfilePicture(MultipartFile file) {
		String extencao = FilenameUtils.getExtension(file.getOriginalFilename());
		
		if(!"PNG".equals(extencao) && !"jpg".equals(extencao)) {
			throw new FileException("Somente imagens PNG e JPG são aceitas");
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(file.getInputStream());
			image = cropSquare(image);
			image = resize(image, size);
			
		} catch (IOException e) {
			throw new FileException("Erro ao redimensionar imagem");
		}
		
		return s3Service.uplodFile(getInputStream(image, extencao), file.getOriginalFilename(), "image");
	}
	
	public BufferedImage cropSquare(BufferedImage sourceImage) {
		int altura = sourceImage.getHeight();
		int largura = sourceImage.getWidth();
		int min = altura <= largura ? altura : largura;
		
		return Scalr.crop(sourceImage, (largura/2) - (min/2), (altura/2) - (min/2), min, min);
	}
	
	public BufferedImage resize(BufferedImage sourceImage, int size) {
		return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);
	}
	
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
}
