package com.webscrap.service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import com.webscrap.dto.WebscrapingDto;
import com.webscrap.dto.WebscrapingInputDto;
import com.webscrap.dto.WebscrapingListDto;

@Service
public class ScrapServiceImpl implements ScrapService{
	
	@Override
	public WebscrapingDto selectWebListPost(WebscrapingInputDto WebscrapingInput) {
		ChromeDriver driver = null;
		WebscrapingDto WebscrapingData = new WebscrapingDto();
		List<WebscrapingListDto> WebscrapingListDataAll = new ArrayList<WebscrapingListDto>();
		try {

			// 서비시 시작 시간
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
			long reqTime = System.currentTimeMillis();
			String current_start = dayTime.format(new Date(reqTime));
			// post input
			System.out.println("패스워드  : " + WebscrapingInput.getPasswd());
			System.out.println("기간 From  : " + WebscrapingInput.getPeroidFrom());
			System.out.println("기간 To  : " + WebscrapingInput.getPeroidTo());

			String period_from = WebscrapingInput.getPeroidFrom();
			String period_to = WebscrapingInput.getPeroidTo();
			String passwd = WebscrapingInput.getPasswd();

			// web scraping area

			// WebDriver 경로 설정
			System.setProperty("webdriver.chrome.driver", "D:\\hanu_work\\RPA\\selenium\\chromedriver_win32_85\\chromedriver.exe");

			// WebDriver 옵션 설정
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized"); // 전체화면으로 실행
			options.addArguments("--disable-popup-blocking"); // 팝업 무시
			options.addArguments("--disable-default-apps"); // 기본앱 사용안함
			options.addArguments("headless"); // 브라우저 오픈하지 않음

			// WebDriver 객체 생성
			// ChromeDriver driver = new ChromeDriver( options );
			driver = new ChromeDriver(options);
			// 국세청 홈텍스 URL Open
			driver.get("https://www.hometax.go.kr/websquare/websquare.html?w2xPath=/ui/pp/index.xml");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// 5초간 중지시킨다.(단위 : 밀리세컨드)
			Thread.sleep(5000);

			// 로그인
			WebElement element = driver.findElement(By.cssSelector("#group88615548"));
			element.click();
			System.out.println("Clicked on" + element);
			// 5초간 중지시킨다.(단위 : 밀리세컨드)
			Thread.sleep(5000);

			// 프레임 이동
			driver.switchTo().frame("txppIframe");
			// 공인인증서 로그인 선택
			WebElement element1 = driver.findElement(By.cssSelector("#trigger38"));
			element1.click();
			System.out.println("Clicked on" + element1);
			// 10초간 중지시킨다.(단위 : 밀리세컨드)
			Thread.sleep(10000);

			// 공인인증서 - 프레임 이동
			driver.switchTo().frame("dscert");
			// 공인인증서 - 하드디스크 이동식 선택
			//WebElement element2 = driver.findElement(By.xpath("/html[1]/body[1]/div[5]/div[2]/div[1]/div[1]/div[4]/div[1]/form[1]/div[1]/div[1]/ul[1]/li[2]/a[1]"));
			WebElement element2 = driver.findElement(By.cssSelector("#stg_hdd"));
			element2.click();
			System.out.println("Clicked on" + element2);
			// 공인인증서 - 로컬 디스크 (C) 선택
			Thread.sleep(2000);
			WebElement element3 = driver.findElement(By.xpath("//a[text()='로컬 디스크 (C)']"));
			element3.click();
			System.out.println("Clicked on" + element3);
			// 공인인증서 - 인증서비밀번호 입력
			WebElement element4 = driver.findElement(By.id("input_cert_pw"));
			// element4.sendKeys("alghkoo3**");
			// password input 설정
			element4.sendKeys(passwd);

			// 공인 인증서 - 확인버튼 선택 , 로그인 완료
			WebElement element5 = driver.findElement(By.id("btn_confirm_iframe"));
			element5.click();
			System.out.println("Clicked on" + element5);

			// 조회 메뉴 선택
			Thread.sleep(5000);
			// WebElement element6 =
			// driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[1]/div[2]/div/ul/li[1]/a"));
			WebElement element6 = driver.findElement(By.id("group1300"));
			element6.click();
			System.out.println("Clicked on" + element6);
			// 현금영수증
			Thread.sleep(2000);
			driver.switchTo().frame("txppIframe");
			WebElement element7 = driver.findElement(By.id("sub_a_0105010000"));
			element7.click();
			System.out.println("Clicked on" + element7);
			// 현금영수증 - 사용내역 소득공제 조회
			WebElement element8 = driver.findElement(By.id("sub_a_0105010100"));
			element8.click();
			System.out.println("Clicked on" + element8);

			Thread.sleep(2000);
			// 월별 hanu
			// 현금영수증 - 사용내역 소득공제 조회 : 월별선택
			// WebElement element9 = driver.findElement(By.id("radio1_input_2"));
			// element9.click();
			// System.out.println("Clicked on"+ element9);
			// Thread.sleep(1000);

			// 현금영수증 - 사용내역 소득공제 조회 : 월별선택 select option - 8월
			// Select dropdown = new Select(driver.findElement(By.id("selectbox5")));
			// dropdown.selectByVisibleText("2020년 08월");
			// 월별 hanu

			// 일별 start
			// 현금영수증 - 사용내역 소득공제 조회 : 일별선택
			WebElement element9 = driver.findElement(By.id("radio1_input_0"));
			element9.click();
			System.out.println("Clicked on" + element9);
			Thread.sleep(1000);
			// 현금영수증 - 사용내역 소득공제 조회 : 일자 입력 - 20200818
			WebElement element9_1 = driver.findElement(By.id("inputCalendar18_input"));
			element9_1.click();
			element9_1.clear();
			// element9_1.sendKeys("20200818");
			element9_1.sendKeys(period_from);
			// System.out.println("기간 From : "+ WebscrapingInput.getPeroidFrom());
			// System.out.println("기간 To : "+ WebscrapingInput.getPeroidTo());
			System.out.println("element9_1 on " + element9_1);
			Thread.sleep(1000);

			// 일별 end

			// 현금영수증 - 사용내역 소득공제 조회 : 조회
			WebElement element10 = driver.findElement(By.id("group1988"));
			element10.click();
			System.out.println("Clicked on" + element10);

			// Table Header
			// WebElement mytable =
			// driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[4]/div/div[2]/div/table"));
			WebElement mytable = driver
					.findElement(By.xpath("/html/body/div[1]/div[3]/div[4]/div/div[2]/div/table/thead[2]"));
			// /html/body/div[1]/div[3]/div[4]/div/div[2]/div/table
			System.out.println("mytable on" + mytable);

			// 테이블 행을 찾습니다.
			List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			// 테이블의 행 수를 계산합니다.
			int rows_count = rows_table.size();
			System.out.println("rows_count " + rows_count);

			String[] head_array = new String[8];
			// 한개 항목
			// WebElement myvalue =
			// driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[4]/div/div[2]/div/table/tbody/tr/td[4]"));
			// System.out.println("가맹점명 : "+ myvalue.getText());
			//
			// 루프는 table의 마지막 행까지 실행됩니다.
			// for (int row = 0; row < rows_count; row++) {
			for (int row = 0; row < rows_count; row++) {
				// 특정 행의 열 (셀)을 찾습니다.
				List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("th"));
				// 그 특정한 행에 열 (셀)을 계산합니다
				int columns_count = Columns_row.size();
				System.out.println("Number of cells In Row " + row + " are " + columns_count);
				// 루프는 특정 행의 마지막 셀까지 실행됩니다.
				for (int column = 0; column < columns_count; column++) {
					// 특정 셀에서 텍스트를 검색합니다.
					String celtext = Columns_row.get(column).getText();
					System.out.println(
							"Cell Value of row number " + row + " and column number " + column + " Is " + celtext);
					if (column > 1 && column < 10) {
						head_array[column - 2] = celtext; // 결과 array 저장 1차원
					}
				}
				System.out.println("-------------------------------------------------- ");
			}

			// Table Body
			// WebElement mytable =
			// driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[4]/div/div[2]/div/table"));
			WebElement mytable_body = driver.findElement(By.id("grdCshpt_body_tbody"));
			// /html/body/div[1]/div[3]/div[4]/div/div[2]/div/table
			System.out.println("mytable_body on" + mytable_body);

			// 테이블 행을 찾습니다.
			List<WebElement> rows_table_body = mytable_body.findElements(By.tagName("tr"));
			// 테이블의 행 수를 계산합니다.
			int rows_count_body = rows_table_body.size();
			System.out.println("rows_count_body " + rows_count_body);
			//

			// 루프는 table의 마지막 행까지 실행됩니다.
			// for (int row = 0; row < rows_count; row++) {
			for (int row = 0; row < rows_count_body; row++) {
				// 특정 행의 열 (셀)을 찾습니다.
				List<WebElement> Columns_row_body = rows_table_body.get(row).findElements(By.tagName("td"));
				// 그 특정한 행에 열 (셀)을 계산합니다
				int columns_count_body = Columns_row_body.size();
				System.out.println("Number of cells In Row " + row + " are " + columns_count_body);
				// 루프는 특정 행의 마지막 셀까지 실행됩니다.
				// 결과 array 저장 N차원: 선언
				// String [ ][ ] results_array = new String [rows_count_body][8];
				// 결과 array 저장 1차원: 선언
				String[] results_array = new String[8];

				for (int column = 0; column < columns_count_body; column++) {
					// 특정 셀에서 텍스트를 검색합니다.
					String celtext_body = Columns_row_body.get(column).getText();
					System.out.println(
							"Cell Value of row number " + row + " and column number " + column + " Is " + celtext_body);
					if (column > 1 && column < 10) {
						// results_array[row][column-2] = celtext_body; //결과 array 저장 N차원
						results_array[column - 2] = celtext_body; // 결과 array 저장 1차원
					}
				}
				System.out.println("-------------------------------------------------- ");
				System.out.println("[ results_array ] ");
				// WebscrapingListData 선언
				WebscrapingListDto WebscrapingListData = new WebscrapingListDto();

				for (int i = 0; i < results_array.length; i++) {
					System.out.println("row number : " + row + " results_array : " + results_array[i]);
					if (i == 0) {
						WebscrapingListData.setTrading_Date(results_array[i]);
					} else if (i == 1) {
						WebscrapingListData.setStore_Name(results_array[i]);
					} else if (i == 2) {
						WebscrapingListData.setAmount_Used(results_array[i]);
					} else if (i == 3) {
						WebscrapingListData.setApproval_Number(results_array[i]);
					} else if (i == 4) {
						WebscrapingListData.setIssued_Method(results_array[i]);
					} else if (i == 5) {
						WebscrapingListData.setTrading_Method(results_array[i]);
					} else if (i == 6) {
						WebscrapingListData.setDeduction_Status(results_array[i]);
					} else if (i == 7) {
						WebscrapingListData.setIssued_classifi(results_array[i]);
					}
				}

				// WebscrapingListDataAll list에 넣기
				WebscrapingListDataAll.add(WebscrapingListData);
			}

			// WebscrapingData 설정
			WebscrapingData.setErrMsg("정상");
			WebscrapingData.setErrYn("N");
			WebscrapingData.setList(WebscrapingListDataAll);
			// Webscraping 설정
			// Webscraping.add(WebscrapingData);
			// return scrapMapper.selectWebList();
			long resTime = System.currentTimeMillis();
			String current_end = dayTime.format(new Date(resTime));
			double elapsed_time = (resTime - reqTime) / 1000.000;

			// 서비스 시작/종료시간 출력
			System.out.println("start_time : " + current_start);
			System.out.println("end_time   : " + current_end);
			System.out.println("end_time - start_time : " + elapsed_time);

			// driver close and quit
			driver.close();
			// 2초 후에 WebDriver 종료
			Thread.sleep(2000);
			// WebDriver 종료
			driver.quit();

		} catch (Exception e) {
			driver.close();
			driver.quit();
			WebscrapingData.setErrMsg(e.getMessage());
			WebscrapingData.setErrYn("Y");
			// System.out.println("Exception WebscrapingData : " +
			// WebscrapingData.toString());
			// System.out.println("Exception : " + e.getMessage());
		}
		return WebscrapingData;
	}
}
