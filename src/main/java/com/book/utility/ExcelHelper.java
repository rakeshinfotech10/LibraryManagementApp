package com.book.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.book.domain.Book;
import com.book.domain.Tags;
import com.book.exceptions.BookUploadException;


/**
 * This class is used as a helper class to import XLS file with Books Data
 * @author Rakesh
 *
 */
@Component
public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public List<Book> excelToBooks(InputStream is) throws BookUploadException {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			List books = new ArrayList<Book>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Book bookpojo = new Book();
				Row currentRow = rows.next();

				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();


				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						bookpojo.setIsbn((int) currentCell.getNumericCellValue());
						break;

					case 1:
						bookpojo.setTitle(currentCell.getStringCellValue());
						break;

					case 2:
						bookpojo.setAuthor(currentCell.getStringCellValue());
						break;

					case 3:
						List<Tags> tags= new ArrayList<Tags>();
						Tags tags1 = new Tags();
						tags1.setTags(currentCell.getStringCellValue());
						tags.add(tags1);
						bookpojo.setTags(tags);
						break;

					default:
						break;
					}

					cellIdx++;
				}

				books.add(bookpojo);
			}

			workbook.close();

			return books;
		} catch (IOException e) {
			throw new BookUploadException("Fail to parse Excel file!!! ");
		}
	}

}

