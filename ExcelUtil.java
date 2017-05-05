package com.spring.excel;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.stereotype.Component;

import com.spring.model.Patch;
import com.spring.model.Release;

@Component
public class ExcelUtil {
	public void importExcel(String[] exportedYears) {
		SessionFactory sessionFactory = new SessionFactory("com.spring.model");
		Session session = sessionFactory.openSession();
		
		Collection<Patch> patches = session.loadAll(Patch.class);
		
		
		Collection<Release> releases = session.loadAll(Release.class);
		
		List<MergeRow> mergedRows = new ArrayList<>();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		int r=0;
		Row row = sheet.createRow(r++);
		
		int col = 0;
		Cell cell = row.createCell(col++);
		
		for (Release release : releases) {
			cell = row.createCell(col++);
			cell.setCellValue(release.getName());
		}
		int i=0;
		
		while(true) {
			col=0;
			
			row = sheet.createRow(r++);
			int year = Integer.parseInt(exportedYears[i]);
			cell = row.createCell(col++);
			cell.setCellValue(year);
			
			for (int week = 1; week < 52; week++) {
				col=0;
				row = sheet.createRow(r++);
				cell = row.createCell(col++);
				
				cell.setCellValue("Wk " + week);
				for (Release release : releases) {
					cell = row.createCell(col++);
					for (Patch patch : patches) {
						if (patch.getRelease()!=null) {
							
						
							if (patch.getRelease().getId()==release.getId() && 
									((patch.getCutOfWeek()==week && 
									patch.getCutOfYear()==year) || 
											((patch.getSanityStartWeek() <= week && 
											patch.getSanityEndWeek() >= week && 
											patch.getSanityEndYear()==year && 
											patch.getSanityStartYear()==year) || 
													(patch.getSanityEndYear() > patch.getSanityStartYear() && 
															((patch.getSanityStartWeek()<=week && 
															patch.getSanityStartYear()==year) || 
																	(patch.getSanityEndWeek()>=week && 
																	patch.getSanityEndYear()==year)))))) {
								cell.setCellValue(patch.getName());
								
								HSSFPalette palette = workbook.getCustomPalette();
								HSSFColor myColor = palette.findSimilarColor(
										Integer.valueOf(patch.getColor().substring( 1, 3 ), 16),
										Integer.valueOf(patch.getColor().substring( 3, 5 ), 16),
										Integer.valueOf(patch.getColor().substring( 5, 7 ), 16));
								
								// get the paletteindex of that color
								
								CellStyle style = workbook.createCellStyle();
								
								style.setFillBackgroundColor(myColor.getIndex());
								//style.setFillPattern(CellStyle.so);
								cell.setCellStyle(style);
								if (patch.getSanityEndWeek()>patch.getSanityStartWeek()) {
									boolean exists=false;
									
									for (MergeRow mr : mergedRows) {
										if (mr.patchId==patch.getId()) {
											exists=true;
										}
										
									}
									if (!exists) {
										MergeRow mergeRow = new MergeRow();
										mergeRow.patchId=patch.getId();
										mergeRow.rowStart=r-1;
										mergeRow.rowEnd=r+(patch.getSanityEndWeek()-patch.getSanityStartWeek())-1;
										mergeRow.colStart=col-1;
										mergeRow.colEnd=col-1;
										
										mergedRows.add(mergeRow);
									}
								}
							}
						}
					}
				}
			}
			if (i==exportedYears.length-1) {
				break;
			}
			i++;
		}
		sheet.setColumnWidth(0, 2500);
		for (int j = 1; j < col; j++) {
			sheet.setColumnWidth(j, 10000);
		}
		for (MergeRow mr : mergedRows) {
			sheet.addMergedRegion(new CellRangeAddress(mr.rowStart, mr.rowEnd, mr.colStart, mr.colEnd));
		}
		try {
			FileOutputStream out = 
					new FileOutputStream(new File("C:\\new.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		
		ExcelUtil e = new ExcelUtil();
		//e.importExcel();
	}
	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	public class MergeRow {
		Long patchId;
		int rowStart;
		int rowEnd;
		int colStart;
		int colEnd;
	}
}
