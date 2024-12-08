package com.example.toeicwebsite.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationDTO {
	private List<?> contents;
	private boolean isFirst;
	private boolean isLast;
	private long totalPages;
	private long totalItems;
	private int pageNumber;
	private int pageSize;
}
