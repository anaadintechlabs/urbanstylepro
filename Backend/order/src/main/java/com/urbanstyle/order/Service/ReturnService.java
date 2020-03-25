package com.urbanstyle.order.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ReturnManagement;

@Service
public interface ReturnService {

	List<ReturnManagement> getReturnByUser(long parseLong, Filter filter);

	void setReturnStatusbyAdmin(long returnId, String status);

	List<ReturnManagement> getReturnByVendor(long parseLong, Filter filter);

	List<ReturnManagement> getReturnForSuperAdmin(Filter filter);

	ReturnManagement getAllDetailOfReturn(long returnId);


}
