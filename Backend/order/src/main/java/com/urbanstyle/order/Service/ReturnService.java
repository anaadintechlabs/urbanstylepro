package com.urbanstyle.order.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ReturnUiDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ReturnManagement;

@Service
public interface ReturnService {

	List<ReturnUiListDTO> getReturnByUser(long parseLong, Filter filter);

	void setReturnStatusbyAdmin(long returnId, String status, String sellable, String fault);

	List<ReturnUiListDTO> getReturnByVendor(long parseLong, Filter filter, String type);

	List<ReturnUiListDTO> getReturnForSuperAdmin(Filter filter);

	ReturnUiDTO getAllDetailOfReturn(long returnId);

	long getCountForSuperAdmin(Filter filter);

	long getReturnCountByUser(long parseLong, Filter filter);

	long getReturnCountByVendor(long parseLong, Filter filter, String type);

	void setTrackingCodeAndUrlForAdmin(long returnId, String trackingId, String trackingUrl);



}
