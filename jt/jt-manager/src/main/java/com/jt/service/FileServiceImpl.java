package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.FileVo;

@Service  //默认对象是单例的,所以不要修改成员变量
@PropertySource(value="classpath:/properties/image.properties")
public class FileServiceImpl implements FileSerice {
	
	//定义本地磁盘路径
	@Value("${image.dirPath}")
	private String localPath;
	@Value("${image.urlPath}")
	private String urlPath;

	@Override
	public FileVo upload(MultipartFile fileImage) {
		FileVo fileVo = new FileVo(1,null,null,null);
		//1.获取文件名称
		String fileName = fileImage.getOriginalFilename();
		//2.将文件名称封装成小写
		fileName = fileName.toLowerCase();
		//3.利用正则表达式判断文件类型
		//^表示开始;$表示结束;.表示除了回车,换行之外的任意单个字符
		//+表示至少一个;*表示0个或多个
		if(!fileName.matches("^.+\\.(png|jpg|gif)$")) {
			//表示文件类型不匹配
			return fileVo;
		}
		//4.判断是否为恶意程序
		try {
			BufferedImage image = ImageIO.read(fileImage.getInputStream());
			//4.1获取宽度和高度
			int width = image.getWidth();
			int height = image.getHeight();
			//4.2判断属性是否为0
			if(width == 0 || height == 0) {
				return fileVo;
			}
			//5.根据时间生成文件夹
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String localDir = localPath + dateDir;
			File dirFile = new File(localDir);
			if(!dirFile.exists()) {
				dirFile.mkdirs();
			}
			//表示文件夹已经生成了
			//6.防止文件名称重复
			//6.1生成UUID
			String uuidName = UUID.randomUUID().toString().replace("-","");
			//6.2获取文件类型,进行拼接
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			String realName = uuidName + fileType;
			//6.3实现文件上传
			File realFile = new File(localDir + "/" + realName);
			fileImage.transferTo(realFile);
			fileVo.setError(0);
			fileVo.setHeight(height);
			fileVo.setWidth(width);
			//设置图片的虚拟访问路径
			String realUrlPath = urlPath + dateDir + "/" + realName;
			fileVo.setUrl(realUrlPath);
			return fileVo;
		} catch (IOException e) {
			e.printStackTrace();
			//表示为恶意程序
			return fileVo;
		}
	}
	
	
	
	
	
	
	
	
}
