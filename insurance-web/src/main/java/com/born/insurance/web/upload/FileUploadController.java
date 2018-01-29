package com.born.insurance.web.upload;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.born.insurance.util.AppConstantsUtil;
import com.born.insurance.util.ImageUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.web.controller.base.BaseController;
import com.born.insurance.web.util.FileUploadUtils;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Controller
@RequestMapping("upload")
public class FileUploadController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	private static List<String> fileTypeList = new ArrayList<String>();
	private static org.json.simple.JSONObject ueconfig = null;
	static {
		//图片
		fileTypeList.add(".jpg");
		fileTypeList.add(".jpeg");
		fileTypeList.add(".bmp");
		fileTypeList.add(".png");
		fileTypeList.add(".tiff");
		fileTypeList.add(".pcx");
		fileTypeList.add(".tga");
		fileTypeList.add(".exif");
		fileTypeList.add(".fpx");
		fileTypeList.add(".svg");
		fileTypeList.add(".psd");
		fileTypeList.add(".cdr");
		fileTypeList.add(".pcd");
		fileTypeList.add(".dxf");
		fileTypeList.add(".ufo");
		fileTypeList.add(".eps");
		fileTypeList.add(".ai");
		fileTypeList.add(".raw");
		//文档
		fileTypeList.add(".doc");
		fileTypeList.add(".docx");
		fileTypeList.add(".xls");
		fileTypeList.add(".xlsx");
		fileTypeList.add(".ppt");
		fileTypeList.add(".pptx");
		fileTypeList.add(".pdf");
		fileTypeList.add(".txt");
		fileTypeList.add(".md");
		fileTypeList.add(".xml");
		//压缩文件
		fileTypeList.add(".apk");
		fileTypeList.add(".rar");
		fileTypeList.add(".zip");
		fileTypeList.add(".tar");
		fileTypeList.add(".gz");
		fileTypeList.add(".7z");
		fileTypeList.add(".bz2");
		fileTypeList.add(".cab");
		fileTypeList.add(".iso");
		//视频
		fileTypeList.add(".mp4");
		fileTypeList.add(".flv");
		fileTypeList.add(".mkv");
		fileTypeList.add(".mpg");
		fileTypeList.add(".mpeg");
		fileTypeList.add(".avi");
		fileTypeList.add(".3gp");
		//音频
		fileTypeList.add(".wmv");
		fileTypeList.add(".mp3");
	};
	
	@ResponseBody
	@RequestMapping(value = "imagesUpload2Front", produces = "application/json")
	public Object imagesUpload2Front(String fileName, HttpServletRequest request,
										HttpServletResponse response) {
		fileName = fileName.split(";")[0];
		JSONObject jsonobj = new JSONObject();
		try {
			ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			fileUpload.setHeaderEncoding("utf-8");
			List<FileItem> fileList = null;
			try {
				fileList = fileUpload.parseRequest(request);
			} catch (FileUploadException ex) {
				logger.error(ex.getMessage(), ex);
				//				return "{\"code\":\"1\",\"resData\":\"" + "文件接收异常！" + "\"}";
				jsonobj.put("success", false);
				jsonobj.put("message", "文件接收异常！");
				return jsonobj.toJSONString();
			}
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			String extName = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					// 解析文件
					name = item.getName();
					if (name == null || name.trim().equals("")) {
						continue;
					}
					// 得到文件的扩展名
					if (name.lastIndexOf(".") >= 0) {
						extName = name.substring(name.lastIndexOf("."));
					}
					if (!fileTypeList.contains(extName.toLowerCase())) {
						jsonobj.put("success", false);
						jsonobj.put("message", "文件上传失败(文件类型不正确)！");
						return jsonobj.toJSONString();
					}
					File file = null;
					
					String imgDir = FileUploadUtils.getStaticFilesImgDir();
					
					// String savePath =
					// request.getServletContext().getRealPath("/") + fileName;
					
					String savePath = imgDir + File.separator + fileName;
					
					logger.info("imagesUpload2Front  savePath:" + savePath);
					
					try {
						file = new File(savePath);
						item.write(file);
						if (".jpg".equalsIgnoreCase(extName) || ".bmp".equalsIgnoreCase(extName)
							|| ".png".equalsIgnoreCase(extName)) {
							boolean pass = this.compressPic(savePath, savePath);
							if (!pass) {
								logger.info("文件压缩异常");
								jsonobj.put("success", false);
								jsonobj.put("message", "文件压缩异常");
								return jsonobj.toJSONString();
							}
						}
					} catch (Exception e) {
						logger.info("文件写入异常，异常信息：{}", e.toString(), e);
						//						return "{\"code\":\"3\",\"resData\":\"" + "文件写入异常" + "\"}";
						jsonobj.put("success", false);
						jsonobj.put("message", "文件写入异常");
						return jsonobj.toJSONString();
					}
				}
				
			}
		} catch (Exception e) {
			logger.info("文件上传异常，异常信息：{}", e.toString(), e);
			//			return "{\"code\":\"2\",\"resData\":\"" + "文件上传异常" + "\"}";
			jsonobj.put("success", false);
			jsonobj.put("message", "文件上传异常");
			return jsonobj.toJSONString();
		}
		// validateFikeExsit(fileName);
		jsonobj.put("success", true);
		jsonobj.put("message", "文件上传成功");
		response.setContentType("application/json");
		JSONObject data = new JSONObject();
		data.put("fileName", fileName);
		jsonobj.put("data", data);
		return jsonobj.toJSONString();
	}
	
	protected void validateFikeExsit(String fileName) {
		try {
			String urlString = null;
			if (StringUtil.isNotBlank(fileName)) {
				if (fileName.startsWith("/")) {
					urlString = AppConstantsUtil.getHostHttpUrl() + fileName;
				} else {
					urlString = fileName;
				}
			}
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			if (urlConnection.getContentLengthLong() > 0) {
				logger.info("文件上传成功文件名={}，长度={}", urlString, urlConnection.getContentLengthLong());
			} else {
				logger.info("文件上传失败文件名={}，长度={}", urlString, urlConnection.getContentLengthLong());
			}
		} catch (Exception e) {
			logger.error("检验文件存在异常={}", e, e);
		}
	}
	
	@RequestMapping(value = "imagesUpload")
	public void imagesUpload(HttpServletRequest request, HttpServletResponse response,
								ModelMap modelMap) throws IOException {
		
		String agent = request.getHeader("user-agent");
		boolean isIE = false;
		if (agent != null && agent.indexOf("MSIE") > -1) {
			isIE = true;
		}
		// 测试 都当IE
		isIE = true;
		String[] pathArray = null;
		File file = null;
		String oldFileName = "";
		JSONObject jsonobj = new JSONObject();
		try {
			ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			fileUpload.setHeaderEncoding("utf-8");
			List<FileItem> fileList = null;
			try {
				fileList = fileUpload.parseRequest(request);
			} catch (FileUploadException ex) {
				logger.error(ex.getMessage(), ex);
				//return "{\"code\":\"1\",\"resData\":\"" + "文件接收异常！" + "\"}";
				jsonobj.put("success", false);
				jsonobj.put("message", "文件接收异常！");
				returnJson(response, isIE, jsonobj);
				return;
			}
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			String extName = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					// 解析文件
					name = item.getName();
					oldFileName = name;
					if (name == null || name.trim().equals("")) {
						continue;
					}
					// 得到文件的扩展名
					if (name.lastIndexOf(".") >= 0) {
						extName = name.substring(name.lastIndexOf("."));
					}
					if (!fileTypeList.contains(extName.toLowerCase())) {
						jsonobj.put("success", false);
						jsonobj.put("message", "文件上传失败(文件类型不正确)！");
						returnJson(response, isIE, jsonobj);
						return;
					}
					if (".pdf".equalsIgnoreCase(extName)) {
						pathArray = FileUploadUtils.getStaticFilesPdfPath(request, name);
					} else {
						pathArray = FileUploadUtils.getStaticFilesImgPath(request, name);
					}
					String savePath = pathArray[0];
					
					try {
						file = new File(savePath);
						item.write(file);
						//png压缩后图片失真？
						if (".jpg".equalsIgnoreCase(extName) || ".bmp".equalsIgnoreCase(extName)) {
							boolean pass = this.compressPic(savePath, savePath);
							if (!pass) {
								logger.info("文件压缩异常");
								jsonobj.put("success", false);
								jsonobj.put("message", "文件压缩异常");
								returnJson(response, isIE, jsonobj);
								return;
								//return "{\"code\":\"2\",\"resData\":\"" + "文件压缩异常" + "\"}";
							}
						}
					} catch (Exception e) {
						
						logger.error("文件写入异常，异常信息：{}", e.toString(), e);
						jsonobj.put("success", false);
						jsonobj.put("message", "文件写入异常");
						returnJson(response, isIE, jsonobj);
						return;
						//return "{\"code\":\"3\",\"resData\":\"" + "文件写入异常" + "\"}";
					}
				}
			}
		} catch (Exception e) {
			logger.error("文件上传异常，异常信息：{}", e.toString(), e);
			jsonobj.put("success", false);
			jsonobj.put("message", "文件上传异常");
			returnJson(response, isIE, jsonobj);
			return;
			//return "{\"code\":\"2\",\"resData\":\"" + "文件上传异常" + "\"}";
		}
		// validateFikeExsit(pathArray[1]);
		jsonobj.put("success", true);
		jsonobj.put("message", "文件上传成功");
		JSONObject data = new JSONObject();
		//		data.put("err", "");
		data.put("msg", pathArray[1]);
		data.put("serverPath", pathArray[0]);
		//		data.put("error", 0);
		data.put("url", pathArray[1]);
		data.put("oldFileName", oldFileName);
		data.put("fileName", file.getName());
		jsonobj.put("data", data);
		returnJson(response, isIE, jsonobj);
		return;
	}
	
	@Override
	protected void returnJson(HttpServletResponse response, boolean isIE, JSONObject jsonobj)
																								throws IOException {
		response.reset();
		response.setHeader("ContentType", "text/html");
		response.setContentType("text/html");
		//		if (isIE) {
		//			response.setHeader("ContentType", "text/html");
		//			response.setContentType("text/html");
		//		} else {
		//			response.setHeader("ContentType", "application/json");
		//			response.setContentType("application/json");
		//		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonobj.toJSONString());
	}
	
	//身份证图片水印，特新加请求
	@ResponseBody
	@RequestMapping(value = "newImagesUpload", produces = "application/json")
	public Object newImagesUpload(HttpServletRequest request, HttpServletResponse response,
									ModelMap modelMap) throws IOException {
		String[] pathArray = null;
		File file = null;
		String oldFileName = "";
		JSONObject jsonobj = new JSONObject();
		try {
			ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			fileUpload.setHeaderEncoding("utf-8");
			List<FileItem> fileList = null;
			try {
				fileList = fileUpload.parseRequest(request);
			} catch (FileUploadException ex) {
				logger.error(ex.getMessage(), ex);
				return "{\"code\":\"1\",\"resData\":\"" + "文件接收异常！" + "\"}";
			}
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			String extName = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					// 解析文件
					name = item.getName();
					oldFileName = name;
					if (name == null || name.trim().equals("")) {
						continue;
					}
					// 得到文件的扩展名
					if (name.lastIndexOf(".") >= 0) {
						extName = name.substring(name.lastIndexOf("."));
					}
					if (!fileTypeList.contains(extName.toLowerCase())) {
						jsonobj.put("code", "1");
						jsonobj.put("message", "文件上传失败(文件类型不正确)！");
						return jsonobj.toJSONString();
					}
					if (".pdf".equalsIgnoreCase(extName)) {
						pathArray = FileUploadUtils.getStaticFilesPdfPath(request, name);
					} else {
						pathArray = FileUploadUtils.getStaticFilesImgPath(request, name);
					}
					String savePath = pathArray[0];
					
					try {
						file = new File(savePath);
						item.write(file);
						//png压缩后图片失真？
						if (".jpg".equalsIgnoreCase(extName) || ".bmp".equalsIgnoreCase(extName)) {
							boolean pass = this.compressPic(savePath, savePath);
							if (!pass) {
								logger.info("文件压缩异常");
								return "{\"code\":\"2\",\"resData\":\"" + "文件压缩异常" + "\"}";
							}
						}
					} catch (Exception e) {
						
						logger.error("文件写入异常，异常信息：{}", e.toString(), e);
						return "{\"code\":\"3\",\"resData\":\"" + "文件写入异常" + "\"}";
					}
				}
			}
		} catch (Exception e) {
			logger.error("文件上传异常，异常信息：{}", e.toString(), e);
			return "{\"code\":\"2\",\"resData\":\"" + "文件上传异常" + "\"}";
		}
		// validateFikeExsit(pathArray[1]);
		//添加水印
		ImageUtils.pressImage(request, "/styles/images/home/shuiying.png", pathArray[0]);
		
		jsonobj.put("code", "0");
		jsonobj.put("resData", pathArray[1]);
		jsonobj.put("err", "");
		jsonobj.put("msg", pathArray[1]);
		jsonobj.put("serverPath", pathArray[0]);
		jsonobj.put("error", 0);
		jsonobj.put("url", pathArray[1]);
		jsonobj.put("oldFileName", oldFileName);
		jsonobj.put("fileName", file.getName());
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonobj.toJSONString());
		return null;
	}
	
	@ResponseBody
	@RequestMapping("getUploadImages")
	public Object getUploadImages(HttpServletResponse response, String imagePath, ModelMap modelMap)
																									throws IOException {
		response.sendRedirect(imagePath);
		return "";
		// InputStream inputStream = null;
		// try {
		// inputStream = new FileInputStream(new File(imagePath));
		// if (!"".equals(imagePath) && imagePath != null) {
		// String imageExtNameWithOutDot =
		// imagePath.substring(imagePath.lastIndexOf(".") + 1);
		// ImageIO.setUseCache(false);
		// BufferedImage image = ImageIO.read(inputStream);
		// String imageExtName = "";
		// if ("jpg".equalsIgnoreCase(imageExtNameWithOutDot)) {
		// imageExtName = "jpeg";
		// imageExtNameWithOutDot = "jpg";
		// } else if ("jpeg".equalsIgnoreCase(imageExtNameWithOutDot)
		// || "jpe".equalsIgnoreCase(imageExtNameWithOutDot)) {
		// imageExtName = "jpeg";
		// } else if ("png".equalsIgnoreCase(imageExtNameWithOutDot)) {
		// imageExtName = "x-png";
		// } else if ("gif".equalsIgnoreCase(imageExtNameWithOutDot)) {
		// imageExtName = "gif";
		// } else if ("bmp".equalsIgnoreCase(imageExtNameWithOutDot)) {
		// imageExtName = "x-ms-bmp";
		// }
		// response.setContentType("image/" + imageExtName);
		// ServletOutputStream out = response.getOutputStream();
		// ImageIO.write(image, imageExtNameWithOutDot, out);
		// out.flush();
		// out.close();
		// }
		// } catch (Exception e) {
		// logger.error(e.getMessage());
		// return "";
		// } finally {
		// if (inputStream != null) {
		// inputStream.close();
		// }
		// }
		// return "";
	}
	
	/**
	 * 压缩图片
	 * @param srcFilePath
	 * @param descFilePath
	 * @return
	 */
	public boolean compressPic(String srcFilePath, String descFilePath) {
		ImageIO.setUseCache(false);
		File file = null;
		BufferedImage src = null;
		FileOutputStream out = null;
		ImageWriter imgWrier;
		ImageWriteParam imgWriteParams;
		
		// 指定写图片的方式为 jpg
		imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
		// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// 这里指定压缩的程度，参数qality是取值0~1范围内，
		imgWriteParams.setCompressionQuality(1);
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		ColorModel colorModel = ColorModel.getRGBdefault();
		// 指定压缩时使用的色彩模式
		imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
			colorModel.createCompatibleSampleModel(16, 16)));
		
		try {
			if (StringUtil.isBlank(srcFilePath)) {
				return false;
			} else {
				file = new File(srcFilePath);
				src = ImageIO.read(file);
				out = new FileOutputStream(descFilePath);
				
				imgWrier.reset();
				// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
				// OutputStream构造
				imgWrier.setOutput(ImageIO.createImageOutputStream(out));
				// 调用write方法，就可以向输入流写图片
				imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	@ResponseBody
	@RequestMapping(value = "ueServer.json", produces = "application/json")
	public Object ueditorConfig(String action, HttpServletRequest request,
								HttpServletResponse response, HttpSession session) {
		Object json = null;
		FileReader fileReader = null;
		
		if (StringUtil.equals("config", action)) { //加载服务器配置
			try {
				if (ueconfig == null) {
					//					FileReader fileReader = new FileReader(new File(this.getClass()
					//						.getClassLoader().getResource("ueconfig.json").getFile()));
					String configPath = request.getServletContext().getRealPath("/")
										+ "js/ueditor/jsp/ueconfig.json";
					fileReader = new FileReader(configPath);
					JSONParser jsonParser = new JSONParser();
					ueconfig = (org.json.simple.JSONObject) jsonParser.parse(fileReader);
				}
				json = ueconfig;
				
			} catch (Exception e) {
				logger.error("加载服务端UEditor配置出错{}", e);
			} finally {
				if (fileReader != null)
					try {
						fileReader.close();
					} catch (IOException e) {
						logger.error("关闭流出错", e);
					}
			}
		} else if (StringUtil.equals("upload", action)) {//上传文件
		
			String[] pathArray = null;
			File file = null;
			String oldFileName = "";
			JSONObject jsonobj = new JSONObject();
			try {
				ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
				fileUpload.setHeaderEncoding("utf-8");
				List<FileItem> fileList = null;
				try {
					fileList = fileUpload.parseRequest(request);
				} catch (FileUploadException ex) {
					logger.error(ex.getMessage(), ex);
					jsonobj.put("state", false);
					jsonobj.put("message", "文件接收异常！");
					return jsonobj;
				}
				Iterator<FileItem> it = fileList.iterator();
				String name = "";
				String extName = "";
				while (it.hasNext()) {
					FileItem item = it.next();
					if (!item.isFormField()) {
						// 解析文件
						name = item.getName();
						oldFileName = name;
						if (name == null || name.trim().equals("")) {
							continue;
						}
						// 得到文件的扩展名
						if (name.lastIndexOf(".") >= 0) {
							extName = name.substring(name.lastIndexOf("."));
						}
						if (!fileTypeList.contains(extName.toLowerCase())) {
							jsonobj.put("state", false);
							jsonobj.put("message", "文件上传失败(文件类型不正确)！");
							return jsonobj;
						}
						if (".pdf".equalsIgnoreCase(extName)) {
							pathArray = FileUploadUtils.getStaticFilesPdfPath(request, name);
						} else {
							pathArray = FileUploadUtils.getStaticFilesImgPath(request, name);
						}
						String savePath = pathArray[0];
						try {
							file = new File(savePath);
							item.write(file);
							//png压缩后图片失真？
							if (".jpg".equalsIgnoreCase(extName)
								|| ".bmp".equalsIgnoreCase(extName)) {
								boolean pass = this.compressPic(savePath, savePath);
								if (!pass) {
									logger.info("文件压缩异常");
									jsonobj.put("state", false);
									jsonobj.put("message", "文件压缩异常");
									return jsonobj;
								}
							}
						} catch (Exception e) {
							logger.error("文件写入异常，异常信息：{}", e.toString(), e);
							jsonobj.put("state", false);
							jsonobj.put("message", "文件写入异常");
							return jsonobj;
						}
					}
				}
			} catch (Exception e) {
				logger.error("文件上传异常，异常信息：{}", e.toString(), e);
				jsonobj.put("success", false);
				jsonobj.put("message", "文件上传异常");
				return jsonobj;
			}
			jsonobj.put("state", "SUCCESS");
			jsonobj.put("message", "文件上传成功");
			jsonobj.put("original", oldFileName);
			jsonobj.put("name", file.getName());
			jsonobj.put("size", file.length());
			jsonobj.put("url", pathArray[1]);
			return jsonobj;
		}
		
		return json;
	}
}
