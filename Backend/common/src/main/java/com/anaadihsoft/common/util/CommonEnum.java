package com.anaadihsoft.common.util;

public class CommonEnum {

	public interface CommonStatus{
		
		public String getStatusName();
		
		public int getStatusId();
		
		public static String findByStatusValue(int value) {
			return null;
		}
	}
	
	
	
	public enum StatusEnum implements CommonStatus{
		
		ACTIVE(1,"Active") , INACTIVE(0,"Inactive") , DELETED(2,"Deleted");
		
		private int value;
		private final String status;
		StatusEnum(int statusId, String status) {
			this.value = statusId;
			this.status = status;
		}

		@Override
		public int getStatusId() {
			return value;
		}

		@Override
		public String getStatusName() {
			return status;
		}

		public static String findStatusByValue(int value) {
			for (StatusEnum se : values()) {
				if (se.getStatusId() == value) {
					return se.getStatusName();
				}
			}
			return null;
		}
	}
}
