package io.dtchain.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import io.dtchain.dao.DataProceDao;
import io.dtchain.dao.MangageDao;
import io.dtchain.dao.RecordDao;
import io.dtchain.dao.UpLoadDao;
import io.dtchain.entity.AttendTable;
import io.dtchain.entity.DataProceTable;
import io.dtchain.entity.EnterTable;
import io.dtchain.entity.OutTable;
import io.dtchain.entity.QueryRecord;
import io.dtchain.entity.RecordTable;
import io.dtchain.entity.ResultProce;
import io.dtchain.service.UpLoadService;
import io.dtchain.utils.Result;

@Service("UpLoadService")
public class UpLoadServiceImpl implements UpLoadService {
	@Resource
	private UpLoadDao uploadDao;
	@Resource
	private RecordDao recordDao;
	@Resource
	private MangageDao mangageDao;
	@Resource
	private DataProceDao dataProceDao;
	private Map<String, String> map = new HashMap<String, String>();
	private Map<String, String> overMap = new HashMap<String, String>();// 储存加班信息
	private Map<Integer, String> dataNum = new HashMap<Integer, String>();

	/**
	 * 上传文件
	 */
	public void upLoad(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String dateS = "";
		String dateE = "";
		List<Map<String, Object>> listNum = mangageDao.queryNum();
		if (!listNum.isEmpty()) {
			for (Map<String, Object> m : listNum) {
				dataNum.put(Integer.parseInt(m.get("empNum").toString()), m.get("empName").toString());

			}
		}

		// 文件保存路径
		String savePath = req.getServletContext().getRealPath("/WEB-INF/upload");
		// 上传生成的临时文件保存目录
		String tempPath = req.getServletContext().getRealPath("/WEB-INF/temp");
		File tmpFile = new File(tempPath);
		// 文件不存在就创建
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		// 消息提示
		String message = "";
		// 创建DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置工厂的缓冲区大小，上传文件超过缓冲区大小就会产生临时文件500kb,默认10kb
		factory.setSizeThreshold(1024 * 500);
		// 临时文件保存路径
		factory.setRepository(tmpFile);
		// 创建文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 监听文件上传进度
		upload.setProgressListener(new ProgressListener() {

			public void update(long pBytesRead, long pContentLength, int arg2) {
				System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);

			}

		});
		// 解决上传名为中文的乱码
		upload.setHeaderEncoding("UTF-8");
		// 判断是否是表单提交上来的数据
		if (!ServletFileUpload.isMultipartContent(req)) {
			// 按照传统方式获取数据
			return;
		}
		// 设置单个文件最大值1MB
		upload.setFileSizeMax(1024 * 1024);
		// 设置上传文件的总量最大值10MB
		upload.setSizeMax(1024 * 1024 * 10);

		try {
			List<?> fileItems = upload.parseRequest(req);
			Iterator<FileItem> fileItem = (Iterator<FileItem>) fileItems.iterator();
			while (fileItem.hasNext()) {
				// FileItemStream item=fileItem.next();
				FileItem item = fileItem.next();
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String fieldName = item.getFieldName();// 获取字段名
					String value = item.getString("UTF-8");// 获取字段值
					if ("dateS".equals("" + fieldName)) {
						dateS = value.replace('-', '/');
					}
					if ("dateE".equals("" + fieldName)) {
						dateE = value.replace('-', '/');
					}
				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					String filename = item.getName();
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 得到上传文件的扩展名
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 仅支持后缀为xltx xlsx
					if (fileExtName.equals("xltx") || fileExtName.equals("xlsx")) {
						// 获取item中的上传文件的输入流
						// InputStream in = item.openStream();
						InputStream in = item.getInputStream();
						// 得到文件保存的名称
						// String saveFilename = makeFileName(filename);
						// 得到文件的保存目录
						String realSavePath = makePath(filename, savePath);
						// 创建一个文件输出流
						FileOutputStream out = new FileOutputStream(realSavePath + "\\" + filename);
						// 创建一个缓冲区
						byte buffer[] = new byte[1024];
						// 判断输入流中的数据是否已经读完的标识
						int len = 0;
						// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
						while ((len = in.read(buffer)) > 0) {
							// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath +
							// "\\" + filename)当中
							out.write(buffer, 0, len);
						}
						// 关闭输入流
						in.close();
						// 关闭输出流
						out.close();
						message = "文件上传成功！";
						String realfileName = realSavePath + "\\" + filename;

						System.out.println(dateS + " " + dateE);
						// 插入考勤记录
						insertAttendRecord(realfileName, dateS, dateE);
						// 读取完删除文件
						File delFile = new File(realfileName);
						if (delFile.exists()) {
							boolean f = delFile.delete();
						}
						res.sendRedirect("../index.html");
					} else {
						System.out.println("不支持后缀为：" + fileExtName + " 的格式");
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建目录，用于保存文件
	 */
	private String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// 构造新的保存目录
		String dir = savePath + "\\" + dir1 + "\\" + dir2; // upload\2\3
															// upload\3\5
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}

	/**
	 * 插入考勤记录到数据库
	 */
	private void insertAttendRecord(String realfileName, String dateS, String dateE) throws ParseException {
		try {
			// 用于存储原始数据
			List<AttendTable> list = new ArrayList<AttendTable>();
			// 用于存储进门信息
			List<AttendTable> enter = new ArrayList<AttendTable>();
			// 用于存储出门信息
			List<AttendTable> out = new ArrayList<AttendTable>();
			AttendTable record;
			// 获取到工作薄
			XSSFWorkbook workbook = new XSSFWorkbook(realfileName);
			int num = workbook.getNumberOfSheets(); // 获取工作表格数
			XSSFSheet sheet;
			int rowNum = 0;
			for (int i = 0; i < num; i++) {
				sheet = workbook.getSheetAt(i); // 获取第i个工作表格
				rowNum = sheet.getLastRowNum(); // 获取最后一行行号，即行数
				SimpleDateFormat sdf = null;
				Cell[] cell = new Cell[7];
				for (int j = 1; j <= rowNum; j++) { // 循环取行
					Row row = sheet.getRow(j);
					if (row != null) {
						record = new AttendTable();
						for (int l = 0; l < row.getLastCellNum(); l++) {
							cell[l] = row.getCell(l);

						}

						// 封装类值
						record.setUserName(judge(cellNull(cell[0]))); // 用户名
						record.setEmpNum((int) cell[1].getNumericCellValue()); // 工号
						record.setCardNum(judge(cellNull(cell[2])));// 卡号
						sdf = new SimpleDateFormat("yyyy/MM/dd");
						String cale = sdf.format(cell[3].getDateCellValue());
						record.setDates(cale);// 日期
						sdf = new SimpleDateFormat("HH:mm:ss");
						String cale1 = sdf.format(cell[4].getDateCellValue());
						record.setTimes(cale1); // 时间
						record.setDirection(cellNull(cell[5])); // 进出门
						record.setSourceEvent(((int) cell[6].getNumericCellValue())); // 事件码

						if (dataNum.get((int) cell[1].getNumericCellValue()) != null) {
							record.setUserName(dataNum.get((int) cell[1].getNumericCellValue()));
						} else {
							continue;
						}

						if (dateS.compareTo(cale) <= 0 && dateE.compareTo(cale) >= 0) {
							if (cellNull(cell[5]).equals("进门")) {
								enter.add(record);
							} else {
								out.add(record);
							}
							// 添加到集合
							list.add(record);

						}

					}
					// 批量处理数据写入数据库，每次200条记录
					if ((list.size() + 1) % 201 == 0) {

						uploadDao.insertAttendRecord(list);
						list.clear();
					}
				}
			}

			if (list.size() != 0) {
				uploadDao.insertAttendRecord(list);
			}

			workbook.close();
			// 判断上下班时间信息
			List<EnterTable> enterList = new ArrayList<EnterTable>();
			List<OutTable> outList = new ArrayList<OutTable>();
			if (!enter.isEmpty()) {
				enterList = workTime(enter);
			}
			if (!out.isEmpty()) {
				outList = offWork(out);
			}
			// 调用搜索日期相同，进行数据合并
			searchDate(enterList, outList);

		} catch (FileNotFoundException e) {
			System.out.println("文件获取异常");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 过滤逗号
	private String judge(String userName) {
		int n = userName.indexOf("'");
		String str;
		if (n == 0) {
			str = userName.substring(1);
		} else {
			str = userName.substring(0);
		}
		return str;
	}

	/**
	 * 判断cell 是否为空值
	 */
	private String cellNull(Cell cell) {
		if (cell == null) {
			return "";
		} else {
			return cell.getStringCellValue();
		}

	}

	/**
	 * 通过进门打卡信息判断上班时间信息
	 */
	private List<EnterTable> workTime(List<AttendTable> enter) {
		List<EnterTable> enterList = new ArrayList<EnterTable>();

		/*
		 * 进门表 日期相同，早上上班 -12：0：0取时间最小 (若无即早上没上班) 下午上班
		 * 12：0：0-14：0：0取时间最大（若无则取大于14：0：0的时间最小）
		 * 
		 */

		// 非空，执行进门时间判断
		Time t1 = Time.valueOf("11:59:59");
		Time t2 = Time.valueOf("13:59:59");
		Time t3 = Time.valueOf("7:59:59");
		Time t4 = Time.valueOf("15:00:01");
		EnterTable en;
		for (int i = enter.size() - 1; i >= 0;) {
			// 用于更新i值
			int index = 1;
			AttendTable att = enter.get(i);
			en = new EnterTable();
			en.setDates(att.getDates());
			en.setEmpName(att.getUserName());
			en.setMinTime(att.getTimes());
			// 早上上班8:00:00-12:00:00
			if (Time.valueOf(att.getTimes()).before(t1) && Time.valueOf(att.getTimes()).after(t3)) {
				en.setWorkMorn(att.getTimes());
			} else if (Time.valueOf(att.getTimes()).after(t2)) { // 否则下午上班
																	// 14：00：00-
				en.setWorkAfter(att.getTimes());
			}
			StringBuilder str = new StringBuilder();
			// 12:00:0-14:00:00
			if (Time.valueOf(att.getTimes()).after(t1) && Time.valueOf(att.getTimes()).before(t2)) {
				str.append(att.getTimes());
			} else {
				str.append("12:00:00");
			}

			for (int j = enter.size() - 2; j >= 0; j--) {
				AttendTable a = enter.get(j);
				// 判断日期是否是同一天
				if (att.getDates().equals(a.getDates()) && att.getUserName().equals(a.getUserName())) {

					index++;
					// 8:00:00-12:00:00
					if (Time.valueOf(a.getTimes()).before(t1) && Time.valueOf(a.getTimes()).after(t3)) {
						// 8:00:00-12:00:00的时间最小值
						if (en.getWorkMorn() != null
								&& Time.valueOf(en.getWorkMorn()).after(Time.valueOf(a.getTimes()))) {
							en.setWorkMorn(a.getTimes());
						} else if (en.getWorkMorn() == null) {
							en.setWorkMorn(a.getTimes());
						}
					} else if (Time.valueOf(a.getTimes()).after(t1) && Time.valueOf(a.getTimes()).before(t2)) {// 12:0:0-14:0:0
						// 12:0:0-14:00:00的时间最大值
						if (Time.valueOf(str.toString()).before(Time.valueOf(a.getTimes()))) {
							str.delete(0, str.length());
							str.append(a.getTimes());
						}
						// 12:00:00-14:00:00的时间最小用于判断下班时间
						if (Time.valueOf(en.getMinTime()).after(Time.valueOf(a.getTimes()))) {
							en.setMinTime(a.getTimes());
						}
					} else { // 14:0:00之后
						// 不为空，14:00:00-15:00:00取时间最小
						if (en.getWorkAfter() != null && Time.valueOf(a.getTimes()).after(t2)
								&& Time.valueOf(a.getTimes()).before(t4)
								&& Time.valueOf(en.getWorkAfter()).after(Time.valueOf(a.getTimes()))) { // -------
							en.setWorkAfter(a.getTimes());
							// 为空且时间在14：00：0-15：00：00
						} else if (en.getWorkAfter() == null && Time.valueOf(a.getTimes()).after(t2)
								&& Time.valueOf(a.getTimes()).before(t4)) {
							en.setWorkAfter(a.getTimes());
						}
					}
					// 判断完成，删除，防止重复判断
					enter.remove(j);
				}
			} // 防止和初始值相同
			if (!str.toString().equals("12:00:00")) {
				//
				if (!str.toString().equals(en.getWorkAfter()) && en.getWorkAfter() != null) {
					en.setWorkAfter(str.toString() + " " + en.getWorkAfter());
				} else {
					en.setWorkAfter(str.toString());
				}
			} // 判断结束，添加到集合
			enterList.add(en);
			// 移除最外层元素
			enter.remove(enter.size() - 1);
			// 更新i值（即减去所移除的次数）
			i -= index;
		}
		return enterList;
	}

	/**
	 * 通过出门打卡信息判断下班时间
	 */
	private List<OutTable> offWork(List<AttendTable> out) {
		/*
		 * 出门表 中午下班8:0:0-12:0:0取时间最大值 (若无则 取12:0:0-14：0：0最小值) 晚上下班 14：0：0-最大值
		 */
		Time t1 = Time.valueOf("11:59:59");
		Time t2 = Time.valueOf("8:00:00");
		Time t3 = Time.valueOf("13:59:59");
		String time = "8:00:00";
		List<OutTable> outlist = new ArrayList<OutTable>();
		OutTable en;
		for (int i = out.size() - 1; i >= 0;) {
			int index = 1;
			AttendTable att = out.get(i);
			en = new OutTable();
			en.setEmpName(att.getUserName());
			en.setDates(att.getDates());

			// 判断昨晚加班00：00：00-05:00:00时间

			if (Time.valueOf(att.getTimes()).after(Time.valueOf("00:00:00"))
					&& Time.valueOf(att.getTimes()).before(Time.valueOf("05:00:00"))) {
				en.setOverTime(att.getTimes());

			} else {
				en.setOverTime("00:00:00");
			}
			// 12:0:0-14:0:0 的一个初始值
			if (Time.valueOf(att.getTimes()).after(t1) && Time.valueOf(att.getTimes()).before(t3)) {
				en.setAtNoon(att.getTimes());
				en.setMaxTime(att.getTimes());
			} else if (Time.valueOf(att.getTimes()).after(t3)) { // 14:0:0-的一个初始值
				en.setAtNight(att.getTimes());
				en.setMaxTime("12:00:00");
			}

			StringBuilder str = new StringBuilder();
			// att>12:00:00
			if (Time.valueOf(att.getTimes()).after(t1)) {
				str.append(time);
			} else {
				str.append(att.getTimes());

			}
			for (int j = out.size() - 2; j >= 0; j--) {
				AttendTable a = out.get(j);
				// 判断名字和日期是否相同
				if (att.getDates().equals(a.getDates()) && att.getUserName().equals(a.getUserName())) {
					index++;
					// 8:00:00-12:00:00
					if (Time.valueOf(a.getTimes()).after(t2) && Time.valueOf(a.getTimes()).before(t1)) {
						// 8:00:00-12:00:00的时间最大值
						if (Time.valueOf(str.toString()).before(Time.valueOf(a.getTimes()))) {
							str.delete(0, str.length());
							str.append(a.getTimes());
						}
					} else if (Time.valueOf(a.getTimes()).after(t1) && Time.valueOf(a.getTimes()).before(t3)) { // 12:0:0-14:0:0
						// 12:00:00-14:00:00的时间最小值
						if (en.getAtNoon() != null && Time.valueOf(en.getAtNoon()).after(Time.valueOf(a.getTimes()))) {
							en.setAtNoon(a.getTimes());
						} else if (en.getAtNoon() == null) {
							en.setAtNoon(a.getTimes());
						}

						// 12:00:00-14:00:00最大时间，用于判断上班时间
						if (en.getMaxTime() != null && a.getTimes() != null)
							if (Time.valueOf(en.getMaxTime()).before(Time.valueOf(a.getTimes()))) {
								en.setMaxTime(a.getTimes());
							}
					} else { // 14:00:00- 且14：00：00之后的最大值
						if (en.getAtNight() != null && Time.valueOf(a.getTimes()).after(t3)
								&& Time.valueOf(a.getTimes()).after(Time.valueOf(en.getAtNight()))) {
							en.setAtNight(a.getTimes());
						} else if (en.getAtNight() == null) {
							en.setAtNight(a.getTimes());
						}
					}
					// 加班到凌晨
					if (Time.valueOf(a.getTimes()).after(Time.valueOf(en.getOverTime()))
							&& Time.valueOf(a.getTimes()).before(Time.valueOf("05:00:01"))) {
						en.setOverTime(a.getTimes());
					}
					out.remove(j);
				}
			}
			if (!str.toString().equals(time)) {
				if (en.getAtNoon() != null) {
					en.setAtNoon(str.toString() + " " + en.getAtNoon());
				} else if (Time.valueOf(str.toString()).after(Time.valueOf("11:00:00"))) {
					en.setAtNoon(str.toString());
				} else {
					en.setAtNoon("未打卡");
				}

			}

			outlist.add(en);
			out.remove(out.size() - 1);
			i -= index;
		}
		return outlist;
	}

	/**
	 * 查找相同的打卡日期
	 */
	private void searchDate(List<EnterTable> enter, List<OutTable> out) throws ParseException {
		List<RecordTable> list = new ArrayList<RecordTable>();
		// 按照日期排序（降序）
		// Collections.sort(out);
		// 加班标识符
		// int over=0;
		// int index=0;
		// 用于存储考勤记录
		RecordTable rt;
		EnterTable en;
		OutTable ou;
		if (!enter.isEmpty() && !out.isEmpty()) {
			for (int i = enter.size() - 1; i >= 0; i--) {
				// 标识符用于判断是否删除最外层循环的集合元素
				boolean flog = false;
				en = enter.get(i);
				rt = new RecordTable();
				for (int j = out.size() - 1; j >= 0; j--) {
					ou = out.get(j); // 0 1 2 3 4 5 6 7 7
					// 进门，出门的名字和日期相同进行合并
					if (en.getEmpName().equals(ou.getEmpName()) && en.getDates().equals(ou.getDates())) {
						flog = true;
						// index=j;
						/*
						 * 进门 获取早上上班时间如果为null,即为未打卡
						 * 获取下午上班时间如果只有一个即为上班时间,如果是两个即取最小值
						 * 
						 * 出门 获取中午下班时间如果为null即为未打卡,如果获取到一个极为下班时间(如果下午上班时间为null,
						 * 即未出公司设置为null) 如果只为两个即取最大值,
						 */
						/*
						 * 对数据进行逻辑判别
						 */
						rt = logjudg(en, ou);
						rt.setConBin(en.getEmpName() + en.getDates());
						if (!ou.getOverTime().equals("00:00:00")) {
							overMap.put(ou.getEmpName() + "" + ou.getDates(), ou.getOverTime());
						}
						// if(over==1&&)

						out.remove(j);
						rt.setDept(deptMapOut(ou));
						list.add(rt);
						// 两百写入一次数据库
						if (list.size() % 301 == 0) {
							Collections.sort(list);
							for (int h = 0; h < list.size(); h++) {
								for (Map.Entry<String, String> m : overMap.entrySet()) {
									if ((list.get(h).getConBin()).equals(m.getKey())
											&& list.get(h).getEmpName().equals(list.get(h - 1).getEmpName())) {
										list.get(h - 1).setAtNight(m.getValue());
									}
								}
							}
							recordDao.workTable(list);
							// 调用函数进行数据处理
							dataProce(list);
							overMap.clear();
							list.clear();
						}
						break;
					}
				}
				// 进出都有打卡才删除
				if (flog) {
					enter.remove(i);
				}

			}
		}
		/*
		 * 剩余的集合元素都是只打半天卡 需要重新写入记录
		 */
		if (!enter.isEmpty()) {
			for (int k = 0; k < enter.size(); k++) {

				rt = logjudg(enter.get(k), new OutTable());
				rt.setConBin(enter.get(k).getEmpName() + enter.get(k).getDates());
				rt.setDept(deptMapEnter(enter.get(k)));
				list.add(rt);
			}
		}
		if (!out.isEmpty()) {
			for (int k = 0; k < out.size(); k++) {
				rt = logjudg(new EnterTable(), out.get(k));
				rt.setConBin(out.get(k).getEmpName() + out.get(k).getDates());
				if (!out.get(k).getOverTime().equals("00:00:00")) {
					overMap.put(out.get(k).getEmpName() + "" + out.get(k).getDates(), out.get(k).getOverTime());
				}
				rt.setDept(deptMapOut(out.get(k)));
				list.add(rt);
			}
		}
		if (!list.isEmpty()) {
			Collections.sort(list);

			for (int h = 0; h < list.size(); h++) {
				for (Map.Entry<String, String> m : overMap.entrySet()) {

					if ((list.get(h).getConBin()).equals(m.getKey())
							&& list.get(h).getEmpName().equals(list.get(h - 1).getEmpName())) {
						list.get(h - 1).setAtNight(m.getValue());
					}
				}
			}
			overMap.clear();
			recordDao.workTable(list);
			// 调用该函数进行数据处理
			dataProce(list);
			// 清空都为null的记录
			recordDao.emptyNull();
			// 清空都为0的记录
			dataProceDao.emptyZero();
		}
	}

	/**
	 * 对数据进行逻辑判别以及数据整合
	 */
	private RecordTable logjudg(EnterTable en, OutTable ou) {

		RecordTable rt = new RecordTable();
		if (en.getEmpName() != null) {
			rt.setEmpName(en.getEmpName());
			rt.setDates(en.getDates());
		} else {
			rt.setEmpName(ou.getEmpName());
			rt.setDates(ou.getDates());
		}
		// 早上上班
		if (en.getWorkMorn() != null) {
			rt.setWorkMorn(en.getWorkMorn());
		} else {
			rt.setWorkMorn("未打卡");
		}
		// 晚上下班
		if (ou.getAtNight() != null) {
			rt.setAtNight(ou.getAtNight());
		} else {
			rt.setAtNight("未打卡");
		}
		// 下午上班
		if (en.getWorkAfter() == null) {
			rt.setWorkAfter("未打卡");
		} else if (en.getWorkAfter().indexOf(" ") != -1) { // 有两个值
			String str1 = en.getWorkAfter().substring(0, en.getWorkAfter().indexOf(" "));
			String str2 = en.getWorkAfter().substring(en.getWorkAfter().indexOf(" ") + 1);
			if (Time.valueOf(str1).after(Time.valueOf(str2))) {
				// rt.setWorkAfter(str1);
				String s = str1;
				str1 = str2;
				str2 = s;

			}
			// 与出门的最大时间比较

			if (ou.getMaxTime() != null && Time.valueOf(ou.getMaxTime()).after(Time.valueOf(str1))
					&& Time.valueOf(ou.getMaxTime()).before(Time.valueOf(str2))
					&& Time.valueOf(ou.getMaxTime()).before(Time.valueOf("14:00:01"))) {
				if (Time.valueOf(str1).after(Time.valueOf("13:44:59"))) {
					rt.setWorkAfter(str1);
				} else if (Time.valueOf(str2).before(Time.valueOf("15:00:00"))) { // 修改
					rt.setWorkAfter(str2);
				} else {
					rt.setWorkAfter("未打卡");
				}
			} else if (ou.getMaxTime() != null && Time.valueOf(ou.getMaxTime()).before(Time.valueOf(str1))) { // 进来两次但中间出去没有打卡
				rt.setWorkAfter(str1);
			} else {
				rt.setWorkAfter(str2);
			}
		} else {
			if (Time.valueOf(en.getWorkAfter()).before(Time.valueOf("15:00:00"))) {
				rt.setWorkAfter(en.getWorkAfter());
			} else {
				rt.setWorkAfter("未打卡");
			}
		}

		// 中午下班
		if (ou.getAtNoon() == null) {
			rt.setAtNoon("未打卡");

		} else if (ou.getAtNoon().indexOf(" ") != -1) { // 两个值
			String str1 = ou.getAtNoon().substring(0, ou.getAtNoon().indexOf(" "));
			String str2 = ou.getAtNoon().substring(ou.getAtNoon().indexOf(" ") + 1);

			if (Time.valueOf(str1).after(Time.valueOf(str2))) {
				// rt.setAtNoon(str2);
				String s = str1;
				str1 = str2;
				str2 = s;
			}
			/*
			 * t1<12:00:00 t2>12:00:00存一个最小的进门时间t 1.t>t1,t2则取max(t1,t2),说明t1没出去
			 * 
			 */
			if (en.getMinTime() != null) {

				if (Time.valueOf(en.getMinTime()).after(Time.valueOf(str1))
						&& Time.valueOf(en.getMinTime()).after(Time.valueOf(str2))) {
					rt.setAtNoon(str2);
				}
				// t>12:20:00 && t2>12:20:0则取t1
				else if (Time.valueOf(en.getMinTime()).after(Time.valueOf("12:20:01"))
						&& Time.valueOf(str2).after(Time.valueOf("12:20:01"))) {
					rt.setAtNoon(str1);
				}
				// .t,t2<12:20:00则取t2
				else if (Time.valueOf(en.getMinTime()).before(Time.valueOf("12:20:01"))
						&& Time.valueOf(str2).before(Time.valueOf("12:20:01"))) {
					rt.setAtNoon(str2);
				}
				// t<12:20:00 && t2>12:20:00则取t2
				else if (Time.valueOf(en.getMinTime()).before(Time.valueOf("12:19:59"))
						&& Time.valueOf(str2).after(Time.valueOf("12:20:01"))) {
					rt.setAtNoon(str2);
				}
			} else if (Time.valueOf(str1).after(Time.valueOf("11:55:00"))
					&& Time.valueOf(str2).before(Time.valueOf("12:10:00"))) {
				rt.setAtNoon(str1);
			} else if (Time.valueOf(str2).before(Time.valueOf("13:10:00"))) {
				rt.setAtNoon(str2);
			} else {
				rt.setAtNoon("未打卡");
			}
		} else {
			rt.setAtNoon(ou.getAtNoon());
		}
		return rt;
	}

	/**
	 * 添加部门字段值
	 */
	private String deptMapOut(OutTable out) {
		if (map.get(out.getEmpName()) != null) {
			return map.get(out.getEmpName());
		} else {
			String s = mangageDao.queryDept(out.getEmpName());
			map.put(out.getEmpName(), s);
			return s;
		}
	}

	private String deptMapEnter(EnterTable enter) {
		if (map.get(enter.getEmpName()) != null) {
			return map.get(enter.getEmpName());
		} else {
			String s = mangageDao.queryDept(enter.getEmpName());
			map.put(enter.getEmpName(), s);
			return s;
		}
	}

	/**
	 * 对工作时间表进行处理
	 */
	private void dataProce(List<RecordTable> list) throws ParseException {
		List<DataProceTable> proce = new ArrayList<DataProceTable>();
		List<DataProceTable> db = new ArrayList<DataProceTable>();
		List<RecordTable> detailed = new ArrayList<RecordTable>();

		DataProceTable data = null;
		for (RecordTable rt : list) {
			StringBuffer sb = new StringBuffer();
			// 迟到早退标记
			boolean fog = false;
			data = new DataProceTable();
			data.setDates(rt.getDates());
			data.setEmpName(rt.getEmpName());
			data.setDept(rt.getDept());

			// 记录迟到
			int late = 0;
			// 记录早退
			int early = 0;
			// 上完一天班
			data.setDays(1);

			// 计算一天上班小时数
			if (!rt.getWorkMorn().equals("未打卡")) { // 早上上班卡已打
				// 9:00:0-18:00:00
				if (Time.valueOf(rt.getWorkMorn()).before(Time.valueOf("09:10:01"))) {
					data.setHours(calcHours(rt, "19:59:59", "18:00:00"));
				} else {// 10:00:00-19:00:00
					data.setHours(calcHours(rt, "20:59:59", "19:00:00"));

				}
			} else if (!rt.getAtNight().equals("未打卡")) { // 晚上上班卡已打
				if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("20:59:59"))
						&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("15:00:00"))) {

					data.setHours(calcAfterTime(rt, rt.getAtNight()));
				} else if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59"))
						|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
								&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00")))) {
					data.setHours(calcAfterTime(rt, "19:00:00"));

				}
			} else if (!rt.getAtNoon().equals("未打卡")) { //
				if (!rt.getWorkAfter().equals("未打卡")) {
					data.setHours(hoursCale("10:00:00", "19:00:00") - 2);
				}
			}
			// 迟到次数,早退次数
			// 加班，正常下班时间超过一小时属于加班时间
			/*
			 * 9:00:00-18:00:00 迟到时间超过9:15:00即属于10:00:00-19：00：00
			 */
			if (!rt.getWorkMorn().equals("未打卡") && Time.valueOf(rt.getWorkMorn()).before(Time.valueOf("09:10:01"))) {
				// 迟到
				if (!rt.getWorkMorn().equals("未打卡") && Time.valueOf(rt.getWorkMorn()).after(Time.valueOf("09:00:00"))) {
					late++;
					fog = true;
					sb.append("l");
				}
				if (!rt.getAtNight().equals("未打卡")) {
					// 早退
					if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("18:00:00"))
							&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("14:00:00"))) {
						early++;
						fog = true;
						sb.append("e");
					}
					// 加班
					if ((Time.valueOf(rt.getAtNight()).after(Time.valueOf("19:59:59"))
							&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("23:59:59")))
							|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
									&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00")))) {
						fog = true;
						sb.append("o");
						data.setOverTime(clacOverTime(rt, "18:00:00"));
					}
				}

			} else { // 10:00:00-19:00:00
				// 迟到
				if (!rt.getWorkMorn().equals("未打卡") && Time.valueOf(rt.getWorkMorn()).after(Time.valueOf("10:00:00"))) {
					late++;
					fog = true;
					sb.append("l");
				}
				if (!rt.getAtNight().equals("未打卡")) {
					// 早退
					if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("19:00:00"))
							&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("14:00:00"))) {
						early++;
						fog = true;
						sb.append("e");
					}
					// 加班
					if ((Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59"))
							&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("23:59:59")))
							|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
									&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00")))) {
						fog = true;
						sb.append("o");
						data.setOverTime(clacOverTime(rt, "19:00:00"));
					}

				}

			}
			// 下午上班迟到
			if (!rt.getWorkAfter().equals("未打卡") && Time.valueOf(rt.getWorkAfter()).after(Time.valueOf("14:00:00"))) {
				late++;
				fog = true;
				sb.append("l");
			}
			// 中午下班早退
			if (!rt.getAtNoon().equals("未打卡") && Time.valueOf(rt.getAtNoon()).before(Time.valueOf("12:00:00"))
					&& !rt.getWorkAfter().equals("未打卡")) {

				early++;
				fog = true;
				sb.append("e");
			}
			data.setLate(late);
			data.setEarlyRetr(early);
			// 排除周末
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cale = Calendar.getInstance();
			cale.setTime(sdf.parse(rt.getDates()));
			if ((cale.get(Calendar.DAY_OF_WEEK) - 1) == 0 || (cale.get(Calendar.DAY_OF_WEEK) - 1) == 6) {
				db.add(data);
				continue;
			}
			// 添加到集合
			proce.add(data);
			if (fog) {
				rt.setConBin(sb.toString());
				detailed.add(rt);
			}
		}
		// 写入迟到早退表
		if (!detailed.isEmpty()) {
			recordDao.detailed(detailed);
		}
		// 储存双休上班表
		if (!db.isEmpty()) {
			dataProceDao.weekInfo(db);
			dataProceDao.empzero();
		}
		// 写入工时统计表
		dataProceDao.dataProce(proce);

	}

	/*
	 * 大于半小时则算一小时
	 */
	private int hoursCale(String night, String times) {
		int hours = (int) Math.abs((Time.valueOf(night).getTime() - Time.valueOf(times).getTime()) / (1000 * 60));
		int h = hours % 60;
		if (h >= 30) {
			hours /= 60;
			hours++;
		} else {
			hours /= 60;
		}
		return hours;
	}

	/**
	 * 判断出去是否超过半小时
	 */
	private int forCale(List<AttendTable> oud, List<AttendTable> end, String time) {
		int n = 0;

		// 修改
		if (!oud.isEmpty() && !end.isEmpty())
			if (Time.valueOf(oud.get(0).getTimes()).before(Time.valueOf(end.get(0).getTimes()))) {
				n += hoursCale(oud.get(0).getTimes(), time);

			}
		for (int i = 0; i < end.size(); i++) {
			for (int j = i; j < oud.size(); j++) {
				if (Time.valueOf(end.get(i).getTimes()).before(Time.valueOf(oud.get(j).getTimes()))) {

					n += hoursCale(end.get(i).getTimes(), oud.get(j).getTimes());
					break;
				}

			}
		}

		return n;
	}

	/**
	 * 计算上班过程中出去的时间
	 */
	private int caleOut(List<AttendTable> oud, List<AttendTable> end, String time) {
		int n = 0;

		for (int i = 0; i < end.size(); i++) {
			for (int j = i; j < oud.size(); j++) {
				if (Time.valueOf(end.get(i).getTimes()).before(Time.valueOf(oud.get(j).getTimes()))
						&& Time.valueOf(end.get(i).getTimes()).before(Time.valueOf(time))) {

					n += hoursCale(end.get(i).getTimes(), oud.get(j).getTimes());
					break;

				}

			}
		}
		return n;
	}

	/**
	 * 查询双休信息
	 */
	public Result<List<ResultProce>> queryWeekInfo(QueryRecord qr) {
		Result<List<ResultProce>> result = new Result<List<ResultProce>>();
		List<ResultProce> list = new ArrayList<ResultProce>();
		if (qr.getEmpDept().equals("全部")) {
			list = uploadDao.queryAllWeekInfo(qr);
		} else if (qr.getEmpName() == "") {
			list = uploadDao.queryDeptWeekInfo(qr);
		} else {
			list = uploadDao.queryWeekInfo(qr);
		}
		result.setData(list);
		result.setMsg("查询双休信息成功");
		result.setState(1);
		return result;
	}

	/*
	 * 计算加班时长
	 */
	private int clacOverTime(RecordTable rt, String time) {
		int num = 0;
		if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59"))
				&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("23:59:59"))) {
			int hours = hoursCale(rt.getAtNight(), time);
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userName", rt.getEmpName());
			map1.put("dates", rt.getDates());
			map1.put("direction", "进门");
			map1.put("times", time);
			List<AttendTable> oud = uploadDao.queryOverTime(map1);
			map1.put("direction", "出门");
			List<AttendTable> end = uploadDao.queryOverTime(map1);

			// 判断出去是否超过半小时
			int n = forCale(oud, end, time); // 修改

			if (n <= 2) {
				num = (hours - n);
			} else {
				num = (hours);
			}

		} else if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
				&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00"))) {
			long hours1 = (Time.valueOf(time).getTime() - Time.valueOf("24:00:00").getTime()) / (1000 * 60 * 60);
			int hours2 = hoursCale(rt.getAtNight(), "00:00:00");
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("userName", rt.getEmpName());
			map1.put("dates", rt.getDates());
			map1.put("direction", "进门");
			map1.put("times", time);
			List<AttendTable> oud = uploadDao.queryOverTime(map1);
			map1.put("direction", "出门");
			List<AttendTable> end = uploadDao.queryOverTime(map1);
			// 判断出去是否超过半小时
			int n = forCale(oud, end, time);
			if (n <= 2) {
				num = ((int) Math.abs(hours1) + (int) Math.abs(hours2) - n);
			} else {
				num = ((int) Math.abs(hours1) + (int) Math.abs(hours2));
			}

		}
		return num;
	}

	/*
	 * 计算正常上班时长
	 */
	private int calcHours(RecordTable rt, String time, String time2) { // time是加班时间的分界点,time2是正常下班时间点18:00:00或者19:00:00
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("userName", rt.getEmpName());
		map1.put("dates", rt.getDates());
		map1.put("direction", "进门");

		if (!rt.getAtNight().equals("未打卡")) {
			if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("20:59:59")) && time2.equals("19:00:00")) {
				map1.put("times", time2);
			} else if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("20:59:59")) && time2.equals("19:00:00")) {
				map1.put("times", rt.getAtNight());

			}
			if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("19:59:59")) && time2.equals("18:00:00")) {
				map1.put("times", time2);
			} else if (Time.valueOf(rt.getAtNight()).before(Time.valueOf("19:59:59")) && time2.equals("18:00:00")) {
				map1.put("times", rt.getAtNight());
			}
			if (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
					&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00"))) {
				map1.put("times", time2);
			}
		}

		List<AttendTable> oud = uploadDao.queryOutTime(map1);
		map1.put("direction", "出门");
		List<AttendTable> end = uploadDao.queryOutTime(map1);
		int n = caleOut(oud, end, time2);

		int num = 0;
		if (!rt.getAtNight().equals("未打卡") && Time.valueOf(rt.getAtNight()).before(Time.valueOf(time)) //
				&& Time.valueOf(rt.getAtNight()).after(Time.valueOf("14:00:00"))) {
			int hours = hoursCale(rt.getAtNight(), rt.getWorkMorn());
			num = ((int) Math.abs(hours) - 2);

			// 处理加班超过00：00：00点的全天时间
		} else if (!rt.getAtNight().equals("未打卡") && (Time.valueOf(rt.getAtNight()).after(Time.valueOf(time))
				|| (Time.valueOf(rt.getAtNight()).after(Time.valueOf("00:00:00"))
						&& Time.valueOf(rt.getAtNight()).before(Time.valueOf("05:00:00"))))) {

			long hours1 = hoursCale(rt.getWorkMorn(), time2);
			num = ((int) Math.abs(hours1) - 2);
		} else if (rt.getAtNight().equals("未打卡") && !rt.getWorkAfter().equals("未打卡")) { // 下午上班卡已打，但未打下班卡
			int hours = hoursCale(time2, rt.getWorkMorn());
			num = ((int) Math.abs(hours) - 2);
		} else if (rt.getAtNight().equals("未打卡") && rt.getWorkAfter().equals("未打卡")) { // 下午未上班
			if (!rt.getAtNoon().equals("未打卡")) { // 中午下班已打卡

				int hours = hoursCale(rt.getAtNoon(), rt.getWorkMorn());
				num = ((int) Math.abs(hours));
			}
		}
		return num - n;

	}

	/*
	 * 早上上班未打卡，计算全天正常上班时长
	 */
	private int calcAfterTime(RecordTable rt, String time) {
		int num = 0;
		if (!rt.getAtNoon().equals("未打卡")) {
			// if(!rt.getAtNight().equals("未打卡")){ //晚上下班打卡
			num = (hoursCale(time, "10:00:00") - 2);
		} else { // 早上没有上班
			// if(!rt.getAtNight().equals("未打卡")){
			if (!rt.getWorkAfter().equals("未打卡")) {
				num = (hoursCale(time, rt.getWorkAfter()));
			}
			// }
		}
		return num;
	}
}
