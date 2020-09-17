package com.webscrap.dto;

import java.util.List;

import lombok.Data;


@Data
public class WebscrapingDto {
	
	private String errMsg;
	
	private String errYn;
	
	private List<WebscrapingListDto> list;
	
}
